package osotnikov.kafka.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerProperties {
    String serverConfig;
    String groupId;
    String topicName;
    String securityProtocol;
    String saslMechanism;
    String saslJaasConfig;
}
