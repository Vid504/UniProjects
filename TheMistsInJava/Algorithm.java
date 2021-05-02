
// Algorithm Class houses methods with complex logic. 
// Includes Shuffler, Sort, Duplicate find/removal, BFS, Graph cycle detection
import java.util.ArrayList;
import java.util.Random;

public class Algorithm {

    // Fisher-Yates shuffle, specifically for list of list of booleans
    public static ArrayList<ArrayList<Boolean>> shuffleFisherYates(ArrayList<ArrayList<Boolean>> list) {
        for (int i = 0; i < list.size() - 2; i++) {
            Random rand = new Random();
            int j = (int) Math.floor((list.size() - i) * rand.nextDouble()) + i;
            ArrayList<Boolean> element1 = list.get(i);
            ArrayList<Boolean> element2 = list.get(j);
            list.set(i, element2);
            list.set(j, element1);
        }
        return list;
    }

    // Sort highest to lowest
    public static void bubbleSort(ArrayList<Integer> arr) {
        boolean sortComplete = true;
        do {
            sortComplete = true;
            for (int i = 0; i < arr.size() - 1; i++) {
                if (arr.get(i) < arr.get(i + 1)) {
                    int temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                    sortComplete = false;
                }
            }
        } while (sortComplete == false);
    }

    // Create a new list that has only 1 copy of any exact element from initial list
    public static ArrayList<ArrayList<Room>> removeDuplicates(ArrayList<ArrayList<Room>> list) {
        ArrayList<ArrayList<Room>> refinedList = new ArrayList<ArrayList<Room>>();
        refinedList.add(list.get(0));
        for (int i = 0; i < list.size(); i++) {
            if (!isDuplicate(refinedList, list.get(i))) {
                refinedList.add(list.get(i));
            }
        }
        return refinedList;
    }

    // Determine whether an element already exists in a list
    private static boolean isDuplicate(ArrayList<ArrayList<Room>> list, ArrayList<Room> poll) {
        boolean duplicate = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).size() == poll.size()) {
                boolean allsame = true;
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (list.get(i).get(j) != poll.get(j)) {
                        allsame = false;
                    }
                }
                if (allsame) {
                    duplicate = true;
                    // System.out.printf("duplicate detected\n");
                }
            }
        }
        return duplicate;
    }

    // Graph Traversal: discover all paths from start to target. Limit by distance.
    // Paths cannot have repeat nodes: must detect and exclude cycles.
    public static ArrayList<ArrayList<Room>> getPathsBFS(ArrayList<Room> roomsList, Room start, Room target) {
        final int MAX_DISTANCE = 7;
        ArrayList<ArrayList<Integer>> pathList = new ArrayList<ArrayList<Integer>>();

        // Declare and initialize all lists req. by BFS, uses Room Object ID
        ArrayList<ArrayList<Integer>> adjList = getAdjList(roomsList);
        ArrayList<ArrayList<Integer>> queue = new ArrayList<ArrayList<Integer>>();
        queue.add(new ArrayList<Integer>());
        queue.get(0).add(start.getID());
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> markedForRemoval = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                ArrayList<Integer> path = queue.get(i);
                int lastNodeinPath = path.get(path.size() - 1);
                if (lastNodeinPath == target.getID()) {
                    pathList.add(path);
                } else if (isCycle(path) || path.size() > MAX_DISTANCE) {
                } else {
                    ArrayList<Integer> neighbors = adjList.get(lastNodeinPath);
                    for (int neighbor : neighbors) {
                        ArrayList<Integer> list = new ArrayList<Integer>(path);
                        list.add(neighbor);
                        queue.add(list);
                    }
                }
                markedForRemoval.add(i);
            }
            removeMarked(queue, markedForRemoval);
        }
        ArrayList<ArrayList<Room>> pathListRooms = indicesToObjects(roomsList, pathList);
        return pathListRooms;
    }

    // Generate adjacency list - uses Room Class instance methods/variables
    public static ArrayList<ArrayList<Integer>> getAdjList(ArrayList<Room> roomsList) {
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
        int index = 0;
        for (Room room : roomsList) {
            adjList.add(new ArrayList<Integer>());
            if (room.getNorth() != null) {
                adjList.get(index).add(room.getNorth().getID());
            }
            if (room.getSouth() != null) {
                adjList.get(index).add(room.getSouth().getID());
            }
            if (room.getEast() != null) {
                adjList.get(index).add(room.getEast().getID());
            }
            if (room.getWest() != null) {
                adjList.get(index).add(room.getWest().getID());
            }
            index++;
        }
        return adjList;
    }

    // Find Cycles - path is cyclic if last node in path exists twice in list
    private static boolean isCycle(ArrayList<Integer> path) {
        boolean cyclic = false;
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(path.size() - 1) == path.get(i) && path.size() != 1) {
                cyclic = true;
            }
        }
        return cyclic;
    }

    // Remove marked indices from queue, highest indices removed first
    public static void removeMarked(ArrayList<ArrayList<Integer>> queue, ArrayList<Integer> marked) {
        bubbleSort(marked);
        for (int i = 0; i < queue.size(); i++) {
            for (Integer mark : marked) {
                if (i == mark) {
                    queue.remove(i);
                }
            }
        }
    }

    // Convert Rooom IDs back to Room objects
    private static ArrayList<ArrayList<Room>> indicesToObjects(ArrayList<Room> roomsList,
            ArrayList<ArrayList<Integer>> pathList) {
        ArrayList<ArrayList<Room>> pathListRooms = new ArrayList<ArrayList<Room>>();
        for (int i = 0; i < pathList.size(); i++) {
            pathListRooms.add(new ArrayList<Room>());
            for (int j = 0; j < pathList.get(i).size(); j++) {
                pathListRooms.get(i).add(roomsList.get(pathList.get(i).get(j)));
            }
        }
        return pathListRooms;
    }

}