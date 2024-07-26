package osotnikov.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.consumer.local")
public class LocalConsumerProperties extends ConsumerProperties {
}
