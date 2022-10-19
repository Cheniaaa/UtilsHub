package practice;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @Description : 柱状图中最大矩形
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/3 15:45:45
 * @Version : 1.0
 */

public class T84 {

    public int solution1(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }

        if (len == 1) {
            return heights[0];
        }
        //增加前后两个哨兵，减少判空栈的过程
        int[] newHeight = new int[len + 2];
        newHeight[0] = 0;
        System.arraycopy(heights, 0, newHeight, 1, len);//相当于替换元素
        newHeight[len + 1] = 0;
        len += 2;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.add(0);

        int height, weight, res = 0;
        for (int i = 1; i < len; i++) {
            while (newHeight[i] < newHeight[stack.peekLast()]) {
                height = newHeight[stack.pollLast()];
                weight = i - stack.peekLast() - 1;
                res = Math.max(res, height * weight);
            }
            /*
                 栈中代表可以继续向右扩展的下标
                 单调栈：栈中呈现单调递增
             */

            stack.offerLast(i);
        }
        return res;
    }


    /**
     * 未使用哨兵的方式，需要考虑栈为空的情况
     *
     * @param heights
     * @return
     */
    public int solution2(int[] heights) {
        /*
        Tips：deque可以作为队列也可以作为栈，作为栈时要注意进出是last还是first，队头封闭和队尾封闭都可以

        栈(stack) -> 双端队列(deque)
            push -> addFirst
            pop -> removeFirst
            peek -> peekFirst

        以下采用队尾封闭
         */
        Deque<Integer> stk = new ArrayDeque<>();
        int res = 0;
        for (int i = 0; i <= heights.length; i++) {
            while (!stk.isEmpty() && (i == heights.length || heights[i] < heights[stk.peekFirst()])) {
                int height = heights[stk.pollFirst()];
                int weight = stk.isEmpty() ? i : i - stk.peekFirst() - 1;
                res = Math.max(res, height * weight);
            }
            if (i < heights.length) stk.offerFirst(i);
        }
        return res;
    }


    public static void main(String[] args) {

        int[] heights = {2, 1, 5, 6, 2, 3};
        T84 t = new T84();
        int res = t.solution2(heights);
        System.out.println(res);
    }
}
