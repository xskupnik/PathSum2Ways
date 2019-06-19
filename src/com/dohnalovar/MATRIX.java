package com.dohnalovar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dohnalovar on 6/19/2019
 */
public class MATRIX {
    private List<List<Integer>> table = new ArrayList<>();
    private int size;

    private List<Integer> getLine(String line) {
        List<Integer> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.parseInt(rowScanner.next()));
            }
        }
        return values;
    }

    public int get(int i, int j) {
        if (0<=i && i<=(size-1) && 0<=j && j<=(size-1))
            return table.get(i).get(j);
        else return -1;
    }

    public int getSize() {
        return size;
    }

    /* two ways - right or down */
    public int findLeastTwoWayPath(int i, int j) {
        //System.out.println(get(i,j));
        if (i == (size-1) && j == (size-1)) {
            return get(i,j);
        }
        if (i < (size-1) && j == (size-1) ) {
            return get(i,j) + findLeastTwoWayPath(i+1, j);
        }
        if (i == (size-1) && j < (size-1)) {
            return get(i,j) + findLeastTwoWayPath(i, j+1);
        }
        int down = findLeastTwoWayPath(i+1, j);
        int right = findLeastTwoWayPath(i, j+1);

        int result = get(i, j) + ( down<right ? down : right );
        //System.out.println(result);
        return result;
    }

    private static MATRIX ourInstance = new MATRIX();

    public static MATRIX getInstance() {
        return ourInstance;
    }

    private MATRIX() {
        try {
            Scanner sc = new Scanner(new File("p081_matrix.txt"));
            size = 0;
            //it takes too long even for 15, reduce matrix for 12 !!!
            int maxim = 12;

            while (sc.hasNextLine() && size<maxim) {
                table.add(getLine(sc.nextLine()));
                size++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
