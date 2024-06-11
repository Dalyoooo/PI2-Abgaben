package de.uni_bremen.pi2;

/**
 * Dies ist eine Liste mit der move-to-front Strategie. Das zuletzt aufgerufene Element ist dabei immer vorne.
 * @param <E> Der Datentyp der Listen-Inhalte
 * @author  Hanna Becker
 */
public class SetMoveToFront<E> extends SetNaive<E> {

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

        // node zum Merken der Vorgängernode
        Node before = current;

        // kontrolliert, ob Element nicht null ist
        if (element != null) {

            // läuft die Liste von Anfang bis Ende durch, bricht ab, wenn Element gefunden wurde
            while(current != null) {

                // kontrolliert, ob aktuelle Node denselben Wert enthält wie das Element, das gesucht wird
                if(current.getElement().equals(element)) {

                    // wenn Element bereits im head ist, muss nicht mehr umsortiert werden
                    if(current.equals(getHead())) {
                        return true;
                    }

                    // vorherige Node kriegt die übernächste als next Node zugewiesen, aktuelle wird somit aus der
                    // Stelle entfernt
                    before.setNext(current.getNext());

                    // aktuelle Node mit gesuchtem Wert kriegt head als next Node und wird somit an den Anfang geschoben
                    current.setNext(getHead());

                    // Node mit selbem Element wie element wird als head gesetzt
                    setHead(current);

                    return true;
                }

                // before zeigt immer auf die Node, die im vorherigen Durchgang current war, ist somit immer der
                // Vorgänger der aktuellen Node
                before = current;

                // current wird auf die danach folgende Node gesetzt, somit wird die Liste durchgegangen
                current = current.getNext();
            }
        }
        return false; // wenn Element nicht gefunden wurde
    }
}
