package de.uni_bremen.pi2;

/**
 * Dies ist eine Liste mit der transpose Strategie. Neu hinzugefügte Elemente stehen dabei vorne, aufgerufene Elemente
 * werden mit ihrem Vorgänger vertauscht.
 * @param <E> Der Datentyp der Listen-Inhalte
 * @author  Hanna Becker
 */
public class SetT<E> extends SetNaive<E>{

    /**
     * Testet, ob ein bestimmtes Element in der Menge gespeichert ist.
     * @param element Das Element, das nach dem gesucht wird. Das Element darf
     *                auch "null" sein, wäre dann aber garantiert nicht enthalten.
     * @return        Ist das gesuchte Element in der Menge enthalten?
     */
    @Override
    public boolean contains(final E element) {
        // node zum Durchlaufen der Liste
        Node current = getHead();

        // node zum Speichern der Vorgängernode
        Node before = getHead();

        // node zum Speichern des Vorgängers vom Vorgänger
        Node beforeBefore = getHead();

        // kontrolliert, ob Element nicht null ist
        if (element != null) {

            // geht Liste von Anfang bis Ende durch, bricht ab, wenn ein Element gefunden wurde
            while(current != null) {

                // kontrolliert, ob die Node denselben Wert enthält, wie den, der eingefügt werden soll
                if(current.getElement().equals(element)) {

                    // wenn es bereits vorne steht, kann nicht mit dem vorherigen vertauscht werden
                    if(current.equals(getHead())) {
                        return true;
                    }

                    // wenn es das zweite ist, muss nicht nur getauscht werden, sondern die Node auch als neuer head
                    // gesetzt werden
                    if(current.equals(getHead().getNext())) {
                        getHead().setNext(current.getNext());
                        current.setNext(getHead());
                        setHead(current);
                        return true;
                    }

                    // ansonsten wird node mit ihrem Vorgänger vertauscht, die Node, die vorher auf den Vorgänger
                    // gezeigt hat, muss jetzt auf die Node mit dem gesuchten Element zeigen
                    beforeBefore.setNext(current);
                    before.setNext(current.getNext());
                    current.setNext(before);
                    return true;
                }
                // jede Node wird einen weiter gesetzt, beforeBefore ist der ehemalige Vorgänger, before die ehemalige
                // current node und current zeigt jetzt auf seinen Nachfolger
                beforeBefore = before;
                before = current;
                current = current.getNext();
            }
        }
        return false;
    }

}
