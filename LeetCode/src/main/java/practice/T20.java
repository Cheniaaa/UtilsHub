package practice;

import java.util.Stack;

/**
 * @Description : 括号匹配
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/28 18:20:20
 * @Version : 1.0
 */

public class T20 {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        String str = "{{}}[]";
        System.out.println(test2(str));
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }

    public static boolean test1(String s) {
        String str;
        while (!s.isBlank()) {
            str = s;
            s = s.replace("()", "").replace("{}", "").replace("[]", "");
            if (str.equals(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean test2(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '[') stack.push(']');
            else if (c == '{') stack.push('}');
            else if (stack.isEmpty() || c != stack.pop()) return false;
        }
        return stack.isEmpty();
    }
}
