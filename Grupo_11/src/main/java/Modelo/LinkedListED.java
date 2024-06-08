package Modelo;

import java.util.NoSuchElementException;

/**
 *
 * @author vv
 */
public class LinkedListED<E> {
    
    private Node first;
    private Node last;
    private int size;
    
    protected class Node{
        E content;
        Node back;
        Node next;
        
        public Node(E content, Node back, Node next){
            this.content = content;
            this.back = back;
            this.next = next;
        }
        public Node(E content){
            this.content = content;
        }
    }
   
    public LinkedListED(){
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    public int size() {
        return size;
    }

    
    public boolean isEmpty() {
        return this.first==null;
    }
    
    public boolean hasNext(E e){
        if(e==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node n = this.getNode(e);
        return n.next!=null;
    }
    
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    public boolean contains(Object o) {
        if(o==null){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        int i=0;
        Node actualNode = this.first;
        while(i<size){
            if(actualNode.content.equals(o)) {return true;}
            else {
                actualNode = actualNode.next;
                i++;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
    }
    
    public boolean removeLast() {
        this.last.back.next = null;
        this.last = this.last.back;
        this.size--;
        return true;
    }
    
    public E getNext(E e){
        if(e==null || !this.contains(e)){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node temp = this.getNode(e);
        if(temp.next==null) throw new NoSuchElementException("No hay elemento siguiente al actual.");
        return temp.next.content;
    }
    
    public E getBack(E e){
        if(e==null || !this.contains(e)){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node temp = this.getNode(e);
        if(temp.back==null) throw new NoSuchElementException("No hay elemento anterior al actual.");
        return temp.back.content;
    }
    
    public boolean add(E e, E actual) {
        if(e==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node nn = new Node(e,this.last, null);
        if(this.isEmpty()){
            this.first = nn;
            this.last = nn;
            this.size++;
            return true;
        }
        if(!actual.equals(this.last.content)){
            Node n = this.getNode(actual);
            n.next = nn;
            nn.back = n;
            this.last = nn;
            this.size++;
            return true;
        }
        this.last.next = nn;
        this.last = nn;
        this.size++;
        return true;
    }
    
    public boolean add(E e) {
        if(e==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node nn = new Node(e,this.last, null);
        if(this.isEmpty()){
            this.first = nn;
            this.last = nn;
            this.size++;
            return true;
        }
       
        this.last.next = nn;
        this.last = nn;
        this.size++;
        return true;
    }
    
    
    public E getLast (){
        return this.last.content;
    }
    
    
    private Node getNode(E e) {
        if(e==null){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        int i =0;
        Node n = this.first;
        while (!n.content.equals(e)){
            n = n.next;
            i++;
        }
        return n;
    }
}