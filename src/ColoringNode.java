import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

public class ColoringNode extends Node {
    public Node parent; // défini dans Main()
    private int x;
    private int l;


    @Override
    public void onStart() {
        x = getID();
        l = log2ceil(Main.n);

        setColor(Color.getColorAt(getID())); // couleur = ID

        for (Node v : getNeighbors()) {
            if (!v.equals(parent)) {
                send(v, new Message(x));
            }
        }
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

    @Override
    public void onClock() {
        Message m = getMailbox().get(0);
        int y = 0; // getContents je sais pas quoi j'ai pas compris
        x = posDiff(x, y);
        //int lp = l;
        l = 1 + log2ceil(l);
        for (Node v : getNeighbors()) {
            if (!v.equals(parent)) {
                send(v, new Message(x));
                // à remplir
            }
        }
        for (Message me : getMailbox()) {
            //à remplir
            ;
        }
    }
}