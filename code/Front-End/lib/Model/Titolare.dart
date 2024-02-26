import 'dart:core';
import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Titolare.g.dart';

@JsonSerializable()
class Titolare {
  late int id;
  late String email;
  late String password;
  late String nome;
  late String cognome;
  late List<Servizio>? servizi;


  Titolare(this.id, this.email, this.password, this.nome, this.cognome,this.servizi);

  factory Titolare.fromJson(Map<String, dynamic> json) =>
      _$TitolareFromJson(json);
  Map<String, dynamic> toJson() => _$TitolareToJson(this);

  int get _id => id;

  set _id(int value) {
    id = value;
  }

  String get _email => email;

  set _email(String value) {
    email = value;
  }

  String get _password => password;

  set _password(String value) {
    password = value;
  }

  String get _nome => nome;

  set _nome(String value) {
    nome = value;
  }

  String get _cognome => cognome;

  set _cognome(String value) {
    cognome = value;
  }
  List<Servizio>? getServizi(){
    return servizi;
  }
  void setServizi(List<Servizio>? Servizi){
    servizi = Servizi;
  }

  @override
  String toString() {
    return 'Titolare{id: $id, email: $email, password: $password, nome: $nome, cognome: $cognome, servizi: $servizi}';
  }
}
