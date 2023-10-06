package com.clt.domain.commons;

import java.util.UUID;

public class UUIDIdFactory implements IdFactory{
    @Override
    public String newId(){
        return UUID.randomUUID().toString();
    }
}
