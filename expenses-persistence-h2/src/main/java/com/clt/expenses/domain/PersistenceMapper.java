package com.clt.expenses.domain;

public interface PersistenceMapper <ENTITY, DOMAIN>{
    ENTITY toEntity(DOMAIN domain);
    DOMAIN toDomain(ENTITY entity);
}
