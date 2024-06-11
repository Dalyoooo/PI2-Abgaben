package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author  Dalia Benamar
 */

public class FrequencyCountSetTest extends SetTest
{

    /**
     * Gibt leeres FrequencyCountSet zurück
     * @return FrequencyCountSet
     */
    Set<Integer> emptySet()
    {
        return new FrequencyCountSet<>();
    }

    /**
     * testet die Methode add nach jedem Hinzufügen eines Elementes
     * Die Frequency wird auch nachjedem Element für alle Werte überprüft
     */
    @Test
    public void  FrequencyCountSetAddFunktioniert(){
        // Erstelle eine neue leere Menge des getesteten Typs
        var fc = emptySet();
        // Überprüfe, ob das erste Element der Menge null ist
        assertEquals(fc.getHead(), null);

        // Füge die Elemente 1 und 2 zur Menge hinzu
        fc.add(1);
        fc.add(2);

        // Den aktuellen Knoten abrufen und überprüfen, ob das Element 1 ist
        Node<Integer> currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 1);
        // Zum nächsten Knoten gehen und überprüfen, ob das Element 2 ist
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);

        // Füge das Element 1 erneut zur Menge hinzu
        fc.add(1);
        // Den aktuellen Knoten abrufen und überprüfen, ob das Element 1 ist
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 1);
        // Zum nächsten Knoten gehen und überprüfen, ob das Element 2 ist. Das alles wird dann für alle weiteren hinzugefügten Elemente nochmal abgefragt.
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);

        fc.add(2);
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        fc.add(3);
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);

        fc.add(4);
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 4);

        fc.add(1);
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 1);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 4);

        fc.add(2);
        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 2);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 4);

        fc.add(4);

        currentNode = fc.getHead();
        assertEquals(currentNode.getElement(), 2);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 1);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 4);

        currentNode = currentNode.getNext();
        assertEquals(currentNode.getElement(), 3);
       // Zum nächsten Knoten gehen und überprüfen, ob es kein weiteres Element gibt
        currentNode = currentNode.getNext();
        assertEquals(currentNode, null);

    }

    /**
     * In dieser Testmethode werden folgende Fälle getestet:
     * Ein Element wird zum Head
     * Nicht mehr umsortiert als nötig
     */
    @Test
    public void sortiertNichtMehrAlsNoetig()
    {
        // Eine neue leere Menge erstellen
        var fc = emptySet();
        // Elemente zur Menge hinzufügen
        fc.add(1);
        fc.add(2);
        fc.add(3);
        fc.add(4);

        // Das Element 4 erneut zur Menge hinzufügen
        fc.add(4);

        // Einen Iterator erstellen, um die Menge zu durchlaufen
        var fcSetIt = fc.iterator();
        // Überprüfen, ob das erste Element des Iterators 4 ist
        assertEquals(4,fcSetIt.next());
        // Überprüfen, ob das nächste Element des Iterators 1 ist
        assertEquals(1,fcSetIt.next());
        // Überprüfen, ob das nächste Element des Iterators 2 ist
        assertEquals(2,fcSetIt.next());
        // Überprüfen, ob das nächste Element des Iterators 3 ist
        assertEquals(3,fcSetIt.next());

       // Das Element 3 erneut zur Menge hinzufügen
        fc.add(3);
        // Einen neuen Iterator erstellen, um die Menge erneut zu durchlaufen
        var fcSetIt2 = fc.iterator();
        // Überprüfen, ob das erste Element des zweiten Iterators 3 ist
        assertEquals(3,fcSetIt2.next());
        // Überprüfen, ob das nächste Element des zweiten Iterators 4 ist
        assertEquals(4,fcSetIt2.next());
        // Überprüfen, ob das nächste Element des zweiten Iterators 1 ist
        assertEquals(1,fcSetIt2.next());
        // Überprüfen, ob das nächste Element des zweiten Iterators 2 ist
        assertEquals(2,fcSetIt2.next());
    }

    /**
     * überprüft die richtige Reihenfolge durch die Iteration, dabei werden Elemente hinzugefügt.
     */
    @Test
    public void FrequencyCountSetIterationFunktioniertRichtig()
    {
        // Eine neue leere Menge erstellen
        var fc = emptySet();
        // Elemente zur Menge hinzufügen
        fc.add(1);
        fc.add(2);
        fc.add(1);
        fc.add(2);
        fc.add(3);
        fc.add(4);
        fc.add(1);
        fc.add(1);
        fc.add(2);
        fc.add(1);
        // Einen Iterator erstellen, um die Menge zu durchlaufen
        var fcListIt = fc.iterator();
        // Überprüfen, ob das erste Element des Iterators 1 ist
        assertEquals(1,fcListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 2 ist
        assertEquals(2,fcListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 3 ist
        assertEquals(3,fcListIt.next());
        // Überprüfen, ob das nächste Element des Iterators 4 ist
        assertEquals(4,fcListIt.next());
        // Überprüfen, ob eine NoSuchElementException ausgelöst wird,
        // wenn versucht wird, ein weiteres Element aus dem Iterator abzurufen
        assertThrows(NoSuchElementException.class,()-> { fcListIt.next();});
    }



}



