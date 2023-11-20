package com.clt.expenses.group;

import com.clt.domain.view.GroupAggregate;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class TestGroupInformation {

    private GroupAggregate newGroup;
    public void set(GroupAggregate newGroup){
        this.newGroup = newGroup;
    }

    public GroupAggregate get(){
        return this.newGroup;
    }
}
