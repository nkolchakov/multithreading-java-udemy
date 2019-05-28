package threadInterrupt;

import java.math.BigInteger;

public class InterruptDemo {
    public static void main(String[] args) {
//        interruptWithException();
        interruptWithHandling();
    }

    private static void interruptWithException () {
        // create a thread
        // sleep in for a long time, this forces to implement InterruptException
        // on interrupt it should exit

        Thread thread = new Thread(() -> {
            try {
                System.out.println("start sleeping ...");
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                System.out.println("interrupted from outside !");
            }
        });

        thread.start();
    }

    private static void interruptWithHandling() {
        Thread thread = new Thread(
                new LongComputationTask(
                        new BigInteger("20000"),
                        new BigInteger(("1200000")))
        );


//        thread.setDaemon(true);
        thread.setName("long-computation");
        System.out.println("start computing ...");
        thread.start();
        thread.interrupt();
    }



    private static class LongComputationTask implements Runnable {

        private BigInteger base;
        private BigInteger power;

        LongComputationTask(BigInteger base, BigInteger power){
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(this.base + "^" + this.power + " = " + pow(this.base, this.power));
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

    }
}
