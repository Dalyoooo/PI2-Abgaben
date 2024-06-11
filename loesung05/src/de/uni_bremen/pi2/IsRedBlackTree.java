package de.uni_bremen.pi2;

import com.sun.source.tree.ReturnTree;

import static de.uni_bremen.pi2.Node.*; // LEFT, RIGHT
import static de.uni_bremen.pi2.RBNode.Color.*; // RED, BLACK
import static de.uni_bremen.pi2.IsRedBlackTree.Result.*; // OK ...

/** Klasse zum Überprüfen der Eigenschaften von Rot-Schwarz-Bäumen. */
public class IsRedBlackTree
{
    /** Das Ergebnis eines Tests. */
    enum Result
    {
        /** Prüfung erfolgreich. */
        OK,
        WRONG_COLOR_CHILD, // Kind hat falsche Farbe

        /**
         * Kommt in unserem Fall eigentlich nicht vor, da in RBTrees nur RBNodes eingefügt werden können und die
         * einzigen verwendbaren Farben RED und BLACK sind. Würde also nur auftreten, wenn enum Color von RBNode neue
         * Farben hinzugefügt bekommen würde, die im Baum aber eigentlich nicht vorkommen sollten. Wir konnten diesen
         * Teil des codes somit auch nicht testen, weshalb die Code-Coverage nicht 100% ist.
         */
        INVALID_COLOR, // Knoten ist nicht schwarz oder rot, also ungültige Farbe oder gar keine Farbe

        WRONG_AMOUNT_BLACK_NODES, // Für jeden Knoten gilt, dass alle Pfade zu darunter liegenden Blättern gleich
                                    // viele schwarze Knoten enthalten → nicht erfüllt
        ROOT_NOT_BLACK // Wurzel ist nicht schwarz
    }

    /**
     * Die Methode überprüft die Rot-Schwarz-Eigenschaften.
     * @param tree Der Baum, dessen Eigenschaften geprüft werden.
     * @return Das Ergebnis der Prüfung.
     * @param <E> Der Typ der im Baum gespeicherten Werte.
     */
    public static <E extends Comparable<E>> Result check(final RBTree<E> tree)
    {
        // Baum darf nicht null sein
        if(tree == null) {
            throw new NullPointerException();
        }

        if(tree.root == null) {
            return OK;
        }


        // Variable für root
        RBNode start = (RBNode) tree.root;

        // root muss schwarz sein, returnt verletztes Kriterium, wenn dies nicht der Fall ist
        if(start.color.equals(RED)) {
            return ROOT_NOT_BLACK;
        }

        // ansonsten werden die Kinder durchgecheckt
        else {
            return checkChildren(start);
        }
    }




    /**
     * Zählt die Anzahl der schwarzen Knoten innerhalb eines Baumes
     * @param root Die Wurzel des Baumes, der überprüft werden soll
     * @return die Anzahl schwarzer Knoten
     */
    private static int checkCountBlackNodes(RBNode root) {
        int counter = 0;

        if(root == null) {
            return 0;
        }

        // wenn Wurzel schwarz: direkt 1 zurück geben
        if(root.color.equals(BLACK)) {
            return 1;
        }

        // rekursiv linken Teilbaum abzählen
        if(root.children[LEFT] != null) {
            RBNode leftChild = (RBNode) root.children[LEFT];
            counter += checkCountBlackNodes(leftChild);
        }
        // rekursiv rechten Teilbaum abzählen
        if(root.children[RIGHT] != null) {
            RBNode rightChild = (RBNode) root.children[RIGHT];
            counter += checkCountBlackNodes(rightChild);
        }

        // Anzahl schwarzer Knoten in dem Teilbaum zurückgeben
        return counter;
    }

    /**
     * Kontrolliert den Baum auf seine Farben und Anzahl schwarzer Knoten
     * @param node Die Node, bei der die Kontrolle gestartet wird
     * @return Result, für die genauen Bedeutungen siehe oben
     */
    private static Result checkChildren(RBNode node) {

        Result leftChildResult = OK;
        Result rightChildResult = OK;

        // null-Node, keine verletzten Kriterien zurückzugeben
        if(node == null) {
            return OK;
        }


        //---------- geht immer über den Teilbaum und kontrolliert die Anzahl der schwarzen Knoten ---------------------
        int counterLeftBlackNodes = checkCountBlackNodes((RBNode) node.children[LEFT]);
        //System.out.println(counterLeftBlackNodes); //debugging
        int counterRightBlackNodes = checkCountBlackNodes((RBNode) node.children[RIGHT]);
        //System.out.println(counterRightBlackNodes); //debugging


        // Anzahl schwarzer Knoten soll gleich sein, wenn nicht: Kriterium verletzt
        if(counterLeftBlackNodes != counterRightBlackNodes) {
            return WRONG_AMOUNT_BLACK_NODES;
        }
        //--------------------------------------------------------------------------------------------------------------

        // variablen für die Kinder
        RBNode leftChild = (RBNode) node.children[LEFT];
        RBNode rightChild = (RBNode) node.children[RIGHT];

        // Variablen der Kinder kriegen die jeweiligen Werte zugewiesen, wenn sie nicht null sind
       // if(node.children[LEFT]!=null) {
         //   leftChild = (RBNode) node.children[LEFT];
        //}
        //if (node.children[RIGHT] != null) {
        //    rightChild = (RBNode) node.children[RIGHT];
        //}

        // wenn Node rot ist
        if(node.color.equals(RED)) {

            // Kinder müssen null oder schwarz sein, rekursiver Aufruf um die weiteren Knoten zu durchlaufen
            if((leftChild == null ||leftChild.color.equals(BLACK)) && (rightChild == null || rightChild.color.equals(BLACK))) {
                leftChildResult = checkChildren(leftChild);
                rightChildResult = checkChildren(rightChild);
            }
            else {
                // rote Kinder: falsche Farbe, da auf Rot nur schwarze Kinder folgen dürfen
                if((!(leftChild == null && rightChild == null)) &&
                        ((leftChild == null || leftChild.color.equals(RED)) ||
                                (rightChild == null || rightChild.color.equals(RED)))) {
                    return WRONG_COLOR_CHILD;
                }
                // ansonsten: keine gültige Farbe - Kriterium verletzt
                else {
                    return INVALID_COLOR;
                }
            }
        }

        // selbes wie bei node.color.equals(RED), nur dürfen auf schwarze Knoten auch schwarze Kinder folgen
        else if(node.color.equals(BLACK)) {

            if((leftChild == null || leftChild.color.equals(RED) || leftChild.color.equals(BLACK))
                    && (rightChild == null || rightChild.color.equals(RED) || rightChild.color.equals(BLACK))) {
                leftChildResult = checkChildren(leftChild);
                rightChildResult = checkChildren(rightChild);
            }
            else {
                return INVALID_COLOR;
            }

        }
        else {
            // ungültige Farbe für Node
            return INVALID_COLOR;
        }

        if(!leftChildResult.equals(OK)) {
            return leftChildResult;
        }
        else {
            return rightChildResult;
        }
    }
}
