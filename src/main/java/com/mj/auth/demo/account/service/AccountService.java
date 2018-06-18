package com.mj.auth.demo.account.service;

import com.mj.auth.demo.account.Account;
import com.mj.auth.demo.account.exception.AccountCreationException;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByEmail(String email);

    Account registerNewOAuthUser(String email, String name) throws AccountCreationException;
}
