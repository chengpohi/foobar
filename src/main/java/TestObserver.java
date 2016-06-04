import java.util.Observable;
import java.util.Scanner;

/**
 * scala99
 * Created by chengpohi on 6/4/16.
 */
public class TestObserver {
    public static void main(String[] args) {
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
