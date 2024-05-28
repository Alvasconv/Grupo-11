package Modelo;

/**
 *
 * @author vv
 */
public class LinkedList<E> {
    
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
   
    public LinkedList(){
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
    

    
    public boolean contains(Object o) {
        if(o==null) {return false;}
        int i=0;
        Node actualNode = this.first;
        while(i<size){
            if(actualNode.content.equals(o)) {return true;}
            else {
                actualNode = actualNode.next;
                i++;
            }
        }
        return false;
    }

    
    public boolean add(E e) {
        if(e==null) {return false;}
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
    
    private boolean removeLast() {
        this.last.back.next = null;
        this.last = this.last.back;
        this.size--;
        return true;
    }
    private boolean removeFirst() {
        this.first.next.back = null;
        this.first = this.first.next;
        this.size--;
        return true;
    }

    
    public boolean remove(Object o) {
        if(o==null || this.isEmpty()) {return false;}
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
        return false;
    }

   
    
    public void clear() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    
    public E get(int index) {
        if(index<0 || index>=this.size) {return null;}
        if(index==this.size-1) {return this.last.content;}
        int i =0;
        Node n = this.first;
        while (i<index){
            n = n.next;
            i++;
        }
        return n.content;
    }
    
    private Node getNode(int index) {
        if(index<0 || index>=this.size) {return null;}
        int i =0;
        Node n = this.first;
        while (i<index){
            n = n.next;
            i++;
        }
        return n;
    }
    
    private Node getNode(E e) {
        if(e==null) {return null;}
        int i =0;
        Node n = this.first;
        while (!n.content.equals(e)){
            n = n.next;
            i++;
        }
        return n;
    }

    public boolean add(int index, E element) {
        if(index<0 || index>=this.size || element==null){return false;}
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

     
    public boolean remove(int index) {
        if(index<0 || index>=size || this.isEmpty()) {return false;}
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
    
    public E getNext(E e){
        if(e==null || !this.contains(e)){return null;}
        Node temp = this.getNode(e);
        if(temp.next==null) {return null;}
        return temp.next.content;
    }
    
    public E getBack(E e){
        if(e==null || !this.contains(e)){return null;}
        Node temp = this.getNode(e);
        if(temp.back==null) {return null;}
        return temp.back.content;
    }
    
    public int indexOf(Object o) {
        if(o== null || this.isEmpty()) {return 0;}
        int i =0;
        Node n = this.first;
        while (i<this.size){
            if(n.content.equals(o)) {return i;}
            else{
                n = n.next;
                i++;
            }
        }
        return 0;
    }
}
