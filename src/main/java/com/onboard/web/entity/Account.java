package com.onboard.web.entity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "account")
@Getter
@Builder
@AllArgsConstructor
public class Account extends BaseEntity {

    public Account() {
        this.id = new ULID().nextULID();
    }

    public Account(String email, AccountPw accountPw) {
        this.id = new ULID().nextULID();
        this.email = email;
        this.accountPw = accountPw;
    }

    @Id
    @Column(columnDefinition = "CHAR(26)", nullable = false)
    private String id;

    @Column(length = 256, nullable = false) //IETF 기준 맥시멈
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // Account 를 등록, 삭제하면 AccountPw 까지 등록, 삭제된다.
    // Account 의 비밀번호 변경 시 AccountPw 하나가 Orphan 상태가 된다.
    // 필요 없어지는 데이터 이므로 삭제한다.
    @JoinColumn(name = "ACCOUNT_PW_ID", nullable = false)
    private AccountPw accountPw;

}
