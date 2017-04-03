package ua.viasat.logistic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.viasat.logistic.model.Company;
import ua.viasat.logistic.repository.CompanyRepository;

import java.util.List;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> listAllCompanies(){   return companyRepository.findAll() ;   }
    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);}
    @Override
    public Company findById(int id) {
        return companyRepository.findById(id);
    }
    @Override
    public Company findByName(String name) {return companyRepository.findByName(name);}
}
