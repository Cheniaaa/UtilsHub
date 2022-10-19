package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description : 合并区间
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/1 01:14:14
 * @Version : 1.0
 */

public class T56 {
    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {5, 8}, {4, 6}, {8, 10}};
        int[][] merge = merge(intervals);
        System.out.println(Arrays.deepToString(merge));
    }

    public static int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        if (intervals.length == 0 || intervals == null) {
            return list.toArray(new int[0][]);
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int index = 0;
        int left, right;
        while (index < intervals.length) {
            left = intervals[index][0];
            right = intervals[index][1];

            while (index < intervals.length - 1 && right >= intervals[index + 1][0]) {
                index++;
                right = Math.max(right, intervals[index][1]);
            }
            index++;
            list.add(new int[]{left, right});
        }

        return list.toArray(new int[0][]);
    }
}
