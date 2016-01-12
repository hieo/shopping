/**
 * @author effine
 * @Date 2016年1月5日  下午6:26:55
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.lab.rabbitmq;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 队列消费者
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {
	
	public QueueConsumer(String queueName) throws IOException {
		super(queueName);
	}

	public void run() {
		try {
			// start consuming messages. Auto acknowledge messages.
			channel.basicConsume(queueName, true, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer " + consumerTag + " registered");
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env, BasicProperties props, byte[] body) throws IOException {
		@SuppressWarnings("unchecked")
		Map<String,Integer> map = (Map<String,Integer>) SerializationUtils.deserialize(body);
		System.out.println("Message Number " + map.get("message number") + " received.");

	}

	public void handleCancel(String consumerTag) {
	}

	public void handleCancelOk(String consumerTag) {
	}

	public void handleRecoverOk(String consumerTag) {
	}

	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
	}
}