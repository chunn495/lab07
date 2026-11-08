package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] elements;
    private Predicate<T> predicate;

    // Costruttore a 2 argomenti (array + predicato)
    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> predicate) {
        this.elements = elements.clone();
        this.predicate = predicate;
    }

    // Costruttore a 1 argomento (predicato sempre true di default)
    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(final T elem) {
                return true;
            }
        });
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.predicate = filter;
    }

    private class MyIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < elements.length && predicate != null && !predicate.test(elements[index])) {
                index++;
            }
            return index < elements.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Nessun altro elemento disponibile");
            }
            return elements[index++];
        }
    }
}