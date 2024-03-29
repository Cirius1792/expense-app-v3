package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.expense.*;
import com.clt.domain.group.GroupNotFound;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.UserStore;
import com.clt.event.Observer;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.ExpenseAggregateFactory;
import reactor.core.publisher.Mono;

public class AddExpenseUseCase implements UseCase {
  private final UserStore personStore;
  private final GroupStore groupStore;
  private final ExpenseFactory expenseFactory;
  private final ExpenseStore expenseStore;
  private final Observer<ExpenseRecord> newExpenseNotifier;

  public AddExpenseUseCase(
      UserStore personStore,
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
      UserStore personStore,
      GroupStore groupStore,
      ExpenseFactory expenseFactory,
      ExpenseStore expenseStore,
      Observer<ExpenseRecord> newExpenseNotifier) {
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
                    expenseFactory.create(description, amount, person.getId(), group.getId()),
                    group))
        .flatMap(er -> expenseStore.store(er.expense()).thenReturn(er))
        .flatMap(er -> newExpenseNotifier.notify(er).thenReturn(er))
        .map(er -> ExpenseAggregateFactory.fromDomain(er.expense(), er.expenseOwner()));
  }
}
