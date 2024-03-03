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
import 'NavigationTab.dart';


class PrenotazioneEffettuata extends StatelessWidget {
  final BuildContext context;
  const PrenotazioneEffettuata({Key? key, required this.context}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
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
  Widget _body(){
    return Column(
      children: [
        const Padding(
          padding: EdgeInsets.only(top: 50),
          child:  Text('Prenotazione effettuata',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Color(0xFF102C57),
              fontSize: 32,
              fontStyle: FontStyle.italic,
              fontFamily: 'ABeeZee',
              fontWeight: FontWeight.w400,
              height: 0.04,
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.only(top: 100),
          child: Image.asset(GetImages.images["operazioneEffettuata"]!),
        ),
        Padding(
          padding: const EdgeInsets.only(top: 20.0),
          child: FilledButton(onPressed: (){
            Navigator.pushNamed(context, '/NavigationTab');
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
              "Torna alla home",
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
        )

      ],
    );

  }
}