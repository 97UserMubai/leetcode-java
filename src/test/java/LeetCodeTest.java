import com.leetcode.entity.ListNode;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangbaitao
 * @version 1.0.0
 * <h>LeetCode 测试类  1~5题 (免费部分)</h>
 * <p>
 * leetcode官方网址：https://leetcode-cn.com
 * </p>
 * @Date 2021/3/9
 **/
public class LeetCodeTest {
    /**
     * twoSum 测试案例入口
     */
    @Test
    public void testTwoNum() {
        int[] nums = {1, 2, 3, 4, 5};
        int[] result = twoSum(nums, 7);
        System.out.println("debug");
    }

    /**
     * 1.twoSum
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
    public void printNodeValue(ListNode listNode) {
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
     * 2.addTwoNumbers
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

    @Test
    public void testLengthOfLongestSubstring() {
        System.out.println(lengthOfLongestSubstring("abcabcbb", "暴力解法"));
        System.out.println(lengthOfLongestSubstring("bbbbbb", "暴力解法"));
        System.out.println(lengthOfLongestSubstring("pwwkew", "暴力解法"));
        System.out.println(lengthOfLongestSubstring("", "暴力解法"));
        System.out.println(lengthOfLongestSubstring(null, "暴力解法"));
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring(""));
        System.out.println("debug");
    }

    /**
     * 3.lengthOfLongestSubstring,无重复字符的最长子串-暴力解法，复杂度最大为O(n²)
     * <pre>
     *     题目地址：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     *     给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     *     案例1：s = "abcabcbb"  输出 3
     *     案例2：s = "bbbbbb" 输出1
     *     案例3：s = "pwwkew" 输出3 ，因为无重复字符的最长子串是 "wke"，所以其长度为3。
     *     请注意，答案必须是子串的长度，"pwke" 是一个子序列，不是子串。
     *     案例4：s="" 输出0
     * </pre>
     */
    public int lengthOfLongestSubstring(String s, String type) {
        System.out.print(type + ":");
        if (s == null || s.length() == 0) {
            return 0;
        }
        //解析思路，将结果进行拆解，在s.length递减和当前长度进行双重循环，得到的结果生成集
        // 如果出现重复char，则直接将当前长度赋值给result
        int result = 0;
        int length = s.length();
        char[] sChars = s.toCharArray();
        ////从index = 0的位置开始，进行字符串拼接
        for (int i = 0; i < length; i++) {
            //得到第二层的遍历长度
            StringBuilder builder = new StringBuilder();
            int tempValue = 0;
            for (int j = i; j < length; j++) {
                if (builder.toString().contains(String.valueOf(sChars[j]))) {
                    //存在该字符串，直接返回结果
                    break;
                } else {
                    builder.append(sChars[j]);
                    tempValue++;
                }
            }
            if (tempValue > result) {
                result = tempValue;
            }
        }
        return result;
    }


    /**
     * 3.lengthOfLongestSubstring,无重复字符的最长子串-滑动窗口
     * <pre>
     *     题目地址：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     *     给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     *     案例1：s = "abcabcbb"  输出 3
     *     案例2：s = "bbbbbb" 输出1
     *     案例3：s = "pwwkew" 输出3 ，因为无重复字符的最长子串是 "wke"，所以其长度为3。
     *     请注意，答案必须是子串的长度，"pwke" 是一个子序列，不是子串。
     *     案例4：s="" 输出0
     * </pre>
     * <pre>
     *     定义一个 map 数据结构存储 (k, v)，其中 key 值为字符，value 值为字符位置 +1，加 1 表示从字符位置后一个才开始不重复
     *     我们定义不重复子串的开始位置为 start，结束位置为 end
     *     随着 end 不断遍历向后，会遇到与 [start, end] 区间内字符相同的情况，此时将字符作为 key 值，获取其 value 值，
     *     并更新 start，此时 [start, end] 区间内不存在重复字符
     *    无论是否更新 start，都会更新其 map 数据结构和结果 ans。
     *    时间复杂度：O(n)
     * </pre>
     * <pre>
     *     个人总结：
     *     在暴力解法得到的结果里面，双重循环的情况下，复杂度为O(n²)，当字符串很长的时候，会放大这个消耗
     *     再看会需求，我们需要找出字符串中最长的不重复子串，那么最低的复杂度也在O(n)
     *     从起始位到遍历的位置，需要两个参数start和end做减法得到ans，那么怎么在一次遍历中完成这个需求就是我们需要解决的问题
     *     首先ans = end - start + 1;
     *     其次：当出现重复的字符串的时候，我们需要将start挪到重复字符串的index
     *     固我们需要保存每个字符的max(index)+1
     *     这样每次出现重复的情况下，我们的start就会自动从重复字符的位置开始重新计数
     *     PS:这里不需要考虑start->end跳跃后中间的字符不进行匹配的问题，因为ans每次都得到最大的长度，
     *     之后的匹配如果出现这些字符，也会和ans比较
     *     PS2:需要注意的是，在比较contain和赋值GetIndex的过程中，我们需要一个中间变量来保存这些数据
     *     选用map的原因是，他的containKey的算法复杂度为O(1)
     *     所以很多时候，在数据算法中需要灵活应用Java数据类型中已经提供给我们的合适类型来辅助我们解决问题
     * </pre>
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            //判断已经包含这个字符，如果包含，则将当前字符对应的value赋值给start
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            //每次循环都判断一下end-start+1（当前窗口大小），并判断是否需要替换ans
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    /**
     * 4.findMedianSortedArrays , 寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * <pre>
     *     案例1：
     *     输入：nums1 = [1,3], nums2 = [2]
     *     输出：2.00000
     *     解释：合并数组 = [1,2,3] ，中位数 2
     *     案例2：
     *     输入：nums1 = [1,2], nums2 = [3,4]
     *     输出：2.50000
     *     解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     *     案例3：
     *     输入：nums1 = [2], nums2 = []
     *     输出：2.00000
     * </pre>
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //获取完整的数组
        //todo 这道题太难了，暂且搁置，争取用最后的log(m+n)复杂度的算法来进行优雅代码的分析
        return 0.00;
    }

    /**
     * 5.longestPalindrome  最长回文字符串， 给你一个字符串 s，找到 s 中最长的回文子串。
     * <pre>
     *     示例1：
     *     输入：s = "babad"
     *     输出："bab"
     *     解释："aba" 同样是符合题意的答案。
     *     示例2：
     *     输入：s = "cbbd"
     *     输出："bb"
     *     示例3：
     *     输入：s = "a"
     *     输出："a"
     *     示例4：
     *     输入：s = "ac"
     *     输出："a"
     * </pre>
     * <pre>
     *     什么是回文字符串，就是正着读和反着读是一样的，所以对于一个
     * </pre>
     */
    public String longestPalindrome(String s, String type) {
        System.out.print(type + ":");
        //暴力解法
        int len = s.length();
        if (len < 2) {
            return s;
        }
        return null;
    }
}
