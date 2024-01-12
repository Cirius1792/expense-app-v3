package com.clt.expenses.security.store;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends ReactiveCrudRepository<ApplicationUserEntity, String> {}
