package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.CompanyRegistrationApplication;

@Repository
public interface CompanyRegistrationApplicationRepository extends JpaRepository<CompanyRegistrationApplication, Integer> {

}
