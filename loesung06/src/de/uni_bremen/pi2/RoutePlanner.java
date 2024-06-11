package de.uni_bremen.pi2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Die Klasse repräsentiert das Fenster des Routenplaners. In ihm wird die
 * Karte angezeigt. Die Klasse skaliert die gezeichnete Karte automatisch
 * so, dass sie komplett dargestellt wird. Wenn man einen Knoten anklickt,
 * wird vom zuletzt angeklickten Knoten zum neuen der kürzeste Weg
 * eingezeichnet.
 */
class RoutePlanner extends JPanel
{
    /** Hilfsklasse zum Zeichnen. */
    private static class Drawable
    {
        /** Ist dies eine Strecke oder ein Punkt? */
        final boolean isLine;

        /** Koordinaten. x2 und y2 werden für Punkte nicht genutzt. */
        final double x1, y1, x2, y2;

        /** Die Farbe, in der gezeichnet wird. */
        final Color color;

        /**
         * Erzeugt einen Punkt.
         * @param x     Die x-Koordinate des Punkts.
         * @param y     Die y-Koordinate des Punkts.
         * @param color Die Farbe, in der gezeichnet wird.
         */
        Drawable(final double x, final double y, final Color color)
        {
            isLine = false;
            x1 = x;
            y1 = y;
            x2 = y2 = 0;
            this.color = color;
        }

