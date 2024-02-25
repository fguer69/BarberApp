package com.BarberApp.BackEnd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;

@RestController
public class AppuntamentoController {
	
	@Autowired
	private AppuntamentoDAO appuntamentoDAO;
	
	//Visualizzazione di tutti gli appuntamenti presenti nel database
	@GetMapping("/appuntamenti/get-all")
	public List<Appuntamento> getAllAppuntamenti(){
		return appuntamentoDAO.getAll();
	}
	
	//Salvataggio di un'appuntamento sul database
	@PostMapping("/appuntamenti/save")
	public int saveAppuntamento(@RequestBody Appuntamento appuntamento) {
		if(appuntamentoDAO.checkAppuntamento(appuntamento.getCliente(), appuntamento) <= 0) {
			System.out.println(appuntamentoDAO.checkAppuntamento(appuntamento.getCliente(),appuntamento));
			System.out.println(appuntamento);
			appuntamentoDAO.saveAppointment(appuntamento);
			return 200;
		}
		else
			return 500;
	}

	//rimozione di un appuntamento dal database
	@PostMapping("/appuntamenti/delete")
	public int removeAppointment(@RequestBody Appuntamento appuntamento) {
		appuntamentoDAO.deleteAppointment(appuntamento);
		return 200;
	}
	//ritorno degli appuntamenti di un cliente
		@PostMapping("/appuntamenti/getAppointment-ByCliente")
		public List<Appuntamento> getAppointment(@RequestBody Cliente cliente){
			return appuntamentoDAO.getAppuntamentiByCliente(cliente);						
		}
		
		//ritorno degli appuntamenti di un dipendente
		@PostMapping("/appuntamenti/getAppointment-ByDipendente")
		public List<Appuntamento> getAppointmentByDipendente(@RequestBody Dipendente dipendente){
			return appuntamentoDAO.getAppuntamentiByDipendente(dipendente);						
		}
		//Visualizzazione di tutti gli appuntamenti nel sistema ordinati per data e ora
		@GetMapping("/appuntamenti/get-all-appuntamenti")
		public List<Appuntamento> getAllAppuntamentiOrdered(){
			return appuntamentoDAO.getAppuntamentiOrdered();
		}
}
