package osotnikov.kafka.consumer;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OagKafkaConsumer implements ConsumerSeekAware {

	private static final Logger logger = LoggerFactory.getLogger(OagKafkaConsumer.class);

	private static AtomicInteger countdown = new AtomicInteger(3);

	// The way to seek to beginning is by invoking ConsumerSeekCallback object, but each thread must have its own such object.
	private final ThreadLocal<ConsumerSeekCallback> callbackForThreadStore = new ThreadLocal<>();
	// Each consumer will have its own assignment of partitions and its own callback on its own thread. Therefore, we
	// have to associate each callback with its own partition assignment. Now we can use the callback at any time we like.
	private final Map<TopicPartition, ConsumerSeekCallback> callbacks = new ConcurrentHashMap<>();

	@Autowired
	KafkaListenerEndpointRegistry registry;

	@KafkaListener(id = "oag-listener", groupId = "consumergroup-1", topics = "eh8411477960", containerFactory = "oagKafkaListenerContainerFactory")
	public void receivedMessage(String message) {
		if(countdown.getAndDecrement() > 0)
			logger.info("\nMessage received using OAG Kafka listener:\n" + message + "\n");
		else
			registry.getListenerContainer("oag-listener").pause();
	}

	@Override
	public void registerSeekCallback(ConsumerSeekCallback callback) {
		this.callbackForThreadStore.set(callback);
	}

	@Override
	public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
		assignments.keySet().forEach((TopicPartition tp) -> this.callbacks.put(tp, this.callbackForThreadStore.get()));
		seekToStart();
	}

	@Override
	public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
	}

	/**
	 * positive to seek from beginning
	 * negative to seek from end
	 * */
	public void seekRelative(int n) {
		this.callbacks.forEach(( TopicPartition tp, ConsumerSeekCallback callback) -> {
			callback.seekRelative(tp.topic(), tp.partition(), n, false);
		});
	}

	public void seekToStart() {
		this.callbacks.forEach((tp, callback) -> callback.seekToBeginning(tp.topic(), tp.partition()));
	}
}
