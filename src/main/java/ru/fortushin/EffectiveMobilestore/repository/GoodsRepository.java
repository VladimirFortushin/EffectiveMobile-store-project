package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.Company;
import ru.fortushin.EffectiveMobilestore.model.Goods;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    List<Goods> getAllByCompanyId(int companyId);

}
