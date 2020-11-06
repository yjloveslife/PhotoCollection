import annotation1.UseCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTest {

    @Value("${url}")
    String url;

    @Test
    public void One() {
        System.out.println(System.currentTimeMillis());
        String path = System.getProperty("user.dir")+"/src/main/resources/static/img/";
        path = path.replace("\\","/");
        File file = new File(path);
        File[] filelist = file.listFiles();
        System.out.println(filelist+"!!!!!!!!!");
        System.out.println(path);
        System.out.println(filelist[0].getName());
    }

    @Test
    public void two() {
        long a = System.currentTimeMillis();
        System.out.println(a);
        System.out.println(url);
    }

    @Test
    public void test1() {
        final int a = -129,b = -129;
        Integer c = -129 ,d = -129;
        System.out.println(a==b);
        System.out.println(c.equals(d));
    }
    @Test
    public void solution(){
        Solution solution = new Solution();
//        List<Integer> list = new ArrayList<Integer>(3);
        int result = solution.islandPerimeter(new int[][]{{0,1,0,0}, {1,1,1,0}, {0,1,0,0}, {1,1,0,0}});
//        Arrays.sort(new int[]{1,3,2,4,6,5});
        System.out.println(result);
    }
    @Test
    public void learn_array(){
        List<Integer> arr = new ArrayList<>();
    }
}

class PasswordUtils{
    @UseCase(id =28 ,description = "hello annotation")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }
}
class Solution {
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public int islandPerimeter(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    ans += dfs(i, j, grid, n, m);
                }
            }
        }
        return ans;
    }

    public int dfs(int x, int y, int[][] grid, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] == 0) {
            return 1;
        }
        if (grid[x][y] == 2) {
            return 0;
        }
        grid[x][y] = 2;
        int res = 0;
        for (int i = 0; i < 4; ++i) {
            int tx = x + dx[i];
            int ty = y + dy[i];
            res += dfs(tx, ty, grid, n, m);
        }
        return res;
    }
}