
// Dungeon Class
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Dungeon {
	private Room roomA, roomB, roomC, roomD, roomE, roomF, roomG, roomH, roomI, roomJ, roomK, roomL, roomM, roomN,
			roomO, roomP, roomQ, roomR, roomS, roomT;
	private ArrayList<Room> path;

	// Constructor
	public Dungeon() {
		this.roomA = new Room("Leo", "The Lion");
		this.roomB = new Room("Virgo", "The Virgin");
		this.roomC = new Room("Phoenix", "The Ashes Reborn");
		this.roomD = new Room("Gemini", "The Twins");
		this.roomE = new Room("Pisces", "The Fishes");
		this.roomF = new Room("Cancer", "The Crab");
		this.roomG = new Room("Aquarius", "The Waterbearer");
		this.roomH = new Room("Sagittarius", "The Archer");
		this.roomI = new Room("Cygnus", "The Swan");
		this.roomJ = new Room("Andromeda", "The Chained Maiden");
		this.roomK = new Room("Scutum", "The Shield");
		this.roomL = new Room("Draco", "The Dragon");
		this.roomM = new Room("Sextans", "The Sextant");
		this.roomN = new Room("Libra", "The Balance");
		this.roomO = new Room("Taurus", "The Bull");
		this.roomP = new Room("Aries", "The Ram");
		this.roomQ = new Room("Scorpius", "The Scorpion");
		this.roomR = new Room("Cassiopeia", "The Queen");
		this.roomS = new Room("Orion", "The Hunter");
		this.roomT = new Room("Capriconicus", "The Sea Goat");
		setupRooms();
		setPath();
	}

	// Set all exits for every Room in Dungeon
	private void setupRooms() {
		this.roomA.setExits(null, this.roomB, this.roomE, this.roomF);
		this.roomB.setExits(this.roomA, this.roomG, this.roomC, null);
		this.roomC.setExits(null, null, this.roomD, this.roomB);
		this.roomD.setExits(this.roomE, null, this.roomJ, this.roomC);
		this.roomE.setExits(this.roomH, this.roomD, this.roomK, this.roomA);
		this.roomF.setExits(this.roomA, this.roomP, this.roomG, this.roomM);
		this.roomG.setExits(this.roomB, this.roomL, null, this.roomF);
		this.roomH.setExits(null, this.roomE, this.roomI, null);
		this.roomI.setExits(this.roomH, this.roomO, this.roomJ, this.roomN);
		this.roomJ.setExits(this.roomK, this.roomI, null, this.roomD);
		this.roomK.setExits(this.roomO, this.roomJ, null, this.roomE);
		this.roomL.setExits(this.roomG, this.roomQ, this.roomR, this.roomM);
		this.roomM.setExits(this.roomF, this.roomP, this.roomL, null);
		this.roomN.setExits(this.roomI, this.roomS, this.roomO, null);
		this.roomO.setExits(this.roomI, this.roomT, this.roomK, this.roomN);
		this.roomP.setExits(this.roomF, null, this.roomQ, this.roomM);
		this.roomQ.setExits(this.roomL, null, this.roomR, this.roomP);
		this.roomR.setExits(this.roomL, null, this.roomS, this.roomQ);
		this.roomS.setExits(this.roomN, null, this.roomT, this.roomR);
		this.roomT.setExits(this.roomO, null, null, this.roomS);
	}

	// Determine the path, uses Algorithm Class
	private void setPath() {
		ArrayList<Room> list = new ArrayList<Room>(Arrays.asList(this.roomA, this.roomB, this.roomC, this.roomD,
				this.roomE, this.roomF, this.roomG, this.roomH, this.roomI, this.roomJ, this.roomK, this.roomL,
				this.roomM, this.roomN, this.roomO, this.roomP, this.roomQ, this.roomR, this.roomS, this.roomT));
		ArrayList<ArrayList<Room>> pathList = Algorithm.getPathsBFS(list, this.getStartingRoom(), this.getTargetRoom());
		pathList = Algorithm.removeDuplicates(pathList);
		Random rand = new Random();
		this.path = pathList.get(rand.nextInt(pathList.size()));
	}

	// Getters
	public ArrayList<Room> getPath() {
		return this.path;
	}

	public Room getStartingRoom() {
		return this.roomR;
	}

	public Room getTargetRoom() {
		return this.roomC;
	}
}