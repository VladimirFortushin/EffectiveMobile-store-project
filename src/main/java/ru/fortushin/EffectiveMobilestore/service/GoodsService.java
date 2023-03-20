package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.Goods;
import ru.fortushin.EffectiveMobilestore.repository.GoodsRepository;

import java.util.List;

@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Goods get(int id){
        return goodsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Goods with id " + id + " was not found"));
    }

    public List<Goods> getList(){
        return goodsRepository.findAll();
    }

    @Transactional
    public void update(Goods updatedGoods, int id) {
        updatedGoods.setId(id);
        goodsRepository.save(updatedGoods);
    }

    @Transactional
    public void create(Goods goods){
        goodsRepository.save(goods);
    }

    @Transactional
    public void delete(Goods goods) {
        goodsRepository.delete(goods);
    }

}
