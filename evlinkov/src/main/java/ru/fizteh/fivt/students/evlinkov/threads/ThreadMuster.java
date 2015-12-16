package ru.fizteh.fivt.students.evlinkov.threads;

import java.util.Random;

public class ThreadMuster {
    public static class Runner {
        private Random randNumber = new Random();
        private boolean ready = false;
        private int printString = 1;
        private int currentNumber = 0;
        class AnswerQuestion extends Thread {
            private volatile int numberOfThread;
            AnswerQuestion(int number) {
                numberOfThread = number + 1;
            }
            @Override
            public void run() {
                while (true) {
                    while (printString != numberOfThread) {
                        if (ready) {
                            return;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) { }
                    }
                    int doit = randNumber.nextInt(10) + 1;
                    if (doit == 10) {
                        System.out.print("No\n");
                    } else {
                        System.out.print("Yes\n");
                        currentNumber++;
                    }
                    printString++;
                }
            }
        }
        void run(int n) {
            System.out.print("Are you ready?\n");
            AnswerQuestion[] threads = new AnswerQuestion[n];
            for (int i = 0; i < n; ++i) {
                threads[i] = new AnswerQuestion(i);
                threads[i].start();
            }
            while (true) {
                if (printString == n + 1) {
                    if (currentNumber == n) {
                        ready = true;
                        break;
                    } else {
                        System.out.print("Are you ready?\n");
                        currentNumber = 0;
                        printString = 1;
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { }
                }
            }
            for (int i = 0; i < n; ++i) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) { }
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
