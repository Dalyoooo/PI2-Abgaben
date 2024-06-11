package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Test-Klasse für die Klasse {@link Set}. Sie enthält Tests, die unabhängig
 * von der konkreten Implementierung der Listenverwaltung sind. Für E
 * wird in den Tests {@link Integer} verwendet. Alle Tests sind "final", damit
 * sie in abgeleiteten Klassen nicht überschrieben werden können. Es gibt
 * eigentlich keinen Grund, diese Klasse zu verändern.
 */
public abstract class SetTest
{
    /**
     * Diese Methode muss überschrieben werden und eine neue, leere Menge
     * des getesteten Typs für Integer-Werte zurückliefern.
     * @return Eine leere Menge, die als Startpunkt für Tests verwendet
     *         werden kann.
     */
    abstract Set<Integer> emptySet();

    /** Testet, dass eine neue Menge leer ist. */
    @Test
    public final void empty()
    {
        assertFalse(emptySet().iterator().hasNext());
    }

    /** Testet, ob {@link Set#contains} eingefügte Elemente auch findet. */
    @Test
    public final void contains()
    {
        final Set<Integer> set = emptySet();
        assertFalse(set.contains(1001));
        set.add(1001);
        assertTrue(set.contains(1001));
        assertFalse(set.contains(1002));
        set.add(1002);
        assertTrue(set.contains(1001));
        assertTrue(set.contains(1002));
        assertFalse(set.contains(1003));
        assertFalse(set.contains(null));
    }

    /** Testet, ob die Menge jedes Element nur einmal enthält. */
    @Test
    public final void noDuplicates()
    {
        // Menge mit einem Element.
        Set<Integer> set = emptySet();
        set.add(1001);
        Iterator<Integer> i = set.iterator();
        assertEquals(1001, i.next());
        assertFalse(i.hasNext());

        // Doppelter Eintrag sollte nichts ändern.
        set.add(1001);
        i = set.iterator();
        assertEquals(1001, i.next());
        assertFalse(i.hasNext());

        // Menge mit drei Elementen (Speicherreihenfolge ist unbekannt).
        set = emptySet();
        set.add(1001);
        set.add(1002);
        set.add(1003);
        i = set.iterator();
        i.next();
        i.next();
        i.next();
        assertFalse(i.hasNext());

        // Zweites Element noch einmal einfügen (Länge sollte gleich bleiben).
        set.add(1002);
        i = set.iterator();
        i.next();
        i.next();
        i.next();
        assertFalse(i.hasNext());
    }

    /** Testet Aufrufe, die Ausnahmen erzeugen sollen. */
    @Test
    public final void exceptions()
    {
        assertThrows(NullPointerException.class, () -> emptySet().add(null));
        assertThrows(NoSuchElementException.class, () -> emptySet().iterator().next());
    }
}
