package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * @author Dalia Benamar
 */

public class SetTTest extends SetTest
{

    /**
     * Gibt die leere Menge von SetMoveToFront zurück
     * @return SetTranspose
     */

    Set<Integer> emptySet()
    {
        return new SetT<>();
    }

    /**
     * testet die Methode add und die Organisation nach jedem hinzugefügten Element.
     */
    @Test
    public void  transposeAddFunktioniert(){
// Erstelle eine neue leere Menge des getesteten Typs
        var transpose = emptySet();

        // Überprüfen, ob das erste Element der Menge null ist
        assertEquals(transpose.getHead(), null);

        // Füge die Elemente 1 und 2 zur Menge hinzu
        transpose.add(1);
        transpose.add(2);

        // Erhalte den aktuellen Knoten (das erste Element der Menge)
        Node<Integer> currentNode = transpose.getHead();
        // Überprüfe, ob das Element des aktuellen Knotens 2 ist
        assertEquals(currentNode.getElement(), 2);
        // Gehe zum nächsten Knoten
        currentNode = currentNode.getNext();
        // Überprüfe, ob das Element des aktuellen Knotens 1 ist
        assertEquals(currentNode.getElement(), 1);

        // Füge das Element 1 erneut zur Menge hinzu
        transpose.add(1);
        // Der Knoten mit dem Element 1 wird an die vorderste Stelle verschoben
        // Erhalte den aktuellen Knoten (das erste Element der Menge)
        currentNode = transpose.getHead();
        // Überprüfe, ob das Element des aktuellen Knotens 1 ist
        assertEquals(currentNode.getElement(), 1);
        // Gehe zum nächsten Knoten
        currentNode = currentNode.getNext();
        // Überprüfe, ob das Element des aktuellen Knotens 2 ist und nach dem Prinzip wird der Rest geprüft.
        assertEquals(currentNode.getElement(), 2);

        transpose.add(2);
        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        transpose.add(3);
        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        transpose.add(4);
        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 4);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        transpose.add(1);
        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 4);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);

        transpose.add(2);
        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 4);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        transpose.add(4);

        currentNode = transpose.getHead();
        assertEquals(currentNode.getElement(), 4);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        // Zum nächsten Knoten gehen und überprüfen, ob es kein weiteres Element gibt
        currentNode = currentNode.getNext();
        assertEquals(currentNode, null);

    }
    /**
     * überprüft die richtige Reihenfolge durch die Iteration, dabei werden Elemente hinzugefügt.
     */
    @Test
    public void transposeIterationFunktioniertRichtig()
    {
        // Eine neue leere Menge erstellen
        var transpose = emptySet();

        // Elemente zur Menge hinzufügen
        transpose.add(1);
        transpose.add(2);
        transpose.add(1);
        transpose.add(2);
        transpose.add(3);
        transpose.add(4);
        transpose.add(1);
        transpose.add(1);
        transpose.add(2);
        transpose.add(1);

        // Einen Iterator erstellen, um die Menge zu durchlaufen
        var naiveListIt = transpose.iterator();
        // Überprüfen, ob das erste Element des Iterators 1 ist
        assertEquals(1,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 4 ist
        assertEquals(4,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 2 ist
        assertEquals(2,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 3 ist
        assertEquals(3,naiveListIt.next());
        // Überprüfen, ob eine NoSuchElementException ausgelöst wird,
        // wenn versucht wird, ein weiteres Element aus dem Iterator abzurufen
        assertThrows(NoSuchElementException.class,()-> { naiveListIt.next();});
    }



}



