import 'dart:core';
import 'package:barberapp_front_end/Model/Cliente.dart';
import 'package:barberapp_front_end/Model/Dipendente.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Appuntamento.g.dart';

@JsonSerializable()
class Appuntamento {
  late int id;
  late DateTime date;
  late DateTime time;
  late Cliente? cliente;
  late Dipendente? dipendente;
  late Servizio? servizio;

  Appuntamento(this.id, this.date, this.time, this.cliente, this.dipendente, this.servizio);

  factory Appuntamento.fromJson(Map<String, dynamic> json) =>
      _$AppuntamentoFromJson(json);
  Map<String, dynamic> toJson() => _$AppuntamentoToJson(this);

  int get _id => id;

  set _id(int value) {
    id = value;
  }

  DateTime get _date => date;

  set _date(DateTime value) {
    date = value;
  }

  DateTime get _time => time;

  set _time(DateTime value) {
    time = value;
  }

  Cliente? get _cliente => cliente;

  set _cliente(Cliente? value) {
    cliente = value;
  }

  Dipendente? get _dipendente => dipendente;

  set _dipendente(Dipendente? value) {
    dipendente = value;
  }
  Servizio? get _servizio => servizio;

  set _servizio(Servizio? value) {
    servizio = value;
  }

  bool isActive() => DateTime.now().toUtc().millisecond > (date.millisecond + _hourMillisecond);


  int get _hourMillisecond => time.millisecond - DateTime(time.year, time.month,time.day).millisecond;

  @override
  String toString() {
    return 'Appuntamento{id: $id, date: $date, time: $time, cliente: $cliente, dipendente: $dipendente, servizio: $servizio}';
  }
}
