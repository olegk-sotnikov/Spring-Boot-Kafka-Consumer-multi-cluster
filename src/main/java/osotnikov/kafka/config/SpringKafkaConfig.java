package osotnikov.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class SpringKafkaConfig {

	@Bean
	public ConsumerFactory<String, String> oagConsumerFactory(OagConsumerProperties oagConsumerProperties) {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, oagConsumerProperties.serverConfig);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, oagConsumerProperties.groupId);
		configMap.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, oagConsumerProperties.securityProtocol);
		configMap.put(SaslConfigs.SASL_MECHANISM, oagConsumerProperties.saslMechanism);
		configMap.put(SaslConfigs.SASL_JAAS_CONFIG, oagConsumerProperties.saslJaasConfig);
		return new DefaultKafkaConsumerFactory<>(configMap);
	}

	@Bean
	public ConsumerFactory<String, String> localConsumerFactory(LocalConsumerProperties localConsumerProperties) {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, localConsumerProperties.serverConfig);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, localConsumerProperties.groupId);
		return new DefaultKafkaConsumerFactory<>(configMap);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> oagKafkaListenerContainerFactory(ConsumerFactory<String, String> oagConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(oagConsumerFactory);
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> localKafkaListenerContainerFactory(ConsumerFactory<String, String> localConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(localConsumerFactory);
		return factory;
	}
}
