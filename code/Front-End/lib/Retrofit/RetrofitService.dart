import 'dart:core';
import 'package:barberapp_front_end/Model/Appuntamento.dart';
import 'package:barberapp_front_end/Model/Dipendente.dart';
import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:retrofit/retrofit.dart';
import 'package:dio/dio.dart';
import 'package:barberapp_front_end/Model/Cliente.dart';

import '../Model/Titolare.dart';
part 'RetrofitService.g.dart';

@RestApi(baseUrl: "http://192.168.1.220:8080")
abstract class RetrofitService {
  factory RetrofitService(Dio dio) = _RetrofitService;

  //Qui vanno scritte le API rest che vanno fatte al backend proprio come sono descritte all'interno del backend controller
  @POST('/recover/recover-password')
  Future<String?> getCodeHash(@Body() String email);

  @POST('/clienti/getClienteByEmail')
  Future<Cliente?> getClienteByEmail(@Body() String email);

  @POST('/titolari/getTitolareByEmail')
  Future<Titolare?> getTitolareByEmail(@Body() String email);

  @POST('/dipendenti/getDipendenteByEmail')
  Future<Dipendente?> getDipendenteByEmail(@Body() String email);

  @GET('/clienti/get-all')
  Future<List<Cliente>> getPosts();

  @POST('/cliente/check')
  Future<int> checkEmail(@Body() String email);

  @POST('/clienti/save')
  Future<int> saveCliente(@Body() Cliente cliente);

  @POST('/clienti/update')
  Future<int> updateCliente(@Body() Cliente cliente);

  @POST('/clienti/delete')
  Future<int> deleteCliente(@Body() Cliente cliente);
  @FormUrlEncoded()
  @POST('/clienti/login')
  Future<Cliente?> clienteLogin(
      @Field("email") String email, @Field("password") String password);
  @FormUrlEncoded()
  @POST('/titolari/login')
  Future<Titolare?> titolareLogin(
      @Field("email") String email, @Field("password") String password);

  @POST('/titolari/delete')
  Future<int> titolareDelete(@Body() Titolare titolare);

  @POST('/titolari/check')
  Future<int> checkEmailTitolare(@Body() String email);

  @POST('/titolari/update')
  Future<int> updateTitolare(@Body() Titolare titolare);

  @GET('/servizi/get-all')
  Future<List<Servizio>> getServices();

  @POST('/servizi/save')
  Future<int> saveServizio(@Body() Servizio servizio);

  @POST('/servizi/delete')
  Future<int> deleteServizio(@Body() Servizio servizio);

  @POST('/servizi/update')
  Future<int> updateServizio(@Body() Servizio servizio);
  @FormUrlEncoded()
  @POST('/dipendenti/dipendentiDisponibili')
  Future<List<Dipendente>> getFreeEmployee(
      @Field("data") DateTime data, @Field("ora") DateTime ora);
  @FormUrlEncoded()
  @POST('/dipendenti/login')
  Future<Dipendente?> dipendenteLogin(
      @Field("email") String email, @Field("password") String password);

  @GET('/dipendenti/get-all')
  Future<List<Dipendente>> dipendentiGetAll();

  @POST('/dipendenti/getById')
  Future<Dipendente> getDipendenteById(@Body() int id);

  @POST('/dipendenti/delete')
  Future<int> deleteDipendente(@Body() Dipendente dipendente);

  @POST('/appuntamenti/save')
  Future<int> saveAppuntamento(@Body() Appuntamento appuntamento);

  @POST('/dipendenti/check')
  Future<int> checkEmailDipendente(@Body() String email);

  @POST('/dipendenti/update')
  Future<int> updateDipendente(@Body() Dipendente dipendente);

  @POST('/dipendenti/save')
  Future<int> saveDipendente(@Body() Dipendente dipendente);

  @POST('/appuntamenti/getAppointment-ByCliente')
  Future<List<Appuntamento>> getAppuntamentiByCliente(@Body() Cliente cliente);

  @POST('/appuntamenti/getAppointment-ByDipendente')
  Future<List<Appuntamento>> getAppuntamentiByDipendente(
      @Body() Dipendente dipendente);

  @POST('/appuntamenti/delete')
  Future<int> deleteAppuntamento(@Body() Appuntamento appuntamento);

  @GET('/appuntamenti/get-all-appuntamenti')
  Future<List<Appuntamento>> getAllAppuntamentiOrdered();
}
