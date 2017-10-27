package com.example;

import java.util.ArrayDeque;

/**
 * Created by linyuan on 2017/10/26 0026.
 */

public class ArrayDequeQueue {

    public static void main(String[] args) {

        ArrayDeque queue = new ArrayDeque();
        queue.offer("疯狂的Java讲义");
        queue.offer("轻量级JavaEE企业应用实战");
        queue.offer("疯狂的Android讲义");

        System.out.println(queue);

        System.out.println(queue.poll());

        System.out.println(queue);
    }
}
