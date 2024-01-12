package com.clt.expenses.security;

public record ApplicationUser (String id, String password, String role){}