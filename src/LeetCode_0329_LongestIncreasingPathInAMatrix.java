public class LeetCode_0329_LongestIncreasingPathInAMatrix {

    int maxLength = 0;

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                longestIncreasingPath(matrix, m, n, i, j, 1);
            }
        }
        return maxLength;
    }


    public void longestIncreasingPath(int[][] matrix, int m, int n, int i, int j, int length) {
        if (length > maxLength) {
            maxLength = length;
        }

        if (i < m - 1 && matrix[i + 1][j] > matrix[i][j]) {
            longestIncreasingPath(matrix, m, n, i + 1, j, length + 1);
        }
        if (i > 0 && matrix[i - 1][j] > matrix[i][j]) {
            longestIncreasingPath(matrix, m, n, i - 1, j, length + 1);
        }
        if (j < n - 1 && matrix[i][j + 1] > matrix[i][j]) {
            longestIncreasingPath(matrix, m, n, i, j + 1, length + 1);
        }
        if (j > 0 && matrix[i][j - 1] > matrix[i][j]) {
            longestIncreasingPath(matrix, m, n, i, j - 1, length + 1);
        }
    }

}
