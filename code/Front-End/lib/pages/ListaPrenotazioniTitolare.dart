import 'dart:developer';

import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/Dipendente.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/utils/navigation_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../Model/Cliente.dart';
import '../Retrofit/RetrofitService.dart';


class ListaPrenotazioniTitolare extends StatefulWidget {
  const ListaPrenotazioniTitolare({super.key});

  @override
  State<ListaPrenotazioniTitolare> createState() => _ListaPrenotazioniTitolareState();
}

class _ListaPrenotazioniTitolareState extends State<ListaPrenotazioniTitolare> {
  late List<Dipendente> dipendenti = Provider.of<UserDataProvider>(context, listen: true).dipendenti;
  late List<Appuntamento> appuntamenti = Provider.of<UserDataProvider>(context, listen: true).appuntamenti;
  late List<Cliente> clienti = Provider.of<UserDataProvider>(context, listen: true).clienti;
  @override
  void initState() {
    final retrofitService = RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    Future<List<Cliente>> futureClienti = retrofitService.getPosts();
    futureClienti.then((clienti) => {
      Provider.of<UserDataProvider>(context, listen: false).setClienti(clienti)
    });
    Future<List<Dipendente>> futureDipendenti = retrofitService.dipendentiGetAll();
    futureDipendenti.then((dipendenti) => {
      Provider.of<UserDataProvider>(context, listen: false).setDipendenti(dipendenti)
    });
    super.initState();
  }

  Future<void> _deleteAppuntamento(int index) async {
    final retrofitService =
    RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    final int responseCode =
    await retrofitService.deleteAppuntamento(appuntamenti[index]);
    if (responseCode == 200) {
      setState(() {
        appuntamenti.removeAt(index); // Rimuovi l'appuntamento dalla lista
      });
    }
  }


  @override
  Widget build(BuildContext context) {
    //controllare se la variabile cliente è stata inizializzata
    return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            centerTitle: true,
            title: const Text(
              "Prenotazioni",
              style: TextStyle(fontStyle: FontStyle.italic),
            ),
            actions: [
              Padding(
                padding: EdgeInsets.only(right: 24.0),
                child: CircleAvatar(
                  child: IconButton(
                    icon: Icon(Icons.person),
                    onPressed: (){ Navigator.pushNamed(context, '/ProfiloTitolare');},
                  ),
                ),
              )
            ],
          ),
          /*
          Leggi la lista da server

        */
          body: _body()
      );
  }
  FutureBuilder _body(){
    final retrofitService = RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    return FutureBuilder(
      future: retrofitService.getAllAppuntamentiOrdered(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          appuntamenti = snapshot.data == null ? [] : (snapshot.data as List<Appuntamento>);
          appuntamenti.forEach((element) {dipendenti.forEach((dipendente) {
            dipendente.appuntamenti?.forEach((elementA) {if(element.id == elementA.id) element.dipendente = dipendente; });
          });});
          appuntamenti.forEach((element) {clienti.forEach((cliente) {
            cliente.appuntamenti?.forEach((elementA) {if(element.id == elementA.id) element.cliente = cliente; });
          });});
          Provider.of<UserDataProvider>(context, listen: false).setAppuntamenti(appuntamenti);
          return  ListView.builder(
            itemCount: appuntamenti.length,//(snapshot.data as List<Appuntamento>).length,
            itemBuilder: (context, index) => BookTile(appuntamento: appuntamenti[index],clienti: clienti, callBack: (index) => _deleteAppuntamento(index), index: index,
            ),
          );

        } else {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }
}
class BookTile extends StatelessWidget {
  const BookTile({super.key,  required this.appuntamento,required this.clienti, required this.callBack, required this.index});
  final Appuntamento appuntamento;
  final Function(int) callBack;
  final int index;
  final List<Cliente> clienti;


  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            children: [
              CircleAvatar(
                radius: 32,
                child: Image.asset(
                  GetImages.images["default"] ?? "",
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(left: 16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(appuntamento.cliente!.nominativo),
                    Text(appuntamento.dipendente!.nominativo),
                    Text('${appuntamento.date.toString().substring(0,11)} ${appuntamento.time.hour}:${appuntamento.time.minute}'),
                  ],
                ),
              ),
              const Expanded(child: SizedBox()),
              appuntamento.isActive()?
              Padding(
                padding: const EdgeInsets.only(right: 16.0),
                child: InkWell(
                    onTap: () async {
                      bool? res = await showDialog<bool>(
                        context: context,
                        builder: (context) => AlertDialog(
                          title: const Text(
                              "Vuoi davvero eliminare questo elemento?"),
                          actions: [
                            TextButton(
                              onPressed: () => Navigator.of(context).pop(true),
                              child: const Text("Si"),
                            ),
                            TextButton(
                              onPressed: () => Navigator.of(context).pop(false),
                              child: const Text("No"),
                            ),
                          ],
                        ),
                      );
                      if (res != null && res == true){
                        callBack(index);
                      }
                      log(res.toString());
                    },
                    child: const Icon(Icons.delete_outline)),
              )
                  : const SizedBox(),
            ],
          ),
        ),
        const Divider()
      ],
    );
  }
}
