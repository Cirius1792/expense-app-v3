package com.clt.expenses.port.in.ledger;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.event.GenericEvent;
import com.clt.event.Listener;
import com.clt.usecase.SplitExpenseUseCase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Profile({"local", "test"})
public class NewExpenseInAppListener implements Listener<ExpenseRecord> {

    private final SplitExpenseUseCase splitExpenseUseCase;

    public NewExpenseInAppListener(SplitExpenseUseCase splitExpenseUseCase) {
        this.splitExpenseUseCase = splitExpenseUseCase;
    }

    @Override
    public Mono<Void> receive(GenericEvent<ExpenseRecord> event) {
        splitExpenseUseCase.split(event.event())
                .subscribe();
        return Mono.empty();

    }
}
