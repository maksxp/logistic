package ua.viasat.logistic.service;


import ua.viasat.logistic.model.Company;

import java.util.List;

public interface CompanyService {
    Company findById(int id) ;
    void saveCompany(Company company);
    List<Company> listAllCompanies();
    Company findByName(String name);
}
