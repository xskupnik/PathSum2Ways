package com.dohnalovar.matrix;

/**
 * Created by dohnalovar on 6/20/2019
 */
public class LeastPath {
    int sum;
    String direction;

    public LeastPath() {
        this.sum = 0;
        this.direction = "";
    }

    protected void setSum(int sum) {
        this.sum = sum;
    }

    protected void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSum() {
        return sum;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "LeastPath{" +
            "sum=" + sum +
            ", direction='" + direction + '\'' +
            '}' + '\n';
    }
}
