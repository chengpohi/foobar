package concurrency;

import java.util.List;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

/**
 * scala99
 * Created by chengpohi on 4/19/16.
 */
public class ListsFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> strings = asList("a", "b", "c");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        BlockingQueue<String> f =  new LinkedBlockingQueue<String>();

        for (int i = 0; i < strings.size(); i++) {
            executorService.execute(new FFF(f, strings.get(i)));
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        f.stream().forEach(System.out::println);
    }
}

class FFF implements Runnable {
    BlockingQueue<String> f;
    String s;
    public FFF(BlockingQueue<String> f, String s) {
        this.f = f;
        this.s = s;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        f.add(this.s.toUpperCase());
    }
}
