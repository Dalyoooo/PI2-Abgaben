package de.uni_bremen.pi2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Die Klasse implementiert eine Menge, die als Liste implementiert ist.
 * Sie ist abstrakt, da das Hinzufügen und das Auffinden von Elementen
 * in einer abgeleiteten Klasse implementiert werden muss. Die Methoden,
 * die "package private" sind (also ohne Zugriffsmodifizierer), sind
 * für die Nutzung aus abgeleiteten Klassen gedacht.
 *
 * Dieser Quelltext darf nicht geändert werden.
 *
 * @param <E> Der Typ der Elemente der Menge. Dieser muss "equals" sinnvoll
 *          implementieren.
 */
public abstract class Set<E> implements Iterable<E>
{
    /** Der Kopf der Liste. */
    private Node<E> head = null;

    /**
     * Hinzufügen eines Elements zu der Menge.
     * @param element               Das neue Element. Es darf nicht "null" sein.
     * @throws NullPointerException Das Element war doch "null".
     */
    public void add(final E element)
    {
        if (element == null) {
            throw new NullPointerException();
        }
        else if (!contains(element)) {
            addToList(element);
        }
    }

    /**
     * Testet, ob ein bestimmtes Element in der Menge gespeichert ist.
     * @param element Das Element, das nach dem gesucht wird. Das Element darf
     *                auch null sein, wäre dann aber garantiert nicht enthalten.
     * @return        Ist das gesuchte Element in der Menge enthalten?
     */
    public abstract boolean contains(final E element);

    /**
     * Fügt der Menge ein neues Element hinzu. Die Methode wird von "add"
     * aufgerufen, wenn das Element noch nicht vorhanden ist.
     * @param element Das Element, das hinzugefügt wird.
     */
    abstract void addToList(final E element);

    /**
     * Liefert den Kopf der Liste.
     * @return Der Kopf der Liste oder null, wenn sie leer ist.
     */
    Node<E> getHead()
    {
        return head;
    }

    /**
     * Setzt den Kopf der Liste.
     * @param head Der neue Kopf der Liste oder null, falls sie leer sein soll.
     */
    void setHead(final Node<E> head)
    {
        this.head = head;
    }

    /**
     * Liefert einen Iterator, mit dem die Menge durchlaufen werden kann.
     * Das Verhalten des Iterators ist undefiniert, wenn die Menge während
     * des Durchlaufens geändert wird.
     * @return Ein Iterator zum Durchlaufen dieser Menge.
     */
    public Iterator<E> iterator()
    {
        return new Iterator<>() {
            private Node<E> node = head;

            public boolean hasNext() {
                return node != null;
            }

            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                else {
                    final E element = node.getElement();
                    node = node.getNext();
                    return element;
                }
            }
        };
    }
}
