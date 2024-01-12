package com.clt.expenses.security.store;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("application_user")
public class ApplicationUserEntity implements Persistable<String> {
    @Id private String id;
    private String password;

    public ApplicationUserEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public ApplicationUserEntity() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ApplicationUserEntity{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUserEntity that = (ApplicationUserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
