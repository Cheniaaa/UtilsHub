package practice;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @Description : 最大矩形
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/4 13:12:12
 * @Version : 1.0
 */

public class T85 {

    /**
     * 单调栈
     * @param matrix
     * @return
     */
    public int solution1(char[][] matrix) {
        int res = 0;
        int len = matrix[0].length;
        if (matrix.length == 0) {
            return res;
        }
        int[] heights = new int[len];
        int height, weight;
        Deque<Integer> deque = new ArrayDeque<>();
        for (char[] chars : matrix) {
            for (int col = 0; col <= len; col++) {
                if (col < len) {
                    if (chars[col] == '1') {
                        heights[col] += 1;
                    } else {
                        heights[col] = 0;
                    }
                }

                while (!deque.isEmpty() && (col == len || heights[col] < heights[deque.peekLast()])) {
                    height = heights[deque.pollLast()];
                    weight = deque.isEmpty() ? col : col - deque.peekLast() - 1;
                    res = Math.max(res, height * weight);
                }
                if (col < len) {
                    deque.offerLast(col);
                }

            }
        }
        return res;
    }


    /**
     * 动态规划
     * 思路：循环找出每层当次列的左右低于本列的下标
     * @param matrix
     * @return
     */
    public int solution2(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int maxArea = 0;
        int cols = matrix[0].length;
        int[] leftLessMin = new int[cols];
        int[] rightLessMin = new int[cols];
        Arrays.fill(leftLessMin, -1); //初始化为 -1，也就是最左边
        Arrays.fill(rightLessMin, cols); //初始化为 cols，也就是最右边
        int[] heights = new int[cols];
        for (int row = 0; row < matrix.length; row++) {
            //更新所有高度
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }

            //更新所有leftLessMin
            int boundary = -1; //记录上次出现 0 的位置
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    //与0的位置进行比较，找出距离本次列更近的列
                    leftLessMin[col] = Math.max(leftLessMin[col], boundary);
                } else {
                    //当前是 0 代表当前高度是 0，所以初始化为 -1，防止对下次循环的影响
                    leftLessMin[col] = -1;
                    //更新 0 的位置
                    boundary = col;
                }
            }
            //右边同理
            boundary = cols;
            for (int col = cols - 1; col >= 0; col--) {
                if (matrix[row][col] == '1') {
                    rightLessMin[col] = Math.min(rightLessMin[col], boundary);
                } else {
                    rightLessMin[col] = cols;
                    boundary = col;
                }
            }

            //更新所有面积
            for (int col = cols - 1; col >= 0; col--) {
                int area = (rightLessMin[col] - leftLessMin[col] - 1) * heights[col];
                maxArea = Math.max(area, maxArea);
            }

        }
        return maxArea;
    }

    public static void main(String[] args) {
        T85 t = new T85();
        char[][] matrix = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        int res = t.solution2(matrix);
        System.out.println(res);
    }
}
