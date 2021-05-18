package String;

import java.util.*;

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

    /**
     * 93. 复原 IP 地址
     */
    static final int SEG_COUNT = 4;
    static List<String> ans = new ArrayList<String>();
    static int[] segments = new int[SEG_COUNT];

    public static List<String> restoreIpAddresses(String s) {
        segments = new int[SEG_COUNT];
        dfs(s, 0, 0);
        return ans;
    }

    public static void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == s.length()) {
                StringBuffer ipAddr = new StringBuffer();
                for (int i = 0; i < SEG_COUNT; ++i) {
                    ipAddr.append(segments[i]);
                    if (i != SEG_COUNT - 1) {
                        ipAddr.append('.');
                    }
                }
                ans.add(ipAddr.toString());
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
        if (segStart == s.length()) {
            return;
        }

        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); ++segEnd) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xFF) {
                segments[segId] = addr;
                dfs(s, segId + 1, segEnd + 1);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        String s = "25525511";
        System.out.println(restoreIpAddresses(s));
//        System.out.println(s.substring(0, 1));
//        System.out.println(longestPalindrome(s));
//        System.out.println(isValid("([)]"));
    }
}
