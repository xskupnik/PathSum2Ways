/*
In the 5 by 5 matrix below, the minimal path sum from the top left to the bottom right,
by only moving to the right and down, is indicated in bold red and is equal to 2427.

[...]

Find the minimal path sum, in matrix.txt (right click and "Save Link/Target As..."), a 31K text
file containing a 80 by 80 matrix, from the top left to the bottom right by only moving right
and down.

 */
package com.dohnalovar;

public class Main {

    public static void main(String[] args) {


        MATRIX x = MATRIX.getInstance();
        System.out.println(x.get(0,0) + " ... " + x.get(x.getSize()-1, x.getSize()-1));

        System.out.println("\n"+x.findLeastTwoWayPath(0,0));


    }
}
