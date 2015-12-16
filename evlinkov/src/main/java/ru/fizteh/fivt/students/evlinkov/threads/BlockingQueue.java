package ru.fizteh.fivt.students.evlinkov.threads;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BlockingQueue<T> {
    private int maxQueueSize;
    private Queue<T> elements;
    BlockingQueue(int maxSize) {
        maxQueueSize = maxSize;
        elements = new LinkedList<>();
    }
    public synchronized void offer(List<T> e) throws InterruptedException {
        while (elements.size() + e.size() > maxQueueSize) {
            wait();
        }
        for (int i = 0; i < e.size(); ++i) {
            elements.add(e.get(i));
        }
        notifyAll();
    }
    public synchronized List<T> take(int n) throws InterruptedException {
        while (elements.size() < n) {
            wait();
        }
        List<T> answer;
        answer = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            answer.add(elements.remove());
        }
        notifyAll();
        return answer;
    }
}
