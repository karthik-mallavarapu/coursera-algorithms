/*----------------------------------------------------------------
 *  Author:        Ramasivakarthik Mallavarapu
 *  Written:       16/2/2014
 *  Last updated:  18/2/2014
 *  Login:         karthik.mallavarapu@gmail.com 
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats arg1 arg2
 *  
 *  Purpose:       The purpose of the program is to experiment with the percolation 
 *  program to calculate mean, standard deviation and confidence intervals. 
 *  The program takes two command line arguments. The first is the size of 
 *  the percolatin grid and the second is the number of independent 
 *  experiments on the percolation grid.
 *----------------------------------------------------------------*/

public class PercolationStats {
    private double[] percThreshold;
    private double mean, stddev, confLo, confHi;
    private int N, T;
    
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0)
                throw new IllegalArgumentException("Illegal arguments.");
        percThreshold = new double[t];
        N = n;
        T = t;
        execute();
    }
    
    private void execute() {
        for (int i = 0; i < T; i++) {
            Percolation grid = new Percolation(N);
            int percCount = 0;
            int gridSize = N * N;
            int[] randIndices = new int[gridSize];
            for (int j = 0; j < gridSize; j++) {
                randIndices[j] = j;
            }
            StdRandom.shuffle(randIndices);
            for (int j = 0; j < gridSize; j++) {
                if (grid.percolates())
                    break;
                int randIndex = randIndices[j];
                int row = (randIndex / N) + 1;
                int column = (randIndex % N) + 1;
                grid.open(row, column);
                percCount++;
            }
            percThreshold[i] = (double) percCount / (N*N);
        }
        mean = StdStats.mean(percThreshold);
        stddev = StdStats.stddev(percThreshold);
        double intermediate = ((1.96)*stddev) / Math.sqrt(T);
        confLo = mean - intermediate;
        confHi = mean + intermediate;   
    }
    
    public double mean() {
        return mean;
    }
    
    public double stddev() {
        return stddev;
    }
    
    public double confidenceLo() {
        return confLo;
    }
    
    public double confidenceHi() {
        return confHi;
    }
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(N, T);
        StdOut.println("mean = "+p.mean());
        StdOut.println("stddev = "+p.stddev());
        StdOut.println("95% confidence interval = " 
                           + p.confidenceLo() + ", " + p.confidenceHi());
    }
}