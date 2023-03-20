package ru.fortushin.EffectiveMobilestore.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fortushin.EffectiveMobilestore.model.Goods;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private String name;

    private String description;

    private String logo;

    private List<Goods> goodsList;
}
