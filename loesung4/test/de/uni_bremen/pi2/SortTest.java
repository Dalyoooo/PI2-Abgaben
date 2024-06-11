package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;


/** Test-Klasse für die Klasse {@link Sort}.
 * @author Hanna Becker*/
public class SortTest
{
    /**
     * Zu PIT-Mutationtest:
     * Die Mutation-Coverage ist genauso groß, wie die Test Strength. Dies liegt daran, dass die überlebenden Mutationen
     * entweder nicht erreicht werden, weil ihre Fälle bereits vorher von anderen Bedingungen abgefangen wurden oder
     * aber, weil sie im Laufe des Programmes nicht relevant sind, weil trotzdem ein korrektes Ergebnis bei herauskommt.
     *
     * Die erste Zeile ist nicht gecovert, weil die Klasse nur statische Methoden nutzt und keine Objekte vom Typ Sort
     * erstellt werden.
     *
     * findInsertionPoint: Bei diesen beiden Stellen ist es ersteinmal irrelevant, ob < oder <= bzw. > oder >= benutzt
     * wird. Dies liegt daran, dass damit nur die Fälle betroffen werden, wo die Elemente gleich groß sind, wodurch das
     * neu einzusortierende Element entweder vor oder nach dem gleich großen Element einsortiert werden soll, was für
     * das Ergebnis egal ist, eben weil beide Elemente gleich groß sind.
     *
     * quickSort: wenn hier bei anfang < ende das Zeichen zu <= verändert wird, ändert sich nicht direkt was, da wir
     * somit eine einelementige Menge haben. Selbst wenn ein pivot-Element versucht wird auszuwählen, ist es für den
     * Algorithmus egal, da in einer einelementigen Menge sowieso nichts mehr umsortiert werden kann. Wenn anfang = ende
     * ist, wird außerdem i = j und je nachdem, ob a[i], bei einem Element dann a[0], positiv oder negativ ist,
     * verändert sich das Ergebnis nicht, da wenn swap noch aufgerufen wird, das Element mit sich selbst getauscht wird
     * und wenn dies nicht passiert, einfach gar nichts passiert. In dem Durchgang ändert sich also das Ergebnis nicht,
     * wodurch die Mutation auch nicht von Tests abgedeckt werden kann. Somit ist die Mutation von while i <= j und
     * while (a[j].compareTo(pivot) > 0) auch irrelevant, das Ergebnis verändert sich nicht.
     *
     * selectPivot: Das pivot-Element kann auch das erste Element sein, der Algorithmus läuft dann trotzdem. Er arbeitet
     * lediglich effizienter, wenn das Pivot-Element nicht das erste Element ist, wodurch die Mutation aber nicht
     * gekillt werden kann, da nach Funktionalität und nicht nach Geschwindigkeit des Codes getestet wird.
     *
     */
    Integer[] array = {-1, 1, 3, 4, 5, 9, 10, 11, 13, 14};

    Integer[] sortedArray = {0, 1, 2, 3, 4, 5};

    Integer[] arrayWithDublicates = {0, 1, 1, 1, 2, 3, 3, 4, 5, 5, 5};

    Integer[] nullArray = null;
    Integer[] emptyArray = {};
    Integer[] oneElementArray = {1};

    Integer[] pivotTestArray = {0, 2, 6, 5, 5, 5, 5, 5, 9, 1};


    /**
     * Testet insertionSort für ein normales Array
     */
    @Test
    public void insertionSortTestNormalArray() {

        // selbe Werte wie in array, nur nicht sortiert
        Integer[] arrayNotSorted = {5, 9, 4, 14, 10, 1, 3, 13, 11, -1};

        // sortieren des Arrays
        Sort.insertionSort(arrayNotSorted);

        // kontrolliert, ob die Werte korrekt sortiert wurden
        for(int i = 0; i < arrayNotSorted.length; i++) {
            assertEquals(array[i], arrayNotSorted[i]);
        }
    }

    /**
     * Testet insertionSort für ein bereits sortiertes Array
     */
    @Test
    public void insertionSortTestSortedArray() {
        // Kopie des sortierten Arrays
        Integer[] arrayBefore = new Integer[sortedArray.length];
        System.arraycopy(sortedArray, 0, arrayBefore, 0, sortedArray.length);

        Sort.insertionSort(sortedArray);

        // kontrolliert, dass das Array sich nicht verändert hat, also nach wie vor sortiert ist
        for(int i = 0; i < arrayBefore.length; i++) {
            assertEquals(arrayBefore[i], sortedArray[i]);
        }
    }

