package com.clt.expenses.user;

import com.clt.domain.group.User;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class TestUser {
    private String username;
    private String userId;

    public void set(User user){
        this.userId = user.getId();
        this.username = user.getUsername();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
