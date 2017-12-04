package concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * scala99
 * Created by chengpohi on 7/27/16.
 */
public class AnonymousLambda {
    public static void main(String[] args) {
        Module module = b -> b.bind("Hello");
        RecordBinder.init(Arrays.asList(module));
        System.out.println("RecordBinder.RECORD_BINDER.strs.size() = " + RecordBinder.RECORD_BINDER.strs.size());
        System.out.println("RecordBinder.RECORD_BINDER.strs[0] = " + RecordBinder.RECORD_BINDER.strs.get(0));
    }
}

interface Module {
    void apply(Binder binder);
}

interface Binder {
    void bind(String s);
}

class RecordBinder implements Binder {
    static RecordBinder RECORD_BINDER = new RecordBinder();
    List<String> strs = new ArrayList<>();

    @Override
    public void bind(String s) {
        strs.add("foo " + s + " bar");
    }

    public static void init(List<Module> modules) {
        modules.forEach(m -> m.apply(RECORD_BINDER));
    }
}
