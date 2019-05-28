package codingExercicse;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BigInteger base1 = new BigInteger("2");
        BigInteger power1 = new BigInteger("10");

        BigInteger base2 = new BigInteger("5");
        BigInteger power2 = new BigInteger("12");

        ComplexCalculation cc = new ComplexCalculation();
        BigInteger result = cc.calculateResult(base1, power1, base2, power2);

        System.out.println("Result = " + result);
    }
}
