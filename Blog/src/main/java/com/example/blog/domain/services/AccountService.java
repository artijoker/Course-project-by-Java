package com.example.blog.domain.services;


import com.example.blog.domain.entities.Role;
import com.example.blog.domain.exceptions.*;
import com.example.blog.domain.repositories.IAccountRepository;
import com.example.blog.domain.repositories.IRoleRepository;
import com.example.blog.http.models.responses.AccountResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(IAccountRepository accountRepository,
                          IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AccountResponseModel> findAllAccounts() {
        var accounts = new ArrayList<AccountResponseModel>();
        accountRepository.findAll().forEach(account -> {
            var model = new AccountResponseModel();
            model.setId(account.getId());
            model.setLogin(account.getLogin());
            model.setEmail(account.getEmail());
            model.setRegistered(account.getRegistered());
            model.setIsBanned(account.getIsBanned());
            model.setIsDeleted(account.getIsDeleted());
            model.setRoleName(account.getRole().getName());
            model.setRoleId(account.getRole().getId());
            accounts.add(model);
        });
        return accounts;
    }

    public List<AccountResponseModel> findAllBlockedAccounts() {
        var accounts = new ArrayList<AccountResponseModel>();
        accountRepository.findAccountsByIsBannedTrue().forEach(account -> {
            var model = new AccountResponseModel();
            model.setId(account.getId());
            model.setLogin(account.getLogin());
            model.setEmail(account.getEmail());
            model.setRegistered(account.getRegistered());
            model.setIsBanned(account.getIsBanned());
            model.setIsDeleted(account.getIsDeleted());
            model.setRoleName(account.getRole().getName());
            model.setRoleId(account.getRole().getId());
            accounts.add(model);
        });
        return accounts;
    }

    public AccountResponseModel getAccountById(int accountId) throws NotFoundAccountException {

        var optional = accountRepository.findById(accountId);

        if (optional.isEmpty())
            throw new NotFoundAccountException();

        var account = optional.get();

        var model = new AccountResponseModel();
        model.setId(account.getId());
        model.setLogin(account.getLogin());
        model.setEmail(account.getEmail());
        model.setRegistered(account.getRegistered());
        model.setRoleId(account.getRole().getId());
        model.setRoleName(account.getRole().getName());
        return model;
    }

    public AccountResponseModel getAccountByIdAndCountPublishedPosts(int accountId) throws NotFoundAccountException {

        var list = accountRepository.getAccountByIdAndCountPostsByStatusId(accountId, 3);

        if (list.size() != 1)
            throw new NotFoundAccountException();


        var objects = list.get(0);

        var model = new AccountResponseModel();
        model.setId(accountId);
        model.setLogin((String) objects[1]);
        model.setEmail((String) objects[2]);
        model.setRegistered(((Timestamp) objects[3]).toLocalDateTime());
        model.setQuantityPosts((BigInteger) objects[4]);
        return model;
    }

    public List<AccountResponseModel> getAccountsAndCountPublishedPostsForEach() {
        var accountModels = new ArrayList<AccountResponseModel>();
        accountRepository.getAccountsAndCountPostsForEachByStatusId(3).forEach(objects -> {
            var model = new AccountResponseModel();
            model.setId((int) objects[0]);
            model.setLogin((String) objects[1]);
            model.setEmail((String) objects[2]);
            model.setRegistered(((Timestamp) objects[3]).toLocalDateTime());
            model.setIsBanned((boolean) objects[4]);
            model.setIsDeleted((boolean) objects[5]);
            model.setRoleName((String) objects[6]);
            model.setRoleId((int) objects[7]);
            model.setQuantityPosts((BigInteger) objects[8]);
            accountModels.add(model);
        });
        return accountModels;
    }

    public List<AccountResponseModel> getAccountsThatHavePublishedPosts() {
        var accountModels = new ArrayList<AccountResponseModel>();
        accountRepository.getLoginsAndCountPostsForEachByStatusId(3).forEach(objects -> {
            if (((BigInteger) objects[2]).longValue() > 0) {
                var model = new AccountResponseModel();
                model.setId((int) objects[0]);
                model.setLogin((String) objects[1]);
                model.setQuantityPosts((BigInteger) objects[2]);
                accountModels.add(model);
            }
        });
        return accountModels;
    }


    public void userUpdateAccount(int accountId, String email, String login, String newPassword)
            throws NotFoundAccountException,
            DuplicateEmailException,
            DuplicateLoginException,
            DefaultAdminException {
        updateAccount(accountId, email, login, newPassword,
                Optional.empty());
    }

    public void adminUpdateAccount(int accountId,
                                   String email,
                                   String login,
                                   String newPassword,
                                   int roleId)
            throws NotFoundAccountException,
            DuplicateEmailException,
            DuplicateLoginException,
            DefaultAdminException {
        updateAccount(accountId, email, login, newPassword,
                Optional.of(roleRepository.getRoleById(roleId)));
    }
    public void updateAccount(int accountId,
                                   String email,
                                   String login,
                                   String newPassword,
                                   Optional<Role> role)
            throws NotFoundAccountException, DuplicateEmailException,
            DuplicateLoginException, DefaultAdminException {

        boolean isUpdate = false;

        var accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty())
            throw new NotFoundAccountException();

        var account = accountOptional.get();

        if (!email.isBlank() && !Objects.equals(account.getEmail(), email)) {

            if (accountRepository.findAccountByEmail(email).isPresent())
                throw new DuplicateEmailException();

            account.setEmail(email);
            isUpdate = true;
        }

        if (!login.isBlank() && !Objects.equals(account.getLogin(), login)) {
            if (accountRepository.findAccountByLogin(login).isPresent())
                throw new DuplicateLoginException();

            account.setLogin(login);
            isUpdate = true;
        }

        if (newPassword != null && !newPassword.isBlank()) {
            account.setPasswordHash(passwordEncoder.encode(newPassword));
            isUpdate = true;
        }
        if (role.isPresent()){
            if (!Objects.equals(account.getRole().getId(), role.get().getId())) {
                if (accountId == 1)
                    throw new DefaultAdminException();

                account.setRole(role.get());
                isUpdate = true;
            }
        }


        if (isUpdate) {
            accountRepository.save(account);
        }
    }

    public void removeAccount(int accountId) throws DefaultAdminException, NotFoundAccountException {
        if (accountId == 1)
            throw new DefaultAdminException();

        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty())
            throw new NotFoundAccountException();

        var account = accountOptional.get();
        account.setIsDeleted(true);

        accountRepository.save(account);
    }

    public void banAccount(int accountId) throws DefaultAdminException, NotFoundAccountException {
        if (accountId == 1)
            throw new DefaultAdminException();

        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty())
            throw new NotFoundAccountException();

        var account = accountOptional.get();

        account.setIsBanned(true);

        accountRepository.save(account);
    }

    public void unlockAccount(int accountId) throws NotFoundAccountException{

        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty())
            throw new NotFoundAccountException();

        var account = accountOptional.get();
        account.setIsBanned(false);

        accountRepository.save(account);
    }

}
