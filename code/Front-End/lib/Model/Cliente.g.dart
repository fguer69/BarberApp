// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Cliente.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Cliente _$ClienteFromJson(Map<String, dynamic> json) => Cliente(
      json['id'] as int,
      json['nome'] as String,
      json['cognome'] as String,
      json['email'] as String,
      json['password'] as String,
      (json['appuntamenti'] as List<dynamic>?)
          ?.map((e) => Appuntamento.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$ClienteToJson(Cliente instance) => <String, dynamic>{
      'id': instance.id,
      'nome': instance.nome,
      'cognome': instance.cognome,
      'email': instance.email,
      'password': instance.password,
      'appuntamenti': instance.appuntamenti,
    };
