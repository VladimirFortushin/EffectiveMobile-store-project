package ru.fortushin.EffectiveMobilestore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "goods_registration_application")
public class GoodsRegistrationApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private boolean isApproved;
    @OneToMany(mappedBy = "goodsRegistrationApplication")
    private List<Goods> goodsList;
}
