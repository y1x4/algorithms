import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeetCode_0054_SpiralMatrix {

    /**
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 10
     * -100 <= matrix[i][j] <= 100
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<Integer> res = new ArrayList<>(m * n);

        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                res.add(matrix[top][j]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom && left <= right) {
                for (int j = right; j >= left; j--) {
                    res.add(matrix[bottom][j]);
                }
                bottom--;

                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        LeetCode_0054_SpiralMatrix obj = new LeetCode_0054_SpiralMatrix();
        assert Objects.equals(obj.spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), new int[]{1, 2, 3, 6, 9, 8, 7, 4, 5});
        assert Objects.equals(obj.spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}), new int[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7});
    }
}
