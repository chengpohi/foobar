package algorithm.lc;

//https://www.nowcoder.com/questionTerminal/12cbdcdf5d1e4059b6ddd420de6342b6?mutiTagIds=593&orderByHotValue=1&questionTypes=100100&commentTags=Java
public class Visit {
    public int countPath(int[][] map, int n, int m) {
        int br = 0, bc = 0, er = 0, ec = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 1) {
                    br = i;
                    bc = j;
                }
                if (map[i][j] == 2) {
                    er = i;
                    ec = j;
                }
            }
        }
        if (br > er) {
            int temp = br;
            br = er;
            er = temp;
        }
        if (bc > ec) {
            int temp = bc;
            bc = ec;
            ec = temp;
        }
        int[][] dp = new int[n][m];
        for (int i = br; i <= er; i++) {
            for (int j = bc; j <= ec; j++) {
                if (i == br && j == bc) {
                    dp[i][j] = 1;//起始位置设为1
                } else if (map[i][j] != -1) {
                    if (right(i, j - 1, br, bc)) { dp[i][j] += dp[i][j - 1]; }
                    if (right(i - 1, j, br, bc)) { dp[i][j] += dp[i - 1][j]; }
                }
            }
        }
        return dp[er][ec];
    }

    public boolean right(int a, int b, int x, int y) {
        return a >= x && b >= y;
    }
}
