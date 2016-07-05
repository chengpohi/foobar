import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * scala99
 * Created by chengpohi on 6/9/16.
 */
public class TestCAS {
    public static void main(String[] args) throws InterruptedException {
        CasCounter casCounter = new CasCounter();
        IntStream.range(0, 2000).parallel().forEach(i -> {
            int increment = casCounter.increment();
            System.out.println("increment = " + increment);
        });
    }

    private static FutureTask<Integer> casThreadFactory(CasCounter casCounter) {
        FutureTask<Integer> futureTask = new FutureTask<>(casCounter::increment);
        return futureTask;
    }
}

class SimulateCAS {
    private int value = 0;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue)
            value = newValue;
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}

class CasCounter {
    private SimulateCAS simulateCAS = new SimulateCAS();

    public int getValue() {
        return simulateCAS.get();
    }

    public int increment() {
        int v;
        do {
            v = simulateCAS.get();
        } while (v != simulateCAS.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
