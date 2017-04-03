package ua.viasat.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viasat.logistic.model.Company;

import java.util.List;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
    Company findById(int id);
    List<Company> findByType(String type);
}
