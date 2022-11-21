package com.example.blog.domain.services;

import com.example.blog.domain.repositories.IAccountRepository;
import com.example.blog.server.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountDetailsService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public UserDetails loadAccountById(int accountId)  throws UsernameNotFoundException {
        var account = accountRepository.findById(accountId) ;

        if (account.isEmpty())
            throw new UsernameNotFoundException("Аккаунт не найден");

        return new AccountDetails(account.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findAccountByLogin(username) ;

        if (account.isEmpty())
            throw new UsernameNotFoundException("Аккаунт не найден");

        return new AccountDetails(account.get());
    }
}
