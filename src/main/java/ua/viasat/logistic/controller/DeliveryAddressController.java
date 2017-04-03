package ua.viasat.logistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.viasat.logistic.model.Company;
import ua.viasat.logistic.model.DeliveryAddress;
import ua.viasat.logistic.service.CompanyService;
import ua.viasat.logistic.service.DeliveryAddressService;

import javax.validation.Valid;

@Controller
public class DeliveryAddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value="/viasat/deliveryAddressRegistration", method = RequestMethod.GET)
    public ModelAndView deliveryAddressRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        modelAndView.addObject("deliveryAddress", deliveryAddress);
        modelAndView.addObject("allCompanies", companyService.listAllCompanies());
        modelAndView.setViewName("/viasat/deliveryAddressRegistration");
        return modelAndView;
    }

    @RequestMapping(value = "/viasat/deliveryAddressRegistration", method = RequestMethod.POST)
    public ModelAndView createNewDeliveryAddress(@Valid DeliveryAddress deliveryAddress, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        DeliveryAddress deliveryAddressExists = deliveryAddressService.findByAddress(deliveryAddress.getAddress());
        System.out.println(deliveryAddress.getAddress());
        System.out.println(deliveryAddress.getCompanyName());
         if (deliveryAddressExists != null) {

            bindingResult
                    .rejectValue("name", "error.model",
                            "Така адреса вже зареєстрована");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("errors");
            modelAndView.addObject("deliveryAddress", new DeliveryAddress());
            modelAndView.addObject("allCompanies", companyService.listAllCompanies());
            modelAndView.setViewName("/viasat/deliveryAddressRegistration");
        } else {
            System.out.println("no errors");
            deliveryAddressService.saveDeliveryAddress(deliveryAddress);
            modelAndView.addObject("successMessage", "Адресу успішно додано");
            modelAndView.addObject("deliveryAddress", new DeliveryAddress());
            modelAndView.addObject("allCompanies", companyService.listAllCompanies());
            modelAndView.setViewName("/viasat/deliveryAddressRegistration");
        }
        return modelAndView;
    }
    @RequestMapping(value="/viasat/allDeliveryAddresses", method = RequestMethod.GET)
    public ModelAndView allDeliveryAddresses(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allDeliveryAddresses", deliveryAddressService.listAllDeliveryAddresses());
        modelAndView.setViewName("/viasat/allDeliveryAddresses");
        return modelAndView;
    }
}
