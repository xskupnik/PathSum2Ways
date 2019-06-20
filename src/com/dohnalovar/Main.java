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
import com.dohnalovar.matrix.Matrix;

public class Main {

    public static void main(String[] args) {


        Matrix x = new Matrix("p081_matrix.txt");
        //System.out.println(x.get(0,0) + " ... " + x.get(x.getSize()-1, x.getSize()-1));

        LeastPath lp = x.getSubPath(0, 0);
        System.out.println("from position (0,0) " + lp.toString());

        lp = x.getSubPath(x.getSize()-2, x.getSize()-2);
        System.out.println("from position (78,78) " + lp.toString());
    }
}
