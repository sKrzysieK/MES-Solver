package agh.ics.rrir.project;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class Integral {
    public static double integrate(double a, double b, PolynomialFunction function) {
        double[] points = {-1 / Math.sqrt(3), 1 / Math.sqrt(3)};
        double result = 0;
        double c1 =  (b - a) / 2.0;
        double c2 =  (b + a) / 2.0;

        for(double point : points){
            result += function.value(c1 * point + c2);
        }

        result *= c1;
        return result;
    }
}