package ru.fortushin.EffectiveMobilestore.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "feedback")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;
}
