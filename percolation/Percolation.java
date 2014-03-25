/*----------------------------------------------------------------
 *  Author:        Ramasivakarthik Mallavarapu
 *  Written:       16/2/2014
 *  Last updated:  18/2/2014
 *  Login:         karthik.mallavarapu@gmail.com 
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  
 *  Purpose:       The purpose of the program is to implement a 
 *  percolation data structure using the WeightedQuickUnionUF class.
 *----------------------------------------------------------------*/

public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] sites;
    private int N, virtualTop;
    private int gridSize;
    private int[] bottomRoots;
    
    
    public Percolation(int n)  {
        N = n;
        gridSize = N * N;
        bottomRoots = new int[N];
        grid = new WeightedQuickUnionUF(gridSize + 1);
        sites = new boolean[gridSize];
        for (int i = 0; i < gridSize; i++) {
            sites[i] = false;
        }
        virtualTop = gridSize;
        for (int i = 0; i < N; i++) {
            grid.union(i, virtualTop);
            bottomRoots[N - 1 - i] = gridSize - 1 - i;
        }
    }
    
    public void open(int i, int j) {
        if ((i <= 0 || i > N) || (j <= 0 || j > N)) 
            throw new IndexOutOfBoundsException("Index out of bounds.");
        int index = (i-1)*N + (j-1);
        sites[index] = true;
        int left = index - 1;
        int right = index + 1;
        int up = index - N;
        int down = index + N;
        
        if (left >= 0 && index % N != 0) {
            if (isOpen(left)) {
                grid.union(index, left);
            }
        }
        if (right % N != 0 && right < gridSize) {
            if (isOpen(right)) {
                grid.union(index, right);
            }
        }
        if (up >= 0) {
            if (isOpen(up)) {
                grid.union(index, up);
            }
        }
        if (down < gridSize) {
            if (isOpen(down)) {
                grid.union(index, down);
            }
        }
        
        for (int k = 0; k < N; k++) {
            if (isOpen(N, k + 1)) {
                bottomRoots[k] = grid.find(gridSize + k - N);
            }
        }
        
    }
    
    public boolean isOpen(int i, int j) {
        if ((i <= 0 || i > N) || (j <= 0 || j > N)) 
            throw new IndexOutOfBoundsException("Index out of bounds.");
        return sites[(i-1)*N + (j-1)];
    }
    
    private boolean isOpen(int index) {
        return sites[index];
    }
    
    public boolean isFull(int i, int j) {
        if ((i <= 0 || i > N) || (j <= 0 || j > N)) 
            throw new IndexOutOfBoundsException("Index out of bounds.");
        if (!isOpen(i, j))
            return false;
        else {
            int index = (i-1)*N + (j-1);
            return grid.connected(index, virtualTop);
        }
    }
    
    public boolean percolates() {
        int topIndex = grid.find(virtualTop);
        for (int i = 0; i < N; i++) {
            if (isOpen(N, i + 1)) {
                if (bottomRoots[i] == topIndex)
                    return true;
            }
        }
        return false;
    }
}