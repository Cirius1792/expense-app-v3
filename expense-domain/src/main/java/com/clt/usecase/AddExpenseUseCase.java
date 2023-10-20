package com.clt.usecase;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.expense.Money;
import com.clt.domain.group.*;
import com.clt.view.ExpenseAggregate;
import com.clt.view.ExpenseAggregateFactory;
import reactor.core.publisher.Mono;

record ExpenseRecord(Person expenseOwner, Expense expense) {}

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

  public Mono<ExpenseAggregate> create(
      String description, Money amount, String ownerId, String groupId) {
    var personProducer =
        personStore.retrieve(ownerId).switchIfEmpty(Mono.error(PersonNotFound::new));
    var groupProducer = groupStore.retrieve(groupId).switchIfEmpty(Mono.error(GroupNotFound::new));
    return Mono.zip(
            personProducer,
            groupProducer,
            (person, group) ->
                new ExpenseRecord(
                    person, expenseFactory.create(description, amount, person.id(), group.id())))
        .doOnNext(er -> expenseStore.store(er.expense()))
        .map(er -> ExpenseAggregateFactory.fromDomain(er.expense(), er.expenseOwner()));
  }
}
