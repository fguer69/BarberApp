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
import 'package:crypt/crypt.dart';
import 'package:crypto/crypto.dart';
import 'dart:convert';

class SignupUser_page extends StatefulWidget {
  const SignupUser_page({super.key});

  @override
  State<SignupUser_page> createState() => _SignupUser_pageState();
}

//FutureBuilder costruisce qualcosa con ei dati che sa che arriveranno lo stream è un flusso constante di dati ad esempio app di treding
class _SignupUser_pageState extends State<SignupUser_page> {
  Future<int> _checkEmail(String email) async {
    int code;
    final retrofitService =
        RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    code = await retrofitService.checkEmail(email);
    return code;
  }

  final _formKey = GlobalKey<FormBuilderState>();
  bool _loading = false;
  late int codeEmail;
  late int codeLogin;
  @override
  Widget build(BuildContext context) {
    return Material(
      child: FormBuilder(
          key: _formKey,
          child: SingleChildScrollView(
            child: Container(
              height: 1000,
              alignment: Alignment.center,
              color: const Color(0xffDAC0A3),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                verticalDirection: VerticalDirection.down,
                children: [
                  Padding(
                    padding: const EdgeInsets.fromLTRB(0, 60, 0, 0),
                    child: Image.asset(GetImages.images["default"]!),
                  ),
                  const Padding(
                    padding: EdgeInsets.only(top: 50),
                    child: Text(
                      'Crea il tuo account',
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        decoration: TextDecoration.none,
                        color: Color(0xFF102C57),
                        fontSize: 30,
                        fontFamily: 'ABeeZee',
                        fontWeight: FontWeight.w400,
                        height: 0.05,
                      ),
                    ),
                  ),
                  Align(
                    alignment: Alignment.center,
                    child: Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 30),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 63,
                            decoration: ShapeDecoration(
                              color: Color(0xFFF8F0E5),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'nome',
                              autofocus: true,
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                hintText: 'Nome',
                                border: InputBorder.none,
                                hintStyle: TextStyle(
                                  color: Color(0xFF102C57),
                                  fontSize: 16,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                ),
                              ),
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                              ]),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 30),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 63,
                            decoration: ShapeDecoration(
                              color: Color(0xFFF8F0E5),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'cognome',
                              autofocus: true,
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                              ]),
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                hintText: 'Cognome',
                                border: InputBorder.none,
                                hintStyle: TextStyle(
                                  color: Color(0xFF102C57),
                                  fontSize: 16,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                ),
                              ),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 30),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 63,
                            decoration: ShapeDecoration(
                              color: Color(0xFFF8F0E5),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'email',
                              autofocus: true,
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
                                hintText: 'Email',
                                border: InputBorder.none,
                                hintStyle: TextStyle(
                                  color: Color(0xFF102C57),
                                  fontSize: 16,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                ),
                              ),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 30),
                          child: Container(
                            padding: const EdgeInsets.only(left: 10, top: 6),
                            width: 316,
                            height: 63,
                            decoration: ShapeDecoration(
                              color: Color(0xFFF8F0E5),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            child: FormBuilderTextField(
                              name: 'password',
                              autofocus: true,
                              obscureText: true,
                              validator: FormBuilderValidators.compose([
                                FormBuilderValidators.required(
                                    errorText: 'Il campo non può essere vuoto'),
                                FormBuilderValidators.minLength(8,
                                    errorText: 'Password troppo corta'),
                                FormBuilderValidators.maxLength(16,
                                    errorText: 'Password troppo lunga'),
                              ]),
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                hintText: 'Password',
                                border: InputBorder.none,
                                hintStyle: TextStyle(
                                  color: Color(0xFF102C57),
                                  fontSize: 16,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                ),
                              ),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 15, left: 65),
                          child: FormBuilderCheckbox(
                            name: 'dirittiCheckBox',
                            validator: FormBuilderValidators.compose([
                              FormBuilderValidators.required(),
                              FormBuilderValidators.equal(true,
                                  errorText:
                                      'Devi accettare i termini e le condizioni'),
                            ]),
                            initialValue: false,
                            decoration: const InputDecoration(
                              border: InputBorder.none,
                              hoverColor: Color(0xFF102C57),
                            ),
                            title: const Text(
                              "Accetto termini & condizioni",
                              style: TextStyle(
                                color: Color(0xFF102C57),
                                fontSize: 16,
                                fontStyle: FontStyle.italic,
                                fontFamily: 'ABeeZee',
                                fontWeight: FontWeight.w400,
                                height: 0.09,
                              ),
                            ),
                          ),
                        ),
                        _loading
                            ? Center(
                                child: CircularProgressIndicator(),
                              )
                            : FilledButton(
                                onPressed: () async {
                                  int ControlloMail = await _checkEmail(_formKey
                                      .currentState!.fields['email']!.value
                                      .toString());
                                  setState(() {
                                    codeEmail = ControlloMail;
                                  });
                                  final validation =
                                      _formKey.currentState?.validate() ??
                                          false;
                                  if (validation) {
                                    setState(() {
                                      _loading = true;
                                    });
                                    _formKey.currentState?.save();
                                    String nome = _formKey
                                        .currentState!.fields['nome']!.value
                                        .toString();
                                    var appuntamenti = <Appuntamento>[];
                                    String password = _formKey
                                        .currentState!.fields['password']!.value
                                        .toString();
                                    var bytes = utf8.encode(password);
                                    var digest = sha512.convert(bytes);
                                    final passwordHash = digest.toString();
                                    Cliente cliente = Cliente(
                                        0,
                                        _formKey
                                            .currentState!.fields['nome']!.value
                                            .toString(),
                                        _formKey.currentState!
                                            .fields['cognome']!.value
                                            .toString(),
                                        _formKey.currentState!.fields['email']!
                                            .value
                                            .toString(),
                                        passwordHash,
                                        []);
                                    Provider.of<UserDataProvider>(context,
                                            listen: false)
                                        .setCliente(cliente);
                                    final retrofitService = RetrofitService(Dio(
                                        BaseOptions(
                                            contentType: "application/json")));
                                    int ControlloLogin = await retrofitService
                                        .saveCliente(cliente);
                                    setState(() {
                                      codeLogin = ControlloLogin;
                                    });

                                    if (ControlloLogin == 200) {
                                      setState(() {
                                        _loading = false;
                                      });
                                      showDialog(
                                        context: context,
                                        barrierDismissible: false,
                                        builder: (BuildContext context) {
                                          return AlertDialog(
                                            title: Text(
                                                'Registrazione avvenuta con successo!'),
                                            content: Text(
                                                'Premere il tasto OK per accedere al proprio account!'),
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
                                            title: Text(
                                                'Registrazione non riuscita'),
                                            content: Text(
                                                'Si è verificato un problema durante la registrazione. Per favore, riprova.'),
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
                                    Size(316, 63),
                                  ),
                                  shape: MaterialStatePropertyAll(
                                    RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(10),
                                    ),
                                  ),
                                ),
                                child: Text(
                                  "Conferma",
                                  style: TextStyle(
                                    color: Colors.white
                                        .withOpacity(0.8999999761581421),
                                    fontSize: 20,
                                    fontStyle: FontStyle.italic,
                                    fontFamily: 'ABeeZee',
                                    fontWeight: FontWeight.w400,
                                    height: 0.07,
                                  ),
                                ),
                              ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          )),
    );
  }
}
