/**
 * scala99
 * Created by chengpohi on 6/25/16.
 */
public class TestTmp {
    public static void main(String[] args) {
        TestFunctionalInterface<String> testFunctionalInterface = String::toUpperCase;
        testFunctionalInterface.apply("asdf");
        int[] a = new int[]{1, 2, 3};
        Foo foo1 = new Foo(a);
        System.out.println(a[0]);
        Dog aDog = new Dog("Max");

        System.out.println(aDog);
        foo(aDog);
        System.out.println(aDog);

        if (aDog.getName().equals("Max")) { //true
            System.out.println("Java passes by value.");
        } else if (aDog.getName().equals("Fifi")) {
            System.out.println("Java passes by reference.");
        }
    }

    public static void foo(Dog d) {
        d.getName().equals("Max"); // true
        d = new Dog("Fifi");
        d.getName().equals("Fifi"); // true
        System.out.println(d);
        System.out.println(d.getName());
    }
}

class Foo {
    public int[] a;

    public Foo(int[] a) {
        this.a = a;
        a[0] = 5;
    }
}

class Dog {
    public String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
