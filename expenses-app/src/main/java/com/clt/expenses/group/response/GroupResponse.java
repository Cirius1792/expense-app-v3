package com.clt.expenses.group.response;

import com.clt.expenses.user.response.UserResponseDto;

import java.util.List;

public class GroupResponse {
    String id;
    String name;
    UserResponseDto owner;
    List<UserResponseDto> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponseDto getOwner() {
        return owner;
    }

    public void setOwner(UserResponseDto owner) {
        this.owner = owner;
    }

    public List<UserResponseDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserResponseDto> members) {
        this.members = members;
    }
}
