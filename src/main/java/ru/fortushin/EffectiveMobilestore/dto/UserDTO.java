package ru.fortushin.EffectiveMobilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fortushin.EffectiveMobilestore.model.PurchaseHistory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userName;
    private String password;
    private String email;
    private double balance;

}
