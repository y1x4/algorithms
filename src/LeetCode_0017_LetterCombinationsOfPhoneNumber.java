import java.util.ArrayList;
import java.util.List;

public class LeetCode_0017_LetterCombinationsOfPhoneNumber {

    /**
     * 0 <= digits.length <= 4
     * digits[i] is a digit in the range ['2', '9'].
     */

    private String[] letters = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() > 0) {
            letterCombinations(digits, res, new StringBuilder(), digits.length(), 0);
        }
        return res;
    }

    private void letterCombinations(String digits, List<String> res, StringBuilder sb, int n, int i) {
        if (i == n) {
            res.add(sb.toString());
        } else {
            String s = letters[digits.charAt(i) - '2'];
            for (int j = 0; j < s.length(); j++) {
                letterCombinations(digits, res, sb.append(s.charAt(j)), digits.length(), i + 1);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }


    public static void main(String[] args) {
        LeetCode_0017_LetterCombinationsOfPhoneNumber obj = new LeetCode_0017_LetterCombinationsOfPhoneNumber();
    }
}
