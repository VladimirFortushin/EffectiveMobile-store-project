package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.PurchaseHistory;
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {
    PurchaseHistory getPurchaseHistoryByUserId(int userId);
}
