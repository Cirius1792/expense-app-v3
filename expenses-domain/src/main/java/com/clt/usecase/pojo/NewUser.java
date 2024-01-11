package com.clt.usecase.pojo;

import com.clt.domain.group.User;

public class NewUser implements User {

    private final String id;
    private final String password;

    public NewUser(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String getId() {
        return this.id;
    }
    public String getPassword() {
        return password;
    }
}
