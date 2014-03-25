/*----------------------------------------------------------------
 *  Author:        Ramasivakarthik Mallavarapu
 *  Written:       13/3/2014
 *  Last updated:  17/3/2014
 *  Login:         karthik.mallavarapu@gmail.com 
 *
 *  Compilation:   javac Subset.java
 *  Execution:     java Subset arg1
 *  
 *  Purpose:       The purpose of the program is to add N elements
 *  to a list data structure and return k random elements.
 *----------------------------------------------------------------*/

public class Subset {

  public static void main(String[] args) {
    RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
    int k = Integer.parseInt(args[0]);

    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      randomQueue.enqueue(item);
    }

    for (int i = 0; i < k; i++) {
      StdOut.println(randomQueue.dequeue());
    }

  }
}
