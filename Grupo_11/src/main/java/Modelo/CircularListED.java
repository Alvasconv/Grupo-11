package Modelo;

import java.util.NoSuchElementException;
import java.io.Serializable;


/**
 *
 * @author vv
 */
public class CircularListED<E> implements Serializable {
    
    public Node first;
    public Node last;
    private int size;
    
    
    public class Node implements Serializable{
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

        public E getContent() {
            return content;
        }
        
        
    }
   
    public CircularListED(){
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
    
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean add(E e) {
        if(e==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node newNode = new Node(e,null,this.first);
        if(this.isEmpty()){
            this.first = newNode;
            this.last = newNode;
            this.first.back = this.last;
            this.first.next = this.last;
            this.last.back = this.first;
            this.last.next = this.first;
            this.size++;
            return true;
        }
        newNode.back = this.getNode(size-1);
        this.last.next = newNode;
        this.first.back= newNode;
        this.last = newNode;
        this.size++;
        return true;
    }
    

    public boolean remove(Object o) {
        if(o==null){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        else if(this.isEmpty()) {throw new IndexOutOfBoundsException("No hay elementos en la lista.");}
        else if(this.first.content.equals(o)) {
            this.clear();
            return true;
        }
        
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
        else if(size==1 && index==0) {
            this.clear();
            return true;
        }
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
        return temp.next.content;
    }
    
    public E getBack(E e){
        if(e==null || !this.contains(e)){throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        Node temp = this.getNode(e);
        return temp.back.content;
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
    
    private Node getNode(E e) {
        if(e==null) {throw new IllegalArgumentException("Se ha ingresado un objeto null como argumento.");}
        int i =0;
        Node n = this.first;
        while (!n.content.equals(e)){
            n = n.next;
            i++;
        }
        return n;
    }
    
    private boolean removeFirst() {
        this.first.next.back = this.last;
        this.last.next = this.first.next;
        this.first = this.first.next;
        this.size--;
        return true;
    }
    
    private boolean removeLast() {
        this.last.back.next = this.first;
        this.first.back = this.last.back;
        this.last = this.last.back;
        this.size--;
        return true;
    }

    @Override
    public String toString() {
        return "CircularListED{" + "first=" + first + ", last=" + last + ", size=" + size + '}';
    }
    
    public String print() {
        if(this.isEmpty()){ return "";}
        String resultado="";
        int i =1;
        Node n = this.first;
        resultado = resultado.concat(n.content.toString());
        while (i<this.size){
            n = n.next;
            resultado = resultado.concat(";"+n.content.toString());
            i++;
        }
        return resultado;
    }
    
}