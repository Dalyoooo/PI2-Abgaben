package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.RBNode.Color.*;

/**
 * Ein Knoten eines Rot-Schwarz-balancierten Binärbaums.
 * @param <E> Der Typ der in dem Knoten gespeicherten Daten.
 */
class RBNode<E> extends Node<E>
{
    /** Die möglichen Farben von Knoten. */
    enum Color
    {
        BLACK,
        RED
    }

    /** Die Farbe dieses Knotens. */
    Color color;

    /**
     * Erzeugt einen neuen Knoten.
     *
     * @param data Die in dem Knoten gespeicherten Daten.
     * @param red
     */
    RBNode(final E data, Color red)
    {
        this(data, RED, null, null);
    }

    /**
     * Erzeugt einen neuen Knoten.
     * @param data Die in dem Knoten gespeicherten Daten.
     * @param color Die Farbe des Knotens.
     * @param left Linker Kindknoten oder null, falls es diesen nicht gibt.
     * @param right Rechter Kindknoten oder null, falls es diesen nicht gibt.
     */
    public RBNode(final E data, final Color color,
                  final RBNode<E> left, final RBNode<E> right)
    {
        super(data, left, right);
        this.color = color;
    }

    /**
     * Liefert eine Zeichenkette, die diesen Knoten darstellt. Die
     * Kinder sind nicht repräsentiert.
     * @return Eine Zeichenkette aus den gespeicherten Daten und
     *         der Farbe.
     */
    @Override
    public String toString()
    {
        return super.toString() + (color == RED ? " (R)" : " (B)");
    }
}
