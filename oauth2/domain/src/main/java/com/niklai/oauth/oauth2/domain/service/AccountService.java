package com.niklai.oauth.oauth2.domain.service;

import com.niklai.oauth.oauth2.domain.mapper.EntityMapper;
import com.niklai.oauth.oauth2.domain.entity.Account;
import com.niklai.oauth.oauth2.infrastructure.entity.AccountInfo;
import com.niklai.oauth.oauth2.infrastructure.repository.AccountInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class AccountService {

    private AccountInfoRepository accountInfoRepository;

    public Mono<Account> getById(long id) {
        return accountInfoRepository.findOne(Example.of(new AccountInfo().setId(id)))
                .map(accountInfo ->
                        accountInfo == null ? null : EntityMapper.INSTANCE.toAccount(accountInfo));
    }

    public Mono<Account> login(String account, String password) {
        return accountInfoRepository.findOne(Example.of(new AccountInfo().setAccount(account).setPassword(password)))
                .map(accountInfo -> accountInfo == null ? null : EntityMapper.INSTANCE.toAccount(accountInfo));
    }
}
