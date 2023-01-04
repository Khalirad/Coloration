import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

public class ColoringNode extends Node {
    public Node parent; // défini dans Main()
    private int x;
    private int l;
    private int lp;
    private int state = 0;


    @Override
    public void onStart() {
        x = getID();
        l = log2ceil(Main.n);

        setColor(Color.getColorAt(getID())); // couleur = ID

        for (Node v : getNeighbors()) {
            send(v, new Message(x));
        }
    }

    private static int posDiff(int x, int y) {
        int p = 0;
        while(x != 0 || y != 0) {
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


    private int firstFree(Color c1, Color c2) {
        int x = 0;

        while (c1.equals(Color.getColorAt(x)) || c2.equals(Color.getColorAt(x))) {
            x++;
        }

        return x;
    }

    @Override
    public void onClock() {
        if (state == 1) {
            for (int i = 3; i <= 5; i++) {
                if (x == i) {
                    Color c1 = getNeighbors().get(0).getColor();
                    Color c2 = getNeighbors().get(1).getColor();
                    x = firstFree(c1, c2);

                    setColor(Color.getColorAt(x));
                }
            }
        }
        else {
            // JUSQU'A l' = l
            if (lp == l) {
                state = 1;
            }

            // b. y := RECEIVE(père(u))
            Message m0 = getMailbox().get(0);
            Message m1 = getMailbox().get(1);

            int y0 = (int) m0.getContent();
            int y1 = (int) m1.getContent();

            // c. x := PosDiff(x, y)
            x = posDiff(posDiff(x, y0), posDiff(y1, x));

            // d. l' = l et l:= 1 + logl
            lp = l;
            l = 1 + log2ceil(1 + log2ceil(l));

            // a. SEND(x, v) pour tout v dans N(u)\père(u)
            for (Node v : getNeighbors()) {
                send(v, new Message(x));
            }

            setColor(Color.getColorAt(x));
        }
    }
}