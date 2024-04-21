package com.BarberApp.BackEnd.model.salone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaloneDAO {
    @Autowired
    private SaloneRepository repository;

    public List<Salone> getAll(){
        return repository.findAll();
    }

    public void saveSalone(Salone salone){
        repository.save(salone);
    }

    public void deleteSalone(Salone salone){
        repository.deleteById(salone.getPiva());
    }

    public boolean checkSalone(Salone salone){
        if(repository.findById(salone.getPiva()).isPresent())
            return false;
        else
            return true;

    }

    public void update(Salone salone){
        repository.save(salone);
    }

}
