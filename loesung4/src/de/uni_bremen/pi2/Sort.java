package de.uni_bremen.pi2;

import java.util.Comparator;

/**
 * Alternative Implementierung zweier Sortierverfahren.
 *
 * @author Dalia Benamar
 */

public class Sort {
    /**
     * Sortieren durch Einfügen entsprechend der natürlichen Ordnung der
     * Elemente eines Arrays.
     * Das Suchen nach der Einfügestelle hat hier nur den Aufwand O(log N).
     * Das Verschieben hat aber weiterhin den Aufwand O(N).
     *
     * @param a   Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void insertionSort(final T[] a) {
        //falls gar nichts im Array ist, soll es nichts zurückgeben
        if ((a == null) || (a.length == 0)) {
            return;
        }

// Iteriere über das Array
        for (int i = 1; i < a.length; i++) {


            final T wert = a[i];
// Finde die Einfügestelle für das aktuelle Element
            int insertionPoint = findInsertionPoint(a, wert, 0, i);

// Verschiebe die Elemente, um Platz für das aktuelle Element zu machen
            System.arraycopy(a, insertionPoint, a, insertionPoint + 1, i - insertionPoint);
// Füge das aktuelle Element an der richtigen Stelle ein
            a[insertionPoint] = wert;
        }


    }

    /**
     * Hier wird ein Schlüsselwert durch die Binäre suche gesucht
     *
     * @param a            Das Array, welches durchsucht wird.
     * @param <T>          Der Typ der Elemente des Arrays.
     * @param key          ist der Schlüssel, nachdem gesucht wird
     * @param untereGrenze Der untere Grenzwert des Suchbereichs.
     * @param obereGrenze  Der obere Grenzwert des Suchbereichs.
     * @return Der Index der Einfügestelle für den Schlüssel.
     */

    private static <T extends Comparable<T>> int findInsertionPoint(final T[] a, final T key, int untereGrenze,
                                                                    int obereGrenze) {


        // Binäre Suche nach dem Einfügepunkt
        while (untereGrenze + 1 != obereGrenze) {
// Berechne die Mitte des aktuellen Suchbereichs
            final int mitte = untereGrenze + (obereGrenze - untereGrenze) / 2;
            // Überprüfe das Element in der Mitte des Suchbereichs
            if (a[mitte].compareTo(key) > 0) {
                // Das Element in der Mitte ist größer als der gesuchte Schlüssel
                // Setze die obere Grenze auf die Mitte, um den Suchbereich zu verkleinern
                obereGrenze = mitte;
            } else {
                // Das Element in der Mitte ist kleiner oder gleich dem gesuchten Schlüssel
                // Setze die untere Grenze auf die Mitte, um den Suchbereich zu verkleinern
                untereGrenze = mitte;
            }
        }
        if (a[untereGrenze].compareTo(key) < 0) {
            return untereGrenze + 1;
        }
        // Die untere Grenze ist größer oder gleich dem gesuchten Element,
        // das Element wird also vor der Grenze eingefügt, weil
        // das gesuchte Element das kleinste der Liste ist
        else {
            return untereGrenze;
        }

    }

