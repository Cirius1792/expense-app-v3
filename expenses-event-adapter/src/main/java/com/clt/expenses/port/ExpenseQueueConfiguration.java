package com.clt.expenses.port;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ExpenseSplitter;
import com.clt.expenses.port.in.ledger.NewExpenseListener;
import com.clt.usecase.SplitExpenseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseQueueConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseQueueConfiguration.class);

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

    @Bean
    SplitExpenseUseCase splitExpenseUseCase(ExpenseChargeStore expenseChargeStore) {
        return new SplitExpenseUseCase(new ExpenseSplitter(new UUIDIdFactory()), expenseChargeStore);
    }

    @Bean
    NewExpenseListener newExpenseListener(SplitExpenseUseCase splitExpenseUseCase) {
        return new NewExpenseListener(splitExpenseUseCase);
    }
}