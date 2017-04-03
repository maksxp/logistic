package ua.viasat.logistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.viasat.logistic.model.Company;
import ua.viasat.logistic.service.CompanyService;

import javax.validation.Valid;

@Controller
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value="/viasat/allCompanies", method = RequestMethod.GET)
    public ModelAndView allCompanies(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allCompanies", companyService.listAllCompanies());
        modelAndView.setViewName("/viasat/allCompanies");
        return modelAndView;
    }
    @RequestMapping(value="/viasat/companyRegistration", method = RequestMethod.GET)
    public ModelAndView companyRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        Company company = new Company();
        modelAndView.addObject("company", company);
        modelAndView.setViewName("/viasat/companyRegistration");
        return modelAndView;
    }

    @RequestMapping(value = "/viasat/companyRegistration", method = RequestMethod.POST)
    public ModelAndView createNewCompany(@Valid Company company, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Company companyExists = companyService.findByName(company.getName());
                if (companyExists != null) {
            bindingResult
                    .rejectValue("name", "error.model",
                            "Таку організацію вже зареєстровано");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/viasat/companyRegistration");
        } else {
            companyService.saveCompany(company);
            modelAndView.addObject("successMessage", "Модель успішно додано");
            modelAndView.addObject("company", new Company());
            modelAndView.setViewName("/viasat/companyRegistration");
        }
        return modelAndView;
    }
 }
