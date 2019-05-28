package vaultExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    final static int MAX_VALUE = 9999;
    public static void main(String[] args) {
        // 1 vault w/ a random password
        // 2 hacker threads , counting in ascending and descending order. Whoever brute-force the password, wins.
                // -- Use +1 level of abstraction - HackerThread that extends Thread to add common logic
        // 1 policeman counting from 10 to 0, if it reaches 0 (no one guessed the password), he catches up all of the hackers

        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_VALUE));
        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHacker(vault));
        threads.add(new DescendingHacker(vault));
        threads.add(new Policeman());

        for (Thread thread: threads) {
            thread.start();
        }

    }

    private static class Vault {
        private  int password;

        public Vault(int password){
            this.password = password;
        }

        public boolean isPasswordCorrect(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  this.password == guess;
        }
    }

    private static class HackerThread extends Thread{

        protected Vault vault;
        public HackerThread(Vault vault){
            this.vault = vault;

            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Hacker " + this.getName() + " starting");
            super.start();
        }
    }

    private static class AscendingHacker extends HackerThread {

        public AscendingHacker(Vault vault){
            super(vault);
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_VALUE; i++) {
                if(this.vault.isPasswordCorrect(i)){
                    System.out.println(this.getName() + " cracked the vault w/: " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHacker extends HackerThread {
        public DescendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_VALUE; i >= 0; i--) {
                if(this.vault.isPasswordCorrect(i)){
                    System.out.println(this.getName() + " cracked the vault w/: " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class Policeman extends Thread {
        @Override
        public void run() {
            for (int i = 10; i >= 0; i--) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
            System.out.println("The policeman caught them all !");
            System.exit(0);
        }
    }
}
