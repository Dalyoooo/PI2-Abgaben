package de.uni_bremen.pi2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class Array can make dynamic arrays with a maximum of 1000 elements.
 * @param <E> is the used data type
 */
public class Array<E> implements Iterable<E> {

    /**
     * size of the array, as big as the highest filled index in the array
     */
    private int size;

    /**
     * the number of all elements including null values in the array, can be higher than size because it also
     * includes the indexes with null values that are higher than the biggest filled index
     */
    private int capacity; // capacity as the actual array size

    /**
     * an array used as buffer
     */
    private E[] array;


    /**
     * The constructor creates an object of the type array, which can
     * be dynamically altered through other methods
     * @param capacity is the size of the buffer
     */
    @SuppressWarnings("unchecked")
    public Array(int capacity) {

        this.size = 0;
        this.capacity = capacity;

        array = (E[]) new Object[capacity]; // buffer
        // NOTE: es sollte auch sichergestellt werden, dass die capacity nicht negativ ist!
    }


    /**
     * shows the size of the array
     * @return size of the array
     */
    public int size() {
        return size;
    }

    /**
     * shows the capacity of the array
     * @return capacity of the array
     */
    public int capacity() {
        return capacity;
    }

    /**
     * This method is used to put values in the array. If the index is higher than the array size, the size
     * will be raised enough to fit the index in. If the index is higher than the capacity, than the capacity
     * will be doubled until the index fits.
     * @param index is the index that is meant to be filled, should be at least 0 and lower than 1000
     * @param value is the value that is put into the element with the previous index
     * @throws ArrayIndexOutOfBoundsException if the index is lower than 0 or higher than 1000
     */
    @SuppressWarnings("unchecked")
    public void set(int index, E value) {

        if (index < capacity) {
            array[index] = value;
            if ((index) >= size) {
                size = index + 1;
            }
        }

        /*
        highest upper threshold would be Integer.MAX_VALUE but is lower here
        because of the speed while running the tests
         */
        else if ((index < 0)||(index > 1000)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else {
            size = index + 1;

            while (capacity < size) {
                if (capacity == 0) {
                    capacity = 1;
                }
               else capacity = capacity * 2;
            }
            
            E[] arrayNew = (E[]) new Object[capacity];
            System.arraycopy(array, 0, arrayNew, 0, array.length);
            array = arrayNew;
            array[index] = value;
        }
    }

    /**
     * This method returns an element at a specific index.
     * @param index is the index of the searched element
     * @return gives back the value at the selected index
     * @throws ArrayIndexOutOfBoundsException if the index is lower than 0 or higher than the size of the array allows
     */
    public E get(int index) {
        if ((index >= size) || (index < 0)) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return array[index];
        }
    }

    /**
     * The iterator iterates over the array. It has to methods:
     *  boolean hasNext() returns, if there is a next element
     *  E next() returns the next element.
     * @return an iterator with the methods hasNext() and next()
     * @throws NoSuchElementException if the method next() is used on an object on which no object follows
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public E next() {
                if(hasNext()) {

                    return array[i++];
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };

    }

}