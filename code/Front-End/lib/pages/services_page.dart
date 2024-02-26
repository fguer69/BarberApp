import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/Retrofit/RetrofitService.dart';
import 'package:barberapp_front_end/utils/navigation_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ServicePage extends StatefulWidget {
  const ServicePage({super.key});

  @override
  State<ServicePage> createState() => _ServicePageState();
}

class _ServicePageState extends State<ServicePage> {
  /*final List<Servizio> servizi = [
    Servizio(1, "taglio", 3, [], Titolare(1, "", "", "", "", [], [])),
    Servizio(1, "tipo", 3, [], Titolare(1, "", "", "", "", [], [])),
    Servizio(1, "taglio", 3, [], Titolare(1, "", "", "", "", [], [])),
  ];*/

  late List<Servizio>services = Provider.of<UserDataProvider>(context, listen: true).servizi;


  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          centerTitle: true,
          title: const Text("Servizi",
              style: TextStyle(fontStyle: FontStyle.italic)),
          actions: [
            Padding(
              padding: EdgeInsets.only(right: 24.0),
              child: CircleAvatar(
                child: IconButton(
                    icon: Icon(Icons.person),
                  onPressed: (){Navigator.pushNamed(context, '/ProfiloUtente');},
                ),
              ),
            )
          ],
        ),
        body: _body()
    );
  }
  FutureBuilder _body(){
    final retrofitService = RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    return FutureBuilder(
        future: retrofitService.getServices(),
        builder: (context, snapshot){
          if(snapshot.connectionState == ConnectionState.done){
            if(snapshot.data == null) {
              services = [];
              Provider.of<UserDataProvider>(context, listen: false).setServizi(
                  []);
            }
            else {
              services = snapshot.data!;
              Provider.of<UserDataProvider>(context,listen: false).setServizi(snapshot.data);
            }
            return _services(services);
          }
          else{
            return Center(
              child: CircularProgressIndicator(),
            );
          }
        },
    );
  }
  Widget _services(List<Servizio> services){
    return ListView.builder(
      itemCount: services.length,
      itemBuilder: (context, index) =>
          ServiceTile(servizio: services[index]),
    );
  }
}

class ServiceTile extends StatelessWidget {
  const ServiceTile({super.key, required this.servizio});

  final Servizio servizio;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: (){
        Provider.of<UserDataProvider>(context, listen: false).setServizio(servizio);
        Navigator.pushNamed(context, '/reservationsPage');
      },
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Image.asset(
                  servizio.assetImage ?? GetImages.images["default"]!,
                  width: 128,
                  height: 64,
                  fit: BoxFit.fitWidth,
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 8.0),
                  child: SizedBox(
                      width: 100,
                      height: 100,
                      child: Text('${servizio.tipo}',
                        textAlign: TextAlign.center,
                        overflow: TextOverflow.clip,
                        style: const TextStyle(
                          color: Color(0xFF565866),
                          fontSize: 15,
                          fontFamily: 'ABeeZee',
                          fontWeight: FontWeight.w400,
                        ),
                      ),
                  ),
                ),
                Padding(
                    padding: const EdgeInsets.only(left: 35),
                  child: Text('${servizio.costo}'),
                ),
              ],
            ),
          ),
          const Divider()
        ],
      ),
    );
  }
}
