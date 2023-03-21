package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.Company;
import ru.fortushin.EffectiveMobilestore.model.Goods;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("SELECT c.isEnabled FROM Company c WHERE c.isEnabled = :b")
    List<Company> getAllByIsEnabled(boolean b);
    Company getCompanyByCompanyRegistrationApplicationId(int companyRegistrationApplicationId);
    List<Company> getCompanyListByUserId(int userId);
}
