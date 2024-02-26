// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Titolare.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Titolare _$TitolareFromJson(Map<String, dynamic> json) => Titolare(
      json['id'] as int,
      json['email'] as String,
      json['password'] as String,
      json['nome'] as String,
      json['cognome'] as String,
      (json['servizi'] as List<dynamic>?)
          ?.map((e) => Servizio.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$TitolareToJson(Titolare instance) => <String, dynamic>{
      'id': instance.id,
      'email': instance.email,
      'password': instance.password,
      'nome': instance.nome,
      'cognome': instance.cognome,
      'servizi': instance.servizi,
    };
