package de.uni_bremen.pi2;

//import java.awt.*;
import java.awt.*;
import java.util.List;
import java.io.*;
import java.util.ArrayList;

/**
 * Die Klasse liest die Karte aus Knoten und Kanten ein und
 * repräsentiert diese. Die Daten stammen ursprünglich aus
 * OpenStreetMap (OSM). Dabei werden für jede eingelesene Kante
 * zwei gerichtete Kanten in die Karte eingetragen. Die Klasse
 * stellt eine Methode zur Verfügung, um die Karte zu zeichnen,
 * sowie eine, die zu einem Punkt den dichtesten Knoten ermittelt.
 */
class Map
{
    /**
     * Aufgabe 1
     * Graph liegt als png im Ordner
     * Vorgehensweise: Es wird bei irgendeinem Knoten gestartet, in diesem Fall haben wir A gewählt. Dann wird die
     * kostengünstigste Kante gesucht, in diesem Fall führt diese zu H. Man sucht also immer die günstigste Kante, die
     * zu einem Knoten führt, der noch nicht besucht wurde. Dies ist sehr eindeutig, bis man zu J gelangt. Dort sind die
     * Kosten der Kanten 12 und 14. Nun gibt es bei R aber eine Kante, die zu einem Knoten führt der noch nicht besucht
     * wurde und welche geringere Kosten hat als die Kanten von J. Daher bildet man nun eine Verknüpfung von R zu C. Es
     * muss also nicht gezwungenermaßen eine "Kette" zwischen den Nodes gebildet werden, sondern kann auch an einer
     * anderen Node wieder angesetzt werden, wenn die einen günstigeren Weg zu einem noch nicht besuchten Knoten
     * besitzt. Dieses Verfahren wird solange fortgeführt, bis jeder Knoten im neuen Graphen enthalten ist.
     */

    /**
     * Aufgabe 3.2
     * Der schnellste Weg ist deutlich schwerer abzuschätzen, da wir hier keinerlei Wissen darüber haben, wie der Weg
     * verlaufen wird, da die Kosten der einzelnen Kanten stark schwanken. Es kann also sein, dass der Weg auf den
     * ersten Blick ein riesiger Umweg ist, dann aber doch der schnellste ist, weil er einfach sehr geringe Kosten hat,
     * da auf diesem Weg hohe Geschwindigkeiten zugelassen sind.
     * Für den kürzesten Weg ist dies einfacher. Wir haben in der Klasse Node die Methode node.distance(Zielnode), mit
     * der der euklidische Abstand zum Ende der Strecke berechnet werden kann, indem man das Ende als Zielnode angibt.
     * Somit entsteht der kürzeste Weg, der bis zum Ziel noch entstehen kann.
     */

    /**
     * Liste aller nodes
      */
    ArrayList<Node> listNodes = new ArrayList<>();


