# expenses-app
This is a sample project developed mainly to allow me to make practice with hexagonal architecture, cucumber tests and reactive programming. 

The implemented application is inspired by [SplitWisea](https://www.splitwise.com/), which I use and love, so for my practice project I chose to implement a subset of splitwise functionalities. 
The main feature implemented are: 
- Create groups to share expenses
- Create and split expenses 
- Retrieve a user balance for the whole group or regarding a single user 
- Pay the user debts 

# Table Of Contents
- [How to run the application](#how-to-run-the-application)
- [How to test](#how-to-test)
- [Use Cases](#use-cases)
    * [Add New Expense Use Case](#add-new-expense-use-case)
    * [Update Balance Use Case ](#update-balance-use-case)
    * [Retrieve Balance Use Case](#retrieve-balance-use-case)
    * [Pay ](#pay)
# How to run the application
The application is developed by using Spring Boot, therefore you can run it as any other spring boot app, just take care to choose the appropriate profile from the ones listed below.
The container is the module expenses-app, that will generate the executable jar to spin up the application.
## Spring Profiles
### Local
An H2 in-memory database is used and no message broker is required
### Dev
An H2 in-memory database is used and rabbitmq is used as message broker
# How to test
the application has a suite of unit and integration tests. The integration tests are under expenses-app and they are independent of the actual implementation of the port modules as they do not rely on any kind of hard wired mock. 

A set of end-to-end test is implemented with the collection postman located under the folder "Postman Collection"
# Use Cases
The following sequence diagrams represent a high level view on the main functionalities of the application. 
## Add New Expense Use Case
```mermaid
sequenceDiagram
    User ->>+ Expenses: newExpense
    Expenses -->> Broker: NEW_EXPENSE
    Expenses ->>- User: Expense
    Broker -->> Ledger: NEW_EXPENSE
    activate Ledger
    Ledger ->> Ledger: splitExpense
    loop for every user in the group
        Ledger ->>+ ChargeStore: Charge
        ChargeStore ->>- Ledger: Charge
        Ledger -->> Broker: EXPENSE_CHARGE
    end
    deactivate Ledger
```
## Update Balance Use Case 
```mermaid
sequenceDiagram
    Broker -->> Balance: EXPENSE_CHARGE
    activate Balance
    Balance ->> Balance: store(Charge)
    Balance ->>+ BalanceStore: store(Charge)
    BalanceStore ->>- Balance: Charge
    deactivate Balance
```
## Retrieve Balance Use Case
```mermaid
sequenceDiagram
    actor User
    User ->>+ Ledger: retrieve(userId)
    Ledger ->>+ ChargeStore: retrieveChargeForUser(userId)
    ChargeStore ->>- Ledger: Collection<Charge>
    Ledger ->> Ledger: reduce(Collection<Charge>)
    Ledger ->>- User: Balance
```

## Pay 
```mermaid
sequenceDiagram
    actor User
    User ->>+ Expenses: pay(group, payer, paid, amount)
    Expenses ->>+ BalanceService: retrieveBalance
    BalanceService ->>- Expenses: balance
    alt is paid amount valid
        Expenses ->>+ ChargeStore: store(charge)
        ChargeStore ->>- Expenses: charge
        Expenses ->> User: Charge
    else
        Expenses ->> User: Invalid amount error
    end
    deactivate Expenses
```
