import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
// TODO: Replace random insertions and removals with random selection without replacement.
// See Wikipedia: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
// For ideas on how to generate random number without replacement.
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int sz = 0;
    private Item[] randQueue;

    private class RandomizedQueueIterator implements Iterator<Item>{
        public boolean hasNext(){
            return n < sz;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            return randQueue[ord[n++]];
        }
        private int[] ord = new int[sz];
        private int n = 0;
        RandomizedQueueIterator(){

            int capacity = randQueue.length;
//            int[] a = new int[capacity];
//            for(int i = 0; i < capacity; i++){
//                a[i] = i;
//            }
//            StdRandom.shuffle(a);

            int[] a = StdRandom.permutation(capacity);

            int currOrdIdx = 0;
            for(int i = 0; i < a.length; i++){
                if(currOrdIdx==sz)
                    break;
                if(randQueue[a[i]]!=null){
                    ord[currOrdIdx] = a[i];
                    currOrdIdx++;
                }
            }

            // Choose non-null indexes
            /*
            int currQueueIdx = 0;
            while(currOrdIdx < sz){
                if(randQueue[a[currQueueIdx]]!=null){
                    ord[currOrdIdx] = currQueueIdx;
                    currOrdIdx++;
                }
                currQueueIdx++;
            }
             */
        }

//        private int[] traverseOrder = new int[sz];

    }
    // construct an empty randomized queue
    public RandomizedQueue(){
        randQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return sz==0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return sz;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        if (sz == randQueue.length){
            resize(2*randQueue.length);
//            randOrder = randOrd(2*randQueue.length);
        }
        int idx = StdRandom.uniformInt(randQueue.length);
        while(randQueue[idx]!=null){
            idx = StdRandom.uniformInt(randQueue.length);
        }
        randQueue[idx] = item;
        sz++;
    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        int n = randQueue.length;
        int newCnt = 0;
        for(int i = 0; i < n; i++) {
            if (randQueue[i] != null) {
                copy[newCnt++] = randQueue[i];
            }
            if (newCnt == capacity)
                break;
        }
        randQueue = copy;
    }

    // Return array of integers returned in random order
    // based off of StdRandom.shuffle
    private int[] randOrd(int n){
        if(n < 1)
            throw new IllegalArgumentException();
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = i;
        }
        StdRandom.shuffle(a);
        return a;

//        for(int i = 0; i < n; i++){
//            int r = i + StdRandom.uniformInt(n-i);
//            a[i] = r;
//            a[r] = i;
//        }
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        int idx = StdRandom.uniformInt(randQueue.length);
        while(randQueue[idx]==null){
            idx = StdRandom.uniformInt(randQueue.length);
        }

        Item item = randQueue[idx];
        randQueue[idx] = null;
        sz--;
        if(sz > 0 && sz == randQueue.length/4)
            resize(randQueue.length/2);
        return item;

//        Item item;
//        while (true) {
//            int i = StdRandom.uniformInt(sz);
//            if (randQueue[i] != null) {
//                item = randQueue[i];
//                randQueue[i] = null;
//                break;
//            }
//        }
//        sz--;
//        return item;
    }
        //Watch out for loitering (see slide 14.)

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();

        int idx = StdRandom.uniformInt(randQueue.length);
        while(randQueue[idx]==null){
            idx = StdRandom.uniformInt(randQueue.length);
        }

        return randQueue[idx];
//        int i = StdRandom.uniformInt(sz);
//        return randQueue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){return new RandomizedQueueIterator();}

    //unit testing
    public static void main(String[] args){
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for(int i =0; i < n; i++)
            queue.enqueue(i);
        for(int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        while(!queue.isEmpty()){
            int b = queue.sample();
            int a = queue.dequeue();
            StdOut.println(b + "-" + a + ", queue size = " + queue.size());
        }
        try {
            queue.dequeue();
        }
        catch (java.util.NoSuchElementException e){
            StdOut.println("Queue is empty");
        }
        try {
            queue.enqueue(null);
        }
        catch(IllegalArgumentException e){
            StdOut.println("Null input not valid.");
        }

        queue.enqueue(4);
        queue.enqueue(47);

        while (!queue.isEmpty()){
            StdOut.println(queue.sample());
            StdOut.println(queue.dequeue());
        }

    }
}
