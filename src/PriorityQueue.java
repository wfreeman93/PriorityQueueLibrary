import java.util.*;

// Removing annoying yellow squiggles for using raw generic types
@SuppressWarnings("rawtypes")

public class PriorityQueue<T> {
    public HeapNode[] Heap;
    public int size;
    public HashMap<HeapNode, Integer> Position;

    public static class HeapNode<E> {
        public E data;
        public int prioValue;

        public HeapNode(E data, int prioValue) {
            this.data = data;
            this.prioValue = prioValue;
        }

        public String toString() {
            String s = "";
            s = "[" + data + ", value: " + prioValue + "]";
            return s;
        }
    }

    // Consturctor for priority queue, uses our start heap method
    public PriorityQueue(int n) {
        StartHeap(n);
    }

    public void swap(Integer i, Integer j) {
        HeapNode<T> temp = Heap[i];
        Heap[i] = Heap[j];
        Heap[j] = temp;
        Position.put(Heap[j], j);
        Position.put(Heap[i], i);
    }

    // Heap constructor for our priority queue.
    // This is called when we use our priority queue constructor.
    // Casts a HeapNode object array (where HeapNode takes a generic E data).
    // Sets heap to size of n + 1 since the zero index is used for storage.
    // Set size to 0 as we have 0 items in array.
    // Returns our generic HeadNode array.
    public HeapNode[] StartHeap(int n) {
        Heap = (HeapNode[]) new HeapNode[n + 1];
        size = 0;
        Position = new HashMap<HeapNode, Integer>();
        return Heap;
    }

    // Inserts a new node into our heap.
    // Uses Heapify_Up to keep heap intact
    // Unsure on this, assignment states to specifically use 'Heapify_Down', but the
    // book as well as many google searches show Heapify_Up as the correct method to
    // use.
    // Heapify_Up also seems to work better.
    public void Insert(T item, Integer v) {
        if (size + 1 > Heap.length - 1) {
            return;
        }
        size++;
        Heap[size] = new HeapNode(item, v);
        Position.put(Heap[size], size);
        Heapify_Up(size);
    }

    // Heapify_Down method, that takes the object at the index and keeps moving it
    // down recursively, until the proper spot is found
    public void Heapify_Down(Integer index) {
            int n = size;
            Integer left;
            Integer right;
            Integer j = index;
            if(size == 2) {
                if(Heap[index].prioValue < Heap[2*index].prioValue) {
                    return;
                } else if (Heap[index].prioValue > Heap[2*index].prioValue) {
                    swap(index, 2*index);
                }
            }
            if(size == 1) {
                return;
            }
            if(2*index > n) {
                return;
            }else if(2*index < n) {
                left = 2*index;
                right = (2*index) + 1;
                if(Heap[left].prioValue < Heap[right].prioValue) {
                    j = left;
                } else if(Heap[left].prioValue > Heap[right].prioValue) {
                    j = right;
                }
               // swap(index, j);
            }else if(2*index == n) {
                j = 2*index;
            }
            if(Heap[j].prioValue < Heap[index].prioValue) {
                    swap(index, j);
                    Heapify_Down(j);
                }
           }

    // Heapify_Up method, use to insert new HeapNodes (according to our textbook,
    // page 60).
    // Again, assignment notes states this backwards, so not exactly sure which is
    // correct.
    // It seemed more plausable to follow the book
    public void Heapify_Up(Integer index) {
        HeapNode<T> temp;
        if (index > 1) {
            int j = index / 2;
            if (Heap[index].prioValue < Heap[j].prioValue) {
                swap(index, j);
            }
        }
    }

    // Delete using the Integer index
    public void Delete(Integer index) {
        Heap[index] = Heap[size];
        Heap[size] = null;
        Position.put(Heap[index], index);
        size--;
        Heapify_Down(index);
    }

    // Delete using just the item, with a hashmap as the underlying data structure
    // for O(1) lookup time.
    // Using just the item, O(1) time to find the index.
    public void Delete(HeapNode<T> item) {
        Delete(Position.get(item));
        Position.remove(item);
    }

    // Finds and deletes the minimum value. This two methods. findMin() returns the
    // item to destroy, Delete removes it.
    public void ExtractMin() {
        System.out.println("extracting min value: " + Heap[1].data);
        Delete(findMin());
    }

    // Method to change the value of a specific key
    // Uses either Heapify_Down if the need value is greater, therfore requiring the
    // Heap to be placed farther down.
    // Or use Heapify_Up if the new value is smaller, thus needing to move the item
    // up the heap.
    public void ChangeKey(HeapNode<T> item, Integer newValue) {
        Integer temp = item.prioValue;
        item.prioValue = newValue;
        Position.put(item, Position.get(item));
        if (temp < newValue) {
            Heapify_Down(temp);
        } else {
            Heapify_Up(temp);
        }
    }

    // Simple method to print the top node without removing it.
    // O(1) time.
    public HeapNode<T> findMin() {
        return Heap[1];
    }

    public static void main(String[] args) {
        PriorityQueue<String> testqueue = new PriorityQueue<>(10);
        testqueue.Insert("wyatt", 3);
        testqueue.Insert("shelby", 1);
        testqueue.Insert("Tony", 6);
        testqueue.Insert("Mark", 5);
        testqueue.Insert("Judy", 2);
        testqueue.Insert("Dude", 4);
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Heap[4].data + " " + testqueue.Heap[4].prioValue);
        System.out.println(testqueue.Heap[5].data + " " + testqueue.Heap[5].prioValue);
        System.out.println(testqueue.Heap[6].data + " " + testqueue.Heap[6].prioValue);
        // System.out.println(testqueue.Heap[0]);
        System.out.println(testqueue.Position);
        testqueue.ChangeKey(testqueue.Heap[1], 10);
        System.out.println(testqueue.Position);
        // size prior to extract
        System.out.println(testqueue.size);
        testqueue.ExtractMin(); // extract shelby
        // size after extract
        System.out.println(testqueue.size);
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Heap[4].data + " " + testqueue.Heap[4].prioValue);
        System.out.println(testqueue.Heap[5].data + " " + testqueue.Heap[5].prioValue);
        System.out.println(testqueue.Position);
        testqueue.ExtractMin(); // extract Judy
        System.out.println(testqueue.size);
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Heap[4].data + " " + testqueue.Heap[4].prioValue);
        System.out.println(testqueue.Position);
        testqueue.ExtractMin(); // extract Wyatt
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Heap[3].data + " " + testqueue.Heap[3].prioValue);
        System.out.println(testqueue.Position);

        testqueue.ExtractMin(); // extract dude
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Heap[2].data + " " + testqueue.Heap[2].prioValue);
        System.out.println(testqueue.Position);

        testqueue.ExtractMin(); // extract Mark
        System.out.println(testqueue.Heap[1].data + " " + testqueue.Heap[1].prioValue);
        System.out.println(testqueue.Position);

    }
}
