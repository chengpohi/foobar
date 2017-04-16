import java.util.function.Predicate;

@FunctionalInterface
public interface TestFunctionalInterface<T> {
    T apply(T t);
}
