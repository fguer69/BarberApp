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

class ListaDipendenti extends StatefulWidget {
  const ListaDipendenti({super.key});

  @override
  State<ListaDipendenti> createState() => _ListaDipendentiState();
}

class _ListaDipendentiState extends State<ListaDipendenti> {
  late Dipendente dipendente;
  late List<Dipendente> dipendentiList =
      Provider.of<UserDataProvider>(context, listen: true).dipendenti;

  Future<void> deleteDipendente(int index) async {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    final int responseCode =
        await retrofitService.deleteDipendente(dipendentiList[index]);
    if (responseCode == 200) {
      setState(() {
        dipendentiList.removeAt(index); // Rimuovi il dipendente dalla lista
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
            "Lista dipendenti",
            style: TextStyle(fontStyle: FontStyle.italic),
          ),
          leading: IconButton(
            onPressed: () {
              Navigator.pushNamed(context, '/SignupDipendente_page');
            },
            icon: Icon(Icons.add),
          ),
          actions: [
            Padding(
              padding: EdgeInsets.only(right: 24.0),
              child: CircleAvatar(
                child: IconButton(
                  icon: Icon(Icons.person),
                  onPressed: () {
                    Navigator.pushNamed(context, '/ProfiloTitolare');
                  },
                ),
              ),
            )
          ],
        ),
        /*
          Leggi la lista da server

        */
        body: _body());
  }

  FutureBuilder _body() {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    return FutureBuilder(
      future: retrofitService.dipendentiGetAll(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          dipendentiList =
              snapshot.data == null ? [] : (snapshot.data as List<Dipendente>);
          Provider.of<UserDataProvider>(context, listen: true)
              .setDipendenti(dipendentiList);
          return ListView.builder(
            itemCount: dipendentiList
                .length, //(snapshot.data as List<Appuntamento>).length,
            itemBuilder: (context, index) => BookTile(
              dipendente: dipendentiList[index],
              callBack: (index) => deleteDipendente(index),
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
      required this.dipendente,
      required this.callBack,
      required this.index});
  final Dipendente dipendente;
  final Function(int) callBack;
  final int index;

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
                    Text('${dipendente.nome} ${dipendente.cognome}'),
                    Text('${dipendente.email}'),
                  ],
                ),
              ),
              const Expanded(child: SizedBox()),
              Padding(
                padding: const EdgeInsets.only(right: 16.0),
                child: InkWell(
                    onTap: () async {
                      bool? res = await showDialog<bool>(
                        barrierDismissible: false,
                        context: context,
                        builder: (context) => AlertDialog(
                          title: const Text(
                              "Vuoi davvero eliminare questo account?"),
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
                      if (res != null && res == true) {
                        callBack(index);
                      }
                    },
                    child: const Icon(Icons.delete_outline)),
              ),
            ],
          ),
        ),
        const Divider()
      ],
    );
  }
}
