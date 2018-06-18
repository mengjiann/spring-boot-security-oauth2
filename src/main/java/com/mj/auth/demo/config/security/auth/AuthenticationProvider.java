package com.mj.auth.demo.config.security.auth;

import com.mj.auth.demo.account.Account;
import com.mj.auth.demo.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationProvider  implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("Authenticating for username: {} and password: {}", email, password);

        if(email == null || email.isEmpty()) {
            throw new BadCredentialsException("Unable to authentication user without email.");
        }
        if(password == null || password.isEmpty()){
            throw new BadCredentialsException("Unable to authentication user without password.");
        }

        Account account = accountService.findByEmail(email)
                .orElseThrow(()->new BadCredentialsException("Unable to find account with email:"+email));

        boolean isValidPassword = passwordEncoder.matches(password,account.getPassword());
        if(!isValidPassword){
            throw new BadCredentialsException("Wrong password for user: "+email);
        }

        CustomPrincipal customPrincipal = new CustomPrincipal(account);
        Authentication auth = new UsernamePasswordAuthenticationToken(customPrincipal,"",customPrincipal.getAuthorities());

        log.info("Complete authentication for user: {}", email);

        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
