package com.clt.domain.commons;

public interface UseCase {
    default String getName() {
        return this.getClass().getName();
    }
}
