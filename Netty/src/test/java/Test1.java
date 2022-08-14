import net.bytebuddy.agent.ByteBuddyAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/9 19:37:37
 * @Version : 1.0
 */

public class Test1 {
    public static void main(String[] args) {
        Scanner scanner;
        Scanner scanner0 = new Scanner(System.in);
        int num = scanner0.nextInt();

        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            arr.add(0);
        }

        for (int i = 1; i <= num; i++) {
            scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            List<Integer> list = Arrays.stream(str.split(" ")).toList().stream().map(Integer::parseInt).toList();
            if (i == 1 || i == num) {
                arr.set(i - 1, list.get(0));
                continue;
            }
            initArr(arr, list, i);
        }
        System.out.println(arr);
    }


    public static void initArr(List<Integer> arr, List<Integer> list, Integer n) {
        int data = 0;

        






        arr.set(n, data);
    }


}
