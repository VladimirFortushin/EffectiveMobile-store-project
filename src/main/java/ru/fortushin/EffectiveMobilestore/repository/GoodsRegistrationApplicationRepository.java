package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.Company;
import ru.fortushin.EffectiveMobilestore.model.GoodsRegistrationApplication;

import java.util.List;

@Repository
public interface GoodsRegistrationApplicationRepository extends JpaRepository<GoodsRegistrationApplication, Integer> {
    GoodsRegistrationApplication findByCompany(Company company);
    List<GoodsRegistrationApplication> findAllByApproved(boolean b);
}
