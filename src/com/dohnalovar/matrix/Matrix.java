package com.dohnalovar.matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dohnalovar on 6/20/2019
 */
public class Matrix {

    private List<List<Integer>> table = new ArrayList<>();

    public Integer getValue(int i, int j) {
        return table.get(i).get(j);
    }

    public int getSize() {
        return table.size();
    }

    private static Matrix ourInstance = new Matrix();

    public static Matrix getInstance() {
        return ourInstance;
    }

    /** process line read from CSV file */
    private List<Integer> getLine(String line) {
        List<Integer> values = new ArrayList<>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.parseInt(rowScanner.next()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }

    private Matrix() {
        try {
            Scanner sc = new Scanner(new File("p081_matrix.txt"));
            int size = 0;
            //read table from input file
            int maxim = 100;

            while (sc.hasNextLine() && size < maxim) {
                table.add(getLine(sc.nextLine()));
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
