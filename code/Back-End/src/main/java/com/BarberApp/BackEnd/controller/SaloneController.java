package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.salone.Salone;
import com.BarberApp.BackEnd.model.salone.SaloneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaloneController {
    @Autowired
    private SaloneDAO saloneDAO;

    @GetMapping("/salone/get-all")
    public List<Salone> getAllsaloni(){return saloneDAO.getAll();}

    @PostMapping("/salone/save")
    public int saveSalone(Salone salone){
        if(saloneDAO.checkSalone(salone)){
            saloneDAO.saveSalone(salone);
            return 200;
        }
        else
            return 500;
    }
    @PostMapping("/salone/delete")
    public int deleteSalone(Salone salone){
        saloneDAO.deleteSalone(salone);
        return 200;
    }
}
