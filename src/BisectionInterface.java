import java.util.*;

public interface BisectionInterface {
    public Vector<Double> stringToDblVector(String coefficients);
    public void printIterationData(double[] iterData);
    public double createFunction(Vector<Double> coefficients, int vectorSize, double value);
    public double getXm(double xl, double xu);
    public double getE(double xmi, double xm);
}