    /**
     * QuickSort entsprechend der natürlichen Ordnung der Elemente eines
     * Arrays.
     *
     * @param a   Das Array, das sortiert wird.
     * @param <T> Der Typ der Elemente des Arrays.
     */
    public static <T extends Comparable<T>> void quickSort(final T[] a) {

        // Überprüfe, ob das Array null oder leer ist
        if (a == null || a.length == 0) {
            return;
        }
// Rufe die private Methode quickSort auf, die den eigentlichen Sortiervorgang durchführt
        quickSort(a, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(
            final T[] a, final int anfang, final int ende) {
        // Überprüfe, ob der zu sortierende Bereich gültig ist (d.h. anfang < ende)
        if (anfang < ende) {
            // Wähle ein Pivot-Element aus dem Array
            T pivot = selectPivot(a, anfang, ende);
            // Überprüfe, ob alle Elemente im Bereich bereits gleich sind
            if (pivot == null) {
                return; // In diesem Fall ist das Array bereits sortiert
            }

            int i = anfang;
            int j = ende;
            // Teile das Array in zwei Hälften, wobei Elemente kleiner als das Pivot-Element
            // auf die linke Seite und Elemente größer als das Pivot-Element auf die rechte Seite kommen
            while (i <= j) {
                // Suche das erste Element von links, das größer oder gleich dem Pivot-Element ist
                while (a[i].compareTo(pivot) < 0) {
                    i++;
                }
                // Suche das erste Element von rechts, das kleiner oder gleich dem Pivot-Element ist
                while (a[j].compareTo(pivot) > 0) {
                    j--;
                }
                // Überprüfe, ob die beiden Suchindizes sich nicht überschneiden
                if (i <= j) {
                    // Tausche die Elemente an den Positionen i und j
                    swap(a, i, j);
                    i++;
                    j--;
                }
            }
            // Rufe rekursiv quickSort für die beiden Teilbereiche auf
            quickSort(a, anfang, j); // Sortiere den linken Teilbereich
            quickSort(a, i, ende); // Sortiere den rechten Teilbereich
        }
    }

    /**
     * @param a      Das Array, das sortiert wird
     * @param anfang der Anfang des zu sortierten Bereiches.
     * @param ende   das Ende des zu sortierten Bereiches.
     * @param <T>    Der Typ des Arrays.
     * @return Das ausgewählte Pivot-Element.
     * Gibt das erste Element zurück, wenn beide Bereiche gleich groß sind.
     * Gibt das Element an der aktuellen Position zurück, wenn es größer als das erste Element ist.
     * Gibt null zurück, wenn alle Elemente im Bereich gleich sind.
     */
    static <T extends Comparable<T>> T selectPivot(final T[] a, int anfang, int ende) {
        // Wähle das erste Element als vorläufiges Pivot-Element
        T erstesElement = a[anfang];

        // Überprüfe die Elemente im Bereich von anfang+1 bis ende
        for (int i = anfang + 1; i <= ende; i++) {
// Überprüfe, ob das aktuelle Element ungleich dem ersten Element ist
            if (a[i].compareTo(erstesElement) != 0) {
// Überprüfe, ob das aktuelle Element größer als das erste Element ist
                if (a[i].compareTo(erstesElement) > 0) {
                    // Das aktuelle Element wird als neues Pivot-Element ausgewählt
                    return a[i];
                } else {
                    // Das erste Element bleibt als Pivot-Element erhalten
                    return erstesElement;

                }
            }

        }
        // Alle Elemente im Bereich sind gleich, es gibt kein geeignetes Pivot-Element
        return null;
    }

    /**
     * Tauscht zwei Elemente eines Arrays aus.
     *
     * @param a Das Array, in dem die beiden Elemente getauscht werden.
     * @param i Der Index des einen Elements.
     * @param j Der Index des anderen Elements.
     */
    private static void swap(final Object[] a, final int i, final int j) {
        final Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    /**
     * AUFGABEN
     * Aufgabe 2.1
     * Bei Quicksort sortieren wir vom Pivot-Element aus. Der Sinn dahinter ist, dass wir zwei Teilhälften
     * bekommen, wobei links alle Elemente hinsortiert werden, die kleiner als das Pivot-Element sind und rechts die
     * Elemente, die größer sind. Wenn wir nun bei bottom mit der Aufteilung beginnen, hätten wir keine zwei Teilhälften.
     * Wenn das pivot-Element also das kleinste Element der Liste ist, dann gäbe es keine Teilfolge mehr links vom
     * pivot-Element. Daher muss es größer sein als das kleinste Element der Liste, damit quicksort nach seinem Schema
     * effizient funktionieren kann.
     *
     * Aufgabe 2.3
     * Dies ist jetzt möglich, da der Index des Pivot-Elements nicht mehr festgeschrieben ist, sondern flexibel. Damit
     * muss nicht mehr außerhalb des Array-Bereiches angehängt werden, sondern das pivot-Element kann einfach nach
     * Bedarf nach links oder rechts verschoben werden.
     *
     */
}
