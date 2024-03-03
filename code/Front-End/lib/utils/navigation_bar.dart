import 'package:flutter/material.dart';

class MyNavigationBar extends StatelessWidget {
  const MyNavigationBar({super.key, this.activeIndex = 0});
  final int activeIndex;

  static const List<String> _routes = ["servizi", "lista_prenotazioni"];
  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: activeIndex,
      onTap: (value) => Navigator.of(context).pushNamed(_routes[value]),
      items: const [
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: "",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.wallet),
          label: "",
        ),
      ],
      showSelectedLabels: false,
      showUnselectedLabels: false,
    );
  }
}
