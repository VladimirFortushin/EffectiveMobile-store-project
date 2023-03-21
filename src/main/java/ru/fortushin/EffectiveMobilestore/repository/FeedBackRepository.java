package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.FeedBack;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {

}
