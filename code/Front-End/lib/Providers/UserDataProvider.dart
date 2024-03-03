

import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:flutter/material.dart';
import 'package:barberapp_front_end/Model/Cliente.dart';
import 'package:barberapp_front_end/Model/Dipendente.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';

import '../Model/Servizio.dart';

class UserDataProvider with ChangeNotifier {
  //Usare notifyListener() per notificare tutti i listener in ascolto
  //Impostare il multiProvider per i vari widget runApp(MultiProvider(providers: providers) MyApp());
  //MultiProvider(providers: [ChangeNotifierProvider(create: (_) => Counter())], child : MyApp(),));
  //Utilizzare nei vari widgets che sono in ascolto '${context.watch<UserDataProvider>().variabile}'
  //Per utilizzare i metodi del provider andare ad implementare nei bottoni context.read<Counter>().metodo();

  late dynamic _utente;
  late Cliente _cliente;
  late Dipendente _dipendente;
  late Titolare _titolare;
  late Servizio _servizio;

  late Appuntamento _appuntamento;

  late List<Appuntamento> _appuntamenti = [];
  late List<Servizio> _servizi = [];
  late List<Cliente> _clienti = [];
  late List<Dipendente> _dipendenti = [];

  dynamic get instance {
    if (_utente is Cliente) {
      _cliente = _utente;
      return _cliente;
    } else if (_utente is Dipendente) {
      _dipendente = _utente;
      return _dipendente;
    } else if (_utente is Titolare) {
      _titolare = _utente;
      return _titolare;
    } else
      return null;
  }

  List<Cliente> get clienti => _clienti;
  void setClienti(List<Cliente> clienti){
    _clienti = clienti;
    notifyListeners();
  }
  void addClienti(Cliente cliente){
    _clienti.add(cliente);
    notifyListeners();
  }

  void removeClienti(Cliente cliente){
    _clienti.remove(cliente);
    notifyListeners();
  }
  List<Dipendente> get dipendenti => _dipendenti;
  void setDipendenti(List<Dipendente> dipendenti){
    _dipendenti = dipendenti;
    notifyListeners();
  }
  void addDipendenti(Dipendente dipendente){
    _dipendenti.add(dipendente);
    notifyListeners();
  }

  void removeDipendenti(Dipendente dipendente){
    _dipendenti.remove(dipendente);
    notifyListeners();
  }
  Appuntamento get appuntamento => _appuntamento;

  void setAppuntamento(Appuntamento appuntamento) {
    _appuntamento = appuntamento;
    notifyListeners();
  }

  List<Appuntamento> get appuntamenti => _appuntamenti;

  void setAppuntamenti(List<Appuntamento> appuntamenti){
    _appuntamenti = appuntamenti;
    notifyListeners();
  }

  void addAppuntamenti(Appuntamento appuntamenti){
    _appuntamenti.add(appuntamenti);
    notifyListeners();
  }
  void deleteAppuntamenti(Appuntamento appuntamenti){
    _appuntamenti.remove(appuntamenti);
    notifyListeners();
  }

  List<Servizio> get servizi => _servizi;
  void setServizi(List<Servizio> servizi){
    _servizi = servizi;
    notifyListeners();
  }
  void addServizi(Servizio servizio){
    _servizi.add(servizio);
    notifyListeners();
  }
  void deleteServizio(Servizio servizio){
    _servizi.remove(servizio);
    notifyListeners();
  }


  dynamic get utente => _utente;

  void setUtente(dynamic utente) {
    _utente = utente;
    notifyListeners();
  }

  Cliente get cliente => _cliente;

  void setCliente(Cliente cliente) {
    _cliente = cliente;
    notifyListeners();
  }

  Dipendente get dipendente => _dipendente;

  void setDipendente(Dipendente dipendente) {
    _dipendente = dipendente;
    notifyListeners();
  }

  Titolare get titolare => _titolare;

  void setTitolare(Titolare titolare) {
    _titolare = titolare;
    notifyListeners();
  }

  Servizio get servizio => _servizio;

  void setServizio(Servizio servizio) {
    _servizio = servizio;
    notifyListeners();
  }
}
