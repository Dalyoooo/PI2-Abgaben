package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;

/**
 * @author Hanna Becker
 */
public class DoodlePPTest {
    /**
     * Testet eine Eingabe, bei der es kein Ergebnis gibt, weil die Aufteilung unmöglich ist.
     */
    @Test
    public void impossibleDistribution() {
        final boolean[][] studentAvailabilities = {
                {false, true},
                {false, true},
                {false, true},
                {true, false}
        };
        final int[] tutorialCapacities = {2, 2};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // unmögliche Aufteilung, daher sollte tutorialOfStudents null werden, wodurch in checkDistribution ein fail
        // geworfen wird um zu verdeutlichen, dass die Aufteilung nicht erfolgreich war
        assertThrows(AssertionFailedError.class, () -> checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));

    }

    /**
     * Backtrackt einmal bis zum allerersten Index, ist aber lösbar.
     */
    @Test
    public void backtrackToBeginning() {
        /*
        Durch diesen Test wird die Zeile
                if (student < 0) {
                    return null;
                }
        auf ihr Zeichen "<" getestet. Bei "<=" würde der Test fehlschlagen. Somit können Mutationen an der Stelle gekillt
        werden.
         */
        final boolean[][] studentAvailabilities = {
                {true, true},
                {true, true},
                {true, false},
                {true, false}
        };
        final int[] tutorialCapacities = {2, 2};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // Verteilung sollte möglich sein, daher kein fail.
        checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents);

    }

    /**
     * Testet, ob bei null-Eingabe der studentAvailabilities null zurückgegeben wird.
     */
    @Test
    public void nullInputStudents() {
        final int[] tutorialCapacities = {5, 5};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        // Kontrolliert, ob null-Werte abgefangen werden, tutorialOfStudents sollte null werden und somit gibt es einen
        // fail in checkDistribution
        final int[] tutorialOfStudents = DoodlePP.distribute(null, tutorialCapacitiesLeft);
        assertThrows(AssertionFailedError.class, () -> checkDistribution(null, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));
    }

    /**
     * Testet, ob bei leerer Eingabe der studentAvailabilities null zurückgegeben wird.
     */
    @Test
    public void emptyInputStudents() {
        final boolean[][] studentAvailabilities = {};
        final int[] tutorialCapacities = {5, 5};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        // Keine mögliche Aufteilung, daher sollte tutorialOfStudents null sein und ein fail in checkDistribution()
        // geworfen werden
        final int[] tutorialOfStudents = DoodlePP.distribute(studentAvailabilities, tutorialCapacitiesLeft);
        assertThrows(AssertionFailedError.class, () -> checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));
    }

    /**
     * Testet, ob der Durchlauf fehlschlägt, wenn das Array für die Tutorien null ist.
     */
    @Test
    public void tutorialNull() {
        final boolean[][] studentAvailabilities = {
                {false, true, true, true, true},
                {true, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, false, true},
                {true, true, true, true, true},
                {false, true, true, false, true},
                {false, false, false, false, true},
                {false, true, false, true, false},
                {true, true, true, true, false},
                {false, false, false, false, true},
                {false, false, false, true, true},
                {true, true, false, false, true},
                {true, true, true, true, true},
                {false, true, true, true, false},
                {false, true, true, false, true},
                {true, true, false, true, false},
                {true, true, true, true, true},
                {false, true, false, false, true},
                {false, false, true, true, true},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, false, true, false, true},
                {true, false, true, true, true},
                {true, false, false, true, false},
                {true, true, true, false, false},
                {false, true, true, false, true},
                {false, true, true, false, false},
                {true, true, true, true, true},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {true, false, true, true, false},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {false, false, true, true, true},
                {false, true, false, true, true},
                {false, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, true, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, false, true, true},
                {true, true, false, true, false},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, true, true, false, true},
                {false, true, false, true, false},
                {false, true, false, true, true},
                {false, true, true, false, false},
                {true, true, false, true, false},
                {true, true, false, false, true},
        };

        final int[] tutorialCapacities = null;
        final int[] tutorialCapacitiesLeft = null;

        // keine mögliche Aufteilung, null-Fall sollte abgefangen werden.
        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // tutorialOfStudents sollte null sein, daher wird ein fail geworfen
        assertThrows(AssertionFailedError.class, () -> checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));
    }

    /**
     * Testet, ob bei fünf leeren Tutorien null zurückgegeben wird.
     */
    @Test
    public void tutorialEmpty() {
        final boolean[][] studentAvailabilities = {
                {false, true, true, true, true},
                {true, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, false, true},
                {true, true, true, true, true},
                {false, true, true, false, true},
                {false, false, false, false, true},
                {false, true, false, true, false},
                {true, true, true, true, false},
                {false, false, false, false, true},
                {false, false, false, true, true},
                {true, true, false, false, true},
                {true, true, true, true, true},
                {false, true, true, true, false},
                {false, true, true, false, true},
                {true, true, false, true, false},
                {true, true, true, true, true},
                {false, true, false, false, true},
                {false, false, true, true, true},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, false, true, false, true},
                {true, false, true, true, true},
                {true, false, false, true, false},
                {true, true, true, false, false},
                {false, true, true, false, true},
                {false, true, true, false, false},
                {true, true, true, true, true},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {true, false, true, true, false},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {false, false, true, true, true},
                {false, true, false, true, true},
                {false, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, true, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, false, true, true},
                {true, true, false, true, false},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, true, true, false, true},
                {false, true, false, true, false},
                {false, true, false, true, true},
                {false, true, true, false, false},
                {true, true, false, true, false},
                {true, true, false, false, true},
        };

        // nicht nur null-Einträge oder leere Arrays sollten null zurückgeben, sondern auch wenn die Tutorien-Kapazität
        // 0 ist.
        final int[] tutorialCapacities = {0, 0, 0, 0, 0};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // tutorialOfStudents sollte null sein, fail wird geworfen
        assertThrows(AssertionFailedError.class, () -> checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));
    }

    /**
     * Testet, ob bei leerer Eingabe für die Tutorien null zurückgegeben wird.
     */
    @Test
    public void tutorialCompletelyEmpty() {
        final boolean[][] studentAvailabilities = {
                {false, true, true, true, true},
                {true, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, false, true},
                {true, true, true, true, true},
                {false, true, true, false, true},
                {false, false, false, false, true},
                {false, true, false, true, false},
                {true, true, true, true, false},
                {false, false, false, false, true},
                {false, false, false, true, true},
                {true, true, false, false, true},
                {true, true, true, true, true},
                {false, true, true, true, false},
                {false, true, true, false, true},
                {true, true, false, true, false},
                {true, true, true, true, true},
                {false, true, false, false, true},
                {false, false, true, true, true},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, false, true, false, true},
                {true, false, true, true, true},
                {true, false, false, true, false},
                {true, true, true, false, false},
                {false, true, true, false, true},
                {false, true, true, false, false},
                {true, true, true, true, true},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {true, false, true, true, false},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {false, false, true, true, true},
                {false, true, false, true, true},
                {false, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, true, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, false, true, true},
                {true, true, false, true, false},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, true, true, false, true},
                {false, true, false, true, false},
                {false, true, false, true, true},
                {false, true, true, false, false},
                {true, true, false, true, false},
                {true, true, false, false, true},
        };

        // ist ein anderer Fall als tutorialCapacities = null, daher müssen beide getestet werden
        final int[] tutorialCapacities = {};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // tutorialOfStudents should be null so checkDistribution returns fail
        assertThrows(AssertionFailedError.class, () -> checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents));
    }

    /**
     * Testet, ob bei leer gebliebenen Plätzen eine Liste zurückgegeben wird. Die leeren Plätze werden über die Konsole angezeigt.
     */
    @Test
    public void remainingCapacity() {
        final boolean[][] studentAvailabilities = {
                {false, true, true, true, true},
                {true, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, false, true},
                {true, true, true, true, true},
                {false, true, true, false, true},
                {false, false, false, false, true},
                {false, true, false, true, false},
                {true, true, true, true, false},
                {false, false, false, false, true},
                {false, false, false, true, true},
                {true, true, false, false, true},
                {true, true, true, true, true},
                {false, true, true, true, false},
                {false, true, true, false, true},
                {true, true, false, true, false},
                {true, true, true, true, true},
                {false, true, false, false, true},
                {false, false, true, true, true},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, false, true, false, true},
                {true, false, true, true, true},
                {true, false, false, true, false},
                {true, true, true, false, false},
                {false, true, true, false, true},
                {false, true, true, false, false},
                {true, true, true, true, true},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {true, false, true, true, false},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {false, false, true, true, true},
                {false, true, false, true, true},
                {false, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, true, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, false, true, true},
                {true, true, false, true, false},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, true, true, false, true},
                {false, true, false, true, false},
                {false, true, false, true, true},
                {false, true, true, false, false},
                {true, true, false, true, false},
                {true, true, false, false, true},
        };
        /*
        Hat mehr Tutorienplätze, als es Studenten gibt. Die Aufteilung sollte aber trotzdem gelingen.
         */

        final int[] tutorialCapacities = {12, 12, 12, 12, 12};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        // muss nicht gezwungen zu gleichgroßer Aufteilung führen, daher sollte die Verteilung möglich sein, wenn die
        // Studierenden auf die Tutorien aufgeteilt werden können, ohne die Kapazität zu überschreiten
        checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents);
    }




    /**
     * Testet das Verteilen von 50 Studierenden auf 5 Tutorien der Kapazität 10.
     */
    @Test
    public void test5TutorialsOf10() {
        final boolean[][] studentAvailabilities = {
                {false, true, true, true, true},
                {true, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, false, true},
                {true, true, true, true, true},
                {false, true, true, false, true},
                {false, false, false, false, true},
                {false, true, false, true, false},
                {true, true, true, true, false},
                {false, false, false, false, true},
                {false, false, false, true, true},
                {true, true, false, false, true},
                {true, true, true, true, true},
                {false, true, true, true, false},
                {false, true, true, false, true},
                {true, true, false, true, false},
                {true, true, true, true, true},
                {false, true, false, false, true},
                {false, false, true, true, true},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, false, true, false, true},
                {true, false, true, true, true},
                {true, false, false, true, false},
                {true, true, true, false, false},
                {false, true, true, false, true},
                {false, true, true, false, false},
                {true, true, true, true, true},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {true, false, true, true, false},
                {true, false, true, true, true},
                {false, true, false, true, false},
                {false, false, true, true, true},
                {false, true, false, true, true},
                {false, true, true, false, true},
                {false, true, true, true, true},
                {true, true, true, true, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, false, true, true},
                {true, true, false, true, false},
                {false, true, true, true, false},
                {true, false, true, true, true},
                {true, true, true, false, true},
                {false, true, false, true, false},
                {false, true, false, true, true},
                {false, true, true, false, false},
                {true, true, false, true, false},
                {true, true, false, false, true},
        };

        final int[] tutorialCapacities = {10, 10, 10, 10, 10};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents);
    }

    /**
     * Testet das Verteilen von 70 Studierenden auf 7 Tutorien der Kapazität 10.
     */
    @Test
    public void test7TutorialsOf10() {
        final boolean[][] studentAvailabilities = {
                {true, true, true, true, true, true, false},
                {true, true, true, false, false, true, true},
                {false, true, false, false, false, false, false},
                {false, true, false, false, true, true, false},
                {false, false, false, false, true, true, true},
                {false, true, false, false, false, false, false},
                {true, true, false, true, false, false, false},
                {true, true, true, false, true, false, false},
                {false, false, false, false, false, true, false},
                {true, true, false, false, false, false, false},
                {true, false, false, false, false, true, true},
                {false, true, false, false, true, true, true},
                {false, true, false, true, false, false, true},
                {true, false, false, true, true, false, true},
                {false, false, true, false, false, false, false},
                {true, true, true, true, true, true, true},
                {true, false, false, false, true, true, true},
                {false, false, true, false, false, false, false},
                {false, true, false, true, true, false, false},
                {false, false, true, false, true, false, false},
                {false, false, false, false, false, true, true},
                {true, true, true, true, true, true, false},
                {true, false, false, false, false, false, false},
                {false, false, false, true, false, true, true},
                {true, false, true, true, false, false, false},
                {false, false, false, true, true, true, false},
                {true, true, false, true, false, true, false},
                {true, false, true, true, false, true, true},
                {true, false, false, false, true, false, true},
                {true, true, true, false, true, false, false},
                {true, true, true, false, false, false, true},
                {true, false, false, false, true, false, false},
                {true, true, true, false, true, false, false},
                {true, false, false, true, false, true, true},
                {true, true, false, false, true, false, true},
                {false, false, false, true, false, false, true},
                {false, false, true, false, false, false, true},
                {true, false, true, false, false, true, true},
                {false, false, true, false, false, true, false},
                {true, false, false, false, false, true, false},
                {true, true, true, true, false, true, false},
                {false, false, true, true, false, false, false},
                {true, true, true, false, true, false, true},
                {true, true, true, false, true, true, true},
                {true, false, false, true, false, false, true},
                {false, false, false, true, false, false, true},
                {true, false, false, false, true, false, false},
                {false, false, true, false, false, true, false},
                {false, true, false, false, false, false, false},
                {true, true, true, false, false, true, false},
                {true, true, false, true, false, true, true},
                {false, true, false, false, false, true, true},
                {true, false, false, false, true, true, true},
                {false, false, false, true, true, false, false},
                {true, false, true, false, false, true, false},
                {false, true, false, false, true, false, true},
                {false, false, false, true, false, false, false},
                {false, false, true, false, true, false, false},
                {true, false, true, true, true, false, true},
                {false, false, false, false, false, true, false},
                {true, true, true, false, false, true, false},
                {true, true, true, true, true, true, false},
                {true, false, true, false, true, true, true},
                {true, true, true, true, false, true, true},
                {false, true, true, true, true, false, false},
                {false, true, true, false, true, true, true},
                {false, true, false, false, true, false, true},
                {true, false, false, false, false, true, true},
                {true, false, true, true, false, true, true},
                {true, false, false, true, false, true, false},
        };

        final int[] tutorialCapacities = {10, 10, 10, 10, 10, 10, 10};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents);
    }


    /**
     * Testet das Verteilen von 100 Studierenden auf 10 Tutorien der Kapazität 10.
     * Dieser Test wird aktuell ignoriert.
     */
    /*
    Der Test läuft durch, wenn der letzte Eintrag von studentAvailabilities an den Anfang gesetzt wird. Weitere
    Erklärungen folgen unten in den Aufgaben.
     */
    @Test
    @Disabled
    public void test10TutorialsOf10() {
        final boolean[][] studentAvailabilities = {
                {true, true, true, true, false, true, false, false, false, false},
                {true, false, false, true, true, true, true, true, true, true},
                {false, true, false, true, false, true, false, false, true, true},
                {true, true, false, true, true, false, false, true, true, true},
                {false, true, true, false, false, true, true, false, true, true},
                {true, true, true, true, false, true, true, true, true, true},
                {true, false, true, true, false, true, true, true, true, false},
                {true, false, true, false, true, true, false, true, true, true},
                {true, true, true, true, true, true, true, false, false, false},
                {true, true, true, true, true, true, false, false, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, false, true, true, true, true, false, true, true},
                {true, true, true, false, true, false, true, false, true, true},
                {false, true, false, false, true, true, false, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {false, true, true, true, true, true, false, true, true, true},
                {true, true, true, false, true, false, false, true, false, false},
                {true, true, true, true, true, true, true, true, true, true},
                {true, true, false, true, false, true, false, false, true, true},
                {true, false, true, true, true, false, true, false, true, true},
                {false, true, false, true, true, true, true, false, true, true},
                {true, true, true, true, true, true, false, true, true, true},
                {true, false, false, false, true, false, true, false, false, false},
                {true, false, false, true, true, true, false, false, true, true},
                {true, true, false, true, true, false, false, true, true, true},
                {true, false, true, true, true, true, true, true, true, true},
                {false, true, false, true, false, true, false, false, true, false},
                {true, true, true, true, false, true, true, false, false, false},
                {false, false, true, true, true, true, true, false, true, true},
                {false, true, true, false, true, true, false, true, true, true},
                {true, true, false, false, true, true, true, true, true, false},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, true, false, true, false, true, true, true},
                {true, true, false, false, true, true, false, false, true, true},
                {true, false, true, false, true, true, true, true, true, true},
                {true, true, true, true, false, false, true, true, true, true},
                {true, true, true, true, true, true, false, true, true, true},
                {true, true, false, false, true, false, false, true, true, true},
                {false, false, true, false, true, true, true, false, false, true},
                {true, true, false, true, true, true, true, true, true, true},
                {true, false, true, true, false, false, true, false, false, true},
                {false, true, false, false, true, true, false, true, false, true},
                {true, true, true, true, true, true, false, true, true, true},
                {false, true, true, true, true, false, true, false, true, true},
                {true, true, true, false, false, false, false, false, false, false},
                {true, true, true, false, false, true, false, false, true, true},
                {true, true, true, true, true, true, true, true, true, true},
                {false, false, true, false, false, false, false, false, true, false},
                {true, true, true, false, true, true, true, true, true, false},
                {true, true, true, true, false, false, true, true, true, true},
                {true, true, true, true, true, true, true, true, true, true},
                {false, false, true, false, false, false, true, true, true, false},
                {true, true, true, false, false, false, true, true, true, false},
                {false, true, false, false, false, true, true, true, true, false},
                {true, true, false, true, true, false, false, false, false, true},
                {true, true, true, false, false, true, true, true, true, true},
                {true, true, true, false, false, true, true, false, true, false},
                {false, false, true, true, false, true, true, false, false, true},
                {true, true, true, true, true, true, true, true, true, false},
                {true, false, false, false, true, true, true, false, true, false},
                {true, false, true, false, true, true, false, false, true, true},
                {true, true, true, true, false, false, true, false, false, true},
                {true, true, true, true, true, true, true, false, false, true},
                {true, false, false, false, false, true, false, false, false, true},
                {true, true, false, false, true, true, false, true, false, true},
                {true, false, true, true, true, true, true, true, false, false},
                {true, true, true, false, false, true, false, true, true, true},
                {true, true, false, true, true, false, true, true, true, true},
                {true, true, false, true, true, true, true, true, true, false},
                {false, true, true, true, false, false, false, true, false, true},
                {true, true, false, false, false, true, false, true, true, true},
                {false, false, true, true, true, false, false, true, true, true},
                {false, true, false, true, false, true, false, true, true, false},
                {true, true, false, true, false, true, true, false, false, true},
                {true, true, false, true, true, false, true, true, true, true},
                {true, true, true, false, true, false, false, false, true, false},
                {true, false, false, false, true, false, true, true, true, false},
                {true, true, true, true, false, true, true, true, true, false},
                {true, true, true, false, true, true, true, false, false, true},
                {true, true, false, true, true, false, true, false, false, true},
                {true, false, true, false, true, false, true, true, true, true},
                {true, true, false, true, true, false, false, true, true, true},
                {true, true, true, true, true, true, false, true, true, true},
                {true, true, true, true, false, true, false, true, true, false},
                {true, true, false, true, true, true, true, false, true, false},
                {true, false, true, false, false, false, true, true, true, true},
                {true, true, false, true, true, true, true, true, true, true},
                {false, false, true, true, true, true, true, true, true, true},
                {true, false, false, true, false, true, false, false, false, true},
                {true, true, false, true, true, true, false, true, true, true},
                {false, true, true, true, false, true, true, true, true, false},
                {true, true, true, false, true, false, false, false, true, false},
                {true, true, true, false, true, false, false, false, false, true},
                {false, true, true, false, true, true, false, true, true, true},
                {true, true, true, true, false, true, false, true, true, false},
                {false, false, true, true, true, true, false, true, true, true},
                {false, false, true, true, true, true, false, true, true, false},
                {false, true, true, true, false, true, true, true, true, false},
                {true, true, false, true, false, true, true, true, true, true},
                {true, true, true, true, true, true, false, false, false, false},
        };

        final int[] tutorialCapacities = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        final int[] tutorialCapacitiesLeft = tutorialCapacities.clone();

        final int[] tutorialOfStudents = DoodlePP.distribute(clone(studentAvailabilities), tutorialCapacitiesLeft);

        checkDistribution(studentAvailabilities, tutorialCapacities, tutorialCapacitiesLeft, tutorialOfStudents);
    }

    /**
     * Erzeugt eine Kopie eines zweidimensionalen boolean-Arrays. Die Motivation für diese Methode ist,
     * dass es falsch wäre, dieselbe Instanz der Studierendenverfügbarkeiten an die zu testende Methode
     * zu übergeben, mit der später auch die Korrektheit des Ergebnisses geprüft wird, da die Methode
     * die Daten ändern könnte.
     *
     * @param a Das Array, von dem eine tiefe Kopie erzeugt wird.
     * @return Die Kopie.
     */
    private boolean[][] clone(final boolean[][] a) {
        return Arrays.stream(a).map(boolean[]::clone).toArray(boolean[][]::new);
    }

    /**
     * Überprüft die Korrektheit einer Zuordnung. Wirft einen Fehler, wenn Zuordnung nicht funktioniert.
     * @param studentAvailabilities Die Studierendenverfügbarkeiten, wie in der zu testenden Methode
     *         definiert.
     * @param tutorialCapacities Die ursprünglichen Kapazitäten der Tutorien.
     * @param tutorialCapacitiesLeft Die nach der Verteilung verbliebenen Kapazitäten der Tutorien.
     * @param tutorialOfStudents Zuordnung von Studierenden zu Tutorien.
     * @throws AssertionFailedError wenn die Studierenden nicht gleichmäßig verteilt werden konnten.
     */
    private void checkDistribution(final boolean[][] studentAvailabilities, final int[] tutorialCapacities,
                                   final int[] tutorialCapacitiesLeft, final int[] tutorialOfStudents)
    {
        /* Mit dieser Zeile kann in den Tests kontrolliert werden, dass die Verteilung nicht möglich ist. Denn in dem
         Fall wird für tutorialOfStudents null zurückgegeben werden. Wenn eine erfolgreiche Aufteilung vorhanden ist,
         läuft die Methode einfach durch. Ansonsten wirft sie einen fail. Wenn jetzt Tests geschrieben werden, wo
         eine unmögliche Aufteilung erwartet wird, kann dies mit assertThrows() abgefangen werden.
         */
        if(tutorialOfStudents == null) {
            fail("Student:innen konnten nicht gleichmäßig aufgeteilt werden.");
        }


        // die aus tutorialOfStudents ausgelesene Belegung der Tutorienplätze
        int[] usedCapacity = new int[tutorialCapacities.length];

        // die aus der usedCapacity und Anfangskapazität ausgerechnete übrige Kapazität
        int[] realLeftCapacity = new int [tutorialCapacities.length];

        // rechnet die reale Belegung der Tutorienplätze aus
        for (int i = 0; i < tutorialOfStudents.length; i++) {
            usedCapacity[tutorialOfStudents[i]]++;

            // Testet, dass nicht mehr eingetragen wurden, als die Kapazität groß ist
            assertFalse(usedCapacity[tutorialOfStudents[i]] > tutorialCapacities[tutorialOfStudents[i]]);
        }

        // Kontrolliert, dass jeder Person ein Tutorium zugeordnet wurde und beide Listen gleichlang sind
        assertEquals(studentAvailabilities.length, tutorialOfStudents.length);
        for(int element : tutorialOfStudents) {
            // nicht belegte Plätze werden im Code mit -1 gekennzeichnet
            assertTrue(element != -1);
        }

        // rechnet die reale übrig gebliebene Kapazität aus und vergleicht sie mit der durch die distribute()-Methode
        // berechneten übrigen Kapazität und kontrolliert so, ob richtig gerechnet wurde
        for (int i = 0; i < tutorialCapacities.length; i++) {
            realLeftCapacity[i] = tutorialCapacities[i] - usedCapacity[i];
            assertEquals(tutorialCapacitiesLeft[i], realLeftCapacity[i]);
        }
        

        int j = 0;

        // gibt über die Konsole die Anzahl der übrig gebliebenen Tutoriumsplätze aus, hauptsächlich zum Nutzen von
        // debugging
        for (int line : realLeftCapacity) {
            System.out.println("Tutorium " + j + " übrig: " + line);
            j++;
        }

    }



}
