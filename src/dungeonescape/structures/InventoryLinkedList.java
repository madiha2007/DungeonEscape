package dungeonescape.structures;

import dungeonescape.model.Item;

/**
 * The player's inventory: our own SINGLY LINKED LIST (no java.util.LinkedList).
 *
 *   head
 *    ↓
 *  [Sword | next] → [Potion | next] → [Shield | next] → null
 *
 * Only 'head' is stored. To reach any other node we must walk from the head.
 */
public class InventoryLinkedList {

    // One node = one item + a pointer to the next node
    private static class Node {
        Item data;     // the item stored in this node
        Node next;     // the next node (null if this is the last one)

        Node(Item data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;   // first node (null when the inventory is empty)
    private int size;

    public InventoryLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * INSERTION: adds an item at the END of the list.
     * Returns false if the item is null or the player already has it.
     */
    public boolean addItem(Item item) {
        if (item == null || searchItem(item.getName()) != null) {
            return false;
        }
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;                  // first item becomes the head
        } else {
            Node current = head;
            while (current.next != null) {   // walk to the last node
                current = current.next;
            }
            current.next = newNode;          // link it at the end
        }
        size++;
        return true;
    }

    /**
     * DELETION: removes the first item with this name.
     * Returns the removed item, or null if it was not found.
     */
    public Item removeItem(String name) {
        if (head == null || name == null) {
            return null;
        }
        // Case 1: the item to delete is the head
        if (head.data.getName().equalsIgnoreCase(name.trim())) {
            Item removed = head.data;
            head = head.next;                // head simply moves forward
            size--;
            return removed;
        }
        // Case 2: the item is further down. Stop one node BEFORE it,
        // then make that node skip over the one being deleted.
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getName().equalsIgnoreCase(name.trim())) {
                Item removed = current.next.data;
                current.next = current.next.next;   // unlink
                size--;
                return removed;
            }
            current = current.next;
        }
        return null;
    }

    /** SEARCHING: linear search from head to end. Returns the item or null. */
    public Item searchItem(String name) {
        if (name == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.data.getName().equalsIgnoreCase(name.trim())) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * UPDATE: finds the node (traversal) and changes the item stored in it.
     * Returns false if the item does not exist.
     */
    public boolean updateItem(String name, String newDescription, int newEffectValue) {
        Item item = searchItem(name);
        if (item == null) {
            return false;
        }
        item.setDescription(newDescription);
        item.setEffectValue(newEffectValue);
        return true;
    }

    /** Returns the item at position index (0 = head). Null if out of range. */
    public Item getItemAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /** TRAVERSAL: visits every node from head to null and prints it. */
    public void displayInventory() {
        if (isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("========== INVENTORY ==========\n");
        Node current = head;
        int number = 1;
        while (current != null) {
            System.out.println(number + ". " + current.data);
            current = current.next;
            number++;
        }
        System.out.println("\nTotal Items: " + size);
        System.out.println("\n===============================");
    }

    /** Returns the list as text, e.g. "Sword → Potion → NULL" (for tests). */
    public String toPathString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        while (current != null) {
            sb.append(current.data.getName()).append(" → ");
            current = current.next;
        }
        sb.append("NULL");
        return sb.toString();
    }

    public boolean isEmpty() { return head == null; }
    public int size()        { return size; }
}