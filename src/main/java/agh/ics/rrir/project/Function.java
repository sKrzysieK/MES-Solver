package agh.ics.rrir.project;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import java.util.Arrays;

public class Function {
    private final double h;
    private PolynomialFunction formula;

    public Function(double h) {
        this.h = h;
    }

    public void setFormula(int interval, int targetInterval) {
        double[] coefs;
        if (interval == targetInterval) coefs = new double[]{1 - interval, 1/h};
        else if (interval + 1 == targetInterval) coefs = new double[]{1 + interval, -1/h};
        else coefs = new double[]{0};
        formula = new PolynomialFunction(coefs);
    }

    public void setDerivativeFormula(int interval, int targetInterval) {
        double[] coefs;
        if (interval == targetInterval) coefs = new double[]{1 / h};
        else if (interval + 1 == targetInterval) coefs = new double[]{-1 / h};
        else coefs = new double[]{0};
        formula = new PolynomialFunction(coefs);
    }

    public void multiplyCoefs(double number) {
        this.formula = new PolynomialFunction(Arrays.stream(this.formula.getCoefficients()).map(coef -> coef * number).toArray());
    }

    public double getFunctionConstCoef() {
        return this.formula.getCoefficients()[0];
    }

    public PolynomialFunction getFunctionFormula(){
        return this.formula;
    }


}