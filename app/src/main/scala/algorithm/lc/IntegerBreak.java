package algorithm.lc;

public class IntegerBreak {
    public static int integerBreak(int n) {
        if (n <= 3) { return n - 1; }
        int x = n / 3, y = n % 3;
        //恰好整除，直接为3^x
        if (y == 0) { return (int)Math.pow(3, x); }
        //余数为1，退一步 3^(x-1)*2*2
        if (y == 1) { return (int)Math.pow(3, x - 1) * 4; }
        //余数为2，直接乘以2
        return (int)Math.pow(3, x) * 2;
    }
}
