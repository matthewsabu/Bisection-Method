import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class BisectionMain {
    private static BisectionMethods methods = new BisectionMethods();
    private static Scanner coefficients = new Scanner(System.in);
    private static Scanner initialConds = new Scanner(System.in);
    private static Scanner stoppingCrit = new Scanner(System.in);
    private static Vector<Double> coeffVec = new Vector<Double>();          //[C,B,A]
    private static ArrayList<Double> initCond = new ArrayList<Double>();    //[xl,xu]
    private static ArrayList<Double> stopCrit = new ArrayList<Double>();    //[i,e,fxr]

    public static void main(String[] args) {
        //initial conditions
        double iterNo=1,xmi,xm,fxl,fxu,fxm,e;
        xmi=xm=fxl=fxu=fxm=e=0;

        //set function assumptions
        setCoeff();
        setInitCond();
        setStopCrit();

        //set initial iteration row (Row #1)
        double[] iterData = {iterNo,initCond.get(0),initCond.get(1),xmi,xm,fxl,fxu,fxm,e};
        
        //perform algo and display table
        System.out.printf("\n%-8s%-12s%-12s%-12s%-12s%-12s%-12s%-12s\n","iter #","xl","xu","xm","f(xl)","f(xu)","f(xm)","e %");
        runBisectionAlgo(coeffVec,stopCrit,iterData);

        coefficients.close();
        initialConds.close();
        stoppingCrit.close();
    }

    //user input
    public static void setCoeff(){
        //define function
        System.out.println("Bisection Method: Find your equation's root!");
        System.out.print("Equation's coefficients ([C B A] Format): ");
        coeffVec = methods.stringToDblVector(coefficients.nextLine());
        return;
    }

    public static void setInitCond() {
        //set initial conditions
        System.out.println("\nInitial Conditions:");
        System.out.print("xl = ");
        initCond.add(initialConds.nextDouble());

        System.out.print("xu = ");
        initCond.add(initialConds.nextDouble());

        System.out.println(initCond);
        return;
    }

    public static void setStopCrit(){
        //set stopping criteria
        System.out.println("\nStopping Criterion:");
        System.out.print("# of Iterations = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.print("|Error| % = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.print("|f(xm)| = ");
        stopCrit.add(stoppingCrit.nextDouble());

        System.out.println(stopCrit);
        return;
    }

    //everything with an "i" at the end indicates the value/s for the next (succeeding) iteration 
    //0=iteration,1=xl,2=xu,3=xmi(NEW),4=xm(OLD),5=fxl,6=fxu,7=fxmi,8=e (iterData index references)
    public static void runBisectionAlgo(Vector<Double> coefficients, ArrayList<Double> stopCrit, double[] iterData) {
        boolean lowerAsymp=false,exactRoot=false;
        double approxRoot,xli,xui;
        approxRoot=xli=xui=0;
        
        iterData[3] = methods.getXm(iterData[1],iterData[2]);   //xmi (NEW xm)
        iterData[5] = methods.createFunction(coefficients,coefficients.size(),iterData[1]); //f(xl)
        iterData[6] = methods.createFunction(coefficients,coefficients.size(),iterData[2]); //f(xu)
        iterData[7] = methods.createFunction(coefficients,coefficients.size(),iterData[3]); //f(xmi)
        iterData[8] = methods.getE(iterData[3],iterData[4]); //e
        iterData[4] = iterData[3];  //xm (OLD xm)
        
        methods.printIterationData(iterData);   //print iteration row

        if((iterData[5]*iterData[7]) < 0){ //f(xl)f(xmi) < 0
            xli=iterData[1];    //xl=xl
            xui=iterData[3];    //xu=xmi (NEW xm)
            lowerAsymp=false;
        } else if((iterData[5]*iterData[7]) > 0){ //f(xl)f(xmi) > 0
            xli=iterData[3];    //xl=xmi (NEW xm)
            xui=iterData[2];    //xu=xu
            lowerAsymp=true;
        } else {
            approxRoot=iterData[3];    //f(xl)f(xmi) = 0
            exactRoot = true;
        }

        iterData[0]++;  //increase iteration counter

        //stopping criterion:
        // iteration <= stopping Iteration && error <= stopping Error% && f(xmi) <= stopping f(xm)
        if(approxRoot!=iterData[3] && !(iterData[0]>stopCrit.get(0)) && !(iterData[8]<=stopCrit.get(1)) && !(Math.abs(iterData[7])<=stopCrit.get(2))){
            //update iteration row (Row #i)
            double[] iterDatai = {iterData[0],xli,xui,iterData[3],iterData[4],iterData[5],iterData[6],iterData[7],iterData[8]};
            
            runBisectionAlgo(coefficients,stopCrit,iterDatai);
        } else {
            System.out.println("\nA stopping criteria has been met--stopped iterating.");
            DecimalFormat df = new DecimalFormat("0.00");
            if(!exactRoot){
                if(lowerAsymp) {
                    if(xli > 0) df.setRoundingMode(RoundingMode.CEILING);
                    else df.setRoundingMode(RoundingMode.FLOOR);
                    approxRoot = Double.parseDouble(df.format(xli));  //lowerAsymp
                }
                else {
                    if(xui > 0) df.setRoundingMode(RoundingMode.FLOOR);
                    else df.setRoundingMode(RoundingMode.CEILING);
                    approxRoot = Double.parseDouble(df.format(xui));  //upperAsymp
                }
                System.out.println("\nThe equation's root is approximately " + approxRoot);
            } else { 
                System.out.println("\nThe equation's root is " + approxRoot);
            }
        }  
        return;
    }
}
