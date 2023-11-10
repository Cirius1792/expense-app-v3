package com.clt.expenses.port.in.ledger;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.event.Listener;
import com.clt.usecase.SplitExpenseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Profile("rabbit")
public class NewExpenseRabbitListener implements Listener<ExpenseRecord> {
    private static final Logger log = LoggerFactory.getLogger(NewExpenseRabbitListener.class);
    private final SplitExpenseUseCase splitExpenseUseCase;

    public NewExpenseRabbitListener(SplitExpenseUseCase splitExpenseUseCase) {
        this.splitExpenseUseCase = splitExpenseUseCase;
    }

    @Override
    @RabbitHandler
    @RabbitListener(queues = "${expenses.topic.new-expense.name:new-expenses}")
    public Mono<Void> receive(GenericEvent<ExpenseRecord> expenseRecord) {
        log.info("Receiving: [{}] - {}", expenseRecord.id(), expenseRecord.event());
        splitExpenseUseCase.split(expenseRecord.event())
                .subscribe();
        return Mono.empty();
    }

}
