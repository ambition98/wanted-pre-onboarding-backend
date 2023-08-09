package com.onboard.web.entity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class AccountPwEntity extends BaseEntity {

    public AccountPwEntity() {
        this.id = new ULID().nextULID();
    }

    public AccountPwEntity(String password) {
        this.id = new ULID().nextULID();
        this.password = password;
    }

    @Id
    @Column(name = "account_pw_id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(length = 60, nullable = false)
    private String password;
}
