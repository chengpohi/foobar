package concurrency;

public class NullPointerExceptionHole {
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            try {
                /**
                 Set VM Option to:
                 -Xint
                 Or
                 -XX:-OmitStackTraceInFastThrow
                 they will disable JIT optimization, this will cause bad performance for this
                 since the full stacktrace in the start. we can just look for start of this exception log.
                 **/
                ((Object) null).getClass();
            } catch (NullPointerException e) {
                System.out.println(e.getStackTrace().length);
            }
        }
    }
}
