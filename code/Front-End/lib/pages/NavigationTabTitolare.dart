import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import 'package:barberapp_front_end/color_schemes.g.dart';
import 'package:barberapp_front_end/pages/HomePage_user.dart';
import 'package:barberapp_front_end/pages/ListaDipendenti.dart';
import 'package:barberapp_front_end/pages/ListaPrenotazioni.dart';
import 'package:barberapp_front_end/pages/SignupUser_page.dart';
import 'package:barberapp_front_end/pages/login_page.dart';
import 'package:barberapp_front_end/pages/services_page.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:barberapp_front_end/pages/ListaPrenotazioniTitolare.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'ListaServiziTitolare.dart';


class NavigationTabTitolare extends StatefulWidget{
  const NavigationTabTitolare({super.key});

  @override
  State<NavigationTabTitolare> createState() => _NavigationTabTitolareState();
}

class _NavigationTabTitolareState extends State<NavigationTabTitolare>{
  int currentPageIndex = 0;
  @override
  Widget build( BuildContext context){
    return Scaffold(
          backgroundColor: Colors.white,
          bottomNavigationBar: NavigationBar(
            backgroundColor: Colors.white,
            onDestinationSelected: (int index){
              setState(() {
                currentPageIndex = index;
              });
            },
            indicatorColor: Colors.lightBlue,
            selectedIndex: currentPageIndex,
            destinations: const <Widget>[
              NavigationDestination(
                selectedIcon: Icon(Icons.home),
                icon: Icon(Icons.home_outlined),
                label: 'Prenotazioni',
              ),
              NavigationDestination(
                selectedIcon: Icon(Icons.person_add),
                icon: Icon(Icons.person_add_outlined),
                label: 'Dipendenti',
              ),
              NavigationDestination(
                  selectedIcon: Icon(Icons.add_business),
                  icon: Icon(Icons.add_business_outlined),
                  label: 'Servizi'
              ),
            ],
          ),
          body: <Widget>[ListaPrenotazioniTitolare(),ListaDipendenti(),ListaServiziTitolare()][currentPageIndex],
        );
  }
}