import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class BisectionMethods implements BisectionInterface{

    @Override
    public Vector<Double> stringToDblVector(String coefficients) {
        String[] abc = coefficients.split(" "); //whitespaces can be denoted by \\s+

        Vector<Double> coeffVec = new Vector<Double>();

        for(String strCoeff : abc){
            double intCoeff = Double.parseDouble(strCoeff);
            coeffVec.addElement(intCoeff);
        }

        System.out.println("Inputted Vector: " + coeffVec);
        return coeffVec;
    }
    
    @Override
    public void printIterationData(double[] iterData) {
        int index=0;
        for(double column : iterData){
            if(index != 4) {
                if(index == 0) System.out.printf("%-8.0f",column);
                else System.out.printf("%-12s",column);
            }
            index++;
        }
        System.out.println();
        return;
    }

    @Override
    public double createFunction(Vector<Double> coefficients, int vectorSize, double value) {
        double sum = 0;
        for(int x=vectorSize-1;x>=0;x--){
            double term = coefficients.get(x)*Math.pow(value,x);
            sum = sum + term;
        }
        DecimalFormat df = new DecimalFormat("0.00000");
        if(sum > 0) df.setRoundingMode(RoundingMode.FLOOR);
        else df.setRoundingMode(RoundingMode.CEILING);
        double truncatedSum = Double.parseDouble(df.format(sum));
        return truncatedSum;
    }

    @Override
    public double getXm(double xl, double xu) {
        double xm = (xl+xu)/2.0;
        double truncatedXm = 0;
        if(xm != 0){
            DecimalFormat df = new DecimalFormat("0.00000");
            if(xm > 0) df.setRoundingMode(RoundingMode.FLOOR);
            else df.setRoundingMode(RoundingMode.CEILING);
            truncatedXm = Double.parseDouble(df.format(xm));
        }
        return truncatedXm;
    }
    
    @Override
    public double getE(double xmi, double xm) {
        double e = Math.abs(((xmi - xm)/xmi) * 100);
        double truncatedE = 0;
        if(e != 0) {
            DecimalFormat df = new DecimalFormat("0.00000");
            df.setRoundingMode(RoundingMode.FLOOR);
            truncatedE = Double.parseDouble(df.format(e));
        }
        return truncatedE;
    }
    
}
