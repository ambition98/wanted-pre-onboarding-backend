package com.onboard.web.entity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "ACCOUNT")
@Getter
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class AccountEntity extends BaseEntity {

    public AccountEntity() {
        this.id = new ULID().nextULID();
    }

    public AccountEntity(String email, AccountPwEntity accountPwEntity) {
        this.id = new ULID().nextULID();
        this.email = email;
        this.accountPwEntity = accountPwEntity;
    }

    @Id
    @Column(columnDefinition = "CHAR(26)")
    private String id;

    @Column(length = 256) //IETF 기준 맥시멈
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_pw_id")
    private AccountPwEntity accountPwEntity;

}
