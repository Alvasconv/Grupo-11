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
    public E getLast (){
        return this.last.content;
    }
    public E getFirst (){
        return this.first.content;
    }

    
    public boolean isEmpty() {
        return this.first==null;
    }
    
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
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
    
    public boolean add(int index, E element) {
        if(index<0 || index>=this.size) {throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: "+ this.size());}
        else if(element==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");} 
        Node nn = new Node(element);
        if(index==0){
            nn.next = this.first;
            this.first.back = nn;
            this.first = nn;
            this.size++;
            return true;
        }
        Node tempNode = this.getNode(index);
        tempNode.back.next = nn;
        nn.back = tempNode.back;
        nn.next = tempNode;
        tempNode.back = nn;
        this.size++;
        return true;
    }

    public boolean remove(Object o) {
         if(o==null){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        else if(this.isEmpty()) {throw new IndexOutOfBoundsException("No hay elementos en la lista.");}
        else if(this.first.content.equals(o)) {return removeFirst();}
        else if(this.last.content.equals(o)) {return removeLast();}
        
        Node actualNode = this.first;
        int i = 0;
        while (i<this.size){
            if(actualNode.content.equals(o)){
                actualNode.back.next = actualNode.next;
                actualNode.next.back = actualNode.back;
                this.size--;
                return true;
            }else{
            actualNode = actualNode.next;
            i++;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
    }

    public boolean remove(int index) {
        if(index<0 || index>=size) {throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: "+ this.size());}
        else if(this.isEmpty()) {throw new IndexOutOfBoundsException("No hay elementos en la lista.");}
        else if(index==0) {return removeFirst();}
        else if(index==this.size-1) {return removeLast();}
        
        Node nn = this.first;
        int i = 0;
        while (i<index){
             nn = nn.next;
            i++;
        }
        nn.back.next = nn.next;
        nn.next.back = nn.back;
        this.size--;
        return true;
    }
    
    public E get(int index) {
        if(index<0 || index>=this.size) {throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: "+ this.size());}
        if(index==this.size-1) {return this.last.content;}
        int i =0;
        Node n = this.first;
        while (i<index){
            n = n.next;
            i++;
        }
        return n.content;
    }
    
    public int indexOf(Object o) {
        if(o== null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");} 
        else if(this.isEmpty()) {throw new IndexOutOfBoundsException("No hay elementos en la lista.");}
        int i =0;
        Node n = this.first;
        while (i<this.size){
            if(n.content.equals(o)) {return i;}
            else{
                n = n.next;
                i++;
            }
        }
        throw new NoSuchElementException("El objeto ingresado no fue encontrado en la lista.");
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
    
    private Node getNode(int index) {
        if(index<0 || index>=this.size) {throw new IndexOutOfBoundsException("Indice ingresado fuera del rango. Cantidad actual de elementos en la lista: "+ this.size());}
        int i =0;
        Node n = this.first;
        while (i<index){
            n = n.next;
            i++;
        }
        return n;
    }
    
    private boolean removeFirst() {
        this.first.next.back = null;
        this.first = this.first.next;
        this.size--;
        return true;
    }
    
    private boolean removeLast() {
        this.last.back.next = null;
        this.last = this.last.back;
        this.size--;
        return true;
    }
}