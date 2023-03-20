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
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "company_registration_application_id")
    private CompanyRegistrationApplication companyRegistrationApplication;

    private String name;

    private String description;

    private String logo;
    @OneToMany(mappedBy = "company")
    private List<Goods> goodsList;
    private boolean isEnabled;
}
