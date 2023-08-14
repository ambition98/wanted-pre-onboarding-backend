package com.onboard.web.model.Resp;

import com.onboard.web.entity.Account;
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

    public static AccountDto bulid(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(account.getEmail());
        return accountDto;
    }
}
