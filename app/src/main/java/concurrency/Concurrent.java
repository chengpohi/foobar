package concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * scala99
 * Created by chengpohi on 1/13/16.
 */
public class Concurrent {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Task> tasks = Arrays.asList(new Task("task1"), new Task("task2"), new Task("task3"));
        for (int i = 0; i <3; i++) {
            executor.execute(new Worker(countDownLatch, tasks.get(i)));
        }
        countDownLatch.await();
        executor.shutdown();
        System.out.println(countDownLatch.getCount());
    }
}

class Commander implements Runnable {
    private CountDownLatch countDownLatch;

    public Commander(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void move() {
        this.countDownLatch.countDown();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            this.move();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Soldier implements Runnable {
    @Override
    public void run() {
        System.out.println("Fighting");
    }
}

class Task {
    private String str;
    public Task(String str) {
        this.str = str;
    }
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
}
class Worker implements Runnable {
    private final CountDownLatch countDownLatch;
    Task task;

    public Worker(CountDownLatch countDownLatch, Task task) {
        this.countDownLatch = countDownLatch;
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(task.getStr());
        this.task.setStr(this.task.getStr().toUpperCase());
        this.countDownLatch.countDown();
        System.out.println("----");
    }
}
