import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
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
import 'package:crypt/crypt.dart';
import 'package:crypto/crypto.dart';
import 'dart:convert';

class ProfiloUtente extends StatefulWidget {
  const ProfiloUtente({super.key});

  @override
  State<ProfiloUtente> createState() => _ProfiloUtenteState();
}

class _ProfiloUtenteState extends State<ProfiloUtente> {
  final _formKey = GlobalKey<FormBuilderState>();
  Future<int> _checkEmail(String email) async {
    int code;
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    code = await retrofitService.checkEmail(email);
    return code;
  }

  bool _loading = false;
  late int codeEmail;
  late Cliente cliente =
      Provider.of<UserDataProvider>(context, listen: true).cliente;

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
            'Profilo',
            style: TextStyle(
              color: Color(0xFF23303B),
              fontSize: 22,
              fontStyle: FontStyle.italic,
              fontFamily: 'ABeeZee',
              fontWeight: FontWeight.w400,
              height: 0,
            ),
          ),
          actions: [
            _loading
                ? Center(
                    child: CircularProgressIndicator(),
                  )
                : Padding(
                    padding: EdgeInsets.only(right: 24.0),
                    child: CircleAvatar(
                      backgroundColor: Color(0x3FA4A9AE),
                      child: PopupMenuButton<int>(
                        onSelected: (int value) {
                          if (value == 1) {
                            Navigator.pushNamed(context, '/login_page');
                          } else if (value == 2) {
                            showDialog(
                              context: context,
                              barrierDismissible: false,
                              builder: (context) {
                                return AlertDialog(
                                  title: Text(
                                      '${cliente.nome} ${cliente.cognome}'),
                                  content: Text('Cancella account'),
                                  actions: [
                                    TextButton(
                                      onPressed: () async {
                                        final retrofitService = RetrofitService(
                                            Dio(BaseOptions(
                                                contentType:
                                                    "application/json")));
                                        setState(() {
                                          _loading = true;
                                        });
                                        int code = await retrofitService
                                            .deleteCliente(cliente);
                                        if (code == 200) {
                                          setState(() {
                                            _loading = false;
                                          });
                                          Navigator.pushNamed(
                                              context, '/login_page');
                                        }
                                      },
                                      child: Text('SI'),
                                    ),
                                    TextButton(
                                      onPressed: () {
                                        Navigator.pop(
                                            context); // Chiudi il popup
                                      },
                                      child: Text('NO'),
                                    ),
                                  ],
                                );
                              },
                            );
                          }
                        },
                        itemBuilder: (BuildContext context) =>
                            <PopupMenuEntry<int>>[
                          const PopupMenuItem<int>(
                            value: 1,
                            child: Text(
                              'Log out',
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                color: Colors.black,
                                fontSize: 20,
                                fontFamily: 'ABeeZee',
                                fontWeight: FontWeight.w400,
                                height: 0,
                              ),
                            ),
                          ),
                          const PopupMenuItem<int>(
                            value: 2,
                            child: Text(
                              'Cancella account',
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                color: Colors.black,
                                fontSize: 20,
                                fontFamily: 'ABeeZee',
                                fontWeight: FontWeight.w400,
                                height: 0,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  )
          ],
        ),
        backgroundColor: Colors.white,
        body: Align(
          alignment: Alignment.topCenter,
          child: SingleChildScrollView(
            padding: EdgeInsets.zero,
            child: Column(
              verticalDirection: VerticalDirection.down,
              children: [
                Image.asset(GetImages.images["avatar"]!),
                Text(
                  cliente.nome,
                  style: const TextStyle(
                    color: Color(0xFF23303B),
                    fontSize: 22,
                    fontStyle: FontStyle.italic,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0,
                  ),
                ),
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
                            height: 75,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'nome',
                              autofocus: false,
                              textInputAction: TextInputAction.next,
                              initialValue: cliente.nome,
                              decoration: const InputDecoration(
                                labelText: 'Nome',
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
                                FormBuilderValidators.minLength(1)
                              ]),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 20.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 75,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'cognome',
                              autofocus: false,
                              initialValue: cliente.cognome,
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                              ]),
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Cognome',
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
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 20.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 75,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'email',
                              autofocus: false,
                              initialValue: cliente.email,
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                                FormBuilderValidators.email(
                                    errorText: 'Formato email non valido'),
                                (value) {
                                  if (codeEmail == 500) {
                                    return 'Email già registrata';
                                  }
                                }
                              ]),
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Email',
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
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 20.0),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 75,
                            decoration: ShapeDecoration(
                              color: Color(0x26A4A9AE),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'password',
                              autofocus: false,
                              obscureText: true,
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.maxLength(16,
                                    errorText: 'Password troppo lunga'),
                                (value) {
                                  if (value.toString().length >= 1 &&
                                      value.toString().length <= 8) {
                                    return 'Password troppo corta';
                                  }
                                }
                              ]),
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Password',
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
                            ),
                          ),
                        ),
                      ],
                    )),
                Padding(
                  padding: EdgeInsets.only(top: 20),
                  child: FilledButton(
                    onPressed: () async {
                      setState(() {
                        _loading = true;
                      });
                      if (_formKey.currentState!.fields['email']!.value
                              .toString() ==
                          Provider.of<UserDataProvider>(context, listen: false)
                              .cliente
                              .email) {
                        setState(() {
                          _loading = false;
                          codeEmail = 200;
                        });
                      } else if (_formKey.currentState!.fields['email']!.value
                          .toString()
                          .isEmpty) {
                        setState(() {
                          _loading = false;
                        });
                      } else {
                        int ControlloMail = await _checkEmail(_formKey
                            .currentState!.fields['email']!.value
                            .toString());
                        setState(() {
                          _loading = false;
                          codeEmail = ControlloMail;
                        });
                      }
                      /* if (_formKey.currentState!.fields['password']?.value !=
                              null &&
                          _formKey.currentState!.fields['password']!.value
                              .toString()
                              .isNotEmpty &&
                          _formKey.currentState!.fields['password']!.value
                                  .toString()
                                  .length <
                              8) {
                        setState(() {
                          _formKey.currentState?.fields['password']!
                              .invalidate("Password troppo corta!");
                          _formKey.currentState?.setState(() {});
                        });
                      }*/

                      final validation =
                          _formKey.currentState?.validate() ?? false;

                      if (validation!) {
                        setState(() {
                          _loading = true;
                        });
                        _formKey.currentState!.save();
                        String passwordFinal;
                        String? passwordHash = "";
                        String? password = "";
                        if (_formKey.currentState!.fields['password']!.value
                                .toString()
                                .isNotEmpty &&
                            _formKey.currentState!.fields['password']?.value !=
                                null) {
                          password = _formKey
                              .currentState!.fields['password']!.value
                              .toString();
                          print("${password} + 1");
                        } else {
                          print("${password} + 2");
                          password = "";
                        }
                        if (password !=
                                Provider.of<UserDataProvider>(context,
                                        listen: false)
                                    .cliente
                                    .password &&
                            password.isNotEmpty &&
                            password != null) {
                          print("${password} + 3");
                          var bytes = utf8.encode(password);
                          var digest = sha512.convert(bytes);
                          passwordHash = digest.toString();
                        } else {
                          print("${password} + 4");
                          passwordHash = "";
                        }
                        if (passwordHash.isEmpty || passwordHash == null) {
                          print("${password} + 5");
                          passwordFinal = Provider.of<UserDataProvider>(context,
                                  listen: false)
                              .cliente
                              .password;
                        } else {
                          print("${password} + 6");
                          passwordFinal = passwordHash;
                        }
                        Provider.of<UserDataProvider>(context, listen: false)
                                .cliente
                                .nome =
                            _formKey.currentState!.fields['nome']!.value
                                .toString();
                        Provider.of<UserDataProvider>(context, listen: false)
                                .cliente
                                .cognome =
                            _formKey.currentState!.fields['cognome']!.value
                                .toString();
                        Provider.of<UserDataProvider>(context, listen: false)
                                .cliente
                                .email =
                            _formKey.currentState!.fields['email']!.value
                                .toString();
                        Provider.of<UserDataProvider>(context, listen: false)
                            .cliente
                            .password = passwordFinal;
                        final retrofitService = RetrofitService(
                            Dio(BaseOptions(contentType: "application/json")));
                        int ControlloUpdate =
                            await retrofitService.updateCliente(
                                Provider.of<UserDataProvider>(context,
                                        listen: false)
                                    .cliente);
                        if (ControlloUpdate == 200) {
                          setState(() {
                            _loading = false;
                          });
                          showDialog(
                            context: context,
                            barrierDismissible: false,
                            builder: (BuildContext context) {
                              return AlertDialog(
                                title: Text('Update'),
                                content:
                                    Text('Profilo aggiornato con successo!'),
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
                            _loading = false;
                          });
                          showDialog(
                            context: context,
                            barrierDismissible: false,
                            builder: (BuildContext context) {
                              return AlertDialog(
                                title: Text('Update Failed'),
                                content: Text(
                                    'Si è verificato un problema durante l\'aggiornamento. Per favore, riprova.'),
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
                        }
                      } else {
                        //_formKey.currentState!.reset();
                      }
                    },
                    style: ButtonStyle(
                      backgroundColor: const MaterialStatePropertyAll<Color>(
                          Color(0xFF102C57)),
                      padding:
                          const MaterialStatePropertyAll<EdgeInsetsGeometry>(
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
                      "Salva",
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
