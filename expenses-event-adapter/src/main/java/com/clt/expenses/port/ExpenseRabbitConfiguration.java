package com.clt.expenses.port;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ExpenseSplitter;
import com.clt.usecase.SplitExpenseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("rabbit")
public class ExpenseRabbitConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter(objectMapper);
        jsonConverter.setAlwaysConvertToInferredType(true);
        return jsonConverter;
    }


    @Bean("newExpenseQueue")
    Queue newExpenseQueue(
            @Value("${expenses.topic.new-expense.name:new-expenses}") String newExpenseQueueName) {
        return new Queue(newExpenseQueueName);
    }


}
