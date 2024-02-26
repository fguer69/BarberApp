import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Cliente.g.dart';

@JsonSerializable()
class Cliente {
  late int id;
  late String nome;
  late String cognome;
  late String email;
  late String password;
  late List<Appuntamento>? appuntamenti;


  Cliente(this.id, this.nome, this.cognome, this.email, this.password, this.appuntamenti);

  factory Cliente.fromJson(Map<String, dynamic> json) =>
      _$ClienteFromJson(json);
  Map<String, dynamic> toJson() => _$ClienteToJson(this);

  String get nominativo => nome+' '+cognome;

  int get _id => id;

  set _id(int value) {
    id = value;
  }

  String get _nome => nome;

  set _nome(String value) {
    nome = value;
  }

  String get _cognome => cognome;

  set _cognome(String value) {
    cognome = value;
  }

  String get _email => email;

  set _email(String value) {
    email = value;
  }

  String get _password => password;

  set _password(String value) {
    password = value;
  }

  List<Appuntamento>? getAppuntamenti(){
    return appuntamenti;
  }

  void setAppuntamenti(List<Appuntamento>? Appuntamenti){
    appuntamenti = Appuntamenti;
  }

  @override
  String toString() {
    return 'Cliente{id: $id, nome: $nome, cognome: $cognome, email: $email, password: $password, appuntamenti: $appuntamenti}';
  }


}
