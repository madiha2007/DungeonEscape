package dungeonescape.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Our own Stack, built from linked nodes (no java.util.Stack).
 * LIFO = Last In, First Out: the last item pushed is the first one popped.
 * The "top" is the only end we ever touch, so every operation is O(1).
 */
public class Stack<T> {

    // One box in the chain. 'next' points to the item BELOW it in the stack.
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top;   // the newest item (null when the stack is empty)
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    /** Puts an item on top of the stack. */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;   // new node sits on top of the old top
        top = newNode;
        size++;
    }

    /** Removes and returns the top item. Returns null if the stack is empty. */
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T item = top.data;
        top = top.next;       // the item below becomes the new top
        size--;
        return item;
    }

    /** Returns the top item WITHOUT removing it. Returns null if empty. */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    /** Prints the stack exactly as it is stored: top first, bottom last. */
    public void display() {
        if (isEmpty()) {
            System.out.println("  (stack is empty)");
            return;
        }
        Node<T> current = top;
        while (current != null) {
            if (current == top) {
                System.out.println("  " + current.data + "   <-- TOP");
            } else {
                System.out.println("  " + current.data);
            }
            current = current.next;
        }
    }

    /**
     * Returns the items from BOTTOM to TOP (oldest first).
     * Used to show the movement history in chronological order.
     * This does not change the stack.
     */
    public List<T> toListBottomToTop() {
        List<T> list = new ArrayList<>();
        Node<T> current = top;
        while (current != null) {
            list.add(0, current.data);   // insert at front so the order flips
            current = current.next;
        }
        return list;
    }
}