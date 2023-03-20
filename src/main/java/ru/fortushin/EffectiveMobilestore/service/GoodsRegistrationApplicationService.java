package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.GoodsRegistrationApplication;
import ru.fortushin.EffectiveMobilestore.repository.GoodsRegistrationApplicationRepository;

@Service
public class GoodsRegistrationApplicationService {
    private final GoodsRegistrationApplicationRepository goodsRegistrationApplicationRepository;
@Autowired
    public GoodsRegistrationApplicationService(GoodsRegistrationApplicationRepository goodsRegistrationApplicationRepository) {
        this.goodsRegistrationApplicationRepository = goodsRegistrationApplicationRepository;
    }

    public GoodsRegistrationApplication get(int id){
        return goodsRegistrationApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GoodsRegistrationApplicationRepository with id " + id + " was not found"));
    }

    @Transactional
    public void create(GoodsRegistrationApplication goodsRegistrationApplication){
        goodsRegistrationApplicationRepository.save(goodsRegistrationApplication);
    }
}
