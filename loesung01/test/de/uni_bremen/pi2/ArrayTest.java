package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
/**
 * This class tests the sourcecode in the class Array
 */
public class ArrayTest {
    Array<Integer> array;

    @BeforeEach
        // Create a new instance of the Array class with a capacity of 3
    void setUp() {
        array = new Array<Integer>(3);
    }

    /**
     * This test is going to test through the methods set(),get(),size(),capacity(), by setting the and the index of the array and by asserting.
     */
    @Test
    void arrayTest() {
        // Test the set method by setting the value 3 at index 1
        array.set(1, 3);
        // Test the size method by asserting that the size is 2
        assertEquals(2, array.size());

        // Test the set method again by setting the value 4 at index 4
        array.set(4, 4);
        // Test the get method by asserting that the value at index 1 is 3
        assertEquals(3, array.get(1));
        // Test the get method by asserting that the value at index 4 is 4
        assertEquals(4, array.get(4));
        // Test the size method by asserting that the size is 5
        assertEquals(5, array.size());
        // Test the capacity method by asserting that the capacity is 6
        assertEquals(6, array.capacity());

        // Create a new instance of the Array class with a size of 0
        Array<Integer> nullValueArray = new Array<Integer>(0);
        // Test the set method by setting the value -1 at index 2
        nullValueArray.set(2, -1);
        // Test the get method by asserting that the value at index 2 is -1
        assertEquals(-1, nullValueArray.get(2));
        // Test the size method by asserting that the size is 3
        assertEquals(3, nullValueArray.size());
        // Test the capacity method by asserting that the capacity is 4
        assertEquals(4, nullValueArray.capacity());

        // Test the set method by setting the value 10 at index 5
        array.set(5, 10);
        // Test the get method by asserting that the value at index 5 is 10
        assertEquals((10), array.get(5));
        // Test the set method by setting the value 100 at index 15
        array.set(15, 100);
        // Test the get method by asserting that the value at index 15 is 100
        assertEquals((100), array.get(15));
        // Create a new instance of the Array class with a size of 1
        Array<Integer> array1 = new Array<>(1);
        // Test the set method by setting the value 1 at index 1
        array1.set(1, 1);
        // Test the capacity method by asserting that the capacity is 2
        assertEquals(2, array1.capacity());

        // für else if ((index < 0)||(index > 1000)) {
        // Test the set method by setting the value 0 at index 1000
        array.set(1000, 0);
        // Test the get method by asserting that the value at index 1000 is 0
        // Test the get method by asserting that the value at index 1000 is 0
        assertEquals(0, array.get(1000));
        // Create a new instance of the Array class with a size of 0
        Array<Integer> array2 = new Array<>(0);
        // Test the set method by setting the value 1 at index 0
        array2.set(0, 1);
        // Test the get method by asserting that the value at index 0 is 1
        assertEquals(1, array2.get(0));

        // für if ((index >= size) || (index < 0)) {
        // Create a new Array object with a capacity of 7
         Array<Integer> array3 = new Array<>(7);
        // Set the value of index 3 to 3
        array3.set(3, 3);
        // Ensure that an ArrayIndexOutOfBoundsException is thrown when trying to access index 4 of the array
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array3.get(4));


    }

    /**
    ensures that the Exception is thrown
     */
    @Test
    void ExeceptionTest() {
        // Ensure that an ArrayIndexOutOfBoundsException is thrown when setting a negative index and value
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(-1, -2));
        // Ensure that an ArrayIndexOutOfBoundsException is thrown when setting an index outside of the array's capacity and a value
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(1001, 42));
        // Ensure that an ArrayIndexOutOfBoundsException is thrown when setting a negative index and a value outside of the array's capacity
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(-1, 1001));
        // Ensure that an ArrayIndexOutOfBoundsException is thrown when trying to get a value at a negative index
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));


    }

    /**
     *  test through the method iterator
     */
    @Test
    void iteratorTest() {
        // Create a new Array object with a capacity of 10
        Array<Integer> array = new Array<>(10);
        // Set the value of index 0 to 1
        array.set(0, 1);
        // Ensure that the value of index 0 is 1
        assertEquals((1), array.get(0));
        // Set the value of index 1 to 3
        array.set(1, 3);
        // Ensure that the size of the array is 2
        assertEquals(2, array.size());
        // Create a new Array object with a capacity of 0
        Array<Integer> iteratorNullArray = new Array<Integer>(0);
        // Get an iterator for the array
        var nullIterator = iteratorNullArray.iterator();
        // Ensure that the iterator has no elements
        assertFalse(nullIterator.hasNext());
        // Ensure that a NoSuchElementException is thrown when trying to get the next element of an empty iterator
        assertThrows(NoSuchElementException.class, nullIterator::next);
        // Get an iterator for the array
        var iterator = array.iterator();
       // Ensure that the iterator has at least one element
        assertTrue(iterator.hasNext());
        // Ensure that the first element of the iterator is 1
        assertEquals(1, iterator.next());
        // Ensure that the second element of the iterator is 3
        assertEquals(3, iterator.next());
    }
}