package demo;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/4 02:29:29
 * @Version : 1.0
 */

public class Test1 {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(0);
        deque.offerLast(1);
        deque.push(2);
        for (Integer integer : deque) {
            System.out.println(integer);
        }
    }
}
