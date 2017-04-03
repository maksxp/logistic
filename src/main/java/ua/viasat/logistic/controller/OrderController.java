package ua.viasat.logistic.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.viasat.logistic.model.DeliveryAddress;
import ua.viasat.logistic.model.Equipment;
import ua.viasat.logistic.model.Order;
import ua.viasat.logistic.model.User;
import ua.viasat.logistic.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value="/viasat/order", method = RequestMethod.GET)
    public ModelAndView createNewViasatOrder(){
        ModelAndView modelAndView = new ModelAndView();
        Order order = new Order ();
        modelAndView.addObject("order", order);
        modelAndView.addObject("listAllDeliveryAddresses", deliveryAddressService.listAllDeliveryAddresses());
        modelAndView.addObject("listAllCompanies", companyService.listAllCompanies());
        modelAndView.addObject("listAllModels", modelService.listAllModels());
        modelAndView.setViewName("viasat/order");
        return modelAndView;
    }
    @RequestMapping(value = "/viasat/order", method = RequestMethod.POST)
    public ModelAndView createNewViasatOrder(@Valid Order order, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("listAllDeliveryAddresses", deliveryAddressService.listAllDeliveryAddresses());
            modelAndView.addObject("listAllCompanies", companyService.listAllCompanies());
            modelAndView.addObject("listAllModels", modelService.listAllModels());
            modelAndView.setViewName("viasat/order");
        } else {
            orderService.saveViasatOrder(order);
            modelAndView.addObject("successMessage", "Заявку успішно створено");
            modelAndView.addObject("listAllDeliveryAddresses", deliveryAddressService.listAllDeliveryAddresses());
            modelAndView.addObject("listAllCompanies", companyService.listAllCompanies());
            modelAndView.addObject("listAllModels", modelService.listAllModels());
            modelAndView.addObject("order", new Order());
            modelAndView.setViewName("viasat/order");
        }
        return modelAndView;
    }
    @RequestMapping(value="/DTH/order", method = RequestMethod.GET)
    public ModelAndView createNewOrderDTH(){
        ModelAndView modelAndView = new ModelAndView();
        Order order = new Order ();
        modelAndView.addObject("order", order);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String company = user.getCompany();
        System.out.println(company);
        modelAndView.addObject("companyDeliveryAddresses", deliveryAddressService.listCompanyDeliveryAddresses(company));
        System.out.println(company+"after");
        modelAndView.addObject("allModels", modelService.listAllModels());
        modelAndView.setViewName("DTH/order");
        return modelAndView;
    }
    @RequestMapping(value = "/DTH/order", method = RequestMethod.POST)
    public ModelAndView createNewOrderDTH(@Valid Order order, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String company = user.getCompany();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("allModels", modelService.listAllModels());
            modelAndView.setViewName("DTH/order");
        } else {
//            order.setCompany(company);
            orderService.saveOrder(order);
            modelAndView.addObject("successMessage", "Заявку успішно створено");
            modelAndView.addObject("allModels", modelService.listAllModels());
            modelAndView.addObject("companyDeliveryAddresses", deliveryAddressService.listCompanyDeliveryAddresses(company));
            modelAndView.addObject("order", new Order());
            modelAndView.setViewName("DTH/order");
        }
        return modelAndView;
    }
    @RequestMapping(value="DTH/ordersList", method = RequestMethod.GET)
    public ModelAndView orders(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("myOrders", orderService.findOrdersByUser(user));
        modelAndView.setViewName("DTH/ordersList");
        return modelAndView;
    }

    @RequestMapping(value="viasat/allOrdersList", method = RequestMethod.GET)
    public ModelAndView allOrders(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allOrders", orderService.listAllOrders());
        modelAndView.setViewName("viasat/allOrdersList");
        return modelAndView;
    }
    @RequestMapping(value="Warehouse/allOrdersList", method = RequestMethod.GET)
    public ModelAndView warehouseAllOrders(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allOrders", orderService.listAllOrders());
        modelAndView.setViewName("Warehouse/allOrdersList");
        return modelAndView;
    }
    @RequestMapping(value="Warehouse/warehouseOrdersList", method = RequestMethod.GET)
    public ModelAndView warehouseOrders(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("warehouseOrders", orderService.listWarehouseOrders());
        modelAndView.setViewName("Warehouse/warehouseOrdersList");
        return modelAndView;
    }
    @RequestMapping(value="viasat/assignedOrdersList", method = RequestMethod.GET)
    public ModelAndView viasatOrders(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("viasatOrders", orderService.listViasatOrders());
        modelAndView.setViewName("viasat/assignedOrdersList");
        return modelAndView;
    }

    @RequestMapping(value = "/viasat/confirmOrder/{id}", method = RequestMethod.GET)
    public ModelAndView confirmOrder(@PathVariable(value = "id") int id){
        Order order =  orderService.findOrderById(id);
        orderService.confirmOrder(order);
        return new ModelAndView("redirect:/viasat/assignedOrdersList");
    }

    @RequestMapping(value = "/viasat/confirmOrder", method = RequestMethod.POST)
    public ModelAndView confirmOrder (@ModelAttribute("order")Order order) {
        orderService.confirmOrder(order);
        return new ModelAndView("redirect:/viasat/allOrdersLists");
    }
    @RequestMapping(value = "/viasat/editOrder/{id}", method = RequestMethod.GET)
    public ModelAndView editOrder(@PathVariable(value = "id") int id){
        ModelAndView modelAndView = new ModelAndView();
        Order order =  orderService.findOrderById(id);
        modelAndView.addObject("order", order);
        modelAndView.setViewName("viasat/editOrder");
        return modelAndView;
    }
    @RequestMapping(value = "/viasat/editOrder", method = RequestMethod.POST)
    public ModelAndView editOrder(@ModelAttribute("order")Order order) {
        ModelAndView modelAndView = new ModelAndView();
        int quantity =  order.getQuantity();
        int id = order.getId();
        order = orderService.findOrderById(id);
        if (quantity<5){
            modelAndView.addObject("successMessage", "Кількість має бути не менше п'яти штук");
            return modelAndView;
        }
        orderService.editOrder(order, quantity);
        modelAndView.addObject("successMessage", "Зміни успішно внесено");
        return modelAndView;
    }
    @RequestMapping(value = "/Warehouse/closeOrder/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable(value = "id") int id){
        ModelAndView modelAndView = new ModelAndView();
        Order order =  orderService.findOrderById(id);
        modelAndView.addObject("order", order);
        modelAndView.setViewName("/Warehouse/closeOrder");
        return modelAndView;
    }

    @RequestMapping(value = "/Warehouse/closeOrder", method = RequestMethod.POST)
    public ModelAndView warehouseEquipmentUpload (@RequestParam("myFile") MultipartFile myFile, @ModelAttribute("order") Order order) {
        Set<String> uploadedSerial = new HashSet<>();
        InputStream is = null;
        ModelAndView modelAndView = new ModelAndView();
        String ttn = order.getTtn();
        order = orderService.findOrderById(order.getId());
        if (ttn.equals("")) {
            modelAndView.addObject("order", order);
            modelAndView.addObject("errorMessage", "Необхідно вказати номер ТТН");
            return modelAndView;
        }
        try {
            is = myFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                uploadedSerial.add(cell.getStringCellValue());
            }
        }
        if (!(uploadedSerial.size()==order.getQuantity())){
            modelAndView.addObject("order", order);
            modelAndView.addObject("errorMessage", "Завантажено "+uploadedSerial.size()+ " тюнерів, а необхідно " + order.getQuantity());
            return modelAndView;
        }
        else {
            List<Equipment> warehouseEquipments = new ArrayList<>();
            List<Equipment> mainWarehouseEquipments = equipmentService.findByLocation("mainWarehouse");
            List<Equipment> localWarehouseEquipments = equipmentService.findByLocation("localWarehouse");
            warehouseEquipments.addAll(mainWarehouseEquipments);
            warehouseEquipments.addAll(localWarehouseEquipments);
            List <String> allSerial = new ArrayList<>();
            List<String> uploadedSerialList = new ArrayList<>();
            uploadedSerialList.addAll(uploadedSerial);
            for (Equipment equipment : warehouseEquipments){
                allSerial.add(equipment.getSerialNumber());
            }

            if (allSerial.containsAll(uploadedSerialList)){
                for (String serial : uploadedSerialList){
                    equipmentService.editEquipmentLocation(serial);
                }
                modelAndView.addObject("successMessage", "Передано "+uploadedSerial.size()+ " тюнерів");
                order.setTtn(ttn);
                orderService.closeOrder(order);
                return new ModelAndView("redirect:/Warehouse/allOrdersList");}
            else
            {
                modelAndView.addObject("order", order);
                modelAndView.addObject("errorMessage", "Завантажене обладнання неможливо передати оскільки воно відстунє на складі");
                return modelAndView;
            }

        }
    }
}
