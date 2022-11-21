package com.example.blog.domain.repositories;


import org.springframework.data.jpa.repository.Query;
import com.example.blog.domain.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends CrudRepository<Account, Integer> {
    List<Account> findAccountsByIsBannedTrue();

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountByLogin(String login);

    @Query(nativeQuery = true,
            value = "select a.id, a.login, a.email, a.registered, a.is_banned, " +
                    "a.is_deleted, r.name, r.id as role_id, count(p.id) as posts_number " +
                    "from accounts as a left join posts as p on a.id = p.account_id " +
                    "and p.status_id = :statusId " +
                    "join roles as r on a.role_id = r.id " +
                    "group by a.id"
    )
    List<Object[]> getAccountsAndCountPostsForEachByStatusId(@Param("statusId") int statusId);

    @Query(nativeQuery = true,
            value = "select a.id, a.login, count(p.id) as posts_number " +
                    "from accounts as a left join posts as p on a.id = p.account_id " +
                    "and p.status_id = :statusId " +
                    "group by a.id"
    )
    List<Object[]> getLoginsAndCountPostsForEachByStatusId(@Param("statusId") int statusId);

    @Query(nativeQuery = true,
            value = "select a.id, a.login, a.email, a.registered, count(p.id) as posts_number " +
                    "from accounts as a left join posts as p on a.id = p.account_id " +
                    "and p.status_id = :statusId " +
                    "where a.id = :accountId " +
                    "group by a.id"
    )
    List<Object[]> getAccountByIdAndCountPostsByStatusId(
            @Param("accountId") int accountId,
            @Param("statusId") int statusId
    );
}
