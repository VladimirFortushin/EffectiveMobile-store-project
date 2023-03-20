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
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "goods_registration_application_id")
    private GoodsRegistrationApplication goodsRegistrationApplication;
    private String name;
    private String description;
    private double price;
    private int quantity;
    @ManyToMany
    @JoinTable(name = "goods_discount",
            joinColumns = {@JoinColumn(name = "goods_id")},
            inverseJoinColumns = {@JoinColumn(name = "discount_id")})
    private List<Discount> discountList;
    @OneToMany(mappedBy = "goods")
    private List<FeedBack> feedBack;
    @ManyToOne
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;
    private String keyWord;
    private double rate;
    private String params;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private double rateCounter = 0.0;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private double rateSum = 0.0;

    public void setRate(double rate) {
        this.rateCounter++;
        this.rateSum+=rate;
        this.rate = rateSum/rateCounter;
    }

    public double getRate() {
        return rate;
    }
}


