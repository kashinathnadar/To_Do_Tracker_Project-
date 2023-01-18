package com.Group8.ToDo.service.rabbitMQ.rabbitCofig;

import com.Group8.ToDo.service.rabbitMQ.TaskDetails;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRabbitMq {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public UserRabbitMq(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public static void sendMessageToRabbitMQ(TaskDetails taskDTO) {
    }
}
