/*----------------------------------------------------------------
 *  Author:        Ramasivakarthik Mallavarapu
 *  Written:       13/3/2014
 *  Last updated:  17/3/2014
 *  Login:         karthik.mallavarapu@gmail.com 
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *  
 *  Purpose:       The purpose of the program is to implement a 
 *  double ended list data structure. Deque can be used both as a 
 *  stack as well as a queue data structure.
*----------------------------------------------------------------*/

import java.util.*;

public class Deque<Item> implements Iterable<Item> {
  
  private int N;
  private Node first, last;

  private class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> prev;
  }

  private class ListIterator implements Iterator<Item> {
    
    private Node<Item> current;

    public ListIterator(Node<Item> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (current == null)
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() { 
      throw new UnsupportedOperationException();
    }

  }

  public Deque() {
    first = null;
    last = null;
    N = 0;
  }
 
  public boolean isEmpty() {
    return N == 0;
  }

  public int size() {
    return N;
  }
 
  public void addFirst(Item item) {
    if (item == null)
      throw new NullPointerException();
    Node<Item> newNode = new Node<Item>();
    newNode.item = item;
    newNode.next = first;
    if (!isEmpty())
      first.prev = newNode;
    first = newNode;
    if (N == 0)
      last = first;
    N++;  
  }
 
  public void addLast(Item item) {
    if (item == null)
      throw new NullPointerException();
    Node<Item> newNode = new Node<Item>();
    newNode.item = item;
    if (!isEmpty())
      last.next = newNode;
    newNode.prev = last;
    last = newNode;
    if (N == 0)
      first = last;
    N++;
  }
 
  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = (Item) first.item;
    first = first.next;
    if (first != null)
      first.prev = null;
    else
      last = null;
    N--;
    return item;
  }

  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = (Item) last.item;
    last = last.prev;
    if (last != null)
      last.next = null;
    else
      first = null;
    N--;
    return item;
  }
  
  public Iterator<Item> iterator() {
    return new ListIterator(first);
  }

}