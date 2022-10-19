package practice;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/12 00:02:02
 * @Version : 1.0
 */

public class T75 {

    public void solution1(int[] nums) {
        Arrays.sort(nums);
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 2, 2, 1, 0};
        T75 t = new T75();
        t.solution1(nums);

    }
}
