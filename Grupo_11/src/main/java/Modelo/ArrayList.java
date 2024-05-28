package Modelo;

import java.util.Iterator;
/**
 *
 * @author vv
 */
public class ArrayList<E> implements Iterable<E>{
    
    private int capacity = 10;
    private int effectiveSize;
    private E[] elements;
    
    public ArrayList(){
        this.elements = (E[]) new Object[this.capacity];
        this.effectiveSize = 0;
    }
    public ArrayList(int capacity){
        this.elements = (E[]) new Object[capacity];
        this.effectiveSize = 0;
    }
    
    private void addCapacity(){
        E[] newList = (E[]) new Object[capacity];
        for(int i=0; i<this.effectiveSize; i++){
            newList[i] = elements[i];
        }
        this.elements = newList;
        this.capacity = this.capacity*2; 
    }

    private boolean isFull(){
        return this.effectiveSize==this.capacity;
    }
    
    
    public int size() {
        return effectiveSize;
    }

    
    public boolean isEmpty() {
        return effectiveSize==0;
    }

    
    public boolean contains(Object o) {
        if(o==null) {return false;}
        for(int i=0; i<effectiveSize; i++){
            if(this.elements[i].equals(o)) {return true;}
        }
        return false;
    }

    
    public boolean add(E e) {
        if(e==null) {return false;}
        else if(this.isFull()) {this.addCapacity();}
        elements[effectiveSize++] = e;
        return true;
    }

    
    public boolean remove(Object o) {
        if(o==null || this.isEmpty()) {return false;}
        for(int i=0; i<this.effectiveSize;i++){
            if(this.elements[i].equals(o)) {
                for(int c = i;c<this.effectiveSize;c++){
                    this.elements[c] = this.elements[c+1];
                }
                this.effectiveSize--;
                return true;
            }
        }
        return false;
    }

    
    public void clear() {
        elements = (E[]) new Object[capacity];
        effectiveSize = 0;
    }

    
    public E get(int index) {
        if(index<0 || index>=this.effectiveSize) {return null;}
        return this.elements[index];
    }

    
    
    public boolean add(int index, E element) {
        if(index<0 || index>=effectiveSize || element==null) {return false;}
        else if(this.isFull()) {this.addCapacity();}
        E[] temp = (E[]) new Object[this.capacity];
        for(int i=0;i<index;i++){
            temp[i] = this.elements[i];
        }
        temp[index] = element;
        for(int i=index;i<this.effectiveSize;i++){
            temp[i+1] = this.elements[i];
        }
        this.elements = temp;
        this.effectiveSize++;
        return true;
    }

    
    public boolean remove(int index) {
        if(index<0 || index>= this.effectiveSize || this.isEmpty()) {return false;}
        for(int i=index; i<this.effectiveSize;i++){
            this.elements[i] = this.elements[i+1];
        }
        this.effectiveSize--;
        return true;
    }

    
    public int indexOf(Object o) {
        if(o== null || this.isEmpty()) {return 0;}
        for(int i = 0; i<this.effectiveSize;i++){
            if(this.elements[i].equals(o)) {return i;}
        }
        return 0;
    }
    
    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<>(){
            private int indActual = 0;
            @Override
            public boolean hasNext() {
                return indActual<effectiveSize;
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
}
