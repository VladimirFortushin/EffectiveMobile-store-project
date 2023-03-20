package ru.fortushin.EffectiveMobilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fortushin.EffectiveMobilestore.model.Goods;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private List<Goods> goodsList;
    private double discountRate;
    private LocalDateTime discountDuration;
}
