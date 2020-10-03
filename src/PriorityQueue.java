import java.util.*;

// Removing annoying yellow squiggles for using raw generic types
@SuppressWarnings("rawtypes")



public class PriorityQueue<T extends Comparable<? super T>> {
        private HeapNode[] Heap;
        private int size;
        private HashMap<HeapNode, Integer> Position;


        public static class HeapNode<E> {
            private E data;
            private Integer prioValue;

            public HeapNode(E data, Integer prioValue)  {
                this.data = data;
                this.prioValue = prioValue;
            }
        }

        //Consturctor for priority queue, uses our start heap method
        public PriorityQueue(int n) {
            StartHeap(n);
        }

        

        // Heap constructor for our priority queue.
        // This is called when we use our priority queue constructor.
        // Casts a  HeapNode object array (where HeapNode takes a generic E data).
        // Sets heap to size of n + 1 since the zero index is used for storage.
        // Set size to 0 as we have 0 items in array.
        // Returns our generic HeadNode array.
        public HeapNode[] StartHeap(int n) {
            Heap = (HeapNode[]) new HeapNode[n + 1];
            size = 0;
            Position = new HashMap<HeapNode, Integer>();
            return Heap;
        }
        

        
        //Inserts a new node into our heap.
        //Uses Heapify_Up to keep heap intact
        //Unsure on this, assignment states to specifically use 'Heapify_Down', but the book as well as many google searches show Heapify_Up as the correct method to use.
        //Heapify_Up also seems to work better.
        public void Insert(HeapNode item, Integer v) {
            if(size + 1 > Heap.length) {
                return;
            }
            Position.put(item, item.prioValue);
            
            size++;
            Heap[size] = item;
            Heapify_Up(size);
            }


        //Heapify_Down method, that takes the object at the index and keeps moving it down recursively, until the proper spot is found
        public void Heapify_Down(Integer index) {
            HeapNode temp;
            int n = size;
            if(2*index > n) {
                return;
            } else if(2*index < n) {
                Integer left = 2*index, right = (2*index) + 1;
                Integer j = Integer.min(left, right);
            } else if(2*index == n) {
                Integer j = 2*index;
                if(Heap[j].prioValue < Heap[index].prioValue) {
                    temp = Heap[index];
                    Heap[index] = Heap[j];
                    Heap[j] = temp;
                    Heapify_Down(j);
                }
            }
        }



        //Heapify_Up method, use to insert new HeapNodes (according to our textbook, page 60).
        //Again, assignment notes states this backwards, so not exactly sure which is correct.
        //It seemed more plausable to follow the book
        public void Heapify_Up(Integer index) {
            HeapNode temp;
            if(index > 1) {
                int j = index / 2;
                if(Heap[index].prioValue < Heap[j].prioValue) {
                    temp = Heap[index];
                    Heap[index] = Heap[j];
                    Heap[j] = temp;
                    Heapify_Up(j);
                }
            }
        }

        //Delete using the Integer index
        public void Delete(Integer index) {
            HeapNode temp = Heap[index];
            Heap[index] = Heap[size];
             if(Heap[index].prioValue > temp.prioValue) {
             Heapify_Down(index);
             } else if(Heap[index].prioValue < temp.prioValue) {
                 Heapify_Up(index);
             }
            size--;
        }

        //Delete using just the item, with a hashmap as the underlying data structure for O(1) lookup time.
        //Using just the item, O(1) time to find the index.
        public void Delete(HeapNode item) {
            Delete(Position.get(item));
        }

        //Finds and deletes the minimum value. This two methods. findMin() returns the item to destroy, Delete removes it.
        public void ExtractMin() {
            System.out.println("extracting min value: " + Heap[1].data);
            Delete(findMin());
        }

        //Method to change the value of a specific key
        //Uses either Heapify_Down if the need value is greater, therfore requiring the Heap to be placed farther down.
        //Or use Heapify_Up if the new value is smaller, thus needing to move the item up the heap.
        public void ChangeKey(HeapNode item, Integer newValue) {
            if(Position.get(item) > newValue) {
                Heap[Position.get(item)].prioValue = newValue;
                Heapify_Down(newValue);
                Position.remove(item);
                Position.put(item, newValue);
            } else {
                Heap[Position.get(item)].prioValue = newValue;
                Heapify_Up(newValue);
                Position.remove(item);
                Position.put(item, newValue);
            }
        }

        //Simple method to print the top node without removing it. After all, this is a priority queue so it should be easy.
        //O(1) time.
        public HeapNode findMin() {
            return Heap[1];
        }


    public static void main (String args[]) {
        PriorityQueue<String> testqueue = new PriorityQueue<>(10);
        testqueue.Insert(new HeapNode("wyatt", 5), 5);
        testqueue.Insert(new HeapNode("shelby", 3), 3);
        testqueue.Insert(new HeapNode("Tony", 15), 15);
        testqueue.Insert(new HeapNode("Mark", 10), 10);
        testqueue.Insert(new HeapNode("Judy", 6), 6);
        testqueue.Insert(new HeapNode("Dude", 21), 21);
        testqueue.Insert(new HeapNode("Cole", 52), 52);
        testqueue.Insert(new HeapNode("Emily", 45), 45);
        testqueue.Insert(new HeapNode("Kenneth", 8), 8);
        System.out.println(testqueue.Heap[1].data  + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Heap[4].data + " " + testqueue.Heap[4].prioValue);
        System.out.println(testqueue.Heap[5].data + " " + testqueue.Heap[5].prioValue);
        System.out.println(testqueue.Heap[6].data + " " + testqueue.Heap[6].prioValue);
        System.out.println(testqueue.Heap[7].data + " " + testqueue.Heap[7].prioValue);
        System.out.println(testqueue.Heap[8].data + " " + testqueue.Heap[8].prioValue);
        //size prior to extract
        System.out.println(testqueue.size);
        testqueue.ExtractMin();
        //size after extract
        System.out.println(testqueue.size);
        System.out.println(testqueue.Heap[1].data  + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Heap[4].data + " " + testqueue.Heap[4].prioValue);
        System.out.println(testqueue.Heap[5].data + " " + testqueue.Heap[5].prioValue);
        System.out.println(testqueue.Heap[6].data + " " + testqueue.Heap[6].prioValue);
        System.out.println(testqueue.Heap[7].data + " " + testqueue.Heap[7].prioValue);
        System.out.println(testqueue.Heap[testqueue.size].data);
    }
}


