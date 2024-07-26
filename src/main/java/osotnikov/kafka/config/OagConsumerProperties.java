package osotnikov.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "kafka.consumer.oag")
public class OagConsumerProperties extends ConsumerProperties {
}
