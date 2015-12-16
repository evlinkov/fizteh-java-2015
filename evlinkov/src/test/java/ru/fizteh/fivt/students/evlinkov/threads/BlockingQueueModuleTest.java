package ru.fizteh.fivt.students.evlinkov.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockingQueueModuleTest {
    private static BlockingQueue <Integer> queue = new BlockingQueue<Integer>(10);
    static class TestOffer implements Runnable {
        private List<Integer> elements = new LinkedList<Integer>();
        TestOffer(int numberOfElements) {
            for (int i = 0; i < numberOfElements; ++i) {
                elements.add(i);
            }
        }
        @Override
        public void run() {
            try {
                queue.offer(elements);
                System.out.print("Offer\n");
            } catch (InterruptedException e) { }
        }
    }
    static class TestTake implements Runnable {
        private List<Integer> elements;
        int numberOfElements;
        TestTake (int number) {
            numberOfElements = number;
        }
        @Override
        public void run() {
            try {
                elements = queue.take(numberOfElements);
                System.out.print("Take :");
                for (int i = 0; i < elements.size(); ++i) {
                    System.out.print(" " + elements.get(i));
                }
                System.out.print("\n");
            } catch (InterruptedException e) { }
        }
    }
    public static void main (String [] args) {
        new Thread(new TestOffer(5)).start();
        new Thread(new TestOffer(3)).start();
        new Thread(new TestOffer(3)).start();
        new Thread(new TestTake(3)).start();
        new Thread(new TestTake(9)).start();
        new Thread(new TestOffer(2)).start();
    }
}
