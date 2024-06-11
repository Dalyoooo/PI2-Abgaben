package de.uni_bremen.pi2;


/**
 * Dies ist eine Liste mit der frequency-count Strategie. Neue Elemente werden immer am Ende einsortiert. Die Elemente
 * werden nach ihrer Aufruf-Häufigkeit geordnet, häufiger aufgerufene Elemente werden weiter vorne einsortiert. Wenn
 * zwei Elemente gleich oft aufgerufen wurden, werden sie nach dem letzten Aufruf sortiert, das zeitlich aktueller
 * aufgerufene steht also dabei vorne.
 * @param <E> Der Datentyp der Listen-Inhalte
 * @author  Hanna Becker
 */
public class SetFC<E> extends Set<E> {

    /**
     * Fügt der Menge ein neues Element hinzu. Die Methode wird von "add"
     * aufgerufen, wenn das Element noch nicht vorhanden ist. Das Element wird am Ende eingefügt.
     * @param element Das Element, das hinzugefügt wird.
     */
    @Override
    public void addToList(final E element) {
        // Node zum Durchgehen der Liste
        Node current = getHead();

        //wenn Liste nicht leer ist, wird das Element an das Ende gepackt, da es neu ist und dementsprechend am noch
        // eine frequency von 0 hat
        if(current != null) {
            while (current != null) {
                // wenn Element das letzte in der Liste ist, kriegt es die neue Node als Nachfolger gesetzt
                if (current.getNext() == null) {
                    current.setNext(new Node(element, null));
                    break;
                }
                current = current.getNext();
            }
        }
        // wenn die Liste leer ist, wird die Node als neuer head eingefügt
        else {
            setHead(new Node(element, null));
        }
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
        // node zum Durchlaufen der Liste
        Node current = getHead();

        // node zum Merken der Vorgängernode
        Node before = getHead();

        // kontrolliert, ob Element nicht null ist
        if (element != null) {

            // läuft die Liste von Anfang bis Ende durch, bricht ab, wenn Element gefunden wurde
            while(current != null) {

                // kontrolliert, ob die Node denselben Wert enthält, wie den, der eingefügt werden soll
                if(current.getElement().equals(element)) {

                    // erhöht die frequency
                    current.incFrequency();

                    // current wird aus der Liste entfernt
                    before.setNext(current.getNext());

                    // node zum erneuten Durchlaufen der Liste
                    Node currentCount = getHead();

                    // kontrolliert, ob Liste durchlaufen werden musste
                    boolean counted = false;

                    // durchläuft die Liste und sucht die Stelle, an die die Node laut frequency hingehört
                    while((currentCount != null) && (currentCount.getFrequency() > current.getFrequency())) {
                        before = currentCount;
                        currentCount = currentCount.getNext();

                        counted = true;
                    }

                    // wenn current bereits das am häufigsten gesuchte Element ist und somit die Liste nicht durchlaufen
                    // wurde
                    if(!counted) {
                        // wenn current bereits head ist: keine Änderung der Reihenfolge
                        if(current.equals(getHead())) {
                            return true;
                        }

                        // wenn current noch nicht head ist, wird es als head gesetzt
                        else {
                            current.setNext(getHead());
                            setHead(current);
                            return true;
                        }
                    }

                    // current wird vor das Element mit gleich viel oder niedrigerer frequency geschoben
                    current.setNext(currentCount);

                    // before wurde durch das erneute durchgehen verändert, ist jetzt das Element vor currentCount und
                    // kriegt als neuen Nachfolger current, da current von der frequency zwischen before und
                    // currentCount liegt
                    before.setNext(current);
                    return true;
                }

                // before zeigt immer auf die Node, die im vorherigen Durchgang current war, ist somit immer der
                // Vorgänger der aktuellen Node
                before = current;

                // current wird auf die danach folgende Node gesetzt, somit wird die Liste durchgegangen
                current = current.getNext();
            }
        }
        // element konnte nicht in der Liste gefunden werden
        return false;
    }
}
