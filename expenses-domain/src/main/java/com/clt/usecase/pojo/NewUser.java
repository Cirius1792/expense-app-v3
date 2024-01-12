package com.clt.usecase.pojo;

import com.clt.domain.group.User;

public class NewUser implements User {

    private final String id;

    public NewUser(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
