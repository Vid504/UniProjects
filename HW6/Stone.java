
// Stone Class 
import java.util.ArrayList;

public class Stone {
    String flowerLeaf;
    String greenPurple;
    String mossVines;

    // Constructor. Maps boolean to String (3 binary choices)
    public Stone(ArrayList<Boolean> hint) {
        if (hint.get(0) == true) {
            this.flowerLeaf = "Flower";
        } else
            this.flowerLeaf = "Leaf";
        if (hint.get(1) == true) {
            this.greenPurple = "Green";
        } else
            this.greenPurple = "Purple";
        if (hint.get(2) == true) {
            this.mossVines = "Moss";
        } else
            this.mossVines = "Vines";
    }

    public String getFlowerLeaf() {
        return this.flowerLeaf;
    }

    public String getGreenPurple() {
        return this.greenPurple;
    }

    public String getMossVines() {
        return this.mossVines;
    }

}
