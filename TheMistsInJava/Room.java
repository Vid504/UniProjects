
// Room Class
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private static int count = 0;
    private Boolean solved;
    private int ID;
    private String name, description;
    private Room north, south, east, west, next, previous;
    private Stone northStone, southStone, eastStone, westStone;

    // Constructors
    public Room(String name, String description, Room north, Room south, Room east, Room west) {
        this.name = name;
        this.description = description;
        this.ID = count;
        this.solved = false;
        count++;
        setExits(north, south, east, west);
    }

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.ID = count;
        this.solved = false;
        count++;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getExits() {
        String n = "", s = "", e = "", w = "";
        if (this.getNorth() != null) {
            n = String.format("[N]orth: %s. %s\n", this.getNorth().getName(), printMist(this.getNorth()));
        }
        if (this.getSouth() != null) {
            s = String.format("[S]outh: %s. %s\n", this.getSouth().getName(), printMist(this.getSouth()));
        }
        if (this.getEast() != null) {
            e = String.format("[E]ast: %s. %s\n", this.getEast().getName(), printMist(this.getEast()));
        }
        if (this.getWest() != null) {
            w = String.format("[W]est: %s. %s\n", this.getWest().getName(), printMist(this.getWest()));
        }

        return String.format("%s%s%s%s", n, s, e, w);
    }

    public Room getNorth() {
        return this.north;
    }

    public Room getSouth() {
        return this.south;
    }

    public Room getEast() {
        return this.east;
    }

    public Room getWest() {
        return this.west;
    }

    public Room getNext() {
        return this.next;
    }

    public Room getPrevious() {
        return this.previous;
    }

    public int getID() {
        return this.ID;
    }

    public boolean getSolved() {
        return this.solved;
    }

    public String getStones() {
        String directions = "North\tSouth\tEast\tWest\t";
        String flowerLeaf = this.northStone.getFlowerLeaf() + "\t" + this.southStone.getFlowerLeaf() + "\t"
                + this.eastStone.getFlowerLeaf() + "\t" + this.westStone.getFlowerLeaf();
        String greenPurple = this.northStone.getGreenPurple() + "\t" + this.southStone.getGreenPurple() + "\t"
                + this.eastStone.getGreenPurple() + "\t" + this.westStone.getGreenPurple();
        String mossVines = this.northStone.getMossVines() + "\t" + this.southStone.getMossVines() + "\t"
                + this.eastStone.getMossVines() + "\t" + this.westStone.getMossVines();
        String hints = String.format("***Stone sigils reveal the path.***\n\n%s\n%s\n%s\n%s\n", directions, flowerLeaf,
                greenPurple, mossVines);
        return hints;
    }

    // Setters
    public void setExits(Room north, Room south, Room east, Room west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public void setNext(Room next) {
        this.next = next;
    }

    public void setPrevious(Room previous) {
        this.previous = previous;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public void setStones() {
        ArrayList<ArrayList<Boolean>> truthTable = getTruthTable();
        ArrayList<Boolean> solution = getSolution(truthTable);
        ArrayList<ArrayList<Boolean>> hints = getValidHints(truthTable, solution);
        assignStones(solution, hints);
    }

    // Stone/TruthTable logic - 4 stones, 1 stone is odd-one-out
    private ArrayList<ArrayList<Boolean>> getTruthTable() {
        // Truth table has 8 permutations of 3 binary values;
        boolean[] p = { false, true };
        boolean[] q = { false, true };
        boolean[] r = { false, true };
        ArrayList<ArrayList<Boolean>> list = new ArrayList<ArrayList<Boolean>>();
        for (boolean pp : p) {
            for (boolean pq : q) {
                for (boolean pr : r) {
                    ArrayList<Boolean> combination = new ArrayList<Boolean>();
                    combination.add(pp);
                    combination.add(pq);
                    combination.add(pr);
                    list.add(combination);
                }
            }
        }
        return list;
    }

    private ArrayList<Boolean> getSolution(ArrayList<ArrayList<Boolean>> list) {
        // The solution is chosen randomly from the truth table
        Random rand = new Random();
        int ID = rand.nextInt(list.size());
        return list.get(ID);
    }

    private ArrayList<ArrayList<Boolean>> getValidHints(ArrayList<ArrayList<Boolean>> list,
            ArrayList<Boolean> solution) {
        // 3 hints chosen from truth table; solution is the ONLY odd-one-out
        Random rand = new Random();
        int oddElement = rand.nextInt(solution.size());
        ArrayList<ArrayList<Boolean>> hints = new ArrayList<ArrayList<Boolean>>();
        ArrayList<Integer> negCounts = new ArrayList<Integer>();
        for (ArrayList<Boolean> combination : list) {
            int combinationIndex = 0;
            int negCount = 0;
            for (Boolean element : combination) {
                if (solution.get(combinationIndex) != element) {
                    negCount++;
                }
                combinationIndex++;
            }
            negCounts.add(negCount);
        }
        for (int i = 0; i < list.size(); i++) {
            if (negCounts.get(i) == 3) {
                hints.add(list.get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (negCounts.get(i) == 2 && list.get(i).get(oddElement) == !solution.get(oddElement)) {
                hints.add(list.get(i));
            }
        }
        return hints;
    }

    private void assignStones(ArrayList<Boolean> solution, ArrayList<ArrayList<Boolean>> hints) {
        // Assign solution to next inpath path.
        // Assign the remaining hints randomly the the remaining directions.
        hints = Algorithm.shuffleFisherYates(hints);
        if (this.next == this.north) {
            this.northStone = new Stone(solution);
            this.southStone = new Stone(hints.remove(0));
            this.eastStone = new Stone(hints.remove(0));
            this.westStone = new Stone(hints.remove(0));
        } else if (this.next == this.south) {
            this.northStone = new Stone(hints.remove(0));
            this.southStone = new Stone(solution);
            this.eastStone = new Stone(hints.remove(0));
            this.westStone = new Stone(hints.remove(0));
        } else if (this.next == this.east) {
            this.northStone = new Stone(hints.remove(0));
            this.southStone = new Stone(hints.remove(0));
            this.eastStone = new Stone(solution);
            this.westStone = new Stone(hints.remove(0));
        } else if (this.next == this.west) {
            this.northStone = new Stone(hints.remove(0));
            this.southStone = new Stone(hints.remove(0));
            this.eastStone = new Stone(hints.remove(0));
            this.westStone = new Stone(solution);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s]\n%s\n%s\n", this.name, this.description, getExits());
    }

    // Show player whether Room is covered with mist or cleared
    private String printMist(Room direction) {
        if (this.next == direction && this.solved) {
            return "Path leads forward in this direction.";
        } else if (this.previous == direction) {
            return "You came from this room.";
        } else
            return "This path is shrouded in mist.";
    }
}