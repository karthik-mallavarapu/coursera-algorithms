/*----------------------------------------------------------------
 *  Author:        Ramasivakarthik Mallavarapu
 *  Written:       13/3/2014
 *  Last updated:  17/3/2014
 *  Login:         karthik.mallavarapu@gmail.com 
 *
 *  Compilation:   javac RandomizedQueue.java
 *  Execution:     java RandomizedQueue
 *  
 *  Purpose:       The purpose of the program is to implement a 
 *  randomized queue which adds the elements in order but deletes 
 *  the elements in random order.
 *----------------------------------------------------------------*/

import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
  
  private Item[] queue;
  private int N, realLen;

  public RandomizedQueue() {
    queue = (Item[]) new Object[2]; 
  }

  private class ListIterator implements Iterator<Item> {
    
    private Item[] randomQueue;
    private int i;

    public ListIterator() {
      i = N;
      randomQueue = (Item[]) new Object[N];
      for (int k = 0, j = 0; k < realLen; k++) {
        if (queue[k] != null)
          randomQueue[j++] = queue[k];  
      }
      StdRandom.shuffle(randomQueue);
    }

    public boolean hasNext() {
      return i > 0;
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      return randomQueue[--i];
    }

    public void remove() { 
      throw new UnsupportedOperationException();
    }

  }

  public boolean isEmpty() {
    return N == 0;
  }

  public int size() {
    return N;
  }
   
  public void enqueue(Item item) {
    if (item == null)
      throw new NullPointerException();
    if (realLen == queue.length) {
      if (N == 0)
        resize(2);
      else
        resize(2 * N);
    }
    queue[realLen++] = item;
    N++;
  }

  public Item dequeue() {
    if (isEmpty())
      throw new NoSuchElementException();
    int removeIndex = randIndex();
    Item item = queue[removeIndex];
    queue[removeIndex] = null;
    N--;
    if (N == queue.length/4)
      resize(queue.length/2);
    return item;
  }

  public Item sample() {
    if (isEmpty())
      throw new NoSuchElementException();
    int sampledIndex = randIndex();
    return queue[sampledIndex];
  }

  private int randIndex() {
    int randInd = StdRandom.uniform(realLen);
    while (queue[randInd] == null) {
      randInd = StdRandom.uniform(realLen);
    }
    return randInd;
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private void resize(int size) {
    Item[] copy = (Item[]) new Object[size];
    for (int i = 0, j = 0; i < queue.length; i++) {
      if (queue[i] != null)
        copy[j++] = queue[i];  
    }
    queue = copy;
    realLen = N;
  }

}