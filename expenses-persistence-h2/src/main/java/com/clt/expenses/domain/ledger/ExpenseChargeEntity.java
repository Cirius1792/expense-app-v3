package com.clt.expenses.domain.ledger;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("expense_charge")
public class ExpenseChargeEntity implements Persistable<String> {
    @Id private String id;
    private String expense;
    private String groupId;
    private String dueAmount;
    private String creditor;
    private String debtor;

    @Override
    public String toString() {
        return "ExpenseChargeEntity{" +
                "id='" + id + '\'' +
                ", expense='" + expense + '\'' +
                ", groupId='" + groupId + '\'' +
                ", dueAmount='" + dueAmount + '\'' +
                ", creditor='" + creditor + '\'' +
                ", debtor='" + debtor + '\'' +
                '}';
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }
}
