package ru.fortushin.EffectiveMobilestore.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fortushin.EffectiveMobilestore.model.Discount;
import ru.fortushin.EffectiveMobilestore.model.FeedBack;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private List<Discount> discountList;

    private List<FeedBack> feedBack;
    private String keyWord;
    private double rate;
    private String params;
}
