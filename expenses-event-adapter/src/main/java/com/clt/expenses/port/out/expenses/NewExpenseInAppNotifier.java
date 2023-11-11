package com.clt.expenses.port.out.expenses;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.event.Listener;
import com.clt.event.Notifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Profile({"local", "test"})
public class NewExpenseInAppNotifier implements Notifier<ExpenseRecord> {

    private final Listener<ExpenseRecord> expenseRecordListener;

    public NewExpenseInAppNotifier(Listener<ExpenseRecord> expenseRecordListener) {
        this.expenseRecordListener = expenseRecordListener;
    }

    @Override
    public Mono<GenericEvent<ExpenseRecord>> notify(ExpenseRecord expenseRecord) {
        GenericEvent<ExpenseRecord> event =
                new GenericEvent<>(UUID.randomUUID().toString(), expenseRecord);
        return expenseRecordListener.receive(event)
                .thenReturn(event);
    }
}
