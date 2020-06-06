import 'dart:async';

import 'package:firebase_analytics/firebase_analytics.dart';
import 'package:firebase_analytics/observer.dart';
import 'package:flutter/material.dart';
import 'package:mgljpndic/HomePage.dart';
import 'package:mgljpndic/model/Word.dart';
import 'package:path_provider/path_provider.dart' as path_provider;
import 'package:cloud_firestore/cloud_firestore.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
//  final appDocumentDir = await path_provider.getApplicationDocumentsDirectory();
//  Hive.init(appDocumentDir.path);

//  Hive.registerAdapter(WordAdapter());
//
//  await Hive.openBox("words");

  FirebaseAnalytics analytics = FirebaseAnalytics();
  FirebaseAnalyticsObserver observer = FirebaseAnalyticsObserver(analytics: analytics);

  runApp(new MaterialApp(
    theme: ThemeData(
        primaryColor: Colors.redAccent, accentColor: Colors.redAccent),
    debugShowCheckedModeBanner: false,
    home: new SplashScreen(),
  ));
}

class SplashScreen extends StatefulWidget {
  // final wordBox = Hive.box("words");

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
//  void addWord(Word word) {
//    Hive.box("words").add(word);
//  }

  // List<Word> wordList = [];

  @override
  void initState() {
    super.initState();
//    Hive.box("words").add(Word("12", "12", "12", "12", "12", "12"));
//    Hive.box("words").add(Word("23", "23", "23", "23", "23", "23"));
//    Hive.box("words").add(Word("34", "34", "34", "34", "34", "34"));

    Timer(
        Duration(seconds: 3),
        () => {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => MyHomePage()),
              )
            });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        fit: StackFit.expand,
        children: <Widget>[
          Container(
            decoration: BoxDecoration(color: Colors.redAccent),
          ),
        ],
      ),
    );
  }
}
