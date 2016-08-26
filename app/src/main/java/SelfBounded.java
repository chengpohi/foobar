public class SelfBounded<SELF extends SelfBounded<SELF>> {
    public String testField = "TEST";

    public SelfBounded() {

    }

    public SELF testMethod() {
        return (SELF) this;
    }
}

