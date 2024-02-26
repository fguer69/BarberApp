// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Appuntamento.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Appuntamento _$AppuntamentoFromJson(Map<String, dynamic> json) => Appuntamento(
      json['id'] as int,
      DateTime.parse(json['date'] as String),
      DateTime.parse(json['time'] as String),
      json['cliente'] == null
          ? null
          : Cliente.fromJson(json['cliente'] as Map<String, dynamic>),
      json['dipendente'] == null
          ? null
          : Dipendente.fromJson(json['dipendente'] as Map<String, dynamic>),
      json['servizio'] == null
          ? null
          : Servizio.fromJson(json['servizio'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$AppuntamentoToJson(Appuntamento instance) =>
    <String, dynamic>{
      'id': instance.id,
      'date': instance.date.toIso8601String(),
      'time': instance.time.toIso8601String(),
      'cliente': instance.cliente,
      'dipendente': instance.dipendente,
      'servizio': instance.servizio,
    };
