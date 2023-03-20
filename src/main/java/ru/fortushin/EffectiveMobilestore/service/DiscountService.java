package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.Discount;
import ru.fortushin.EffectiveMobilestore.repository.DiscountRepository;


import java.util.List;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount get(int id){
        return discountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discount with id " + id + " was not found"));
    }

    public List<Discount> getList(){
        return discountRepository.findAll();
    }
    @Transactional
    public void update(Discount updatedDiscount, int id) {
        updatedDiscount.setId(id);
        discountRepository.save(updatedDiscount);
    }

    @Transactional
    public void create(Discount discount) {
        discountRepository.save(discount);
    }

    @Transactional
    public void delete(Discount discount) {
        discountRepository.delete(discount);
    }


}
