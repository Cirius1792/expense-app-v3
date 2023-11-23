package com.clt.domain.expense;

import com.clt.domain.commons.Page;
import com.clt.domain.commons.Store;
import reactor.core.publisher.Flux;

public interface ExpenseStore extends Store<Expense, String> {
  Flux<Expense> retrieveByGroup(String groupId, Page page);
}
