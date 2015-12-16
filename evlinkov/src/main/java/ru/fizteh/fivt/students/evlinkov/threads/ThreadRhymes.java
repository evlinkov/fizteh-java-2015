package ru.fizteh.fivt.students.evlinkov.threads;

public class ThreadRhymes {
    public static class Runner {
        private int currentNumber = 1;
        class PutsThread extends Thread {
            private volatile int numberOfThread;
            PutsThread(int number) {
                numberOfThread = number + 1;
            }
            @Override
            public void run() {
                while (true) {
                    while (currentNumber != numberOfThread) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) { }
                    }
                    System.out.print("Thread-" + numberOfThread + "\n");
                    currentNumber++;
                }
            }
        }
        void run(int n) {
            PutsThread[] threads = new PutsThread[n];
            for (int i = 0; i < n; ++i) {
                threads[i] = new PutsThread(i);
                threads[i].start();
            }
            while (true) {
                if (currentNumber == n + 1) {
                    currentNumber = 1;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { }
                }
            }
        }
    }
    public static void main(String[] args) {
        int n;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        } else {
            n = 0;
        }
        new Runner().run(n);
    }
}
