package com.leetcode.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author wangbaitao
 * @version 1.0.0
 * <h></h>
 * @Date 2021/3/9
 **/
@Data
@Builder
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
