package com.clt.expenses;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseQueueConfiguration {

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
    Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
    return jsonConverter;
  }


  @Bean("newExpenseQueue")
  Queue newExpenseQueue(
      @Value("${expenses.topic.new-expense.name:new-expenses}") String newExpenseQueueName) {
    return new Queue(newExpenseQueueName);
  }
}
