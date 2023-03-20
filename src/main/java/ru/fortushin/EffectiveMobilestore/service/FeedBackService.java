package ru.fortushin.EffectiveMobilestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.FeedBack;
import ru.fortushin.EffectiveMobilestore.repository.FeedBackRepository;

@Service
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;
@Autowired
    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Transactional
    public void create(FeedBack feedBack){
        feedBackRepository.save(feedBack);
    }

    @Transactional
    public void delete(FeedBack feedBack){
        feedBackRepository.delete(feedBack);
    }

}
