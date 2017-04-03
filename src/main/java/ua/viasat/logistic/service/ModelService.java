package ua.viasat.logistic.service;

import ua.viasat.logistic.model.Model;

import java.util.List;

public interface ModelService {
    Model findById(int id) ;
    void saveModel(Model model);
    List<Model> listAllModels();
    Model findByName(String name);
  }
