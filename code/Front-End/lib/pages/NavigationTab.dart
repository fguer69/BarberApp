import 'package:barberapp_front_end/Providers/UserDataProvider.dart';
import 'package:barberapp_front_end/RouteGenerator.dart';
import 'package:barberapp_front_end/color_schemes.g.dart';
import 'package:barberapp_front_end/pages/HomePage_user.dart';
import 'package:barberapp_front_end/pages/ListaPrenotazioni.dart';
import 'package:barberapp_front_end/pages/SignupUser_page.dart';
import 'package:barberapp_front_end/pages/login_page.dart';
import 'package:barberapp_front_end/pages/services_page.dart';
import 'package:barberapp_front_end/Model/getImages.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';


class NavigationTab extends StatefulWidget{
  const NavigationTab({super.key});

  @override
  State<NavigationTab> createState() => _NavigationTabState();
}

class _NavigationTabState extends State<NavigationTab>{
int currentPageIndex = 0;
  @override
  Widget build(BuildContext context){
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
                label: 'Home',
            ),
            NavigationDestination(
                selectedIcon: Icon(Icons.add_box_rounded),
                icon: Icon(Icons.add_box_outlined),
                label: 'Servizi',
            ),
            NavigationDestination(
                selectedIcon: Icon(Icons.access_time_filled),
                icon: Icon(Icons.access_time),
                label: 'Prenotazioni'
            ),
          ],
        ),
        body: <Widget>[HomePage_user(),ServicePage(),ListaPrenotazioni()][currentPageIndex],
      );
  }
}