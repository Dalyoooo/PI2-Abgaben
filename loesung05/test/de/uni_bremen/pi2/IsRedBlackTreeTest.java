package de.uni_bremen.pi2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static de.uni_bremen.pi2.IsRedBlackTree.Result.*;
import static de.uni_bremen.pi2.RBNode.Color.*;

/** Test-Klasse für die Klasse {@link IsRedBlackTree}. */

public class IsRedBlackTreeTest {


    @Test
    void stillRBTreeAfterInsert() {
        /**
         * Das Problem mit den vorgegebenen Klassen ist, dass das Kriterium mit der gleichen Anzahl an schwarzen Knoten auf
         * den darunter liegenden Pfaden teilweise nicht beachtet wurde. Wenn man bereits eine schwarze Node im Baum hat und
         * mit insert weitere Nodes hinzufügt, kommt das Problem auf, dass die Anzahl der schwarzen Knoten auf den darunter
         * liegenden Pfaden nicht mehr gleich viele sind, da lediglich kontrolliert wird, ob auf rot kein rot folgt. Da aber
         * auf schwarz schwarz folgen darf und das beim insert und anschließendem rebalance nicht beachtet wird. Wenn man
         * in einen leeren Baum insertet funktioniert zwar alles, es gibt aber Probleme wenn man in einen Baum insertet, der
         * bereits Nodes enthält, weil so das Kriterium der schwarzen Knoten auf den Pfaden verletzt wird.
         *
         * Beispiel: bei dem Test testFunctionalityOfRBTree
         * wenn man:
         *      RBTree<Integer> tree = new RBTree<>();
         * durch:
         *      RBTree<Integer> tree = new RBTree<>(new RBNode<>(8, BLACK, null, null));
         * ersetzt, wird folgender Baum ausgegeben:
         *
         * 6 (B)
         * ├── 8 (B)
         * │   ├── []
         * │   └── 7 (R)
         * │       ├── []
         * │       └── []
         * └── 2 (R)
         *     ├── 4 (B)
         *     │   ├── 5 (R)
         *     │   │   ├── []
         *     │   │   └── []
         *     │   └── 3 (R)
         *     │       ├── []
         *     │       └── []
         *     └── 1 (B)
         *         ├── []
         *         └── []
         *
         * Wie man sehen kann, ist das Kriterium: "Für jeden Knoten gilt, dass alle Pfade zu darunter liegenden Blättern
         * gleich viele schwarze Knoten enthalten." verletzt. Der Knoten mit value 8 darf zwar auf schwarz folgen, aber
         * das vorherige Kriterium geht somit nicht mehr auf. Es gibt also in den vorgegebenen Klassen Fehler, wenn man
         * davon ausgeht, dass sie eigentlich immer korrekte rot-schwarz-Bäume erzeugen soll.
         */

        // Erzeugen eines RBTrees
        RBTree<Integer> tree = new RBTree<>(new RBNode<>(8, BLACK, null, null));

        // Überprüfen, ob der RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        IsRedBlackTree.Result checkResult = IsRedBlackTree.check(tree);
        assertNotEquals(IsRedBlackTree.Result.OK, checkResult);

    }

