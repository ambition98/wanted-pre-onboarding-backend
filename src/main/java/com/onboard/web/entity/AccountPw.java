package com.onboard.web.entity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ACCOUNT_PW")
@Getter
public class AccountPw extends BaseEntity {

    public AccountPw() {
        this.id = new ULID().nextULID();
    }

    public AccountPw(String password) {
        this.id = new ULID().nextULID();
        this.password = password;
    }

    @Id
    @Column(columnDefinition = "CHAR(26)")
    private String id;

    @Column(columnDefinition = "CHAR(60)", nullable = false)
    private String password;
}
