package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.CompanyRegistrationApplication;
import ru.fortushin.EffectiveMobilestore.repository.CompanyRegistrationApplicationRepository;

import java.util.List;

@Service
public class CompanyRegistrationApplicationService {
    private final CompanyRegistrationApplicationRepository companyRegistrationApplicationRepository;
@Autowired
    public CompanyRegistrationApplicationService(CompanyRegistrationApplicationRepository companyRegistrationApplicationRepository) {
        this.companyRegistrationApplicationRepository = companyRegistrationApplicationRepository;
    }

    public CompanyRegistrationApplication get(int id){
        return  companyRegistrationApplicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Company Registration Application with id " + id + " was not found"));
    }

    public List<CompanyRegistrationApplication> getList(){
        return companyRegistrationApplicationRepository.findAll();
    }

    @Transactional
    public void update(CompanyRegistrationApplication updatedCompanyRegistrationApplication, int id) {
        updatedCompanyRegistrationApplication.setId(id);
        companyRegistrationApplicationRepository.save(updatedCompanyRegistrationApplication);
    }

    @Transactional
    public void create(CompanyRegistrationApplication companyRegistrationApplication) {
        companyRegistrationApplicationRepository.save(companyRegistrationApplication);
    }

    @Transactional
    public void delete(CompanyRegistrationApplication companyRegistrationApplication) {
        companyRegistrationApplicationRepository.delete(companyRegistrationApplication);
    }

}
