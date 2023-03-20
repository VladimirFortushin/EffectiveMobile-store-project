package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.Company;
import ru.fortushin.EffectiveMobilestore.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
@Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company get(int id){
        return companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discount with id " + id + " was not found"));
    }

    public List<Company> getList(){
        return companyRepository.findAll();
    }

    @Transactional
    public void update(Company updatedCompany, int id){
        updatedCompany.setId(id);
        companyRepository.save(updatedCompany);
    }

    @Transactional
    public void create(Company company){
        companyRepository.save(company);
    }

    @Transactional
    public void delete(Company company){
        companyRepository.delete(company);
    }
}
