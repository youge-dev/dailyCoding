package String;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StringTest {
    /**
     * 20 有效的括号
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (s.length() % 2 == 1) return false;
        Map<Character, Character> map = new HashMap<>() {{
            put(']', '[');
            put('}', '{');
            put(')', '(');
        }};
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsValue(s.charAt(i))) stack.push(s.charAt(i));
            if (map.containsKey(s.charAt(i))) {
                if (stack.isEmpty() || stack.peek() != map.get(s.charAt(i))) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * 5. 最长回文子串
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s.length() < 2) return s;

        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        //// dp[i][j] 表示 s[i到j] 是否是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        char[] array = s.toCharArray();
        int begin = 0, maxLen = 1;
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界
            for (int i = 0; i < len; i++) {
                //L=j-i+1;
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) break;
                if (array[i] != array[j]) {
                    dp[i][j] = false;
                } else if (j - i < 3 && array[i] == array[j]) {
                    dp[i][j] = true;
                } else if (array[i] == array[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                }
                if (dp[i][j] && j - i + 1 > maxLen) {
                    begin = i;
                    maxLen = j - i + 1;
                }
            }

        }
        return s.substring(begin, begin + maxLen);
    }

    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println(s.substring(0,1));
        System.out.println(longestPalindrome(s));
//        System.out.println(isValid("([)]"));
    }
}
