package concurrency;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * scala99
 * Created by chengpohi on 5/28/16.
 */
public class TestConcurrent {
    public static void main(String[] args) {
        ArrayList<Big> bigs = new ArrayList<>();

        for (int i = 1; i <=  Integer.MAX_VALUE; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bigs.add(new Big());
        }
    }
}
class Big{
    double[] data = new double[1000000];
}
