package com.clt.expenses.group;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class TestUsersInformation {

    private Map<String, String> users = new HashMap<>();
    public void put(String userName, String userId){
        this.users.put(userName, userId);
    }
    public String get(String userName){
        return this.users.get(userName);
    }
}
