package com.dohnalovar.matrix;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dohnalovar on 6/19/2019
 */
public class Matrix {

    /** input table */
    private List<List<Integer>> table = new ArrayList<>();
    private int size;
    /** computed table of the lowest paths */
    private List<List<LeastPath>> subPaths = new ArrayList<>();

    /** get the least two-way path from position (i, j) to the lowest rightest corner */
    public LeastPath getSubPath(int i, int j) {
        return subPaths.get(i).get(j);
    }

    public int get(int i, int j) {
        if (0<=i && i<=(size-1) && 0<=j && j<=(size-1))
            return table.get(i).get(j);
        else return -1;
    }

    public int getSize() {
        return size;
    }

    /** find the least two ways path (right/down) from position (i, j) to the lowest rightest corner */
    private int findLeastTwoWayPath(int i, int j) {

        //skip computing if it has been already computed
        LeastPath lp = subPaths.get(i).get(j);
        int sum = lp.getSum();
        if ( sum != 0 ) return sum;

        //lowest rightest corner
        if (i == (size-1) && j == (size-1)) {
            sum = get(i, j);
            lp.setSum(sum);
            return sum;
        }

        //rightest column
        if (i < (size-1) && j == (size-1) ) {
            sum = get(i,j) + findLeastTwoWayPath(i+1, j);
            lp.setSum(sum);
            lp.setDirection("D"+ subPaths.get(i+1).get(j).getDirection());
            return sum;
        }

        //lowest row
        if (i == (size-1) && j < (size-1)) {
            sum = get(i,j) + findLeastTwoWayPath(i, j+1);
            lp.setSum(sum);
            lp.setDirection("R" + subPaths.get(i).get(j+1).getDirection());
            return sum;
        }

        // somewhere inside the matrix
        int down = findLeastTwoWayPath(i+1, j);
        int right = findLeastTwoWayPath(i, j+1);

        if ( down<right ) {
            sum = get(i, j) + down;
            lp.setSum(sum);
            lp.setDirection("D" + subPaths.get(i+1).get(j).getDirection());
        } else {
            sum = get(i, j) + right;
            lp.setSum(sum);
            lp.setDirection("R" + subPaths.get(i).get(j+1).getDirection());
        }

        return sum;
    }


    /** process line read from CSV file */
    private List<Integer> getLine(String line) {
        List<Integer> values = new ArrayList<>();
        List<LeastPath> myPaths = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.parseInt(rowScanner.next()));
                myPaths.add(new LeastPath());
            }
        }
        subPaths.add(myPaths);
        return values;
    }

    public Matrix(String file) {
        try {
            Scanner sc = new Scanner(new File(file));
            size = 0;
            //read table from input file
            int maxim = 100;

            while (sc.hasNextLine() && size<maxim) {
                table.add(getLine(sc.nextLine()));
                size++;
            }

            //generate subPath table
            findLeastTwoWayPath(0,0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MATRIX{" +
            "subPaths=\n" + subPaths +
            '}';
    }
}