    @Test
    void stillRBTreeAfterInsert2() {

        // Erzeugen eines leeren RBTree
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        IsRedBlackTree.Result checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);
        // -> insert funktioniert, wenn Baum anfangs leer ist
    }


    @Test
    void stillRBTreeAfterDelete() {
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        IsRedBlackTree.Result checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);

        // Wert aus dem RBTree löschen
        tree.delete(4);

        // Überprüfen, ob der RBTree nach dem Löschen korrekt erkannt wird
        checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);
        // Wert suchen und überprüfen, ob er gefunden wird


        RBNode enkelLeftLeft = new RBNode<>(4, BLACK, null, null);
        RBNode enkelLeftRight = new RBNode<>(5, BLACK, null, null);
        RBNode enkelRightLeft = new RBNode<>(6, BLACK, null, null);
        RBNode enkelRightRight = new RBNode<>(7, BLACK, null, null);
        RBNode childLeft = new RBNode<>(2, RED, enkelLeftLeft, enkelLeftRight);
        RBNode childRight = new RBNode(3, RED,  enkelRightLeft, enkelRightRight);
        RBTree<Integer> baum = new RBTree(new RBNode<Integer>(1, BLACK, childLeft, childRight));

        baum.delete(2);

        // ist immer noch Baum - unabhängig davon testen, ob Knoten überhaupt gelöscht wurde
        assertEquals(OK, IsRedBlackTree.check(baum));

    }
    @Test
    public void testRebalanceAfterDelete() {
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        IsRedBlackTree.Result checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);

        // Wert aus dem RBTree löschen
        tree.delete(4);

        // Überprüfen, ob der RBTree nach dem Löschen korrekt erkannt wird
        checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);

        // Wert suchen und überprüfen, ob er gefunden wird
        assertNull(tree.search(4));
    }



    @Test
    void findAfterInsert(){
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);

        // soll gefunden werden, daher soll nicht null zurückgegeben werden
        assertNotEquals(null, tree.search(2));
    }

    @Test
        // Diese Methode überprüft, ob die Suche nach gelöschten Elementen in einem Rot-Schwarz-Baum null zurückgibt.
    void findAfterDelete(){
        // Erzeugt einen neuen RBNode mit dem Wert 4 und der Farbe SCHWARZ.
        RBNode enkelLeftLeft = new RBNode<>(4, BLACK, null, null);
// Erzeugt einen neuen RBNode mit dem Wert 5 und der Farbe SCHWARZ.
        RBNode enkelLeftRight = new RBNode<>(5, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 6 und der Farbe SCHWARZ.
        RBNode enkelRightLeft = new RBNode<>(6, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 7 und der Farbe SCHWARZ.
        RBNode enkelRightRight = new RBNode<>(7, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 2 und der Farbe ROT, der die beiden vorherigen Knoten als linke Kinder hat
        RBNode childLeft = new RBNode<>(2, RED, enkelLeftLeft, enkelLeftRight);
        // Erzeugt einen neuen RBNode mit dem Wert 3 und der Farbe ROT, der die beiden vorherigen Knoten als rechte Kinder hat.
        RBNode childRight = new RBNode(3, RED,  enkelRightLeft, enkelRightRight);
        // Erzeugt ein neues RBTree-Objekt mit dem Wurzelknoten, der den Wert 1, die Farbe SCHWARZ und die beiden erstellten Kinderknoten hat.
        RBTree<Integer> baum = new RBTree(new RBNode<Integer>(1, BLACK, childLeft, childRight));
// Löscht das Element mit dem Wert 7 aus dem Baum.
        baum.delete(7);
        // Löscht das Element mit dem Wert 3 aus dem Baum.
        baum.delete(3);
// Überprüft, ob die Suche nach dem Wert 7 im Baum null zurückgibt.
        assertEquals(null, baum.search(7));
        // Überprüft, ob die Suche nach dem Wert 3 im Baum null zurückgibt.
        assertEquals(null, baum.search(3));
    }


    @Test
    public void test() {
        // Erzeugt einen neuen RBNode mit dem Wert 1 und der Farbe SCHWARZ als Wurzel des Baums.
        RBNode<Integer> root = new RBNode(1, BLACK, null, null);
        // Erzeugt ein neues RBTree-Objekt mit dem Wurzelknoten, der zuvor erstellt wurde.
        RBTree<Integer> tree = new RBTree(root);
// Erzeugt einen neuen RBNode mit dem Wert 2 und der Farbe ROT.
        RBNode<Integer> child1 = new RBNode(2, RED, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 3 und der Farbe ROT.
        RBNode<Integer> child2 = new RBNode(3, RED, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 4 und der Farbe SCHWARZ.
        RBNode<Integer> child3 = new RBNode(4, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 5 und der Farbe SCHWARZ.
        RBNode<Integer> child4 = new RBNode(5, BLACK, null, null);
        // Setzt den Knoten "child1" als linkes Kind des Wurzelknotens "root" im Rot-Schwarz-Baum.
        tree.setChild(root, child1, 0);
        // Setzt den Knoten "child2" als rechtes Kind des Wurzelknotens "root" im Rot-Schwarz-Baum.
        tree.setChild(root, child2, 1);
        // Setzt den Knoten "child3" als linkes Kind des Knotens "child2" im Rot-Schwarz-Baum.
        tree.setChild(child2, child3, 0);
        // Setzt den Knoten "child4" als rechtes Kind des Knotens "child2" im Rot-Schwarz-Baum.
        tree.setChild(child2, child4, 1);

        // Überprüft, ob der Baum die Eigenschaften eines Rot-Schwarz-Baums erfüllt und erwartet den Rückgabewert "WRONG_AMOUNT_BLACK_NODES".
        assertEquals(WRONG_AMOUNT_BLACK_NODES, IsRedBlackTree.check(tree));
    }


    @Test
    public void testIncorrectRedBlackTree() {
        // Erzeugen eines fehlerhaften Rot-Schwarz-Baums
        RBNode<Integer> root = new RBNode<>(2, RED, null, null);
        RBNode<Integer> left = new RBNode<>(1, BLACK, null, null);
        RBNode<Integer> right = new RBNode<>(3, BLACK, null, null);

        RBTree<Integer> tree = new RBTree<>(root);
        tree.setChild(root, left, 0);
        tree.setChild(root, right, 1);

        // Überprüfen, ob der fehlerhafte Rot-Schwarz-Baum als fehlerhaft erkannt wird
        assertEquals(IsRedBlackTree.Result.ROOT_NOT_BLACK, IsRedBlackTree.check(tree));
    }

    @Test
    public void testFunctionalityOfRBTree() {
        // Testen der Funktionalität eines RBTree nach Einfüge- und Löschoperationen

        // Erzeugen eines leeren RBTree
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));


        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(6);
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);


        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        tree.delete(3);
        assertEquals(OK, IsRedBlackTree.check(tree));
    }

    @Test
    public void testCheckMethod() {
        // Erzeugen eines korrekten Rot-Schwarz-Baums
        RBNode<Integer> root = new RBNode<>(4, BLACK, null, null);
        RBNode<Integer> left = new RBNode<>(2, RED, null, null);
        RBNode<Integer> right = new RBNode<>(6, RED, null, null);
        RBNode<Integer> leftLeft = new RBNode<>(1, BLACK, null, null);
        RBNode<Integer> leftRight = new RBNode<>(3, BLACK, null, null);
        RBNode<Integer> rightLeft = new RBNode<>(5, BLACK, null, null);
        RBNode<Integer> rightRight = new RBNode<>(7, BLACK, null, null);

        RBTree<Integer> tree = new RBTree<>(root);
        tree.setChild(root, left, 0);
        tree.setChild(root, right, 1);
        tree.setChild(left, leftLeft, 0);
        tree.setChild(left, leftRight, 1);
        tree.setChild(right, rightLeft, 0);
        tree.setChild(right, rightRight, 1);

        // Überprüfen, ob die Methode `check` den korrekten Rot-Schwarz-Baum als korrekt erkennt
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Erzeugen eines fehlerhaften Rot-Schwarz-Baums
        RBNode<Integer> invalidRoot = new RBNode<>(2, RED, null, null);
        RBNode<Integer> invalidLeft = new RBNode<>(1, BLACK, null, null);
        RBNode<Integer> invalidRight = new RBNode<>(3, BLACK, null, null);

        RBTree<Integer> invalidTree = new RBTree<>(invalidRoot);
        invalidTree.setChild(invalidRoot, invalidLeft, 0);
        invalidTree.setChild(invalidRoot, invalidRight, 1);

        // Überprüfen, ob die Methode `check` den fehlerhaften Rot-Schwarz-Baum als fehlerhaft erkennt
        assertEquals(IsRedBlackTree.Result.ROOT_NOT_BLACK, IsRedBlackTree.check(invalidTree));
    }

    @Test

    public void testFunctionalityOfRBTreeidk() {
        // Testen der Funktionalität eines RBTree nach Einfüge- und Löschoperationen

        // Erzeugen eines leeren RBTree
        RBTree<Integer> tree = new RBTree<>();

        // Überprüfen, ob der leere RBTree korrekt erkannt wird
        assertEquals(IsRedBlackTree.Result.OK, IsRedBlackTree.check(tree));

        // Werte in den RBTree einfügen
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        IsRedBlackTree.Result checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);

        // Wert aus dem RBTree löschen
        tree.delete(3);

        // Überprüfen, ob der RBTree nach dem Löschen korrekt erkannt wird
        checkResult = IsRedBlackTree.check(tree);
        assertEquals(IsRedBlackTree.Result.OK, checkResult);
        // Wert suchen und überprüfen, ob er gefunden wird

    }


    @Test

    public void deleteRoot() {

        RBTree<Integer> tree = new RBTree();
        // Fügt den Wert 1 in den Rot-Schwarz-Baum ein.
        tree.insert(1);
        // Fügt den Wert 0 in den Rot-Schwarz-Baum ein.
        tree.insert(0);
        // Überprüft, ob der Rot-Schwarz-Baum nach dem Einfügen der Elemente immer noch die Eigenschaften eines Rot-Schwarz-Baums erfüllt.
        assertEquals(OK, IsRedBlackTree.check(tree));
        // Löscht das Element mit dem Wert 1 aus dem Rot-Schwarz-Baum.
        tree.delete(1);
        // Überprüft, ob der Rot-Schwarz-Baum nach dem Löschen des Elements immer noch die Eigenschaften eines Rot-Schwarz-Baums erfüllt.

        assertEquals(OK, IsRedBlackTree.check(tree));
    }

    @Test
    void rootcolor() {
        RBNode white = new RBNode(1, null, null, null);
        RBNode root = new RBNode(3, RED, null, null);
        RBTree<Integer> tree = new RBTree();
        tree.root = root;
        tree.setChild(root, white, 1);
        // Überprüft, ob die Farbe der Wurzel nicht schwarz ist und ob der Rot-Schwarz-Baum die Eigenschaften erfüllt.
        assertEquals(ROOT_NOT_BLACK,IsRedBlackTree.check(tree));


    }

    @Test

    public void emptyTree (){
        // leerer Baum
        RBTree <Integer> tree = new RBTree<>(null);
    }


    @Test
    void testWrongColorChild(){
        // Erzeugt einen neuen RBNode mit dem Wert 9 und der Farbe ROT, der Enkel von einem Enkel von einem Enkelknoten ist.
        RBNode enkelVonEnkelVonEnkel = new RBNode<>(9, RED, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 8 und der Farbe ROT, der Enkel von einem Enkelknoten ist und den vorherigen Knoten als linkes Kind hat.
        RBNode enkelVonEnkel = new RBNode<>(8, RED, enkelVonEnkelVonEnkel, null);
        // Erzeugt einen neuen RBNode mit dem Wert 4 und der Farbe SCHWARZ, der den vorherigen Knoten als linkes Kind hat.
        RBNode enkelLeftLeft = new RBNode<>(4, BLACK, enkelVonEnkel, null);
        // Erzeugt einen neuen RBNode mit dem Wert 5 und der Farbe SCHWARZ.
        RBNode enkelLeftRight = new RBNode<>(5, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 6 und der Farbe SCHWARZ.
        RBNode enkelRightLeft = new RBNode<>(6, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 7 und der Farbe SCHWARZ.
        RBNode enkelRightRight = new RBNode<>(7, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 2 und der Farbe ROT, der die beiden vorherigen Knoten als linke Kinder hat.
        RBNode childLeft = new RBNode<>(2, RED, enkelLeftLeft, enkelLeftRight);
        // Erzeugt einen neuen RBNode mit dem Wert 3 und der Farbe ROT, der die beiden vorherigen Knoten als rechte Kinder hat.
        RBNode childRight = new RBNode(3, RED,  enkelRightLeft, enkelRightRight);
        // Erzeugt ein neues RBTree-Objekt mit dem Wurzelknoten, der den Wert 1, die Farbe SCHWARZ und die beiden erstellten Kinderknoten hat.
        RBTree<Integer> baum = new RBTree(new RBNode<Integer>(1, BLACK, childLeft, childRight));
        // Überprüft, ob der Baum die Eigenschaften eines Rot-Schwarz-Baums erfüllt und erwartet den Rückgabewert "WRONG_COLOR_CHILD".
        assertEquals(WRONG_COLOR_CHILD, IsRedBlackTree.check(baum));
    }

    @Test
    void testWrongColorChild2(){
        // Erzeugt einen neuen RBNode mit dem Wert 9 und der Farbe ROT, der Enkel von einem Enkel von einem Enkelknoten ist.
        RBNode enkelVonEnkelVonEnkel = new RBNode<>(9, RED, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 8 und der Farbe ROT, der Enkel von einem Enkelknoten ist und den vorherigen Knoten als rechtes Kind hat.
        RBNode enkelVonEnkel = new RBNode<>(8, RED, null, enkelVonEnkelVonEnkel);
        // Erzeugt einen neuen RBNode mit dem Wert 4 und der Farbe SCHWARZ.
        RBNode enkelLeftLeft = new RBNode<>(4, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 5 und der Farbe SCHWARZ.
        RBNode enkelLeftRight = new RBNode<>(5, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 6 und der Farbe SCHWARZ.
        RBNode enkelRightLeft = new RBNode<>(6, BLACK, null, null);
        // Erzeugt einen neuen RBNode mit dem Wert 7 und der Farbe SCHWARZ, der den vorherigen Knoten als rechtes Kind hat.
        RBNode enkelRightRight = new RBNode<>(7, BLACK, null, enkelVonEnkel);
        // Erzeugt einen neuen RBNode mit dem Wert 2 und der Farbe ROT, der die beiden vorherigen Knoten als linke Kinder hat.
        RBNode childLeft = new RBNode<>(2, RED, enkelLeftLeft, enkelLeftRight);
        // Erzeugt einen neuen RBNode mit dem Wert 3 und der Farbe ROT, der die beiden vorherigen Knoten als rechte Kinder hat.
        RBNode childRight = new RBNode(3, RED,  enkelRightLeft, enkelRightRight);
        // Erzeugt ein neues RBTree-Objekt mit dem Wurzelknoten, der den Wert 1, die Farbe SCHWARZ und die beiden erstellten Kinderknoten hat.
        RBTree<Integer> baum = new RBTree(new RBNode<Integer>(1, BLACK, childLeft, childRight));
        // Überprüft, ob der Baum die Eigenschaften eines Rot-Schwarz-Baums erfüllt und erwartet den Rückgabewert "WRONG_COLOR_CHILD".
        assertEquals(WRONG_COLOR_CHILD, IsRedBlackTree.check(baum));
    }

    @Test
    void nullTree() {
        assertThrows(NullPointerException.class, () -> IsRedBlackTree.check(null));
    }



//--------------------------------------------------------------------

    @Test
// Diese Methode testet das Einfügen von Elementen in den Rot-Schwarz-Baum.
    public void testInsert() {
        RBTree<Integer> tree = new RBTree<>();
        // Fügt Elemente in den Baum ein.
        tree.insert(5);
        tree.insert(3);
        tree.insert(8);
        tree.insert(2);
        tree.insert(4);
        tree.insert(7);
        tree.insert(9);
        tree.insert(1);
        tree.insert(6);
        // Überprüft, ob die Elemente im Baum gefunden wird.
        assertTrue(tree.search(5) != null);
        assertTrue(tree.search(3) != null);
        assertTrue(tree.search(8) != null);
        assertTrue(tree.search(2) != null);
        assertTrue(tree.search(4) != null);
        assertTrue(tree.search(7) != null);
        assertTrue(tree.search(9) != null);
        assertTrue(tree.search(1) != null);
        assertTrue(tree.search(6) != null);
    }
// Diese Methode testet das Löschen von Elementen aus dem Rot-Schwarz-Baum.

    @Test
    public void testDelete() {
        RBTree<Integer> tree = new RBTree<>();
        // Fügt Elemente in den Baum ein.
        tree.insert(5);
        tree.insert(3);
        tree.insert(8);
        tree.insert(2);
        tree.insert(4);
        tree.insert(7);
        tree.insert(9);
        tree.insert(1);
        tree.insert(6);
        tree.delete(3);
        tree.delete(8);
        // Überprüft, ob die Elemente im Baum gefunden wird.
        assertTrue(tree.search(3) == null);
        assertTrue(tree.search(8) == null);
    }






}
    // Testet hier, ob IsRedBlackTree.check richtig funktioniert
    // und dann damit, ob RBTree richtig funktioniert. toString-
    // Methoden brauchen nicht getestet zu werden.
