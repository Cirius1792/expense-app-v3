package com.clt.usecase;

import com.clt.domain.expense.*;
import com.clt.domain.group.GroupNotFound;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.PersonStore;
import com.clt.event.Notifier;
import com.clt.view.ExpenseAggregate;
import com.clt.view.ExpenseAggregateFactory;
import reactor.core.publisher.Mono;

public class AddExpenseUseCase {
  private final PersonStore personStore;
  private final GroupStore groupStore;
  private final ExpenseFactory expenseFactory;
  private final ExpenseStore expenseStore;
  private final Notifier<ExpenseRecord> newExpenseNotifier;

  public AddExpenseUseCase(
      PersonStore personStore,
      GroupStore groupStore,
      ExpenseFactory expenseFactory,
      ExpenseStore expenseStore) {
    this.personStore = personStore;
    this.groupStore = groupStore;
    this.expenseFactory = expenseFactory;
    this.expenseStore = expenseStore;
    this.newExpenseNotifier = new NewExpenseNotifierNop();
  }

  public AddExpenseUseCase(
      PersonStore personStore,
      GroupStore groupStore,
      ExpenseFactory expenseFactory,
      ExpenseStore expenseStore,
      Notifier<ExpenseRecord> newExpenseNotifier) {
    this.personStore = personStore;
    this.groupStore = groupStore;
    this.expenseFactory = expenseFactory;
    this.expenseStore = expenseStore;
    this.newExpenseNotifier = newExpenseNotifier;
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
                    person,
                    expenseFactory.create(description, amount, person.id(), group.id()),
                    group))
        .doOnNext(er -> expenseStore.store(er.expense()))
        .doOnNext(newExpenseNotifier::notify)
        .map(er -> ExpenseAggregateFactory.fromDomain(er.expense(), er.expenseOwner()));
  }
}
