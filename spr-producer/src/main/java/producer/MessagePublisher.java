package producer;

import com.reactivestax.avro.OrderEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class MessagePublisher {

//    @Value("${kafka.topic}")
//    private String topic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(OrderEvents orderEvent) {
        log.info("sending Order Id Event {}", orderEvent.getOrderId());
        kafkaTemplate.send("order1", orderEvent.getOrderId(), orderEvent);
    }
}
