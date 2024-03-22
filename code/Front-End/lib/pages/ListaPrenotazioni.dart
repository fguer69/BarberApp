import 'dart:developer';

import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/utils/navigation_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../Model/Cliente.dart';
import '../Model/Dipendente.dart';
import '../Model/Servizio.dart';
import '../Retrofit/RetrofitService.dart';

class ListaPrenotazioni extends StatefulWidget {
  const ListaPrenotazioni({super.key});

  @override
  State<ListaPrenotazioni> createState() => _ListaPrenotazioniState();
}

class _ListaPrenotazioniState extends State<ListaPrenotazioni> {
  late Cliente cliente =
      Provider.of<UserDataProvider>(context, listen: true).cliente;
  late List<Appuntamento> appuntamenti =
      Provider.of<UserDataProvider>(context, listen: true).appuntamenti;

  late List<Dipendente> dipendenti =
      Provider.of<UserDataProvider>(context, listen: true).dipendenti;
  late List<Servizio> servizi =
      Provider.of<UserDataProvider>(context, listen: true).servizi;
  @override
  void initState() {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    Future<List<Dipendente>> futureDipendenti =
        retrofitService.dipendentiGetAll();
    futureDipendenti.then((dipendenti) => {
          Provider.of<UserDataProvider>(context, listen: false)
              .setDipendenti(dipendenti)
        });
    Future<List<Servizio>> futureServizi = retrofitService.getServices();
    futureServizi.then((servizi) => {
          Provider.of<UserDataProvider>(context, listen: false)
              .setServizi(servizi)
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
            "Lista Prenotazioni",
            style: TextStyle(fontStyle: FontStyle.italic),
          ),
          actions: [
            Padding(
              padding: const EdgeInsets.only(right: 24.0),
              child: CircleAvatar(
                child: IconButton(
                  icon: const Icon(Icons.person),
                  onPressed: () {
                    Navigator.pushNamed(context, '/ProfiloUtente');
                  },
                ),
              ),
            )
          ],
        ),
        /*
          Leggi la lista da server

        */
        body: _body(cliente));
  }

  FutureBuilder _body(Cliente cliente) {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    return FutureBuilder(
      future: retrofitService.getAppuntamentiByCliente(cliente),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          appuntamenti = snapshot.data == null
              ? []
              : (snapshot.data as List<Appuntamento>);
          appuntamenti.forEach((element) {
            dipendenti.forEach((dipendente) {
              dipendente.appuntamenti?.forEach((elementA) {
                if (element.id == elementA.id) element.dipendente = dipendente;
              });
            });
          });
          appuntamenti.forEach((appuntamento) {
            servizi.forEach((servizio) {
              servizio.appuntamenti?.forEach((elementA) {
                if (appuntamento.id == elementA.id)
                  appuntamento.servizio = servizio;
              });
            });
          });
          Provider.of<UserDataProvider>(context, listen: false)
              .setAppuntamenti(appuntamenti);
          return ListView.builder(
            itemCount: appuntamenti
                .length, //(snapshot.data as List<Appuntamento>).length,
            itemBuilder: (context, index) => BookTile(
              appuntamento: appuntamenti[index],
              appuntamenti: appuntamenti,
              callBack: (index) => _deleteAppuntamento(index),
              dipendenti: dipendenti,
              index: index,
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
  const BookTile(
      {super.key,
      required this.appuntamento,
      required this.appuntamenti,
      required this.callBack,
      required this.dipendenti,
      required this.index});
  final Appuntamento appuntamento;
  final Function(int) callBack;
  final int index;
  final List<Dipendente> dipendenti;
  final List<Appuntamento> appuntamenti;

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
                    Text(appuntamento.dipendente!.nominativo),
                    Text(appuntamento.servizio!.tipo),
                    Text(
                        '${appuntamento.date.toString().substring(0, 11)} ${appuntamento.time.hour}:${appuntamento.time.minute}0'),
                  ],
                ),
              ),
              const Expanded(child: SizedBox()),
              appuntamento.isActive()
                  ? Padding(
                      padding: const EdgeInsets.only(right: 16.0),
                      child: InkWell(
                          onTap: () async {
                            bool? res = await showDialog<bool>(
                              context: context,
                              barrierDismissible: false,
                              builder: (context) => AlertDialog(
                                title: const Text(
                                    "Vuoi davvero eliminare questo elemento?"),
                                actions: [
                                  TextButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(true),
                                    child: const Text("Si"),
                                  ),
                                  TextButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(false),
                                    child: const Text("No"),
                                  ),
                                ],
                              ),
                            );
                            if (res != null && res == true) {
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
