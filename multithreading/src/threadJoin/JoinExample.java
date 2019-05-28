package threadJoin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputs = Arrays.asList(1000000000L, 412L, 2312L, 332L, 6544L, 2221L, 5L);
        List<FactorialThread> threads = new ArrayList<>();

        for (Long input: inputs) {
            threads.add(new FactorialThread(input));
        }

        for (FactorialThread thread: threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (FactorialThread thread: threads) {
            thread.join(2000);
        }

        for (FactorialThread thread: threads) {

            if(thread.isFinished()){
                System.out.println(thread.getInput() +  " done and equal " + thread.getValue());
            }else {
                System.out.println(thread.getInput() + "! NOT calculated yet ...");
            }
        }
    }

    private static class FactorialThread extends Thread {

        private long inputNumber;
        private BigInteger result;
        private boolean isFinished;

        FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = computeFactorial(this.inputNumber);
            this.isFinished = true;
        }

        private BigInteger computeFactorial(long inputNumber){
            BigInteger tempResult = BigInteger.ONE;
            for (long i = inputNumber; i > 0 ; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        public BigInteger getValue() {
            return this.result;
        }

        public long getInput() {
            return this.inputNumber;
        }
    }
}
