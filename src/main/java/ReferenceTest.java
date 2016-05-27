import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * scala99
 * Created by chengpohi on 5/15/16.
 */
public class ReferenceTest {
    public static void main(String[] args) throws InterruptedException {
        weakReferenceGC();
    }

    private static void weakReferenceGC() throws InterruptedException {
        WeakReference r = new WeakReference<>(new String("I'm here"));
        WeakReference sr = new WeakReference<>("I'm here");
        System.out.println("before gc: r=" + r.get() + ", static=" + sr.get());
        System.gc();
        Thread.sleep(100);
        // only r.get() becomes null
        System.out.println("after gc: r=" + r.get() + ", static=" + sr.get());

        Object aObject = new Object();
        WeakReference wr = new WeakReference<>(aObject);
        String extraData = "Extra Data";
        //create test object reference
        HashMap<WeakReference, String> weakReferenceStringHashMap = new HashMap<>();
        weakReferenceStringHashMap.put(wr, extraData);

        System.out.println("WeakReference Object: " + wr.get());
        System.out.println("WeakReference is Enqueue: " + wr.isEnqueued());

        //remove strong reference
        aObject = null;
        extraData = null;

        System.out.println("---- GC ----");

        System.gc();

        System.out.println("Gc HashMap Size: " + weakReferenceStringHashMap.size());
        System.out.println("Gc WeakReference Object: " + wr.get());
        System.out.println("Gc WeakReference is Enqueue: " + wr.isEnqueued());
    }
}
