import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:flutter/widgets.dart';
import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:provider/provider.dart';
import '../Model/Cliente.dart';
import '../Retrofit/RetrofitService.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:crypt/crypt.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:provider/provider.dart';
import '../Model/Cliente.dart';
import '../Model/Dipendente.dart';
import '../Retrofit/RetrofitService.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import 'package:crypto/crypto.dart';
import 'dart:convert';

class ModificaPassword extends StatefulWidget {
  const ModificaPassword({super.key});

  @override
  State<ModificaPassword> createState() => _ModificaPassword();
}

class _ModificaPassword extends State<ModificaPassword> {
  final _formKey = GlobalKey<FormBuilderState>();
  bool _loading = false;
  late String codeHashed;
  late String code;
  late var utente;
  late String password1 = "";
  late String password2 = "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          leading: IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: Icon(Icons.arrow_back),
          ),
          backgroundColor: Colors.white,
          centerTitle: true,
          title: const Text(
            'Modifica password',
            style: TextStyle(
              color: Color(0xFF23303B),
              fontSize: 22,
              fontStyle: FontStyle.italic,
              fontFamily: 'ABeeZee',
              fontWeight: FontWeight.w400,
              height: 0,
            ),
          ),
        ),
        backgroundColor: Colors.white,
        body: Align(
          alignment: Alignment.topCenter,
          child: SingleChildScrollView(
            padding: EdgeInsets.zero,
            child: Column(
              verticalDirection: VerticalDirection.down,
              children: [
                FormBuilder(
                    key: _formKey,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      verticalDirection: VerticalDirection.down,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 10.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 63,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'Codice',
                              autofocus: false,
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText:
                                    'Inserisci il codice inviato per email.',
                                labelStyle: TextStyle(
                                  color: Color(0xFF23303B),
                                  fontSize: 19,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.08,
                                ),
                                border: InputBorder.none,
                              ),
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                                FormBuilderValidators.maxLength(6,
                                    errorText: 'Codice non valido'),
                              ]),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 10.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 70,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'Password',
                              autofocus: false,
                              obscureText: true,
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Inserisci la nuova password',
                                labelStyle: TextStyle(
                                  color: Color(0xFF23303B),
                                  fontSize: 19,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.08,
                                ),
                                border: InputBorder.none,
                              ),
                              validator: FormBuilderValidators.compose([
                                (value) {
                                  if (value != password2) {
                                    return 'Password non corrispondenti';
                                  }
                                },
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                                FormBuilderValidators.minLength(8,
                                    errorText: 'Password troppo corta'),
                                FormBuilderValidators.maxLength(16,
                                    errorText: 'Password troppo lunga'),
                              ]),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 10.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 70,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'RepeatPassword',
                              obscureText: true,
                              autofocus: false,
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Ripeti la password',
                                labelStyle: TextStyle(
                                  color: Color(0xFF23303B),
                                  fontSize: 19,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.08,
                                ),
                                border: InputBorder.none,
                              ),
                              validator: FormBuilderValidators.compose([
                                (value) {
                                  if (value != password1) {
                                    return 'Password non corrispondenti';
                                  }
                                },
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                                FormBuilderValidators.minLength(8,
                                    errorText: 'Password troppo corta'),
                                FormBuilderValidators.maxLength(16,
                                    errorText: 'Password troppo lunga'),
                              ]),
                            ),
                          ),
                        ),
                      ],
                    )),
                _loading
                    ? Center(
                        child: CircularProgressIndicator(),
                      )
                    : Padding(
                        padding: EdgeInsets.only(top: 20),
                        child: FilledButton(
                          onPressed: () async {
                            setState(() {
                              password1 = _formKey
                                  .currentState!.fields['Password']!.value
                                  .toString();
                              password2 = _formKey
                                  .currentState!.fields['RepeatPassword']!.value
                                  .toString();
                            });
                            final validation =
                                _formKey.currentState?.validate();
                            if (validation!) {
                              setState(() {
                                _loading = true;
                              });
                              _formKey.currentState?.save();

                              final retrofitService = RetrofitService(Dio(
                                  BaseOptions(
                                      contentType: "application/json")));
                              Cliente? cliente =
                                  await retrofitService.getClienteByEmail(
                                      Provider.of<UserDataProvider>(context,
                                              listen: false)
                                          .email);
                              Dipendente? dipendente =
                                  await retrofitService.getDipendenteByEmail(
                                      Provider.of<UserDataProvider>(context,
                                              listen: false)
                                          .email);
                              Titolare? titolare =
                                  await retrofitService.getTitolareByEmail(
                                      Provider.of<UserDataProvider>(context,
                                              listen: false)
                                          .email);
                              if (cliente != null) {
                                setState(() {
                                  utente = cliente;
                                });
                              } else if (dipendente != null) {
                                setState(() {
                                  utente = dipendente;
                                });
                              } else if (titolare != null) {
                                setState(() {
                                  utente = titolare;
                                });
                              } else
                                setState(() {
                                  utente = null;
                                });
                              setState(() {
                                codeHashed = Provider.of<UserDataProvider>(
                                        context,
                                        listen: false)
                                    .codiceRecupero;
                              });
                              var bytes = utf8.encode(_formKey
                                  .currentState!.fields['Codice']!.value
                                  .toString());
                              var digest = sha512.convert(bytes);
                              setState(() {
                                code = digest.toString();
                              });
                              if (codeHashed == code) {
                                if (utente is Cliente) {
                                  var bytes = utf8.encode(password1);
                                  var digest = sha512.convert(bytes);
                                  final passwordHash = digest.toString();
                                  Cliente client = utente as Cliente;
                                  client.password = passwordHash;
                                  int code = await retrofitService
                                      .updateCliente(client);
                                  if (code != 200) {
                                    setState(() {
                                      _loading = false;
                                    });
                                    showDialog(
                                      context: context,
                                      barrierDismissible: false,
                                      builder: (BuildContext context) {
                                        return AlertDialog(
                                          title: Text('Modifica Fallita'),
                                          content: Text(
                                              'Si è verificato un\' durante la modifica. Riprovare.'),
                                          actions: [
                                            TextButton(
                                              onPressed: () {
                                                Navigator.pop(
                                                    context); // Chiudi il popup
                                              },
                                              child: Text('OK'),
                                            ),
                                          ],
                                        );
                                      },
                                    );
                                  }
                                } else if (utente is Dipendente) {
                                  var bytes = utf8.encode(password1);
                                  var digest = sha512.convert(bytes);
                                  final passwordHash = digest.toString();
                                  Dipendente employee = utente as Dipendente;
                                  employee.password = passwordHash;
                                  int code = await retrofitService
                                      .updateDipendente(employee);
                                  if (code != 200) {
                                    setState(() {
                                      _loading = false;
                                    });
                                    showDialog(
                                      context: context,
                                      barrierDismissible: false,
                                      builder: (BuildContext context) {
                                        return AlertDialog(
                                          title: Text('Modifica Fallita'),
                                          content: Text(
                                              'Si è verificato un\' durante la modifica. Riprovare.'),
                                          actions: [
                                            TextButton(
                                              onPressed: () {
                                                Navigator.pop(
                                                    context); // Chiudi il popup
                                              },
                                              child: Text('OK'),
                                            ),
                                          ],
                                        );
                                      },
                                    );
                                  }
                                } else if (utente is Titolare) {
                                  var bytes = utf8.encode(password1);
                                  var digest = sha512.convert(bytes);
                                  final passwordHash = digest.toString();
                                  Titolare tito = utente as Titolare;
                                  tito.password = passwordHash;
                                  int code = await retrofitService
                                      .updateTitolare(tito);
                                  if (code != 200) {
                                    setState(() {
                                      _loading = false;
                                    });
                                    showDialog(
                                      context: context,
                                      barrierDismissible: false,
                                      builder: (BuildContext context) {
                                        return AlertDialog(
                                          title: Text('Modifica Fallita'),
                                          content: Text(
                                              'Si è verificato un\' durante la modifica. Riprovare.'),
                                          actions: [
                                            TextButton(
                                              onPressed: () {
                                                Navigator.pop(
                                                    context); // Chiudi il popup
                                              },
                                              child: Text('OK'),
                                            ),
                                          ],
                                        );
                                      },
                                    );
                                  }
                                } else
                                  utente = null;
                                setState(() {
                                  _loading = false;
                                });
                                showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text('Reset password avvenuto!'),
                                      content: Text(
                                          'La password è stata resettata con successo!.'),
                                      actions: [
                                        TextButton(
                                          onPressed: () {
                                            Navigator.pushNamed(context,
                                                '/login_page'); // Chiudi il popup
                                          },
                                          child: Text('OK'),
                                        ),
                                      ],
                                    );
                                  },
                                );
                              } else {
                                setState(() {
                                  _loading = false;
                                });
                                showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text('Codice invalido!'),
                                      content: Text(
                                          'Il codice di verifica è errato! Riprovare.'),
                                      actions: [
                                        TextButton(
                                          onPressed: () {
                                            Navigator.pop(
                                                context); // Chiudi il popup
                                          },
                                          child: Text('OK'),
                                        ),
                                      ],
                                    );
                                  },
                                );
                              }
                            } else {
                              //_formKey.currentState!.reset();
                            }
                          },
                          style: ButtonStyle(
                            backgroundColor:
                                const MaterialStatePropertyAll<Color>(
                                    Color(0xFF102C57)),
                            padding: const MaterialStatePropertyAll<
                                    EdgeInsetsGeometry>(
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
                            "Reset",
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 24,
                              fontFamily: 'Inter',
                              fontWeight: FontWeight.w600,
                              height: 0.08,
                            ),
                          ),
                        ),
                      ),
              ],
            ),
          ),
        ));
  }
}
