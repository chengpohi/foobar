import java.util.Observable;
import java.util.Scanner;

/**
 * scala99
 * Created by chengpohi on 6/4/16.
 */
public class TestObserver {
    private volatile int i = 0;
    public static void main(String[] args) {
        int a = 1;
        int b = a;
        a = 2;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        EventSource eventSource = new EventSource();
        eventSource.addObserver((Observable obj, Object org) -> {
            System.out.println("Received response:" + org);
        });

        new Thread(eventSource).start();
    }
}

class EventSource extends Observable implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Input next:");
            String input = new Scanner(System.in).next();
            setChanged();
            notifyObservers(input);
        }
    }
}
