package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Eine spezialisierte Testklasse für eine Menge mit naiver Selbstanordnung.
 * @author Dalia Benamar
 */

public class SetNaiveTest extends SetTest
{

    /**
     * Liefert eine neue, leere Menge des getesteten Typs 
     * für Integer-Werte.
     * @return Eine neue Menge mit naiver Selbstanordnung.
     */

    Set<Integer> emptySet()
    {
        return new SetNaive<>();
    }


    /**
     * testet die Methode add und die Organisation nach jedem hinzugefügten Element.
     */
@Test
public void  naiveAddFunktioniert(){
// Erstelle eine neue leere Menge des getesteten Typs
    var naiveSet = emptySet();

    // Überprüfen, ob das erste Element der Menge null ist
    assertEquals(naiveSet.getHead(), null);

    // Füge die Elemente 1 und 2 zur Menge hinzu
    naiveSet.add(1);
    naiveSet.add(2);
    // Erhalte den aktuellen Knoten (das erste Element der Menge)
    Node<Integer> currentNode = naiveSet.getHead();
    // Überprüfe, ob das Element des aktuellen Knotens 2 ist
    assertEquals(currentNode.getElement(), 2);
    // Gehe zum nächsten Knoten
    currentNode = currentNode.getNext();
    // Überprüfe, ob das Element des aktuellen Knotens 1 ist
    assertEquals(currentNode.getElement(), 1);

    // Füge das Element 1 erneut zur Menge hinzu
    naiveSet.add(1);
    // Der Knoten mit dem Element 1 wird an die vorderste Stelle verschoben
    // Erhalte den aktuellen Knoten (das erste Element der Menge)
    currentNode = naiveSet.getHead();
    // Überprüfe, ob das Element des aktuellen Knotens 2 ist
    assertEquals(currentNode.getElement(), 2);
    // Gehe zum nächsten Knoten
    currentNode = currentNode.getNext();
    // Überprüfe, ob das Element des aktuellen Knotens 2 ist und nach dem Prinzip wird der Rest geprüft.
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(2);
    currentNode = naiveSet.getHead();
    assertEquals(currentNode.getElement(), 2);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(3);
    currentNode = naiveSet.getHead();
    assertEquals(currentNode.getElement(), 3);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 2);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(4);
    currentNode = naiveSet.getHead();
    assertEquals(currentNode.getElement(), 4);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 3);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 2);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(1);
    currentNode = naiveSet.getHead();
    assertEquals(currentNode.getElement(), 4);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 3);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 2);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(2);
    currentNode = naiveSet.getHead();
    assertEquals(currentNode.getElement(), 4);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 3);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 2);
    currentNode = currentNode.getNext();
    assertEquals(currentNode.getElement(), 1);

    naiveSet.add(4);

    currentNode = naiveSet.getHead();
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
    public void naiveIterationFunktioniertRichtig()
    {
        // Eine neue leere Menge erstellen
        var naiveSet = emptySet();
        // Elemente zur Menge hinzufügen
        naiveSet.add(1);
        naiveSet.add(2);
        naiveSet.add(1);
        naiveSet.add(2);
        naiveSet.add(3);
        naiveSet.add(4);
        naiveSet.add(1);
        naiveSet.add(1);
        naiveSet.add(2);
        naiveSet.add(1);

        // Einen Iterator erstellen, um die Menge zu durchlaufen
        var naiveListIt = naiveSet.iterator();
        // Überprüfen, ob das nächste Element des Iterators 4 ist
        assertEquals(4,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 3 ist
        assertEquals(3,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 2 ist
        assertEquals(2,naiveListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 1 ist
        assertEquals(1,naiveListIt.next());
        // Überprüfen, ob eine NoSuchElementException ausgelöst wird,
        // wenn versucht wird, ein weiteres Element aus dem Iterator abzurufen
        assertThrows(NoSuchElementException.class,()-> { naiveListIt.next();});
    }



}



