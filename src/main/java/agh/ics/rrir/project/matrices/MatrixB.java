package agh.ics.rrir.project.matrices;

import agh.ics.rrir.project.Function;
import agh.ics.rrir.project.Integral;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixB {
    private double[][] matrix;
    private int n;
    private double h;

    public MatrixB(int n, double h){
        this.n = n;
        this.h = h;
        matrix = new double[n - 1][n - 1];
        populate(matrix);
    }

    private void populate(double[][] matrix){
        Function f = new Function(h);

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (i == j) {
                    f.setDerivativeFormula(i, j);
                    f.multiplyCoefs(f.getFunctionConstCoef());
                    matrix[i][j] = (-2) * Integral.integrate(h * i, h * (i + 1), f.getFunctionFormula());
                } else if (Math.abs(i - j) == 1) {
                    PolynomialFunction derivative = new PolynomialFunction(new double[]{-1 / (h * h)});
                    matrix[i][j] = (-1) * Integral.integrate(h, h + h, derivative);
                } else matrix[i][j] = 0;
            }
        }
    }

    public RealMatrix getMatrix(){
        return new Array2DRowRealMatrix(matrix);
    }
}
