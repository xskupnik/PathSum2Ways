/*
In the 5 by 5 matrix below, the minimal path sum from the top left to the bottom right,
by only moving to the right and down, is indicated in bold red and is equal to 2427.

[...]

Find the minimal path sum, in matrix.txt (right click and "Save Link/Target As..."), a 31K text
file containing a 80 by 80 matrix, from the top left to the bottom right by only moving right
and down.

 */
package com.dohnalovar;

import com.dohnalovar.matrix.LeastPath;
import com.dohnalovar.matrix.LeastPathCounter;

import java.util.Arrays;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {


        LeastPathCounter x = new LeastPathCounter(
             new HashSet<>(Arrays.asList('R', 'D')),
            0, 0, 79, 79
        );

        LeastPath lp = x.getSubPath(0, 0);
        System.out.println("from position (0,0) " + lp.toString());

        lp = x.getSubPath(78, 78);
        System.out.println("from position (78,78) " + lp.toString());
/*
        LeastPathCounter y = new LeastPathCounter(
            new HashSet<>(Arrays.asList('L', 'U')),
            2, 2, 0, 0
        );
        LeastPath lp = y.getSubPath(2, 2);
        System.out.println("from position (2,2) " + lp.toString());
*/
    }
}
