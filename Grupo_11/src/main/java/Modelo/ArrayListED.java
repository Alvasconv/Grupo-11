package Modelo;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author vv
 */
public class ArrayListED<E> implements Iterable<E>, Serializable {

    private int capacity = 10;
    private int effectiveSize;
    private E[] elements;

    public ArrayListED() {
        this.elements = (E[]) new Object[this.capacity];
        this.effectiveSize = 0;
    }

    public ArrayListED(int capacity) {
        this.elements = (E[]) new Object[capacity];
        this.effectiveSize = 0;
    }

    private boolean isFull() {
        return this.effectiveSize == this.capacity - 1;
    }

    public int size() {
        return effectiveSize;
    }

    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    public void clear() {
        elements = (E[]) new Object[capacity];
        effectiveSize = 0;
    }

    public boolean add(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");
        } else if (this.isFull()) {
            this.addCapacity();
        }
        elements[effectiveSize++] = e;
        return true;
    }

    public boolean add(int index, E element) {
        if (index < 0 || index >= effectiveSize) {
            throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: " + this.size());
        } else if (element == null) {
            throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");
        } else if (this.isFull()) {
            this.addCapacity();
        }
        E[] temp = (E[]) new Object[this.capacity];
        for (int i = 0; i < index; i++) {
            temp[i] = this.elements[i];
        }
        temp[index] = element;
        for (int i = index; i < this.effectiveSize; i++) {
            temp[i + 1] = this.elements[i];
        }
        this.elements = temp;
        this.effectiveSize++;
        return true;
    }

    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");
        } else if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("No hay elementos en la lista.");
        }
        for (int i = 0; i < this.effectiveSize; i++) {
            if (this.elements[i].equals(o)) {
                for (int c = i; c < this.effectiveSize; c++) {
                    this.elements[c] = this.elements[c + 1];
                }
                this.effectiveSize--;
                return true;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
    }

    public boolean remove(int index) {
        if (index < 0 || index >= this.effectiveSize) {
            throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: " + this.size());
        } else if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("No hay elementos en la lista.");
        }
        for (int i = index; i < this.effectiveSize; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.effectiveSize--;
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= this.effectiveSize) {
            throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: " + this.size());
        }
        return this.elements[index];
    }

    public int indexOf(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");
        } else if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("No hay elementos en la lista.");
        }
        for (int i = 0; i < this.effectiveSize; i++) {
            if (this.elements[i].equals(o)) {
                return i;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
    }

    public boolean contains(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");
        }
        for (int i = 0; i < effectiveSize; i++) {
            if (this.elements[i].equals(o)) {
                return true;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<>() {
            private int indActual = 0;

            @Override
            public boolean hasNext() {
                return indActual < effectiveSize;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw null;
                }
                return elements[indActual++];
            }
        };
        return it;
    }

    private void addCapacity() {
        E[] newList = (E[]) new Object[capacity * 2];
        for (int i = 0; i < this.effectiveSize; i++) {
            newList[i] = elements[i];
        }
        this.elements = newList;
        this.capacity = this.capacity * 2;

    }

    @Override
    public String toString() {
        return "ArrayListED{" + "capacity=" + capacity + ", effectiveSize=" + effectiveSize + ", elements=" + elements + '}';
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException("La colección ingresada es null.");
        }
        for (E e : c) {
            if (e == null) {
                throw new IllegalArgumentException("La colección contiene un elemento null.");
            }
        }
        while (this.effectiveSize + c.size() > this.capacity) {
            this.addCapacity();
        }
        for (E e : c) {
            this.elements[effectiveSize++] = e;
        }
        return true;
    }

    public E[] toArray() {
        E[] array = (E[]) new Object[this.effectiveSize];
        System.arraycopy(this.elements, 0, array, 0, this.effectiveSize);
        return array;
    }

    public String print() {
        if (this.isEmpty()) {
            return "";
        }
        String resultado = "";
        resultado = resultado.concat(this.elements[0].toString());
        for (int i = 1; i < this.effectiveSize; i++) {
            resultado = resultado.concat(";" + this.elements[i].toString());
        }
        return resultado;
    }

    public void sort(Comparator<E> c) {
        for (int i = 0; i < effectiveSize - 1; i++) {
            for (int j = 0; j < effectiveSize - i - 1; j++) {
                if (c.compare(elements[j], elements[j + 1]) > 0) {
                    E temp = elements[j];
                    elements[j] = elements[j + 1];
                    elements[j + 1] = temp;
                }
            }
        }
    }

    public String[] toStringArray() {
        String[] array = new String[this.effectiveSize];
        for (int i = 0; i < this.effectiveSize; i++) {
            array[i] = this.elements[i].toString();
        }
        return array;
    }
}
