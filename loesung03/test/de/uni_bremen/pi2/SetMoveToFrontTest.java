package de.uni_bremen.pi2;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Dalia Benamar
 */
public class SetMoveToFrontTest extends SetTest {

    /**
     * Gibt die leere Menge von SetMoveToFront zurück
     * @return SetMoveToFront
     */
    @Override
    Set<Integer> emptySet() { return new SetMoveToFront<>(); }

    /**
     * testet die Methode add und die Organisation nach jedem hinzugefügten Element.
     */
    @Test
    public void NaiveFunktioniertRichtig()
    {


            // Erstelle eine neue leere Menge des getesteten Typs
            var mtfSet = emptySet();
            // Überprüfen, ob das erste Element der Menge null ist
            assertEquals(mtfSet.getHead(), null);
            // Füge die Elemente 1 und 2 zur Menge hinzu
            mtfSet.add(1);
            mtfSet.add(2);
        // Erhalte den aktuellen Knoten (das erste Element der Menge)
            Node<Integer> currentNode = mtfSet.getHead();
        // Überprüfe, ob das Element des aktuellen Knotens 2 ist
            assertEquals(currentNode.getElement(), 2);
        // Gehe zum nächsten Knoten
            currentNode = currentNode.getNext();
        // Überprüfe, ob das Element des aktuellen Knotens 1 ist
            assertEquals(currentNode.getElement(), 1);

        // Füge das Element 1 erneut zur Menge hinzu
            mtfSet.add(1);
        // Der Knoten mit dem Element 1 wird an die vorderste Stelle verschoben
        // Erhalte den aktuellen Knoten (das erste Element der Menge)
            currentNode = mtfSet.getHead();
        // Überprüfe, ob das Element des aktuellen Knotens 1 ist
            assertEquals(currentNode.getElement(), 1);
        // Gehe zum nächsten Knoten
            currentNode = currentNode.getNext();
        // Überprüfe, ob das Element des aktuellen Knotens 2 ist und nach dem Prinzip wird der Rest geprüft.
            assertEquals(currentNode.getElement(), 2);

            mtfSet.add(2);
            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 2);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 1);

            mtfSet.add(3);
            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 3);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 2);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 1);

            mtfSet.add(4);
            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 4);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 3);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 2);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 1);

            mtfSet.add(1);
            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 1);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 4);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 3);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 2);

            mtfSet.add(2);
            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 2);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 1);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 4);
            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 3);

            mtfSet.add(4);

            currentNode = mtfSet.getHead();
            assertEquals(currentNode.getElement(), 4);

            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 2);

            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 1);

            currentNode = currentNode.getNext();
            assertEquals(currentNode.getElement(), 3);
         // Zum nächsten Knoten gehen und überprüfen, ob es kein weiteres Element gibt
            currentNode = currentNode.getNext();
            assertEquals(currentNode, null);

    }
    /**
     * überprüft die richtige Reihenfolge durch die Iteration, dabei werden Elemente hinzugefügt.
     */
    @Test
    public void SetMoveToFrontIterationFunktioniertRichtig()
    {
        // Eine neue leere Menge erstellen
        var mtf = emptySet();

        // Elemente zur Menge hinzufügen
        mtf.add(1);
        mtf.add(2);
        mtf.add(1);
        mtf.add(2);
        mtf.add(3);
        mtf.add(4);
        mtf.add(1);
        mtf.add(2);
        mtf.add(4);

        // Einen Iterator erstellen, um die Menge zu durchlaufen
        var mtfSetIt = mtf.iterator();
        // Überprüfen, ob das erste Element des Iterators 4 ist
        assertEquals(4,mtfSetIt.next());
        // Überprüfen, ob das erste Element des Iterators 2 ist
        assertEquals(2,mtfSetIt.next());
        // Überprüfen, ob das erste Element des Iterators 1 ist
        assertEquals(1,mtfSetIt.next());
        // Überprüfen, ob das erste Element des Iterators 3 ist
        assertEquals(3,mtfSetIt.next());
        // Überprüfen, ob eine NoSuchElementException ausgelöst wird,
        // wenn versucht wird, ein weiteres Element aus dem Iterator abzurufen
        assertThrows(NoSuchElementException.class,()-> { mtfSetIt.next();});
    }

}
