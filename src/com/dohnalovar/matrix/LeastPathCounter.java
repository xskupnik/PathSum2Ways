package com.dohnalovar.matrix;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dohnalovar on 6/19/2019
 */
public class LeastPathCounter {

    class Position {
        int i;
        int j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    /** input table */
    private Matrix table = Matrix.getInstance();
    /** Find the least path from -> to */
    private Position from;
    private Position to;
    /** computed table of the lowest paths */
    private List<List<LeastPath>> subPaths = new ArrayList<>();

    /** reachable positions */
    private int rowMin;
    private int rowMax;
    private int colMin;
    private int colMax;


    /** get the least path from position (i, j) to Position to */
    public LeastPath getSubPath(int i, int j) {
        return subPaths.get(i).get(j);
    }

    /** get value[i, j] of original matrix */
    public int get(int i, int j) {
        if (0<=i && i<=(table.getSize()-1) && 0<=j && j<=(table.getSize()-1))
            return table.getValue(i, j);
        else return -1;
    }

    /** set sum and direction in computed table */
    private void go(int i, int j, int sum, char direction) throws Exception {
        LeastPath lp = subPaths.get(i).get(j);
        lp.setSum(sum);
        switch (direction) {
            case 'D':
                lp.setDirection("D"+ subPaths.get(i+1).get(j).getDirection());
                break;
            case 'U':
                lp.setDirection("U"+ subPaths.get(i-1).get(j).getDirection());
                break;
            case 'R':
                lp.setDirection("R" + subPaths.get(i).get(j+1).getDirection());
                break;
            case 'L':
                lp.setDirection("L" + subPaths.get(i).get(j-1).getDirection());
                break;
            default:
                throw new Exception("Unsupported direction");
        }

    }



    /** right, left, up, down - may cycle - works just for 2 ways and with different axes*/
    private int findLeastPath(int i, int j, Set<Character> way) {

        //skip computing if it has been already computed
        LeastPath lp = subPaths.get(i).get(j);
        int sum = lp.getSum();
        if ( sum != 0 ) return sum;

        //requested position
        if (i == to.i && j == to.j) {
            sum = get(i, j);
            lp.setSum(sum);
            return sum;
        }

        // I hope matrix does not contain Integer.MAX_VALUE;
        int up = Integer.MAX_VALUE;
        int down = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;


        if (i < rowMax && way.contains('D')) {
            down = findLeastPath(i+1, j, way);
        }
        if (j < colMax && way.contains('R')) {
            right = findLeastPath(i, j+1, way);
        }
        if (i > rowMin && way.contains('U')) {
            up = findLeastPath(i-1, j, way);
        }
        if (j > colMin && way.contains('L')) {
            left = findLeastPath(i, j-1, way);
        }


        int min = Math.min(Math.min(Math.min(up, down), right), left);
        sum = get(i, j) + min;

        try {
            if (up == min) {
                go(i, j, sum, 'U');
            } else if (down == min) {
                go(i, j, sum, 'D');
            } else if (right == min){
                go(i, j, sum, 'R');
            } else {
                go(i, j, sum, 'L');
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return sum;
    }


    public LeastPathCounter(Set<Character> way, int startI, int startJ, int endI, int endJ) {
        try {

            int size = table.getSize();

            //check input parameters
            if (startI < 0 || endI < 0 || startI > (size-1) || endI > (size-1) ||
                startJ < 0 || endJ < 0 || startJ > (size-1) || endJ > (size-1))
                throw new Exception("Start and End positions must be between 0 and "+(size-1));


            rowMax = way.contains('U') ? (table.getSize()-1) : endI;
            rowMin = way.contains('D') ? 0 : endI;
            colMax = way.contains('L') ? (table.getSize()-1) : endJ;
            colMin = way.contains('R') ? 0 : endJ;

            if (!(rowMin <= startI && startI <= rowMax))
                throw new Exception("StartI position must be between " + rowMin + " and " + rowMax);
            if (!(colMin <= startJ && startJ <= colMax))
                throw new Exception("StartJ position must be between " + colMin + " and " + colMax);

            // set start and target position
            this.from = new Position(startI, startJ);
            this.to = new Position(endI, endJ);

            // initialize calculating table
            for (int i = 0; i < size; i++) {
                List<LeastPath> myPaths = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    myPaths.add(new LeastPath());
                }
                subPaths.add(myPaths);
            }

            findLeastPath(from.i, from.j, way);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    @Override
    public String toString() {
        return "MATRIX{" +
            "subPaths=\n" + subPaths +
            '}';
    }
}
