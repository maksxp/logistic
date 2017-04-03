package ua.viasat.logistic.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.viasat.logistic.model.Equipment;
import ua.viasat.logistic.service.EquipmentService;
import ua.viasat.logistic.service.ModelService;
import ua.viasat.logistic.service.OrderService;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value="/viasat/allEquipments", method = RequestMethod.GET)
    public ModelAndView allEquipment(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allEquipments",equipmentService.listAllEquipments());
        modelAndView.setViewName("/viasat/allEquipments");
        return modelAndView;
    }

    @RequestMapping(value = "/viasat/equipmentUpload", method = RequestMethod.GET)
    public ModelAndView newEquipmentUpload() {
        ModelAndView modelAndView = new ModelAndView();
        Equipment equipment = new Equipment();
        modelAndView.addObject("allModels", modelService.listAllModels());
        modelAndView.addObject("equipment", equipment);
        modelAndView.setViewName("/viasat/equipmentUpload");
        return modelAndView;
         }
@RequestMapping(value = "/viasat/equipmentUpload", method = RequestMethod.POST)
public ModelAndView newEquipmentUpload (@RequestParam("myFile") MultipartFile myFile, Equipment equipment) {
    String type = equipment.getType();
    String state = equipment.getState();
    String model = equipment.getModelName();
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("allModels", modelService.listAllModels());
    InputStream is = null;
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
                Equipment eq = new Equipment();
                eq.setType(type);
                eq.setState(state);
                eq.setModelName(model);
                eq.setSerialNumber(cell.getStringCellValue());
                equipmentService.saveEquipmentToWarehouse(eq);
                 }
                             }
    modelAndView.addObject("successMessage", "Обладнання успішно додано");
    modelAndView.addObject("allEquipments",equipmentService.listAllEquipments());
    return new ModelAndView("redirect:/viasat/allEquipments");
}

}


