import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Deque<Item> implements Iterable<Item>{

    private int sz = 0;

    private class Node{
        Item item;
        Node next;
        Node prev;
    }

    private Node first = null, last = null;

    private class DequeIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return current != null;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    //construct an empty deque
    public Deque(){
    }

    // is the deque empty?
    public boolean isEmpty(){
        return (sz == 0);
    }

    // return the # items on the deque
    public int size(){
        return sz;
    }

    // add item to the front
    public void addFirst(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if(isEmpty())
            last = first;
        sz++;
    }

    // add item to the back
    public void addLast(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if(isEmpty())
            first = last;
        else
            oldLast.next = last;
        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        sz--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        sz--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    // unit testing
    public static void main(String[] args){
        Deque<String> D = new Deque<String>();
        while(!StdIn.isEmpty()){
            String s = StdIn.readString();
            if(s.equals("+"))
                StdOut.print(D.removeFirst() + " ");
            else if(s.equals("-"))
                StdOut.print(D.removeLast() + " ");
            else if(s.length()>1 && s.charAt(0)=='+')
                D.addFirst(s.substring(1));
            else if(s.length()>1 && s.charAt(0)=='-')
                D.addLast(s.substring(1));
            else
                throw new RuntimeException("Illegal String!");
        }
        StdOut.println("Is D Empty?: " + D.isEmpty());
        StdOut.println("Size of D: " + D.size());

        Iterator<String> i = D.iterator();
        while(i.hasNext()){
            StdOut.print(i.next() + " ");
        }
    }
}