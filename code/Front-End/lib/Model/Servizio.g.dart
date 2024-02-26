// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Servizio.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Servizio _$ServizioFromJson(Map<String, dynamic> json) => Servizio(
      json['id'] as int,
      json['tipo'] as String,
      json['assetImage'] as String,
      const DoubleConverter().fromJson(json['costo']),
      (json['appuntamenti'] as List<dynamic>?)
          ?.map((e) => Appuntamento.fromJson(e as Map<String, dynamic>))
          .toList(),
      json['titolare'] == null
          ? null
          : Titolare.fromJson(json['titolare'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ServizioToJson(Servizio instance) => <String, dynamic>{
      'id': instance.id,
      'tipo': instance.tipo,
      'assetImage': instance.assetImage,
      'costo': const DoubleConverter().toJson(instance.costo),
      'appuntamenti': instance.appuntamenti,
      'titolare': instance.titolare,
    };
