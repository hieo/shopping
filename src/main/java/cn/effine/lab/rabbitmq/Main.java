/**
 * @author effine
 * @Date 2016年1月5日  下午6:28:05
 * @email iballader#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.lab.rabbitmq;

import java.util.HashMap;

public class Main {
    public Main() throws Exception {

        QueueConsumer consumer = new QueueConsumer("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        Producer producer = new Producer("queue");
        int maxNum = 100000;
        for (int i = 0; i < maxNum; i++) {
            HashMap<String, Integer> message = new HashMap<String, Integer>();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number " + i + " sent.");
        }
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
