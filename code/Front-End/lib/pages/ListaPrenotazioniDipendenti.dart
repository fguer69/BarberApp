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
import '../Model/Servizio.dart';

class ListaPrenotazioniDipendenti extends StatefulWidget {
  const ListaPrenotazioniDipendenti({super.key});

  @override
  State<ListaPrenotazioniDipendenti> createState() =>
      _ListaPrenotazioniDipendentiState();
}

class _ListaPrenotazioniDipendentiState
    extends State<ListaPrenotazioniDipendenti> {
  late Dipendente dipendente =
      Provider.of<UserDataProvider>(context, listen: true).dipendente;
  late List<Appuntamento> appuntamenti =
      Provider.of<UserDataProvider>(context, listen: true).appuntamenti;
  late List<Cliente> clienti =
      Provider.of<UserDataProvider>(context, listen: true).clienti;
  late List<Servizio> servizi =
      Provider.of<UserDataProvider>(context, listen: true).servizi;
  @override
  void initState() {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    Future<List<Cliente>> futureClienti = retrofitService.getPosts();
    futureClienti.then((clienti) => {
          Provider.of<UserDataProvider>(context, listen: false)
              .setClienti(clienti)
        });
    Future<List<Servizio>> futureServizi = retrofitService.getServices();
    futureServizi.then((servizi) => {
          Provider.of<UserDataProvider>(context, listen: false)
              .setServizi(servizi)
        });
    super.initState();
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
              padding: EdgeInsets.only(right: 24.0),
              child: CircleAvatar(
                child: IconButton(
                  icon: Icon(Icons.person),
                  onPressed: () {
                    Navigator.pushNamed(context, '/ProfiloDipendente');
                  },
                ),
              ),
            )
          ],
        ),
        /*
          Leggi la lista da server

        */
        body: _body(dipendente));
  }

  FutureBuilder _body(Dipendente dipendente) {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    return FutureBuilder(
      future: retrofitService.getAppuntamentiByDipendente(dipendente),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          appuntamenti = snapshot.data == null
              ? []
              : (snapshot.data as List<Appuntamento>);
          appuntamenti.forEach((element) {
            clienti.forEach((cliente) {
              cliente.appuntamenti?.forEach((elementA) {
                if (element.id == elementA.id) element.cliente = cliente;
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
              clienti: clienti,
              callBack: (index) => setState(() =>
                  retrofitService.deleteAppuntamento(appuntamenti[index])),
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
      required this.clienti,
      required this.callBack,
      required this.index});
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
                    Text(appuntamento.servizio!.tipo),
                    Text(
                        '${appuntamento.date.toString().substring(0, 11)} ${appuntamento.time.hour}:${appuntamento.time.minute}0'),
                  ],
                ),
              ),
            ],
          ),
        ),
        const Divider()
      ],
    );
  }
}
