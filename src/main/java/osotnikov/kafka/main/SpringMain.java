package osotnikov.kafka.main;

import osotnikov.kafka.config.LocalConsumerProperties;
import osotnikov.kafka.config.OagConsumerProperties;
import osotnikov.kafka.consumer.OagKafkaConsumer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

@SpringBootApplication
@ComponentScan(basePackages = "osotnikov.*")
@EnableConfigurationProperties({LocalConsumerProperties.class, OagConsumerProperties.class})
public class SpringMain {
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);
	}

	@Bean
	public ApplicationRunner runner(OagKafkaConsumer oagKafkaConsumer, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaListenerEndpointRegistry registry) {
		return args -> {
			//registry.getListenerContainers().forEach(MessageListenerContainer::pause);
			oagKafkaConsumer.seekToStart();
			//registry.getListenerContainers().forEach(MessageListenerContainer::resume);
//			while (true) {
//				registry.getListenerContainers().forEach(MessageListenerContainer::pause);
//				System.out.print( "Enter number of messages to read from the end of the OAG and local streams: " );
//				Scanner scanner = new Scanner(System.in);
//				try {
//					int msgN = scanner.nextInt();
//					System.out.print("Entered number of messages as : "+ msgN);
//					oagKafkaConsumer.seekRelative(-msgN);
//					registry.getListenerContainers().forEach(MessageListenerContainer::resume);
//				} catch(Exception e) {
//					System.err.print(e);
//				}

		//	}
		};
	}

}
