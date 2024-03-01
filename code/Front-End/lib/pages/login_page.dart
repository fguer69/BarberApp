import 'package:barberapp_front_end/Model/Dipendente.dart';
import 'package:barberapp_front_end/color_schemes.g.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:provider/provider.dart';

import '../Model/Appuntamento.dart';
import '../Model/Cliente.dart';
import '../Model/Titolare.dart';
import '../Model/getImages.dart';
import '../Providers/UserDataProvider.dart';
import '../Retrofit/RetrofitService.dart';
import 'package:crypt/crypt.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormBuilderState>();
  bool isObscured = true;
  bool _loading = false;
  @override
  Widget build(BuildContext context) {
    return Material(
      child: Container(
        child: FormBuilder(
            key: _formKey,
            child: Container(
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
                      'Accedi al tuo account',
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
                    child: SingleChildScrollView(
                      padding: EdgeInsets.zero,
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
                                name: 'email',
                                autofocus: true,
                                validator: FormBuilderValidators.compose([
                                  FormBuilderValidators.required(
                                      errorText:
                                          'Il campo non può essere vuoto'),
                                  FormBuilderValidators.email(
                                      errorText: 'Formato email non valido'),
                                ]),
                                textInputAction: TextInputAction.next,
                                decoration: const InputDecoration(
                                  hintText: 'Email',
                                  border: InputBorder.none,
                                  hintStyle: TextStyle(
                                    color: Color(0xFF5C5E5F),
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
                                obscureText: isObscured,
                                validator: FormBuilderValidators.compose([
                                  FormBuilderValidators.required(
                                      errorText:
                                          'Il campo non può essere vuoto'),
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
                                    color: Color(0xFF565866),
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
                          _loading
                              ? Center(
                                  child: CircularProgressIndicator(),
                                )
                              : Padding(
                                  padding: const EdgeInsets.only(top: 20),
                                  child: FilledButton(
                                    onPressed: () async {
                                      final validation =
                                          _formKey.currentState?.validate() ??
                                              false;
                                      if (validation) {
                                        setState(() {
                                          _loading = true;
                                        });
                                        _formKey.currentState?.save();
                                        String email = _formKey.currentState!
                                            .fields['email']!.value
                                            .toString();
                                        String password = _formKey.currentState!
                                            .fields['password']!.value
                                            .toString();
                                        final passwordHash =
                                            Crypt.sha512(password);
                                        final retrofitService = RetrofitService(
                                            Dio(BaseOptions(
                                                contentType:
                                                    "application/json")));
                                        Cliente? codeU =
                                            await retrofitService.clienteLogin(
                                                email, passwordHash.toString());
                                        Dipendente? codeD =
                                            await retrofitService
                                                .dipendenteLogin(email,
                                                    passwordHash.toString());
                                        Titolare? codeT =
                                            await retrofitService.titolareLogin(
                                                email, passwordHash.toString());

                                        if (codeU != null) {
                                          setState(() {
                                            _loading = false;
                                          });
                                          Provider.of<UserDataProvider>(context,
                                                  listen: false)
                                              .setCliente(codeU!);
                                          Navigator.pushNamed(
                                              context, '/NavigationTab');
                                        } else if (codeD != null) {
                                          setState(() {
                                            _loading = false;
                                          });
                                          Provider.of<UserDataProvider>(context,
                                                  listen: false)
                                              .setDipendente(codeD!);
                                          Navigator.pushNamed(context,
                                              '/ListaPrenotazioniDipendente');
                                        } else if (codeT != null) {
                                          print(codeT);
                                          setState(() {
                                            _loading = false;
                                          });
                                          Provider.of<UserDataProvider>(context,
                                                  listen: false)
                                              .setTitolare(codeT!);
                                          Navigator.pushNamed(context,
                                              '/NavigationTabTitolare');
                                        } else {
                                          setState(() {
                                            _loading = false;
                                          });
                                          showDialog(
                                            context: context,
                                            builder: (BuildContext context) {
                                              return AlertDialog(
                                                title:
                                                    Text('Login non riuscita'),
                                                content: Text(
                                                    'Email o password errati, riprovare.'),
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
                                          borderRadius:
                                              BorderRadius.circular(10),
                                        ),
                                      ),
                                    ),
                                    child: Text(
                                      "Accedi",
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
                                ),
                          Padding(
                            padding: EdgeInsets.only(top: 10),
                            child: TextButton(
                              onPressed: () {
                                Navigator.pushNamed(
                                    context, '/SignupUser_page');
                              },
                              child: const Text(
                                'Non hai un account? Registrati',
                                style: TextStyle(
                                  color: Color(0xFF102C57),
                                  fontSize: 18,
                                  fontStyle: FontStyle.italic,
                                  fontFamily: 'ABeeZee',
                                  fontWeight: FontWeight.w400,
                                  height: 0.08,
                                  decoration: TextDecoration.underline,
                                  decorationColor: Colors.blue,
                                  decorationStyle: TextDecorationStyle.double,
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            )),
      ),
    );
  }
}
