import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:provider/provider.dart';
import 'package:day_night_time_picker/day_night_time_picker.dart';

import '../Model/Cliente.dart';
import '../Model/Dipendente.dart';
import '../Model/Servizio.dart';
import '../Retrofit/RetrofitService.dart';
import '../RouteGenerator.dart';
import '../color_schemes.g.dart';

class ReservationsPage extends StatefulWidget {
  const ReservationsPage({Key? key}) : super(key: key);
  @override
  State<ReservationsPage> createState() => ReservationsPageState_();
}

class ReservationsPageState_ extends State<ReservationsPage> {
  late Servizio servizio =
      Provider.of<UserDataProvider>(context, listen: false).servizio;
  late Cliente cliente =
      Provider.of<UserDataProvider>(context, listen: false).cliente;

  final _formKey = GlobalKey<FormBuilderState>();
  Time _ora = Time(hour: 8, minute: 00);
  late DateTime _oraFinale = DateTime.now().toUtc();
  dynamic _label = 'Seleziona ora';
  void onTimeChanged(Time newTime) {
    setState(() {
      _ora = newTime;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: FormBuilder(
        key: _formKey,
        child: Container(
          color: Colors.white,
          alignment: Alignment.center,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            verticalDirection: VerticalDirection.down,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 50.0),
                child: Container(
                  color: Colors.white,
                  child: Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        textDirection: TextDirection.ltr,
                        children: [
                          const Padding(
                            padding: EdgeInsets.only(left: 140),
                            child: Text(
                              'Prenotazione',
                              textAlign: TextAlign.center,
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
                          Spacer(),
                          IconButton(
                            icon: const Icon(
                              Icons.person,
                              size: 45,
                              shadows: [
                                Shadow(
                                  color: Colors.grey,
                                )
                              ],
                            ),
                            onPressed: () {
                              Navigator.pushNamed(context, '/ProfiloUtente');
                            },
                          ),
                        ],
                      ),
                      const Padding(
                        padding: EdgeInsets.only(top: 20),
                        child: Text(
                          'Seleziona Giorno e Ora',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: Color(0xFF23303B),
                            fontSize: 33,
                            fontStyle: FontStyle.italic,
                            fontFamily: 'ABeeZee',
                            fontWeight: FontWeight.w400,
                            height: 0,
                            letterSpacing: -0.99,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 20.0, right: 20),
                child: FormBuilderDateTimePicker(
                  name: 'OrologioEdata',
                  inputType: InputType.date,
                  timePickerInitialEntryMode: TimePickerEntryMode.dialOnly,
                  autovalidateMode: AutovalidateMode.onUserInteraction,
                  initialDate: DateTime.now().toUtc(),
                  firstDate: DateTime(DateTime.now().toUtc().year),
                  lastDate: DateTime(2030),
                  autofocus: false,
                  validator: FormBuilderValidators.compose([
                    FormBuilderValidators.required(
                        errorText: 'Il campo non può essere vuoto'),
                    (value) {
                      if (value!.weekday == DateTime.monday ||
                          value.weekday == DateTime.sunday ||
                          value.month == DateTime.now().month &&
                              value.day < DateTime.now().day ||
                          value.month < DateTime.now().month &&
                              value.year == DateTime.now().year ||
                          value.year < DateTime.now().year) {
                        return 'Giorno non valido';
                      }
                    },
                  ]),
                  decoration: const InputDecoration(
                    icon: Icon(
                      Icons.calendar_month_rounded,
                      color: Colors.blue,
                    ),
                    hintText: 'Seleziona data',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(20.0)),
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(top: 20),
                child: Row(
                  children: [
                    SizedBox(
                      width: 390,
                      height: 60,
                      child: TextField(
                        readOnly: true,
                        controller: TextEditingController(
                          text: '${_label}',
                        ),
                        onTap: () {
                          Navigator.of(context).push(showPicker(
                            showSecondSelector: false,
                            is24HrFormat: true,
                            context: context,
                            value: _ora,
                            accentColor: Colors.blue,
                            borderRadius: 20,
                            iosStylePicker: true,
                            disableMinute: true,
                            minHour: 8,
                            maxHour: 18,
                            focusMinutePicker: false,
                            disableAutoFocusToNextInput: true,
                            onChange: onTimeChanged,
                            onChangeDateTime: (DateTime datetime) {
                              debugPrint(
                                  "[debug datetime]: $datetime ${_formKey.currentState!.fields['OrologioEdata']!.value}");
                              setState(() {
                                _oraFinale = datetime.toUtc();
                                _label = '${_oraFinale.hour}';
                              });
                            },
                          ));
                        },
                        decoration: const InputDecoration(
                          icon: Icon(
                            Icons.access_time_outlined,
                            color: Colors.blue,
                          ),
                          hintText: 'Seleziona ora',
                          border: OutlineInputBorder(
                            borderRadius:
                                BorderRadius.all(Radius.circular(20.0)),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              const Padding(
                padding: EdgeInsets.only(top: 50.0),
                child: Text(
                  '* Le prenotazioni possono essere effettuate soltanto dal\n '
                  'martedi al sabato, e possono essere effettute soltanto\n '
                  'dalle 8:00 alle 12:00 e dalle 15:00 alle 18:00.',
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
              Padding(
                padding: const EdgeInsets.only(top: 170.0),
                child: FilledButton(
                  onPressed: () {
                    final validation = _formKey.currentState?.validate();
                    if (validation! &&
                        _oraFinale.hour != 13 &&
                        _oraFinale.hour != 14) {
                      _formKey.currentState!.save();
                      Appuntamento appuntamento = Appuntamento(
                          1,
                          _formKey.currentState!.fields['OrologioEdata']!.value,
                          _oraFinale,
                          cliente,
                          null,
                          servizio);
                      Provider.of<UserDataProvider>(context, listen: false)
                          .setAppuntamento(appuntamento);
                      Navigator.pushNamed(context, '/BarbersAvaible');

                      //Provider.of<UserDataProvider>(context, listen: false).setAppuntamento(appuntamento);
                      //Navigator.pushNamed(context, '/BarbersAvaible');
                    } else if (validation! && _oraFinale.hour == 13 ||
                        _oraFinale.hour == 14) {
                      showDialog(
                        context: context,
                        barrierDismissible: false,
                        builder: (BuildContext context) {
                          return AlertDialog(
                            title: Text('Prenotazione non riuscita'),
                            content:
                                Text('Ora selezionata non corretta riprovare.'),
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
                  },
                  style: ButtonStyle(
                    backgroundColor: const MaterialStatePropertyAll<Color>(
                        Color(0xFF102C57)),
                    padding: const MaterialStatePropertyAll<EdgeInsetsGeometry>(
                        EdgeInsets.fromLTRB(0, 0, 0, 0)),
                    fixedSize: const MaterialStatePropertyAll(
                      Size(226, 74),
                    ),
                    shape: MaterialStatePropertyAll(
                      RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                  ),
                  child: const Text(
                    "Avanti",
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 23,
                      fontFamily: 'Inter',
                      fontWeight: FontWeight.w600,
                      height: 0.07,
                    ),
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
