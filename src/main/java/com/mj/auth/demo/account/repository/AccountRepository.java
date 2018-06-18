package com.mj.auth.demo.account.repository;

import com.mj.auth.demo.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByEmail(String email);

}
