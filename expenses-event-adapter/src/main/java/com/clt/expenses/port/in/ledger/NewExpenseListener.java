package com.clt.expenses.port.in.ledger;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.usecase.SplitExpenseUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class NewExpenseListener {
    private static final Logger log = LoggerFactory.getLogger(NewExpenseListener.class);
    private final SplitExpenseUseCase splitExpenseUseCase;

    public NewExpenseListener(SplitExpenseUseCase splitExpenseUseCase) {
        this.splitExpenseUseCase = splitExpenseUseCase;
    }

    @RabbitHandler
    @RabbitListener(queues = "${expenses.topic.new-expense.name:new-expenses}")
    public void receive(GenericEvent<ExpenseRecord> expenseRecord) {
        log.info("Received Event: [{}] - {}", expenseRecord.id(), expenseRecord.event());
        splitExpenseUseCase.split(expenseRecord.event())
                .subscribe();
    }

}
