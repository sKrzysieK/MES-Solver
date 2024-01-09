package agh.ics.rrir.project;

import agh.ics.rrir.project.matrices.MatrixB;
import agh.ics.rrir.project.matrices.VectorL;
import org.apache.commons.math3.linear.*;

public class Solver {
    private final int n;
    private final double h;

    public Solver(int n, double h){
        this.n = n;
        this.h = h;
    }

    public RealVector solve(){
        // Create real matrices and vectors for the linear system
        RealMatrix B = new MatrixB(n, h).getMatrix();
        RealVector L = new VectorL(n, h).getVector();

        // Solve the linear system
        DecompositionSolver solver = new LUDecomposition(B).getSolver();
        RealVector solution = solver.solve(L);

        System.out.println("SOLUTION:");
        System.out.println(solution);
        return solution;
    }

}
