import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import 'package:barberapp_front_end/color_schemes.g.dart';
import 'package:barberapp_front_end/pages/AggiungiServizi.dart';
import 'package:barberapp_front_end/pages/BarbersAvaible.dart';
import 'package:barberapp_front_end/pages/HomePage_user.dart';
import 'package:barberapp_front_end/pages/ListaPrenotazioni.dart';
import 'package:barberapp_front_end/pages/ListaServiziTitolare.dart';
import 'package:barberapp_front_end/pages/NavigationTab.dart';
import 'package:barberapp_front_end/pages/NavigationTabTitolare.dart';
import 'package:barberapp_front_end/pages/PrenotazioneEffettuata.dart';
import 'package:barberapp_front_end/pages/ProfiloUtente.dart';
import 'package:barberapp_front_end/pages/ReservationsPage.dart';
import 'package:barberapp_front_end/pages/SignupUser_page.dart';
import 'package:barberapp_front_end/pages/login_page.dart';
import 'package:barberapp_front_end/pages/services_page.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';

import 'Model/Servizio.dart';
import 'Model/Titolare.dart';

void main() {
  runApp(MultiProvider(providers: [ChangeNotifierProvider(create: (_) => UserDataProvider())], child: MyApp(),));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'BarberApp',
      theme: ThemeData(
        colorScheme: lightColorScheme,
        textTheme: TextTheme(
            bodyLarge: Theme.of(context)
                .textTheme
                .bodyLarge
                ?.copyWith(color: const Color(0xFF102C57))),
        useMaterial3: true,
      ),
      home: const _HomePage(),
      onGenerateRoute: RouteGenerator.generateRoute,
    );
  }
}

class _HomePage extends StatelessWidget{
  const _HomePage({super.key});
  @override
  Widget build(BuildContext context){
    return Container(
        color: const Color(0xffDAC0A3),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          verticalDirection: VerticalDirection.down,
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(0,100,0,0),
              child: Image.asset(GetImages.images["default"]!),
            ),
           const Padding(
             padding: EdgeInsets.fromLTRB(0,70,0,20),
               child: Text(
                 'Sei già registrato?',
                 textAlign: TextAlign.center,
                 style: TextStyle(
                   decoration: TextDecoration.none,
                   color: Color(0xFF102C57),
                   fontSize: 30,
                   fontFamily: 'ABeeZee',
                   fontWeight: FontWeight.w400,
                   height: 0.05,
                 ),
               ),
           ),
            Padding(
             padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
             child: FilledButton(onPressed: () => Navigator.pushNamed(context, '/login_page'),
                  style: ButtonStyle(
                    backgroundColor: const MaterialStatePropertyAll<Color>(Color(0xFF102C57)),
                    padding: const MaterialStatePropertyAll<EdgeInsetsGeometry>(EdgeInsets.fromLTRB(0, 8, 0, 0)),
                    fixedSize: const MaterialStatePropertyAll(Size(316, 63),
                    ),
                    shape: MaterialStatePropertyAll(RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                    ),
                  ),
                  child:  Text("Accedi",
                    style: TextStyle(
                      color: Colors.white.withOpacity(0.8999999761581421),
                      fontSize: 20,
                      fontStyle: FontStyle.italic,
                      fontFamily: 'ABeeZee',
                      fontWeight: FontWeight.w400,
                      height: 0.07,
                    ),
                  ),
              ),
           ),
            const Padding(
              padding: EdgeInsets.fromLTRB(0,70,0,20),
              child: Text(
                'Non hai un account?',
                textAlign: TextAlign.center,
                style: TextStyle(
                  decoration: TextDecoration.none,
                  color: Color(0xFF102C57),
                  fontSize: 30,
                  fontFamily: 'ABeeZee',
                  fontWeight: FontWeight.w400,
                  height: 0.05,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 20, 0, 0),
              child: FilledButton(onPressed: () => Navigator.pushNamed(context, '/SignupUser_page'),
                style: ButtonStyle(
                  backgroundColor: const MaterialStatePropertyAll<Color>(Color(0xFF102C57)),
                  padding: const MaterialStatePropertyAll<EdgeInsetsGeometry>(EdgeInsets.fromLTRB(0, 8, 0, 0)),
                  fixedSize: const MaterialStatePropertyAll(Size(316, 63),
                  ),
                  shape: MaterialStatePropertyAll(RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                ),
                child: Text("Registrati",
                  style: TextStyle(
                    color: Colors.white.withOpacity(0.8999999761581421),
                    fontSize: 20,
                    fontStyle: FontStyle.italic,
                    fontFamily: 'ABeeZee',
                    fontWeight: FontWeight.w400,
                    height: 0.07,
                  ),
                ),
              ),
            ),
      
          ],
      
        ),
      
      );
  }
}
