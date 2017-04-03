package ua.viasat.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.viasat.logistic.model.Order;
import ua.viasat.logistic.model.User;
import ua.viasat.logistic.repository.OrderRepository;
import ua.viasat.logistic.repository.UserRepository;

import java.util.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List <Order> findOrdersByUser(User user){
        List<Order> ordersByUser = new ArrayList<>();
        List<Order> list = orderRepository.findAll();
        ListIterator <Order> iterator= list.listIterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getUser().equals(user)) {
               ordersByUser.add(order);
               }
        }
        return ordersByUser;}
   @Override
    public Order findOrderById(int id){ return orderRepository.findById(id);}
    @Override
    public List <Order> findOrdersByStatus(String orderStatus)
    {
        return orderRepository.findByStatusLike (orderStatus);
    }

    public List <Order> findOrdersByDate(Date dateStart, Date dateEnd){
        return orderRepository.findByStartDateBetween(dateStart,dateEnd);
    }

    @Override
    public void saveOrder(Order order) {
          int finalQuantity = order.getQuantity();
          order.setFinalQuantity(finalQuantity);
          order.setStartDate(new Date());
          order.setStatus("На погодженні");
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user;
        user = userRepository.findByEmail(getEmail(principal));
         order.setUser(user);
        order.setCompany(user.getCompany());
         orderRepository.save(order);
    }
    @Override
    public void saveViasatOrder(Order order) {
        int finalQuantity = order.getQuantity();
        order.setFinalQuantity(finalQuantity);
        order.setStartDate(new Date());
        order.setStatus("В роботі");
        order.setApproveDate(new Date());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user;
        user = userRepository.findByEmail(getEmail(principal));
        order.setUser(user);
        orderRepository.save(order);
    }
    @Override
    public List<Order> listAllOrders(){
        return orderRepository.findAll();
    }
    @Override
    public List<Order> listViasatOrders(){
        return orderRepository.findByStatusLike("На погодженні");
    }
    @Override
    public List<Order> listWarehouseOrders(){
        return orderRepository.findByStatusLike("В роботі");
    }
    @Override
    public void confirmOrder(Order order) {
        order.setApproveDate(new Date());
        order.setStatus("В роботі");
        orderRepository.save(order);
    }
    @Override
    public void editOrder(Order order, int quantity) {
        order.setFinalQuantity(quantity);
        order.setStatus("В роботі");
        order.setApproveDate(new Date());
        orderRepository.save(order);
    }
    @Override
    public void closeOrder(Order order) {
        order.setCloseDate(new Date());
        order.setStatus("Виконано");
        orderRepository.save(order);
    }
    private static String getEmail (Object principal){
        if (principal instanceof UserDetails) {
            String email = ((UserDetails)principal).getUsername();
            return email;
        } else {
            String email = principal.toString();
            return email;
             }
    }
}
