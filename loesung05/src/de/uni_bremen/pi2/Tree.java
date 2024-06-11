package de.uni_bremen.pi2;

import static de.uni_bremen.pi2.Node.*;

/**
 * Ein Baum.
 * @param <E> Der Typ der Daten, die im Baum gespeichert werden.
 */
public class Tree<E>
{
    /** Die Wurzel des Baums. */
    Node<E> root;

    /**
     * Konstruktor für einen leeren Baum.
     */
    public Tree()
    {
        this(null);
    }

    /**
     * Konstruktor für einen Baum mit Knoten.
     * @param root Die Wurzel des Baums.
     */
    public Tree(final Node<E> root)
    {
        this.root = root;
    }

    /**
     * Hängt einen Kindknoten an einen Elternknoten in einer bestimmten Richtung
     * an.
     * @param parent Der Elternknoten, an den angehängt wird. Ist er null, wird
     *         das Kind die neue Wurzel des Baums.
     * @param child Der Kindknoten, der angehängt wird. Darf ein Blatt (null)
     *         sein.
     * @param direction Die Richtung, in die angehängt wird (LEFT, RIGHT). Ist
     *         parent null, hat dieser Parameter keine Relevanz.
     */
    void setChild(final Node<E> parent, final Node<E> child, final int direction)
    {
        if (parent == null) {
            root = child;
        }
        else {
            parent.children[direction] = child;
        }
        if (child != null) {
            child.parent = parent;
        }
    }

    /**
     * Bestimmt die Richtung, in der ein Knoten an seinem Elternknoten hängt.
     * @param node Der Knoten. Darf kein Blatt (null) sein.
     * @return Die Richtung, in der Knoten an seinem Elternknoten hängt (LEFT,
     *         RIGHT). Ist der Knoten die Wurzel, ist das Ergebnis LEFT und damit
     *         unsinnig.
     */
    int whichChild(final Node<E> node)
    {
        return node.parent == null || node == node.parent.children[LEFT]
                ? LEFT : RIGHT;
    }

    /**
     * Liefert eine mehrzeilige Darstellung des Baums.
     * @return Ein mehrzeiliger String mit den Knoten des Baums.
     */
    @Override
    public String toString()
    {
        return "\n" + toString(root, "", "");
    }

    /**
     * Erzeugt eine mehrzeilige Darstellung eines Knotens und seiner Kinder.
     * @param node Der Knoten.
     * @param horizontal Die horizontale Kante, die vor dem Knoten stehen soll.
     * @param vertical Die vertikale Kante, die vor allen Kindknoten stehen soll.
     * @return Ein mehrzeiliger String mit den Knoten des Teilbaums.
     */
    private String toString(final Node<E> node, final String horizontal, final String vertical)
    {
        return horizontal
                + (node == null
                ? "[]"
                : node + "\n"
                + toString(node.children[RIGHT], vertical + "├── ", vertical + "│   ") + "\n"
                + toString(node.children[LEFT], vertical + "└── ", vertical + "    "));
    }
}