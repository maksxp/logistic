package ua.viasat.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.viasat.logistic.model.Model;
import ua.viasat.logistic.repository.ModelRepository;

import java.util.List;

@Service("modelService")
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public List<Model> listAllModels(){   return modelRepository.findAll() ;   }
    @Override
    public void saveModel(Model model) {
    modelRepository.save(model);}
    @Override
    public Model findById(int id) {
        return modelRepository.findById(id);
    }
    @Override
    public Model findByName(String name) {return modelRepository.findByName(name);}

}
