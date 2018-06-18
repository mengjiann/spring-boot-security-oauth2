package com.mj.auth.demo.account.service;

import com.mj.auth.demo.account.Account;
import com.mj.auth.demo.account.exception.AccountCreationException;
import com.mj.auth.demo.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<Account> findByEmail(String email) {
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    @Override
    public Account registerNewOAuthUser(String email, String name) throws AccountCreationException {
        try{
            Account account = new Account();

            account.setEmail(email);
            account.setUsername(name);
            account.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

            return accountRepository.save(account);
        }catch (Exception e){
            log.error("{}",e.getMessage(),e);
            throw new AccountCreationException("Unable to create account with email: "+email);
        }
    }
}
