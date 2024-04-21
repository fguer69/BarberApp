package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.OrariServizio.OrariServizio;
import com.BarberApp.BackEnd.model.OrariServizio.OrariServizioDAO;
import com.BarberApp.BackEnd.model.salone.Salone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrariServizioController {
    @Autowired
    private OrariServizioDAO orariServizioDAO;

    @GetMapping("/OrariServizio/get-all")
    public List<OrariServizio> getAllOrariServizio(){
        return orariServizioDAO.getAll();
    }

    @PostMapping("/OrariServizio/get-by-salone")
    public List<OrariServizio> getBySalone(@RequestBody Salone salone){
        return orariServizioDAO.getBySalone(salone);
    }

    @PostMapping("/OrariServizio/save")
    public int save(@RequestBody OrariServizio orariServizio){
        orariServizioDAO.save(orariServizio);
        return 200;
    }

    @PostMapping("/OrariServizio/delete")
    public int delete(@RequestBody OrariServizio orariServizio){
        orariServizioDAO.delete(orariServizio);
        return 200;
    }

    @PostMapping("/OrariServizio/update")
    public int update(@RequestBody OrariServizio orariServizio){
        orariServizioDAO.update(orariServizio);
        return 200;
    }

}
