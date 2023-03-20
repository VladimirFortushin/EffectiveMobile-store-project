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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String email;
    private String password;
    private double balance;
    private String role;
    @OneToMany(mappedBy = "user")
    private List<FeedBack> feedBackList;
    @OneToMany(mappedBy = "user")
    private List<CompanyRegistrationApplication> companyRegistrationApplicationList;
    @ManyToMany
    @JoinTable(name = "user_notification",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List<Notification> notificationList;
    @OneToOne
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;
    private boolean accountEnabled;
    public void updateBalance(double sumForUpdate){
        this.balance += sumForUpdate;
    }

}
