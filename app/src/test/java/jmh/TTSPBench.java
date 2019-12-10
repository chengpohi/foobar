package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.SafepointsProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgsAppend = {"-Xmx1g"})
public class TTSPBench {

    //tune this to change the allocation rate (with <= G1GC)
    private static final int WORKS = 100_000_000;

    @GroupThreads(3)
    @Group("test")
    @Benchmark
    public byte[] youngWillDieFirst() {
        //16 bytes headers with compressedOops: 12 + 4
        //1 cache line is 64 bytes -> it helps to not have fragmentations in regions
        return new byte[64];
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Group("test")
    @Benchmark
    public long ttspDelayer() {
        int limit = WORKS;
        long k = 0;
        for (int l = 0; l < limit; l++) {
            k++;
            if ((k % 2) == 1) { k += l; }
        }
        return k;
        //safepoint goes here if not inlined
    }

    @Group("test")
    @Benchmark
    public void safepointOperation() {
        //the stack is not that deep: it will just trigger a safepoint
        ManagementFactory.getThreadMXBean().dumpAllThreads(false, false);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder()
            .include(TTSPBench.class.getSimpleName())
            //.jvmArgs("-XX:+UseShenandoahGC")
            //.jvmArgs("-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC")
            .addProfiler(GCProfiler.class)
            .addProfiler(SafepointsProfiler.class)
            .build();
        new Runner(opt).run();
    }
}