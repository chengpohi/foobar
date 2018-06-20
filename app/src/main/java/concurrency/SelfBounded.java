package concurrency;

public class SelfBounded<SELF extends SelfBounded<SELF>> {
    public String testField = "TEST";

    public SelfBounded() {

    }

    public SELF testMethod() {
        return (SELF) this;
    }

    public static void main(String[] args) {
        SelfBounded selfBounded = new SelfBounded<>();
        SelfBounded selfBounded1 = selfBounded.testMethod();
    }
}

