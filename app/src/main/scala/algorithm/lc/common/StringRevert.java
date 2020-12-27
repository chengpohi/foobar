package algorithm.lc.common;

public class StringRevert {

    public static String revert(String str) {
        char[] chars = str.toCharArray();
        int i = 0;
        int j = chars.length - 1;

        while (i < j) {
            char t = chars[i];
            chars[i] = chars[j];
            chars[j] = t;
            i++;
            j--;
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(revert("hello"));
    }

}
