package com.clt.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ExpenseApp {
  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    SpringApplication.run(ExpenseApp.class, args);
  }
}
