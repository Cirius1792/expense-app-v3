package com.clt.expenses;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.event.Notifier;
import java.util.UUID;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class NewExpenseNotifierImpl implements Notifier<ExpenseRecord> {
  private final RabbitTemplate template;

  private final Queue newExpenseQueue;

  public NewExpenseNotifierImpl(RabbitTemplate template, Queue newExpenseQueue) {
    this.template = template;
    this.newExpenseQueue = newExpenseQueue;
  }

  @Override
  public Mono<GenericEvent<ExpenseRecord>> notify(ExpenseRecord expenseRecord) {
    return Mono.fromCallable(() -> sendEvent(expenseRecord))
        .subscribeOn(Schedulers.boundedElastic());
  }

  private GenericEvent<ExpenseRecord> sendEvent(ExpenseRecord expenseRecord) {
    GenericEvent<ExpenseRecord> event =
        new GenericEvent<>(UUID.randomUUID().toString(), expenseRecord);
    this.template.convertAndSend(this.newExpenseQueue.getName(), event);
    return event;
  }
}
