package LinkedList;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class LinkedList {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 25. K 个一组翻转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;
        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) end = end.next;
            if (end == null) break;
            ListNode start = pre.next;//反转前的链表头
            ListNode next = end.next;//反转后的链表尾
            end.next = null;//截断与下一个链表的关系
            pre.next = reverse(start); //相邻待反转的链表头
            start.next = next;  // 和没反转的链表链接上
            pre = start;
            end = pre;//pre 和end重置起始点
        }
        return dummy.next;
    }

    //反转链表
    public static ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode cur = head, pre = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    /**
     * 2. 两数相加
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        while (l1 == null || l2 == null) return null;
        ListNode sum = new ListNode(-1);
        ListNode res = sum;
        int jinwei = 0;
        while (l1 != null && l2 != null) {
            if (l1.val + l2.val + jinwei >= 10) {
                sum.next = new ListNode(l1.val + l2.val + jinwei - 10);
                jinwei = 1;
            } else {
                sum.next = new ListNode(l1.val + l2.val + jinwei);
                jinwei = 0;
            }
            l1 = l1.next;
            l2 = l2.next;
            sum = sum.next;
        }
        ListNode diff = l1 == null ? l2 : l1;
        while (diff != null) {
            if (diff.val + jinwei >= 10) {
                sum.next = new ListNode(diff.val + jinwei - 10);
                jinwei = 1;
            } else {
                sum.next = new ListNode(diff.val + jinwei);
                jinwei = 0;
            }
            sum = sum.next;
            diff = diff.next;
        }
        if (jinwei != 0) sum.next = new ListNode(jinwei);
        return res.next;
    }

    public ListNode mergeTwoListsRecur(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1);
        ListNode res = pre;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                res.next = l1;
                l1 = l1.next;
            } else {
                res.next = l2;
                l2 = l2.next;
            }
            res = res.next;
        }
        res.next = l1 == null ? l2 : l1;
        return pre.next;
    }

    /**
     * 142 环形链表
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
//        1.使用set保存所有listnode
//        if (head == null) return head;
//        Set<ListNode> set = new HashSet<>();
//        while (head != null) {
//            if (set.contains(head)) {
//                return head;
//            }
//            set.add(head);
//            head = head.next;
//        }
//        return null;

        if (head == null || head.next == null) return null;
        ListNode low = head, fast = head;
        while (fast != null) {
            low = low.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            //第一次相遇时
            if (fast == low) {
                ListNode pre = head;
                while (pre != low) {
                    low = low.next;
                    pre = pre.next;
                }
                return pre;
            }
        }
        return null;
    }

    /**
     * @param filePath  文件路径（不包括格式后缀）
     * @param fileCount 分割文件数量
     * @throws IOException
     */

    public static void splitFile(String filePath, int fileCount, String outputPath) throws IOException {

        try {
            FileInputStream fis = new FileInputStream(filePath);
            FileChannel inputChannel = fis.getChannel();
            final long fileSize = inputChannel.size();
            long average = fileSize / fileCount;//平均值
            long bufferSize = 200; //缓存块大小，自行调整
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.parseInt(bufferSize + "")); // 申请一个缓存区
            long startPosition = 0; //子文件开始位置
            long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
            for (int i = 0; i < fileCount; i++) {
                if (i + 1 != fileCount) {
                    int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                    readW:
                    while (read != -1) {
                        byteBuffer.flip();//切换读模式
                        byte[] array = byteBuffer.array();
                        for (int j = 0; j < array.length; j++) {
                            byte b = array[j];
                            if (b == 10 || b == 13) { //判断\n\r
                                endPosition += j;
                                break readW;
                            }
                        }
                        endPosition += bufferSize;
                        byteBuffer.clear(); //重置缓存块指针
                        read = inputChannel.read(byteBuffer, endPosition);
                    }
                } else {
                    endPosition = fileSize; //最后一个文件直接指向文件末尾
                }

                int m = i + 1;
                String subFile = filePath + m;
                while (new File(filePath + m).exists()) {
                    m++;
                }
                FileOutputStream fos = new FileOutputStream(filePath + m);
                FileChannel outputChannel = fos.getChannel();
                inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
                outputChannel.close();
                fos.close();
                startPosition = endPosition + 1;
                endPosition += average;
                distinctIp(subFile, outputPath);
            }
            inputChannel.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void distinctIp(String inputPath, String outputPath) {
        BufferedReader reader, reader_output;
        List<String> list = new ArrayList<>();
        // read
        try {
            reader = new BufferedReader(new FileReader(new File(inputPath)));
            reader_output = new BufferedReader(new FileReader(new File(inputPath)));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
            }
            reader.close();
            while ((tempString = reader_output.readLine()) != null) {
                list.add(tempString);
            }
            reader_output.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // write
        try {
            FileWriter fw = new FileWriter(outputPath, true);
            PrintWriter out = new PrintWriter(fw);
            list.stream().distinct().forEach(s -> {
                out.write(s);
                out.println();
            });
            fw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        splitFile("/Users/yaquan.zhou/a.txt", 2, "/Users/yaquan.zhou/b.txt");
//        splitBigFile("/Users/yaquan.zhou/a.txt",4);
//        distinctIp("/Users/yaquan.zhou/a.txt", "/Users/yaquan.zhou/b.txt");

//        ListNode t1 = new ListNode(9);
//        t1.next = new ListNode(9);
//
//        ListNode t2 = new ListNode(9);
//        t2.next = new ListNode(9);
//        t2.next.next = new ListNode(9);
//        ListNode res = addTwoNumbers(t1, t2);
    }
}
