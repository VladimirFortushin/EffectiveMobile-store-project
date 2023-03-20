package ru.fortushin.EffectiveMobilestore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(mappedBy = "notificationList")
    private List<User> userList;
    private String header;
    private LocalDateTime notificationDate;
    private String text;

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = LocalDateTime.now();
    }
}