    /**
     * Konstruktor. Liest die Karte ein.
     * @throws FileNotFoundException Entweder die Datei "nodes.txt" oder die
     *         Datei "edges.txt" wurden nicht gefunden.
     * @throws IOException Ein Lesefehler ist aufgetreten.
     */
    Map() throws FileNotFoundException, IOException
    {
        // Nodes einlesen
        try {
            final InputStream stream = new FileInputStream("nodes.txt");

            try {
                final Reader reader = new InputStreamReader(stream, "UTF-8");
                final BufferedReader buffer = new BufferedReader(reader);

                String line;

                while ((line = buffer.readLine()) != null) {

                    // splitten der eingelesenen Zeile an den Leerzeichen
                    String[] results = line.split(" ", 3);

                    // Variablenwerte initialisieren durch typecasting
                    int id = Integer.valueOf(results[0]);
                    double x = Double.valueOf(results[1]);
                    double y = Double.valueOf(results[2]);

                    // node erstellen
                    Node node = new Node(id, x, y);

                    // node in Liste aller nodes ergänzen
                    listNodes.add(node);
                }
            }
            catch (IOException e) {
                throw new IOException("Bei 'nodes.txt' ist ein Lesefehler aufgetreten.");
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Datei 'nodes.txt' konnte nicht gefunden werden.");
        }


        // Edges einlesen
        try {
            final InputStream streamEdge = new FileInputStream("edges.txt");

            try {
                final Reader readerEdge = new InputStreamReader(streamEdge, "UTF-8");
                final BufferedReader bufferEdge = new BufferedReader(readerEdge);

                String lineEdge;


                while (((lineEdge = bufferEdge.readLine()) != null)) {
                    // eingelesene Zeilen am Leerzeichen splitten
                    String[] resultsEdge = lineEdge.split(" ", 3);

                    // bricht ab, wenn nur noch leere Zeilen folgen
                    if(resultsEdge[0].equals("")) {
                        break;
                    }

                    // Variablen initialisieren
                    int from = Integer.valueOf(resultsEdge[0]);
                    int to = Integer.valueOf(resultsEdge[1]);
                    String edgeType = resultsEdge[2];

                    // cost auf -1, wenn ungültiger edgeType
                    double cost = -1;

                    // initialisieren der Kosten
                    // kosten errechnet durch Konstante 50 (für gut differenzierte Werte) geteilt durch die abgeschätzte
                    // km/h Zahl
                    switch(edgeType) {
                        case "primary":
                            cost = 50/130;
                            break;
                        case "primaryLink":
                            cost = 50/((50+130)/2);
                            break;
                        case "secondary":
                            cost = 50/100;
                            break;
                        case "secondaryLink":
                            cost = 50/((50+100)/2);
                            break;
                        case "tertiary":
                            cost = 50/90;
                            break;
                        case "unclassified":
                            cost = 50/75;
                            break;
                        case "residential":
                            cost = 50/50;
                            break;
                        case "livingStreet":
                            cost = 50/7;
                            break;
                        case "path":
                            cost = 50/19;
                            break;
                        case "cycleway":
                            cost = 50/17;
                            break;
                        case "rack":
                            cost = 50/15; // keine Ahnung, was rack sein soll
                            break;
                        case "footway", "notKnownYet": // notKnownYet als footway wegen worst possible case
                            cost = (double) 50/5;
                            break;
                        case "track":
                            cost = 50/20;
                            break;
                        default:
                            System.out.println("kein gültiger EdgeType");
                    }

                    Node nodeTo = null;
                    Node nodeFrom = null;

                    // sucht Ziel- und Ausgangsnode aus der Liste aller nodes
                    for(Node node : listNodes) {
                        // to und from aus Datei ausgelesen
                        // Zielnode der Kante einspeichern
                        if(node.getId() == to) {
                            nodeTo = node;
                        }
                        // Ausgangsnode der Kante einspeichern
                        if(node.getId() == from) {
                            nodeFrom = node;
                        }

                        // wird nur so lange durchlaufen, bis beide nodes gefunden wurden. Nicht für die Funktion
                        // wichtig, vermeidet nur unnötige Schleifendurchgänge
                        if((nodeTo != null) && (nodeFrom != null)) {
                            break;
                        }
                    }

                    // da ungerichteter Graph: Edge muss in beide Richtungen laufen
                    Edge directionForwards = new Edge(nodeTo, cost);
                    Edge directionBackwards = new Edge(nodeFrom, cost);

                    // als Edge in Ausgangs- und Zielnode speichern
                    nodeTo.getEdges().add(directionBackwards);
                    nodeFrom.getEdges().add(directionForwards);
                }
            }
            catch (IOException e) {
                throw new IOException("Bei 'nodes.txt' ist ein Lesefehler aufgetreten.");
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Datei 'nodes.txt' konnte nicht gefunden werden.");
        }
    }

    /** Zeichnen der Karte. */
    void draw()
    {
        // zeichnen aller Kanten in pink
        for(Node node : listNodes) {
            List<Edge> edges = node.getEdges();
            for(Edge edge : edges) {
                node.draw(edge.getTarget(), Color.pink);
            }
        }
    }

    /**
     * Findet den dichtesten Knoten zu einer gegebenen Position.
     * @param x Die x-Koordinate.
     * @param y Die y-Koordinate.
     * @return Der Knoten, der der Position am nächsten ist. null,
     *         falls es einen solchen nicht gibt.
     */
    Node getClosest(final double x, final double y)
    {
        // referenceNode bestehend aus den Daten des Klicks
        Node referenceNode = new Node(1, x, y);

        double minDistance = referenceNode.distance(listNodes.get(0)); // zum Initialisieren: Abstand anfangs = Abstand zu
                                                                     // erster Node in Liste

        Node closestNode = null;

        // geht alle Nodes durch, sucht die mit der geringsten Distanz raus und speichert sie in closestNode
        for(Node node : listNodes) {
            if(referenceNode.distance(node) < minDistance) {
                minDistance = referenceNode.distance(node);
                closestNode = node;
            }
        }

        return closestNode;
    }

    /** Löschen aller Vorgängereinträge und Setzen aller Kosten auf unendlich. */
    void reset()
    {
        for(Node node : listNodes) {
            node.reachedFromAtCosts(null, Double.MAX_VALUE);
        }
    }
}