        /**
         * Erzeugt eine Strecke.
         * @param x1    Die x-Koordinate des einen Endpunkts.
         * @param y1    Die y-Koordinate des einen Endpunkts.
         * @param x2    Die x-Koordinate des anderen Endpunkts.
         * @param y2    Die y-Koordinate des anderen Endpunkts.
         * @param color Die Farbe, in der gezeichnet wird.
         */
        Drawable(final double x1, final double y1,
                        final double x2, final double y2, final Color color)
        {
            isLine = true;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    /** Alle Zeichenanweisungen, die während eines "paint"-Aufrufs anfallen. */
    private static final List<Drawable> drawables = new ArrayList<>();

    /** Die kleinste x-Koordinate in den Daten. Erst nach "paint" gültig. */
    private double xMin;

    /** Die kleinste y-Koordinate in den Daten. Erst nach "paint" gültig. */
    private double yMin;

    /** Die x-Skalierung für das Zeichnen der Daten. Erst nach "paint" gültig. */
    private double xScale;

    /** Die y-Skalierung für das Zeichnen der Daten. Erst nach "paint" gültig. */
    private double yScale;

    /** Die Karte. */
    private final Map map;

    /** Der Startknoten, wie er durch Mausklick gewählt wurde. */
    private Node start;

    /** Der Zielknoten, wie er durch Mausklick gewählt wurde. */
    private Node goal;

    /**
     * Der Konstruktor erzeugt das Hauptfenster und die Zeichenfläche.
     * Hier muss auch die Karte erzeugt werden.
     * @throws IOException Das Einlesen der Karte hat nicht geklappt.
     */
    private RoutePlanner() throws IOException
    {
        // Beim Lesen von Text wird der Punkt als Dezimalzeichen verwendet
        Locale.setDefault(new Locale("C"));

        map = new Map();

        final JFrame frame = new JFrame("Routenplaner"); // Fensterrahmen erzeugen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this); // Zeichenfläche hinzufügen
        setBackground(Color.white); // Hintergrund ist weiß
        setPreferredSize(new Dimension(700, 700)); // Größe der Zeichenfläche setzen
        frame.pack(); // Fenster so anpassen, dass die Zeichenfläche hineinpasst
        frame.setVisible(true); // Fenster anzeigen

        // Setzen von Start- und Zielposition per Mausklick.
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent event)
            {
                // Fensterkoordinaten in Kartenkoordinaten umrechnen
                final double x = event.getX() / xScale + xMin;
                final double y = (getSize().height - 1 - event.getY()) / yScale + yMin;

                // Das bisherige Ziel wird der neue Startpunkt.
                start = goal;

                // Der dichteste Knoten wird das neue Ziel.
                goal = map.getClosest(x, y);

                // Karte neuzeichnen.
                repaint();
            }
        });
    }

    /**
     * Zeichnen eines Punkts in die Zeichenfläche. Diese Methode kann nur während
     * der Ausführung von "paint" verwendet werden. Dafür kann sie aber von
     * überall aus aufgerufen werden.
     * @param x     x-Koordinate des Punkts.
     * @param y     y-Koordinate des Punkts.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    static void draw(final double x, final double y, final Color color)
    {
        drawables.add(new Drawable(x, y, color));
    }

    /**
     * Zeichnen einer Strecke in die Zeichenfläche. Diese Methode kann nur
     * während der Ausführung von "paint" verwendet werden. Dafür kann sie
     * aber von überall aus aufgerufen werden.
     * @param x1    x-Koordinate des Anfangspunkts.
     * @param y1    y-Koordinate des Anfangspunkts.
     * @param x2    x-Koordinate des Endpunkts.
     * @param y2    y-Koordinate des Endpunkts.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    static void draw(final double x1, final double y1,
                            final double x2, final double y2, final Color color)
    {
        drawables.add(new Drawable(x1, y1, x2, y2, color));
    }

    /**
     * Zeichnen von Karte und Weg. Start- und Zielpunkt werden gezeichnet,
     * wenn sie bekannt sind. Der Weg wird nur eingezeichnet, wenn sowohl
     * Start- als auch Zielpunkt bekannt sind.
     * @param graphics Der Grafikkontext, in den gezeichnet wird.
     */
    public void paintComponent(final Graphics graphics)
    {
        super.paintComponent(graphics);

        // Alte Zeichnung löschen
        drawables.clear();

        // Zeichnen der Karte.
        map.draw();

        // Wurden sowohl Start als auch Ziel gewählt, wird auch der kürzeste Weg bestimmt
        // und eingezeichnet.
        if (start != null && goal != null) {
            map.reset();
            shortestPath(start, goal);
        }

        // Markieren der gewählten Knoten
        if (start != null) {
            start.draw(Color.RED);
        }
        if (goal != null) {
            goal.draw(Color.RED);
        }

        // Bestimmen der Grenzen der zu zeichnenden Elemente
        xMin = Double.POSITIVE_INFINITY;
        yMin = Double.POSITIVE_INFINITY;
        double xMax = Double.NEGATIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;
        for (final Drawable drawable : drawables) {
            xMin = Math.min(xMin, drawable.x1);
            xMax = Math.max(xMax, drawable.x1);
            yMin = Math.min(yMin, drawable.y1);
            yMax = Math.max(yMax, drawable.y1);
            if (drawable.isLine) {
                xMin = Math.min(xMin, drawable.x2);
                xMax = Math.max(xMax, drawable.x2);
                yMin = Math.min(yMin, drawable.y2);
                yMax = Math.max(yMax, drawable.y2);
            }
        }

        // Zeichnen aller Elemente mit der richtigen Skalierung
        xScale = xMax - xMin > 0 ? getSize().width / (xMax - xMin) : 1;
        yScale = yMax - yMin > 0 ? getSize().height / (yMax - yMin) : 1;
        for (final Drawable drawable : drawables) {
            graphics.setColor(drawable.color);
            if (drawable.isLine) {
                graphics.drawLine((int) ((drawable.x1 - xMin) * xScale),
                        (int) (getSize().height - 1 - (drawable.y1 - yMin) * yScale),
                        (int) ((drawable.x2 - xMin) * xScale),
                        (int) (getSize().height - 1 - (drawable.y2 - yMin) * yScale));
            }
            else {
                graphics.fillOval((int) ((drawable.x1 - xMin) * xScale - 2),
                        (int) (getSize().height - 1 - (drawable.y1 - yMin) * yScale - 2), 5, 5);
            }
        }
    }

    /**
     * Die Methode gibt den kürzesten/schnellsten Pfad zurück. Nach dem Klick für die zweite Node wird über die Konsole
     * abgefragt, ob nach dem kürzesten oder dem schnellsten Weg gesucht werden soll. Bei der Eingabe von s wird der
     * kürzeste und bei der Eingabe von f der schnellste Weg berechnet und angezeigt.
     * @param from die Startnode
     * @param to die Zielnode
     */
    private void shortestPath(final Node from, final Node to)
    {
        /*
        Scanner zur gleichzeitigen Realisierung von Aufgabe 2.3 und 3 in einer Methode. Bei Zeichen das nicht s oder a
        ist, also f oder einer anderen Eingabe wird der schnellste Weg berechnet. Das Zeichen f wird also nur abgefragt,
        damit die Struktur einheitlich ist, also ist außer s für den kürzesten Pfad und a für a* die Eingabe eigentlich
        irrelevant
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("for shortest path type 's', for fastest path 'f', for A*-shortest type 'a'.");
        String shortestOrFastest = scanner.nextLine();

        dijkstra(from, to, shortestOrFastest);
    }

    private void dijkstra(Node from, Node to, String shortestOrFastest) {

        // HashSet mit allen abgelaufenen Nodes
        Set<Node> visited = new HashSet<Node>();

        // PriorityQueue mit den kostengünstigsten Nodes am weitesten oben
        PriorityQueue<Node> queue = new PriorityQueue<Node>(Comparator.comparingDouble(Node::getCosts));

        // Einfügen der Startnode in die queue
        queue.add(from);

        // Variable für die aktuell ausgewählte Node, anfangs auf die Startnode gesetzt
        Node current = from;

        // kosten der Startnode auf 0 setzen
        current.reachedFromAtCosts(null, 0);


        // geht jede ausgewählte Node durch, bis die Zielnode erreicht wird
        while(!queue.isEmpty()) {//!current.equals(to)) {
            if(current.equals(to)) {
                break;
            }

            // current bekommt den Wert des heads der queue
            current = queue.poll();

            System.out.println("current" + current);
            System.out.println("to" + to);

            // current wird in die visitedNodes eingetragen
            visited.add(current);

            // schaut sich die Nachbarn der aktuellen Node an
            visitNeighbors(current, visited, queue, shortestOrFastest, to);
        }

        // arbeitet sich vom Ziel über den Verweis durch den Parameter from auf die Vorgängernode wieder zum start
        // zurück und zeichnet den Weg rot
        while(!to.equals(from)) {
            System.out.println("toInWhile" + to);
            to.draw(to.getFrom(), Color.red);
            to = to.getFrom();
        }


    }

    private void visitNeighbors(Node current, Set<Node> visited, PriorityQueue<Node> queue, String shortestOrFastest, Node to) {
        // alle Nachbar-Edges durchgehen
        for (int i = 0; i < current.getEdges().size(); i++) {

            // Faktor für die Kosten, nur wirklich relevant für fastest, da dieser Faktor mit der Distanz multipliziert
            // wird, um die Kosten anzupassen
            double costFactor;

            // wird bei A*-shortest noch draufgerechnet
            double aStarAssumption = 0;

            // auswählen der Nachbarnode
            Node neighbor = current.getEdges().get(i).getTarget();

            // wenn shortest: Kosten = 1
            if (shortestOrFastest.equals("s")) {
                costFactor = 1;
            }
            // wenn a*: Kosten = 1 und aStarAssumption die euklidische Distanz von aktueller Nachbarnode zum Ziel
            else if (shortestOrFastest.equals("a")) {
                costFactor = 1;
                aStarAssumption = neighbor.distance(to);
            }
            // bei fastest: Kosten der Edge aus der edge auslesen
            else {
                costFactor = current.getEdges().get(i).getCosts();
            }

            // wenn kontrollierte Node bereits abgelaufen: überspringen dieser Node
            if (visited.contains(neighbor)) {
                continue;
            }

            // einzeichnen der kontrollierten Pfade in Blau
            current.draw(neighbor, Color.blue);

            // distanz der aktuell ausgewählten Edge
            double edgeDistance = costFactor * current.distance(neighbor);

            // Kosten des Weges bis zur current Node
            double currentCost = current.getCosts();

            // bei a*: abziehen der vorher aufaddierten Distanz der Node zum Ziel, da diese Distanz immer nur auf die zu
            // kontrollierende Node aufaddiert wird, muss also für die Vorgängernode wieder abgezogen werden um Ergebnis
            // nicht zu verfälschen
            if(shortestOrFastest.equals("a")) {
                currentCost = currentCost - current.distance(to);
            }

            // neue Distanz für den aktuellen Knoten ausrechnen, bei a* noch Distanz zum Ziel aufaddieren, wenn nicht
            // a* ausgewählt ist, ist diese Distanz = 0, wird also nicht mit einberechnet
            double newDistance = currentCost + edgeDistance + aStarAssumption;

            // wenn neue Distanz kleiner ist als vorherige: setzen der neuen Distanz und hinzufügen des Knotens in die
            // queue
            if (newDistance < neighbor.getCosts()) {
                neighbor.reachedFromAtCosts(current, newDistance);
                queue.add(neighbor);
            }
        }
    }


    /**
     * Das Hauptprogramm öffnet das Hauptfenster.
     * @param args Die Parameter werden ignoriert.
     * @throws IOException Das Einlesen der Karte hat nicht geklappt.
     */
    public static void main(final String[] args) throws IOException
    {
        new RoutePlanner();
    }
}
