package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.PurchaseHistory;
import ru.fortushin.EffectiveMobilestore.repository.PurchaseHistoryRepository;

import java.util.List;

@Service
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    public PurchaseHistoryService(PurchaseHistoryRepository purchaseHistoryRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    public PurchaseHistory get(int id) {
        return purchaseHistoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("PurchaseHistory with id " + id + " was not found"));
    }

    public List<PurchaseHistory> getList() {
        return purchaseHistoryRepository.findAll();
    }

    @Transactional
    public void update(PurchaseHistory updatedPurchaseHistory, int id) {
        updatedPurchaseHistory.setId(id);
        purchaseHistoryRepository.save(updatedPurchaseHistory);
    }

    @Transactional
    public void create(PurchaseHistory purchaseHistory) {
        purchaseHistoryRepository.save(purchaseHistory);
    }

    @Transactional
    public void delete(PurchaseHistory purchaseHistory) {
        purchaseHistoryRepository.delete(purchaseHistory);
    }
}
