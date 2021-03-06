package com.dl.linked;

/**
 * @Author : lhk
 * @Description :
 * @Date : 2020/8/26 11:05
 * 给定两个有序链表的头指针head1和head2，打印两个链表的公共部分
 *
 * 本题难度很低，因为是有序链表，所以从两个链表的头开始进行如下判断：
 * ● 如果head1的值小于head2，则head1往下移动。
 * ● 如果head2的值小于head1，则head2往下移动。
 * ● 如果head1的值与head2的值相等，则打印这个值，然后head1与head2都往下移动。
 * ● head1或head2有任何一个移动到null，则整个过程停止。
 */
public class Demo01 {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value=data;
        }
    }
    public void printCommonPart(Node head1,Node head2){
        System.out.print("开始打印公共部分");
        while (head1!=null && head2!=null){
            if (head1.value<head2.value){
                head1=head1.next;
            }else if (head1.value>head2.value){
                head2=head2.next;
            }else {
                System.out.print(head1.value+" ");
                head1=head1.next;
                head2=head2.next;
            }
        }
        System.out.println();
    }

    //尾部插入
    public Node add(int value,Node head){
        Node newNode=new Node(value);
        if (head==null){
            head=newNode;
            return head;
        }
        Node last=head;
        while (last.next!=null){
            last=last.next;
        }
        last.next=newNode;
        return head;
    }

    public Node removeLastKthNode(Node head,int lastKth){
        if (head==null || lastKth<1){
            return head;
        }
        Node cur=head;
        while (cur!=null){
            lastKth--;
            cur=cur.next;
        }
        if (lastKth==0){
            head=head.next;
        }
        if (lastKth<0){
            cur=head;
            while (++lastKth!=0){
                cur=cur.next;
            }
            cur.next=cur.next.next;
        }

        return head;
    }
}
