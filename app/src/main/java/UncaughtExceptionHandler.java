/**
 * scala99
 * Created by chengpohi on 7/21/16.
 */
public class UncaughtExceptionHandler {
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtException());
        TestThread testThread = new TestThread();
        Thread thread = new Thread(testThread);
        thread.start();
        thread.join();
    }
}

class TestThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello");
        throw new RuntimeException("I am uncaught exception");
    }
}

class CustomUncaughtException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught Exception Caught !!!");
        System.out.println("t = [" + t.getName() + "], e = [" + e.getMessage() + "]");
    }
}
