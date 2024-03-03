import 'package:barberapp_front_end/Model/Servizio.dart';
import 'package:barberapp_front_end/Model/Titolare.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/Retrofit/RetrofitService.dart';
import 'package:barberapp_front_end/utils/navigation_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ListaServiziTitolare extends StatefulWidget {
  const ListaServiziTitolare({super.key});

  @override
  State<ListaServiziTitolare> createState() => _ListaServiziTitolareState();
}

class _ListaServiziTitolareState extends State<ListaServiziTitolare> {
  /*final List<Servizio> prototipo = [
    Servizio(1, "taglio",'', 3, [], Titolare(1, "", "", "", "", [], [])),
    Servizio(1, "tipo",'', 3, [], Titolare(1, "", "", "", "", [], [])),
    Servizio(1, "taglio",'', 3, [], Titolare(1, "", "", "", "", [], [])),
    Servizio(1, "taglio+barba+shampoo+ciaociao+byebye",'', 3, [], Titolare(1, "", "", "", "", [], [])),
  ];*/

  late List<Servizio>services = Provider.of<UserDataProvider>(context, listen: true).servizi;
  Future<void> _deleteServizio(int index) async {
    final retrofitService =
    RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    final int responseCode =
    await retrofitService.deleteServizio(services[index]);
    if (responseCode == 200) {
      setState(() {
        services.removeAt(index); // Rimuovi l'appuntamento dalla lista
      });
    }
  }
  Future<void> _updateServizio(int index) async {
    Servizio temp = services[index];
    print(temp);
    final retrofitService =
    RetrofitService(Dio(BaseOptions(contentType: "application/json")));
    final int responseCode =
    await retrofitService.updateServizio(services[index]);
    if (responseCode == 200) {
      setState(() {
        services.removeAt(index);
        services.add(temp);// Rimuovi l'appuntamento dalla lista
      });
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            leading: IconButton(
              icon: Icon(Icons.add),
              onPressed: (){Navigator.pushNamed(context, '/AggiungiServizio');},
            ),
            centerTitle: true,
            title: const Text("Servizi",
                style: TextStyle(fontStyle: FontStyle.italic)),
            actions: [
              Padding(
                padding: EdgeInsets.only(right: 24.0),
                child: CircleAvatar(
                  child: IconButton(
                    onPressed: (){Navigator.pushNamed(context, '/ProfiloTitolare');},
                    icon: Icon(Icons.person),
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
            Provider.of<UserDataProvider>(context,listen: false).setServizi([]);
          }
          else {
            services = snapshot.data;
            Provider.of<UserDataProvider>(context, listen: false).setServizi(snapshot.data!);
          }
          return _services(services, retrofitService);
        }
        else{
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }
  Widget _services(List<Servizio> services, RetrofitService retrofitService){
    return ListView.builder(
      itemCount: services.length,//(snapshot.data as List<Appuntamento>).length,
      itemBuilder: (context, index) => ServiceTile(servizio: services[index], callBack: (index) => _deleteServizio(index),callBack2: (index) => _updateServizio(index), index: index,
      ),
    );
  }
}

class ServiceTile extends StatefulWidget {
  const ServiceTile({Key? key, required this.servizio, required this.callBack,required this.callBack2, required this.index}) : super(key: key);

  final Servizio servizio;
  final Function(int) callBack;
  final Function(int) callBack2;
  final int index;

  @override
  State<ServiceTile> createState()=> _ServiceTileState();
}

class _ServiceTileState extends State<ServiceTile>{
  late double _costo;
  @override
  void initState(){
    super.initState();
    _costo = widget.servizio.costo;
  }
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Image.asset(
                widget.servizio.assetImage ?? GetImages.images["default"]!,
                width: 128,
                height: 64,
                fit: BoxFit.fitWidth,
              ),
              Padding(
                padding: const EdgeInsets.only(left: 8.0),
                child: SizedBox(
                  width: 100,
                  height: 100,
                  child: Text('${widget.servizio.tipo}',
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
                padding: const EdgeInsets.only(left: 35.0),
                child: Text('${widget.servizio.costo}€',),
              ),

              IconButton(
                onPressed: () async{
                  _showEditDialog();
                },
                icon: Icon(Icons.edit),
              ),
              IconButton(
                onPressed: () async{
                  bool? res = await showDialog<bool>(
                    context: context,
                    builder: (context) => AlertDialog(
                      title: const Text(
                          "Vuoi davvero eliminare questo servizio?"),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(true),
                          child: const Text("Si"),
                        ),
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(false),
                          child: const Text("No"),
                        ),
                      ],
                    ),
                  );
                  if (res != null && res == true){
                    widget.callBack(widget.index);
                  }
                },
                icon: Icon(Icons.delete_outline),
              )
            ],
          ),
        ),
        const Divider()
      ],
    );
  }
  Future<void> _showEditDialog() async {
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Modifica Costo'),
          content: TextFormField(
            initialValue: _costo.toString(),
            keyboardType: TextInputType.number,
            onChanged: (value) {
              setState(() {
                _costo = double.tryParse(value) ?? _costo;
              });
            },
          ),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('Annulla'),
            ),
            TextButton(
              onPressed: () {
                setState(() {
                  widget.servizio.costo = _costo;
                });
                widget.callBack2(widget.index);
                Navigator.of(context).pop();
              },
              child: Text('Salva'),
            ),
          ],
        );
      },
    );
  }
}
