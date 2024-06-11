package de.uni_bremen.pi2;

/**
 * Dies ist der Anfang einer Implementierung der naiven Selbstanordnungsstrategie.
 * Er muss erweitert werden. Für die anderen Strategien sollen ähnliche Klassen
 * erzeugt werden.
 * @author  Hanna Becker
 */
public class SetNaive<E> extends Set<E>
{
    /**
     * Fügt der Menge ein neues Element hinzu. Die Methode wird von "add"
     * aufgerufen, wenn das Element noch nicht vorhanden ist.
     * @param element Das Element, das hinzugefügt wird.
     */
    @Override
    void addToList(final E element)
    {
        // neue Node wird erstellt, deren folgende Node der ursprüngliche head ist, somit steht sie ganz am Anfang
        Node current = new Node(element, getHead());

        // neuer head ist die neue Node
        setHead(current);
    }

    /**
     * Testet, ob ein bestimmtes Element in der Menge gespeichert ist.
     * @param element Das Element, das nach dem gesucht wird. Das Element darf
     *                auch "null" sein, wäre dann aber garantiert nicht enthalten.
     * @return        Ist das gesuchte Element in der Menge enthalten?
     */
    @Override
    public boolean contains(final E element)
    {
        // node zum Durchlaufen der Liste, gestartet wird bei head
        Node current =  getHead();

        // kontrolliert, ob Element nicht null ist
        if (element != null) {

            // läuft vom Anfang bis zum Ende der Liste, bricht ab, wenn Element gefunden wurde
            while(current != null) {

                // kontrolliert, ob die Node denselben Wert enthält, wie den, der gesucht wird
                if(current.getElement().equals(element)) {
                    return true;
                }
                // wählt die nächste Node aus
                current = current.getNext();
            }
        }
        return false; // wenn das Element noch nicht in der Liste steht
    }
}
