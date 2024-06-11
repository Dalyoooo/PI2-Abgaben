package de.uni_bremen.pi2;

/**
 * Die Klasse repräsentiert einen Knoten in einer einfach verketteten Liste.
 * Der Knoten speichert ein Element, das wieder abgefragt, aber nicht geändert
 * werden kann. Zudem gibt es eine Referenz auf einen Nachfolgeknoten. Diese
 * kann nachträglich verändert werden.
 *
 * Diese Klasse darf nicht geändert werden.
 *
 * @param <E> Der Typ des zu speichernden Elements.
 * @author  Hanna Becker
 */
class Node<E>
{
    /** Das gespeicherte Element. */
    private final E element;

    /** Der Nachfolgeknoten. Ist er null, gibt es keinen Nachfolger. */
    private Node<E> next;

    /** Die Häufigkeit des Zugriffs. Wird nur für die FC-Regel benötigt. */
    private int frequency = 0;

    /**
     * Konstruktor.
     * @param element Das zu speichernde Element.
     * @param next Der Nachfolgeknoten. Ist er null, gibt es keinen Nachfolger.
     */
    Node(final E element, final Node<E> next)
    {
        this.element = element;
        this.next = next;
    }

    /**
     * Liefert das gespeicherte Element.
     * @return Das Element.
     */
    E getElement()
    {
        return element;
    }

    /**
     * Liefert den Nachfolgeknoten.
     * @return Der Nachfolgeknoten oder null, falls es keinen gibt.
     */
    Node<E> getNext()
    {
        return next;
    }

    /**
     * Setzt den Nachfolgeknoten.
     * @param next Der Nachfolgeknoten oder null, falls es keinen gibt.
     */
    void setNext(final Node<E> next)
    {
        this.next = next;
    }

    /**
     * Liefert die Häufigkeit des Zugriffs auf diesen Knoten.
     * Wird nur für die FC-Regel benötigt.
     * @return Die Zugriffshäufigkeit.
     */
    int getFrequency()
    {
        return frequency;
    }

    /** Erhöht die Zugriffshäufigkeit um 1. Wird nur für die FC-Regel benötigt. */
    void incFrequency()
    {
        ++frequency;
    }
}
