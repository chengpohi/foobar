package concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * scala99
 * Created by chengpohi on 7/19/16.
 */
public class Daemon {
    private final CountDownLatch keepAliveLatch = new CountDownLatch(1);
    private final Thread keepAliveThread;

    public Daemon() {
        keepAliveThread = new Thread(() -> {
            try {
                keepAliveLatch.await();
                System.out.println("Exit !!!");
            } catch (InterruptedException e) {
            }
        });
        keepAliveThread.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void start() {
                keepAliveLatch.countDown();
                System.out.println("Shutdown Hook Executed !!!");
            }
        });
    }

    public static void main(String[] args) {
        Daemon daemon = new Daemon();
        daemon.start();
        System.out.println("Started !!!");
        System.out.close();
        System.out.println("Foo Bar");
    }

    public void start() {
        keepAliveThread.start();
    }
}


