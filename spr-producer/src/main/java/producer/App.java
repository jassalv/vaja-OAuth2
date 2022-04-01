package com.reactivestax;

import com.reactivestax.avro.OrderEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import producer.MessagePublisher;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private MessagePublisher messagePublisher;

    @Override
    public void run(String... args)  {
        for (long nEvents = 0; nEvents < 100; nEvents++) {
            OrderEvents order = new OrderEvents();
            order.setOrderId(nEvents);
            order.setOrderDate(System.currentTimeMillis());
            order.setOrderAmt(nEvents*1.13*12);
            messagePublisher.send(order);
        }
    }
}
