package codingExercicse;

import java.math.BigInteger;
import java.util.ArrayList;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result = null;
        ArrayList<PowerCalculatingThread> threads = new ArrayList<PowerCalculatingThread>();
        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2, power2));

        for (PowerCalculatingThread thread: threads) {
            thread.setDaemon(true);
            thread.start();
        };

        for (PowerCalculatingThread thread: threads) {
            try {
                thread.join(3000);
            } catch (InterruptedException e) {
                System.out.println(thread.getName() + " Interrupted");
            }
        }

        BigInteger result1 = threads.get(0).getResult();
        BigInteger result2 = threads.get(1).getResult();

        result = result1.add(result2);

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public synchronized void start() {
            System.out.println("Start calculate " + this.base + "^" + this.power + " in thread: " + this.getName());
            super.start();
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            this.result = pow(this.base, this.power); // or BigInteger.pow(...)
            System.out.println(this.base + "^" + this.power + " = " + this.result);
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Task: " + Thread.currentThread().getName() + " is interrupted");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }

        public BigInteger getResult() { return result; }
    }
}