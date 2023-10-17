package com.clt.expenses.domain.common;

public interface PersistenceMapper <ENTITY, DOMAIN>{
    ENTITY toEntity(DOMAIN domain);
    DOMAIN toDomain(ENTITY entity);
}
