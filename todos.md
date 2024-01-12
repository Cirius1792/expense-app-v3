- [ ] GroupManagerService.create: check per group name e owner not null
- [ ] CreateGroupUseCase.create: check per utenti inseriti a doppio fra i membri
- [x] Split Expense Use Case
- [ ] Expense Charge Store
- [ ] Approfondire csrf:
```

The error "An expected CSRF token cannot be found" is likely because your application is using CSRF protection and the POST request to the "/user" route is missing the CSRF token.
CSRF (Cross-Site Request Forgery) protection is a security measure that prevents malicious websites from performing actions on behalf of authenticated users. It is typically implemented using tokens that are included in requests to verify their authenticity.
When you disable authentication on the "/user" route, the CSRF protection may still be active for that route. To resolve this issue, you can try one of the following options:

```