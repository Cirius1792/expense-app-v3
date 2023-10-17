package com.clt.usecase;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.expense.Money;
import com.clt.domain.group.GroupNotFound;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.PersonStore;
import reactor.core.publisher.Mono;

public class AddExpenseUseCase {
  private final PersonStore personStore;
  private final GroupStore groupStore;
  private final ExpenseFactory expenseFactory;
  private final ExpenseStore expenseStore;

  public AddExpenseUseCase(
      PersonStore personStore,
      GroupStore groupStore,
      ExpenseFactory expenseFactory,
      ExpenseStore expenseStore) {
    this.personStore = personStore;
    this.groupStore = groupStore;
    this.expenseFactory = expenseFactory;
    this.expenseStore = expenseStore;
  }

  public Mono<Expense> create(String description, Money amount, String ownerId, String groupId) {
    var personProducer =
        personStore.retrieve(ownerId).switchIfEmpty(Mono.error(PersonNotFound::new));
    var groupProducer = groupStore.retrieve(groupId).switchIfEmpty(Mono.error(GroupNotFound::new));
    return Mono.zip(
            personProducer,
            groupProducer,
            (person, group) -> expenseFactory.create(description, amount, person.id(), group.id()))
        .doOnNext(expenseStore::store);
  }
}
