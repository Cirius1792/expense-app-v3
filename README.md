# expense-app-v3
# Use Cases
## Add New Expense Use Case
```mermaid
sequenceDiagram
    User ->>+ Expenses: newExpense
    Expenses -->> Broker: NEW_EXPENSE
    Expenses ->>- User: Expense
    Broker -->> Ledger: NEW_EXPENSE
    activate Ledger
    Ledger ->> Ledger: splitExpense
    loop for every person in the group
     Ledger -->> Broker: EXPENSE_CHARGE
    end
    deactivate Ledger
```
## Update Balance Use Case 
```mermaid
sequenceDiagram
    Broker -->> Balance: EXPENSE_CHARGE
    activate Balance
    Balance ->> Balance: store(ExpenseCharge)
    Balance ->>+ BalanceStore: store(ExpenseCharge)
    BalanceStore ->>- Balance: ExpenseCharge
    deactivate Balance
```
## Retrieve Balance Use Case
```mermaid
sequenceDiagram
    actor User
    User ->>+ Balance: retrieve(userId)
    Balance ->>+ BalanceStore: retrieveExpenseChargeForUser(userId)
    BalanceStore ->>- Balance: Collection<ExpenseCharge>
    Balance ->> Balance: reduce(Collection<ExpenseCharge>)
    Balance ->>- User: Balance
```