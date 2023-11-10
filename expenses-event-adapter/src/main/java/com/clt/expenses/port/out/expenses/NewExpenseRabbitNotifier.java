package com.clt.expenses.port.out.expenses;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.event.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
public class NewExpenseRabbitNotifier implements Notifier<ExpenseRecord> {
  private static final Logger logger = LoggerFactory.getLogger(NewExpenseRabbitNotifier.class);
  private final RabbitTemplate template;

  private final Queue newExpenseQueue;

  public NewExpenseRabbitNotifier(RabbitTemplate template, Queue newExpenseQueue) {
    logger.info("Initializing Expense Notifier");
    this.template = template;
    this.newExpenseQueue = newExpenseQueue;
  }

  @Override
  public Mono<GenericEvent<ExpenseRecord>> notify(ExpenseRecord expenseRecord) {
    return Mono.fromCallable(() -> sendEvent(expenseRecord))
        .subscribeOn(Schedulers.boundedElastic());
  }

  private GenericEvent<ExpenseRecord> sendEvent(ExpenseRecord expenseRecord) {
    logger.info("Sending: {}", expenseRecord);
    GenericEvent<ExpenseRecord> event =
        new GenericEvent<>(UUID.randomUUID().toString(), expenseRecord);
    this.template.convertAndSend(this.newExpenseQueue.getName(), event);
    return event;
  }
}
