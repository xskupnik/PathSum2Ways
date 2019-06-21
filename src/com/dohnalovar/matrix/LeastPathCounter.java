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
        }
        return sum;
    }


    public LeastPathCounter(Set<Character> way, int ... position) {
        try {

            int size = table.getSize();

            //check input parameters
            if (position.length < 4)
                throw new Exception("4 position integers are expected.");
            for (int i = 0; i < 4; i++) {
                if (position[i] < 0 || position[i] > (size-1))
                    throw new Exception("Position ints must be between 0 and "+ (size-1));
            }
            rowMax = !way.contains('U') ? position[2] : (table.getSize()-1);
            rowMin = !way.contains('D') ? position[2] : 0;
            colMax = !way.contains('L') ? position[3] : (table.getSize()-1);
            colMin = !way.contains('R') ? position[3] : 0;

            if (rowMin > position[0] && position[0] > rowMax)
                throw new Exception("Start I position must be between " + rowMin + " and " + rowMax);
            if (colMin > position[1] || position[1] > colMax)
                throw new Exception("Start J position must be between " + colMin + " and " + colMax);

            // set start and target position
            this.from = new Position(position[0], position[1]);
            this.to = new Position(position[2], position[3]);

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
        }
    }

    @Override
    public String toString() {
        return "MATRIX{" +
            "subPaths=\n" + subPaths +
            '}';
    }
}
