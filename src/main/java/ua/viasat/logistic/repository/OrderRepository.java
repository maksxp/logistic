package ua.viasat.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viasat.logistic.model.Order;
import java.util.Date;
import java.util.List;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(int id);
    List<Order> findByModel(String model);
    List<Order> findByStatusLike(String orderStatus);
    List<Order> findByStartDateBetween(Date dateStart, Date dateEnd);
}