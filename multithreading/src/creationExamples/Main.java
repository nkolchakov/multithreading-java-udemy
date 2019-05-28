package creationExamples;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        threadsCreation();
            creationWithInheritance();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("New thread created w/ name: " + this.getName());
        }
    }

    private static void creationWithInheritance() {
        Thread thread = new NewThread();
        thread.start();
    }

    private static void threadsCreation() {
        Thread thread = new Thread(() -> {
            System.out.println("new thread is running w/ name: " + Thread.currentThread().getName());
        });
        thread.setName("worker 1");
        thread.setPriority(Thread.MAX_PRIORITY);

        Thread misbehavingThread = new Thread(new Runnable(){
            @Override
            public void run() {
                throw new RuntimeException("whoops");
            }
        });
        misbehavingThread.setName("misbehaving thread");
        misbehavingThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("critical error on " + t.getName());
            }
        });

        System.out.println("before: " + Thread.currentThread().getName());
        thread.start();
        misbehavingThread.start();
        System.out.println("after: " + Thread.currentThread().getName());
    }
}
