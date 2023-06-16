import java.util.Objects;

public class LeetCode_0383_RansomNote {

    /**
     * - 1 <= ransomNote.length, magazine.length <= 105
     * - ransomNote and magazine consist of lowercase English letters.
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        // letter map stores occur times of letters in the magazine
        short[] letterMap = new short[26];
        for (int i = 0; i < magazine.length(); i++) {
            letterMap[magazine.charAt(i) - 'a']++;
        }

        // iterate and check if the char of ransomNote is enough in letter map
        for (int i = 0; i < ransomNote.length(); i++) {
            if (--letterMap[ransomNote.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        LeetCode_0383_RansomNote obj = new LeetCode_0383_RansomNote();
        assert Objects.equals(obj.canConstruct("aa", "ab"), true);
        assert Objects.equals(obj.canConstruct("aa", "aab"), true);
    }
}
