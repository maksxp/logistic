package ua.viasat.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viasat.logistic.model.Model;


@Repository("modelRepository")
public interface ModelRepository extends JpaRepository<Model, Long>{
	Model findByName(String name);
	Model findById(int id);
}
