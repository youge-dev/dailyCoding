package Tree;

import java.util.*;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Tree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        boolean isLeft = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            Deque<Integer> integerDeque = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (isLeft) {
                    integerDeque.offerLast(node.val); //顺序
                } else {
                    integerDeque.offerFirst(node.val); // 逆序
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            isLeft = !isLeft;
            res.add(new LinkedList<>(integerDeque));
        }
        return res;
    }

    /**
     * 236. 二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // p 和 q 在不同的左/右树
        if (left != null && right != null) return root;
        // p和q在相同侧
        return left == null ? right : left;

    }

    /**
     * 42. 接雨水
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int ans = 0;
        int size = height.length;
        for (int i = 1; i < size - 1; i++) {
            int maxLeft = 0, maxRight = 0;
            for (int j = i; j >= 0; j--) {
                maxLeft = Math.max(maxLeft, height[j]);
            }
            for (int j = i; j < size; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }
            ans += Math.min(maxLeft, maxRight) - height[i];
        }
        return ans;
    }

    /**
     * 二叉树的最大深度：max(左子树深度,右子树深度)+1 递归dfs实现
     */
    public static int maxDepthWithDfs(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepthWithDfs(root.left), maxDepthWithDfs(root.right)) + 1;
    }

    /**
     * 二叉树的最大深度：广度优先遍历，每层加1
     */
    public static int maxDepthWithBfs(TreeNode root) {
        if (root == null) return 0;
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.peek();
                queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                size--;
            }
            res += 1;
        }
        return res;
    }

    /**
     * 199. 二叉树的右视图
     * 用层序遍历，每层输出最后一个节点
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            res.add(queue.getLast().val);
            while (size > 0) {
                TreeNode node = queue.peek();
                queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                size--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3);
        node.left = new TreeNode(9);
        node.left.left = new TreeNode(10);
//        node.right = new TreeNode(20);
//        node.right.left = new TreeNode(15);
//        node.right.right = new TreeNode(7)；

    }
}
