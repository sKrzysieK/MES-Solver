package agh.ics.rrir.project.matrices;

import agh.ics.rrir.project.Function;
import agh.ics.rrir.project.Integral;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class VectorL {
    private double[] vector;
    private int n;
    private double h;
    private final static double G = 6.674e-1;

    public VectorL(int n, double h){
        this.n = n;
        this.h = h;
        this.vector = new double[n - 1];
        populate(vector);
    }

    private void populate(double[] vectorL){
        Function fun = new Function(h);

        for (int i = 0; i < n - 1; i++) {
            double start = h * (i);
            double mid = h * (i + 1);
            double end = h * (i + 2);
            vectorL[i] = 0;

            if (1 <= mid && start <= 2) {
                fun.setFormula(i + 1, i + 1);
                vectorL[i] = Integral.integrate(Math.max(1, start), Math.min(2, mid), fun.getFunctionFormula());
            }
            if (1 <= end && mid <= 2) {
                fun.setFormula(i + 1, i + 2);
                vectorL[i] += Integral.integrate(Math.max(1, mid), Math.min(2, end), fun.getFunctionFormula());
            }

            fun.setDerivativeFormula(i, i);
            fun.multiplyCoefs(-1 / 3.0);
            vectorL[i] -= (-1) * Integral.integrate(h * i, h * (i + 1), fun.getFunctionFormula());
            fun.setDerivativeFormula(i, i + 1);
            fun.multiplyCoefs(-1 / 3.0);
            vectorL[i] -= (-1) * Integral.integrate(h * (i + 1), h * (i + 2), fun.getFunctionFormula());
            vectorL[i] *= 4 * Math.PI * G;
        }
    }

    public RealVector getVector(){
        return new ArrayRealVector(vector);
    }
}