    /**
     * Testet insertionSort für ein Array mit Dublikaten
     */
    @Test
    public void insertionSortTestDublicates() {

        // selbes Array wie arrayWithDublicates nur nicht sortiert
        Integer[] arrayNotSorted = {5, 3, 1, 2, 0, 5, 4, 1, 3, 1, 5};

        Sort.insertionSort(arrayNotSorted);

        // kontrolliert, ob Array richtig sortiert wurde
        for (int i = 0; i < arrayWithDublicates.length; i++) {
            assertEquals(arrayWithDublicates[i], arrayNotSorted[i]);
        }

        // Testet, ob Arrays mit überwiegend demselben Wert richtig sortiert werden
        Integer[] mostlyOneNumber = {3, 3, 3, 3, 3, 3, 1};
        Integer[] mostlyOneNumberSorted = {1, 3, 3, 3, 3, 3, 3};
        Sort.insertionSort(mostlyOneNumber);
        for(int j = 0; j < mostlyOneNumber.length; j++) {
            assertEquals(mostlyOneNumberSorted[j], mostlyOneNumber[j]);
        }

    }






    /**
     * Testet insertionSort für leere Arrays, null-Arrays und einelementige Arrays
     */
    @Test
    public void insertionSortSpecialCases() {
        // leere oder nullArrays sind streng genommen schon sortiert, muss nur
        // nachgeschaut werden, dass keine Exceptions geworfen werden

        // schaut, ob nullArrays abgefangen werden
        Sort.insertionSort(nullArray);

        // schaut, ob leere Arrays abgefangen werden
        Sort.insertionSort(emptyArray);

        // schaut, ob einelementige Arrays sortiert werden
        Sort.insertionSort(oneElementArray);

        // schaut, ob Element bei einelementigem Array immer noch richtig ist
        assertEquals(1, oneElementArray[0]);
    }


    /**
     * Testet quickSort auf einem normalen Array
     */
    @Test
    public void quicksortTestNormalArray() {

        // selbe Werte wie in array, nur nicht sortiert
        Integer[] arrayNotSorted = {5, 9, 4, 14, 10, 1, 3, 13, 11, -1};

        // sortieren des Arrays
        Sort.quickSort(arrayNotSorted);

        // kontrolliert, ob die Werte korrekt sortiert wurden
        for(int i = 0; i < arrayNotSorted.length; i++) {
            assertEquals(array[i], arrayNotSorted[i]);
        }
    }

    /**
     * Testet quicksort wenn das Pivot-Element am Ende steht
     */
    @Test
    public void quicksortTestPivotLast() {
        Integer[] a = {2, 1, 1, 2};

        Integer[] aBefore = {1, 1, 2, 2};

        Sort.quickSort(a);

        for (int i = 0; i < a.length; i ++ ) {
            assertEquals(aBefore[i], a[i]);
        }
    }

    /**
     * Testet quickSort auf ein bereits sortiertes Array und schaut, dass es gleich bleibt
     */
    @Test
    public void quickSortTestSortedArray() {
        Integer[] arrayBefore = new Integer[sortedArray.length];
        System.arraycopy(sortedArray, 0, arrayBefore, 0, sortedArray.length);

        Sort.quickSort(sortedArray);

        for(int i = 0; i < arrayBefore.length; i++) {
            assertEquals(arrayBefore[i], sortedArray[i]);
        }
    }

    /**
     * Testet quickSort auf ein Array mit dublikaten
     */
    @Test
    public void quickSortTestDublicates() {

        Integer[] arrayNotSorted = {5, 3, 1, 2, 0, 5, 4, 1, 3, 1, 5};

        Sort.quickSort(arrayNotSorted);

        for (int i = 0; i < arrayWithDublicates.length; i++) {
            assertEquals(arrayWithDublicates[i], arrayNotSorted[i]);
        }

    }

    /**
     * Testet quicksort auf null-Arrays, leere Arrays und einelementige Arrays
     */
    @Test
    public void quickSortSpecialCases() {
        // leere oder nullArrays sind streng genommen schon sortiert, muss nur
        // nachgeschaut werden, dass keine Exceptions geworfen werden

        // schaut, ob nullArrays abgefangen werden
        Sort.quickSort(nullArray);

        // schaut, ob leere Arrays abgefangen werden
        Sort.quickSort(emptyArray);

        Integer[] a = {0};
        Sort.quickSort(a);
        assertEquals(0, a[0]);

        Integer[] b = {-1};
        Sort.quickSort(b);
        assertEquals(-1, b[0]);

        // schaut, ob einelementige Arrays sortiert werden
        Sort.quickSort(oneElementArray);

        // schaut, ob Element bei einelementigem Array immer noch richtig ist
        assertEquals(1, oneElementArray[0]);
    }


}
