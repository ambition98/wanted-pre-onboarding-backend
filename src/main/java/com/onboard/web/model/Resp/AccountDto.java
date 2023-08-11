package com.onboard.web.model.Resp;

import com.onboard.web.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String email;

    public static AccountDto bulid(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(accountEntity.getEmail());
        return accountDto;
    }
}
