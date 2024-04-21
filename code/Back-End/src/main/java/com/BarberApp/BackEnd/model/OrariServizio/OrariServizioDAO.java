package com.BarberApp.BackEnd.model.OrariServizio;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.salone.Salone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrariServizioDAO {
    @Autowired
    private OrariServizioRepository repository;

    public List<OrariServizio> getAll(){
        return repository.findAll();
    }

    public List<OrariServizio> getBySalone(Salone salone){
        return repository.findBySalone(salone.getPiva());
    }

    public void save(OrariServizio orariServizio){
        repository.save(orariServizio);
    }

    public void delete(OrariServizio orariServizio){
        repository.delete(orariServizio);
    }

    public void update(OrariServizio orariServizio){
        repository.save(orariServizio);
    }

}
