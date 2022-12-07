import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

public class ColoringNode extends Node {
    public Node parent; // défini dans Main()

    @Override
    public void onStart() {
        setColor(Color.getColorAt(getID())); // couleur = ID
    }

    private static int posDiff(int x, int y) {
        int p = 0;
        while(x != 0 && y != 0) {
            if (x % 2 != y % 2) {
                return 2 * p + x % 2;
            } else {
                p++;
                x /= 2;
                y /= 2;
            }
        }
        return -1;
    }

    private static int log2ceil(int k) {
        double log_k = Math.log(k) / Math.log(2);
        return (int) log_k;
    }
}