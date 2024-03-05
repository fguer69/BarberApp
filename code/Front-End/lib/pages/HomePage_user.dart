import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:provider/provider.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import '../Model/Cliente.dart';
import '../Retrofit/RetrofitService.dart';

class HomePage_user extends StatelessWidget {
  const HomePage_user({super.key});

  @override
  Widget build(BuildContext context) {
    return Material(
      child: SingleChildScrollView(
        child: Container(
          color: Colors.white,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            verticalDirection: VerticalDirection.down,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 8.0),
                child: Align(
                  alignment: Alignment.topRight,
                  child: IconButton(
                    onPressed: () {
                      Navigator.pushNamed(context, '/ProfiloUtente');
                    },
                    icon: Icon(Icons.person),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(20, 130, 0, 0),
                child: Image.asset(GetImages.images["default"]!),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 100),
                child: SizedBox(
                  width: 318,
                  height: 43,
                  child: Text(
                    'Ciao ${Provider.of<UserDataProvider>(context, listen: true).cliente.nome}' /*Qui bisogna inserire il nome utente preso dal provider*/,
                    textAlign: TextAlign.center,
                    textDirection: TextDirection.ltr,
                    style: const TextStyle(
                      color: Color(0xFF102C57),
                      fontSize: 38,
                      fontFamily: 'ABeeZee',
                      fontWeight: FontWeight.w400,
                      height: 0.04,
                    ),
                  ),
                ),
              ),
              const Padding(
                padding: const EdgeInsets.only(top: 40),
                child: Text(
                  'Effettua una',
                  textDirection: TextDirection.ltr,
                  textAlign: TextAlign.center,
                  softWrap: true,
                  style: TextStyle(
                    color: Color(0xFF102C57),
                    fontSize: 30,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0.05,
                  ),
                ),
              ),
              const Padding(
                padding: const EdgeInsets.only(top: 50),
                child: Text(
                  'nuova prenotazione',
                  textDirection: TextDirection.ltr,
                  textAlign: TextAlign.center,
                  softWrap: true,
                  style: TextStyle(
                    color: Color(0xFF102C57),
                    fontSize: 30,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0.05,
                  ),
                ),
              ),
              const Padding(
                padding: const EdgeInsets.only(top: 50),
                child: Text(
                  'scegliendo il ',
                  //textDirection: TextDirection.ltr,
                  //textAlign: TextAlign.center,
                  textAlign: TextAlign.left,
                  //softWrap: true,
                  style: TextStyle(
                    color: Color(0xFF102C57),
                    fontSize: 30,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0.05,
                  ),
                ),
              ),
              const Padding(
                padding: const EdgeInsets.only(top: 50),
                child: Text(
                  'servizio più adatto a te!',
                  textDirection: TextDirection.ltr,
                  textAlign: TextAlign.center,
                  softWrap: true,
                  style: TextStyle(
                    color: Color(0xFF102C57),
                    fontSize: 30,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0.05,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
