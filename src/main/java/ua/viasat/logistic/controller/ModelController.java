package ua.viasat.logistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.viasat.logistic.model.Model;
import ua.viasat.logistic.service.ModelService;

import javax.validation.Valid;

@Controller
public class ModelController {
    @Autowired
    private ModelService modelService;

    @RequestMapping(value="/viasat/allModels", method = RequestMethod.GET)
    public ModelAndView allModels(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allModels", modelService.listAllModels());
        modelAndView.setViewName("/viasat/allModels");
        return modelAndView;
    }
    @RequestMapping(value="/viasat/modelRegistration", method = RequestMethod.GET)
    public ModelAndView modelRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        Model model = new Model();
        modelAndView.addObject("model", model);
        modelAndView.setViewName("/viasat/modelRegistration");
        return modelAndView;
    }

    @RequestMapping(value = "/viasat/modelRegistration", method = RequestMethod.POST)
    public ModelAndView createNewModel(@Valid Model model, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Model modelExists = modelService.findByName(model.getName());
                if (modelExists != null) {
            bindingResult
                    .rejectValue("name", "error.model",
                            "Таку модель вже зареєстровано");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/viasat/modelRegistration");
        } else {
            modelService.saveModel(model);
            modelAndView.addObject("successMessage", "Модель успішно додано");
            modelAndView.addObject("model", new Model());
            modelAndView.setViewName("/viasat/modelRegistration");
        }
        return modelAndView;
    }
 }
