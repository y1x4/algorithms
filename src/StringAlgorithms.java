import java.util.Arrays;

public class StringAlgorithms {


    /**
     * 前后可能有空格，O(1)空间
     */
    public String reverseWords(String s) {
        if (s == null || s.length() == 0)
            return s;

        int n = s.length();
        char[] chars = s.toCharArray();

        // 1.翻转
        char temp;
        for (int i = 0; i <= (n >> 1); i++) {
            temp = chars[i];
            chars[i] = chars[n - 1 - i];
            chars[n - 1 - i] = temp;
        }

        // 2.
        int nextIdx = 0, i = 0, j;
        while (i < n) {
            while (i < n && !isLetter(chars[i]))
                i++;
            j = i;
            while (j < n && isLetter(chars[j]))
                j++;

            if (j > i) {
                for (int k = j - 1; k >= i; k--) {
                    chars[nextIdx++] = chars[k];
                }
                i = j;
            }
        }
        return Arrays.toString(chars);
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


    public static void main(String[] args) {
        System.out.println(new StringAlgorithms().reverseWords("  Bob    Loves  Alice   "));
        System.out.println("----------");

    }
}
