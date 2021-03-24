import com.leetcode.entity.ListNode;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangbaitao
 * @version 1.0.0
 * <h>LeetCode 测试类  1~20题 (免费部分)</h>
 * <p>
 * leetcode官方网址：https://leetcode-cn.com
 * </p>
 * @Date 2021/3/9
 **/
public class LeetCodeTest {
    @Test
    public void testTwoNum() {
        int[] nums = {1, 2, 3, 4, 5};
        int[] result = twoSum(nums, 7);
        System.out.println("debug");
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那两个整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * 你可以按任意顺序返回答案。
     * 解题思路：
     * 标签：哈希映射
     * 这道题本身如果通过暴力遍历的话也是很容易解决的，时间复杂度在 O(n²)
     * 由于哈希查找的时间复杂度为 O(1)，所以可以利用哈希容器 map 降低时间复杂度
     * 遍历数组 nums，i 为当前下标，每个值都判断map中是否存在 target-nums[i] 的 key 值
     * 如果存在则找到了两个值，如果不存在则将当前的 (nums[i],i) 存入 map 中，继续遍历直到找到为止
     * 如果最终都没有结果则抛出异常
     */
    public int[] twoSum(int[] nums, int target) {
        //创建一个空map
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //匹配直接返回
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            //不匹配进行map的填充，value = index，这里不需要关心key的重复问题
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * addTwoNumber 测试案例入口
     */
    @Test
    public void testAddTwoNumbers() {
        ListNode preNodeOne = new ListNode(0);
        ListNode preNodeTwo = new ListNode(0);
        ListNode currentNodeOne = preNodeOne;
        ListNode currentNodeTwo = preNodeTwo;
        for (int i = 0; i < 3; i++) {
            currentNodeOne.next = new ListNode(RandomUtils.nextInt(0, 9));
            currentNodeTwo.next = new ListNode(RandomUtils.nextInt(0, 9));
            currentNodeOne = currentNodeOne.next;
            currentNodeTwo = currentNodeTwo.next;
        }
        ListNode resultNode = addTwoNumbers(preNodeOne.next, preNodeTwo.next);
        //输出入参和计算结果
        printNodeValue(preNodeOne.next);
        printNodeValue(preNodeTwo.next);
        printNodeValue(resultNode);
        System.out.println("debug");
    }

    /**
     * 遍历输出链表数据
     */
    private void printNodeValue(ListNode listNode) {
        StringBuilder stringBuilder = new StringBuilder();
        while (listNode != null) {
            stringBuilder.append(listNode.val);
            if (listNode.next != null) {
                stringBuilder.append(",");
            }
            listNode = listNode.next;
        }
        //由于链表是从个位数开始增加节点的，所有这里要反向输出
        System.out.println(stringBuilder.reverse().append("]").insert(0, "[").toString());
    }


    /**
     * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
     * 题目地址：https://leetcode-cn.com/problems/add-two-numbers/
     * 示例1：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * 示例2：
     * 输入：l1 = [0], l2 = [0]
     * 输出：[0]
     * 示例3：
     * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * 输出：[8,9,9,9,0,0,0,1]
     * <pre>
     *     这是一道链表问题，在链表计算中，我们通常需要对链表进行加工(重新初始化):
     *     a)保持计算的链表的长度一致，例如：983+23 = 983+023
     *     b)链表的计算需要考虑上一位的进位问题，如果链表全部遍历完成，进位值为1，则新链表需要在最前方节点添加新节点1
     *     总结：这是一个需要进行预设指针的算法题，该指针指向真正的头节点head。使用预设指针的目的在于链表初始化时无可用节点值，
     *     而链表构造过程需要指针移动，进而导致头指针丢失，无法返回结果。
     * </pre>
     * <pre>
     *   计算步骤：
     *   1、创建预设指针pre作为head
     *   2、创建cur作为初始化的操作节点
     *   3、进行while循环，从第一个节点开始获取数据，对于两个节点的val相加，整除10得到是否进位carry(循环体外变量)，求余10得到当前节点的val
     *   将求余结果通过构造器创建当前节点，将cur替换成当前节点的next，循环操作到while结束
     *   4、循环结束，判断carry是否等于1(进位只能等于1)，如果是，则再进行next节点的创建并赋值
     *   5、将pre.next接口返回
     * </pre>
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //创建预设指针
        ListNode pre = new ListNode(0);
        //设置当前节点，用于计算
        ListNode cur = pre;
        int carry = 0;
        //进行循环遍历
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            //这里有个小技巧，逻辑计算的效率更好
            //两个各位数相加必定小于20，这里目的是为了获取下一个节点的进位1,
            carry = sum / 10;
//            carry = sum > 10 ? 1 : 0;
            sum = sum % 10;
            //将求余结果通过构造器赋值给当前节点
            cur.next = new ListNode(sum);

            //将当前节点替换成下一节点
            cur = cur.next;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }
}
