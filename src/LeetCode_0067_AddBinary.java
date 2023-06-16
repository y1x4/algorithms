import java.util.Objects;

public class LeetCode_0067_AddBinary {

    /**
     * - 1 <= a.length, b.length <= 104
     * - a and b consist only of '0' or '1' characters.
     * - Each string does not contain leading zeros except for the zero itself.
     */
    public String addBinary(String a, String b) {
        // fast return
        if ("0".equals(a)) return b;
        if ("0".equals(b)) return a;

        // initial variables
        int i = a.length() - 1, j = b.length() - 1;
        int d1, d2, sum, carry = 0;

        StringBuilder res = new StringBuilder();

        // for loop till i or j < 0 and carry == 0
        while ((i >= 0 && j >= 0) || carry > 0) {
            d1 = i >= 0 ? (a.charAt(i--) - '0') : 0;
            d2 = j >= 0 ? (b.charAt(j--) - '0') : 0;
            sum = d1 + d2 + carry;

            res.append(sum % 2);
            carry = sum / 2;
        }

        // add remind digits to result
        while (i >= 0) {
            res.append(a.charAt(i--));
        }
        while (j >= 0) {
            res.append(b.charAt(j--));
        }

        return res.reverse().toString();
    }


    public static void main(String[] args) {
        LeetCode_0067_AddBinary obj = new LeetCode_0067_AddBinary();
        assert Objects.equals(obj.addBinary("11", "1"), "100");
        assert Objects.equals(obj.addBinary("1010", "1011"), "10101");
    }
}
