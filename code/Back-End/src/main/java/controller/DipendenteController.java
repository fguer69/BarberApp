package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.dipendente.Dipendente;
import model.dipendente.DipendenteDAO;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

@RestController
public class DipendenteController {
	
	@Autowired
	private DipendenteDAO dipendenteDAO;
	
	//visualizzazione di tutti i dipendenti nel sistema
	@GetMapping("/dipendenti/get-all")
	public List<Dipendente> getAllEmployee(){
		return dipendenteDAO.getEmployee();
	}
	
	//registrazione di un nuovo dipendente nel sistema
	@PostMapping("/dipendenti/save")
	public int saveEmployee(@RequestBody Dipendente dipendente) {
		if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
			dipendenteDAO.saveDipendente(dipendente);
			return 200;
		}
		else
			return 500;
	}
	
	//Eliminazione di un dipendente dal sistema
	@PostMapping("/dipendenti/delete")
	public int delete(@RequestBody Dipendente dipendente) {
		dipendenteDAO.deleteDipendente(dipendente);
		return 200;
	}
	
	//Aggiornamento dei dati dell'account di un dipendente
	@PostMapping("/dipendenti/update")
	public int update(@RequestBody Dipendente dipendente) {
		Dipendente dipendente1 = dipendenteDAO.getDipendenteById(dipendente.getId()).orElse(null);
		if(dipendente1 != null) {
			if(dipendente.getEmail() == dipendente1.getEmail()) {
				dipendenteDAO.updateEmployee(dipendente);
				return 200;
			}
			else {
				if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
					dipendenteDAO.updateEmployee(dipendente);
					return 200;
				}
				else
					return 500;
			}
		}
		else
			return 501;
	}
	//Visualizzazione di tutti i dipendenti disponibili per una determinata data e ora
	@PostMapping("/dipendenti/dipendentiDisponibili")
	public List<Dipendente> getDipendentiByDate(@RequestBody DateTime data, @RequestBody DateTime ora){
		return dipendenteDAO.getEmployeeByDate(data, ora);
		
	}

}
