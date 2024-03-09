import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/Retrofit/RetrofitService.dart';
import 'package:barberapp_front_end/utils/navigation_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../Model/Dipendente.dart';
import '../RouteGenerator.dart';
import '../color_schemes.g.dart';

class BarbersAvaible extends StatefulWidget {
  const BarbersAvaible({super.key});

  @override
  State<BarbersAvaible> createState() => BarbersAvaibleState();
}

class BarbersAvaibleState extends State<BarbersAvaible> {
  late Appuntamento appuntamento =
      Provider.of<UserDataProvider>(context, listen: true).appuntamento;
  late List<Dipendente> dipendentiDisponibili;
  Dipendente? _selectedDipendente;

  bool _loading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          leading: IconButton(
            style: const ButtonStyle(
              backgroundColor: MaterialStatePropertyAll(Color(0x3FA4A9AE)),
            ),
            onPressed: () {
              Navigator.of(context).pop();
            },
            icon: const Icon(Icons.arrow_back),
          ),
          centerTitle: true,
          title: const Text("Prenotazione",
              style: TextStyle(fontStyle: FontStyle.italic)),
          actions: const [
            Padding(
              padding: EdgeInsets.only(right: 24.0),
              child: CircleAvatar(
                child: IconButton(
                  icon: Icon(Icons.person),
                  onPressed: null,
                ),
              ),
            )
          ],
        ),
        body: _body());
  }

  FutureBuilder _body() {
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));

    return FutureBuilder(
      future:
          retrofitService.getFreeEmployee(appuntamento.date, appuntamento.time),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          if (snapshot.data == null || snapshot.data == [])
            dipendentiDisponibili = [];
          else
            dipendentiDisponibili = snapshot.data!;
          return _dipendentiDisponibili(dipendentiDisponibili);
        } else {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }

  Widget _dipendentiDisponibili(List<Dipendente> dipendentiDisponibili) {
    return Column(
      children: [
        const Text(
          'Barbers avaiable',
          textAlign: TextAlign.left,
          style: TextStyle(
            color: Color(0xFF23303B),
            fontSize: 20,
            fontStyle: FontStyle.italic,
            fontFamily: 'ABeeZee',
            fontWeight: FontWeight.w400,
            height: 0,
            letterSpacing: -0.60,
          ),
        ),
        Container(
          height: 600,
          child: ListView.builder(
            itemCount: dipendentiDisponibili.length,
            itemBuilder: (context, index) => DipendentiDisponibiliTile(
              dipendente: dipendentiDisponibili[index],
              isSelected:
                  (dipendentiDisponibili[index].id == _selectedDipendente?.id)
                      ? true
                      : false,
              onTap: () {
                setState(() {
                  _selectedDipendente = dipendentiDisponibili[index];
                  print(dipendentiDisponibili[index] == _selectedDipendente);
                  //Appuntamento appuntamento = Provider.of<UserDataProvider>(context, listen: false).appuntamento;
                  //appuntamento.dipendente = _selectedDipendente;
                  //Navigator.pushNamed(context, '/reservationsPage');
                });
              },
            ),
          ),
        ),
        _loading
            ? Center(
                child: CircularProgressIndicator(),
              )
            : FilledButton(
                onPressed: () async {
                  if (_selectedDipendente == null) {
                    showDialog(
                      context: context,
                      barrierDismissible: false,
                      builder: (BuildContext context) {
                        return AlertDialog(
                          title: Text('Prenotazione non riuscita'),
                          content: Text('Seleziona un barbiere!'),
                          actions: [
                            TextButton(
                              onPressed: () {
                                Navigator.pop(context); // Chiudi il popup
                              },
                              child: Text('OK'),
                            ),
                          ],
                        );
                      },
                    );
                  } else {
                    setState(() {
                      _loading = true;
                    });
                    final retrofitService = RetrofitService(
                        Dio(BaseOptions(contentType: "application/json")));
                    appuntamento.cliente =
                        Provider.of<UserDataProvider>(context, listen: false)
                            .cliente;
                    appuntamento.dipendente = _selectedDipendente;
                    print(appuntamento.toJson());
                    int code =
                        await retrofitService.saveAppuntamento(appuntamento);
                    if (code == 200) {
                      Provider.of<UserDataProvider>(context, listen: false)
                          .setAppuntamento(appuntamento);
                      Provider.of<UserDataProvider>(context, listen: false)
                          .addAppuntamenti(appuntamento);
                      setState(() {
                        _loading = false;
                      });
                      Navigator.pushNamed(context, '/prenotazioneEffettuata');
                    } else {
                      setState(() {
                        _loading = false;
                      });
                      showDialog(
                        context: context,
                        barrierDismissible: false,
                        builder: (BuildContext context) {
                          return AlertDialog(
                            title: Text('Prenotazione non riuscita'),
                            content: Text(
                                'Mi dispiace, ma hai già effettuato una prenotazione in questa giornata.'),
                            actions: [
                              TextButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                  Navigator.pushNamed(context,
                                      '/reservationsPage'); // Chiudi il popup
                                },
                                child: Text('OK'),
                              ),
                            ],
                          );
                        },
                      );
                    }
                  }
                },
                style: ButtonStyle(
                  backgroundColor:
                      const MaterialStatePropertyAll<Color>(Color(0xFF102C57)),
                  padding: const MaterialStatePropertyAll<EdgeInsetsGeometry>(
                      EdgeInsets.fromLTRB(0, 0, 0, 0)),
                  fixedSize: const MaterialStatePropertyAll(
                    Size(226, 74),
                  ),
                  shape: MaterialStatePropertyAll(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                  ),
                ),
                child: const Text(
                  "Conferma",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 24,
                    fontFamily: 'Inter',
                    fontWeight: FontWeight.w600,
                    height: 0.06,
                  ),
                ),
              ),
      ],
    );
  }
}

class DipendentiDisponibiliTile extends StatelessWidget {
  DipendentiDisponibiliTile({
    Key? key,
    required this.dipendente,
    required this.isSelected,
    required this.onTap,
  }) : super(key: key);

  Dipendente dipendente;
  bool isSelected;
  VoidCallback onTap;
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      child: AnimatedContainer(
        duration: const Duration(seconds: 1),
        color: isSelected ? Colors.blue.withOpacity(0.5) : null,
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Image.asset(
                    GetImages.images["default"]!,
                    width: 128,
                    height: 64,
                    fit: BoxFit.fitWidth,
                  ),
                  Padding(
                    padding: const EdgeInsets.only(left: 8.0),
                    child: Text('${dipendente.nome} ${dipendente.cognome}'),
                  )
                ],
              ),
            ),
            const Divider()
          ],
        ),
      ),
    );
  }
}
