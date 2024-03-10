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

class RecoverPassword extends StatefulWidget {
  const RecoverPassword({super.key});

  @override
  State<RecoverPassword> createState() => _RecoverPassword();
}

class _RecoverPassword extends State<RecoverPassword> {
  final _formKey = GlobalKey<FormBuilderState>();
  bool _loading = false;
  String? codeHashed = "";

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
            'Recupera password',
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
                              name: 'Email',
                              autofocus: false,
                              textInputAction: TextInputAction.next,
                              decoration: const InputDecoration(
                                labelText: 'Inserisci la tua email',
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
                                FormBuilderValidators.email(
                                    errorText: 'Email non valida!'),
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
                              codeHashed = await retrofitService.getCodeHash(
                                  _formKey.currentState!.fields['Email']!.value
                                      .toString());
                              Provider.of<UserDataProvider>(context,
                                      listen: false)
                                  .setEmail(_formKey
                                      .currentState!.fields['Email']!.value
                                      .toString());
                              setState(() {
                                _loading = false;
                              });
                              if (codeHashed != null) {
                                Provider.of<UserDataProvider>(context,
                                        listen: false)
                                    .setCodiceRecupero(codeHashed!);
                                showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title:
                                          Text('Codice inviato con successo!'),
                                      content: Text(
                                          'Ti abbiamo inviato un codice via email che ti servirà per resettare la password. Se non la trovi controlla negli spam.'),
                                      actions: [
                                        TextButton(
                                          onPressed: () {
                                            Navigator.pushNamed(context,
                                                '/ModificaPassword'); // Chiudi il popup
                                          },
                                          child: Text('OK'),
                                        ),
                                      ],
                                    );
                                  },
                                );
                              } else {
                                showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text('Invio Fallito'),
                                      content: Text(
                                          'Si è verificato un\' durante l\'invio del codice. Riprovare.'),
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
                            "Send",
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
