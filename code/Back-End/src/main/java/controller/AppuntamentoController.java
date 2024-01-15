package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.appuntamento.Appuntamento;
import model.appuntamento.AppuntamentoDAO;

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
		appuntamentoDAO.saveAppointment(appuntamento);
		return 200;
	}

	//rimozione di un appuntamento dal database
	@PostMapping("/appuntamenti/delete")
	public int removeAppointment(@RequestBody Appuntamento appuntamento) {
		appuntamentoDAO.deleteAppointment(appuntamento);
		return 200;
	}
}
