import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:mgljpndic/AddNewWord.dart';
import 'package:fluttertoast/fluttertoast.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<String> litems = [];
  final TextEditingController eCtrl = new TextEditingController();

  void _incrementCounter() {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => AddNewWord()),
    );
  }

  String txt = '';

  @override
  Widget build(BuildContext context) {
    //  final wordBox = Hive.box("words");
    return Scaffold(
      appBar: AppBar(
        title: Text("Толь бичиг"),
        bottom: PreferredSize(
          preferredSize: Size.fromHeight(48.0),
          child: Row(
            children: <Widget>[
              Expanded(
                child: Container(
                  margin: const EdgeInsets.only(left: 15.0, right: 15.0, bottom: 8.0),
                  decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(24.0)),
                  child: TextFormField(
                    onChanged: (text) {
                      setState(() {
                        txt = text;
                      });
                    },
                    controller: eCtrl,
                    decoration: InputDecoration(
                        labelText: "Хайх", prefixIcon: Icon(Icons.search)),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            StreamBuilder(
                stream: Firestore.instance.collection("dicorig").snapshots(),
                builder: (context, snapshot) {
                  if (!snapshot.hasData)
                    return Text('Уншиж байна. Түр хүлээнэ үү.');
                  if (txt != '') {
                    List<DocumentSnapshot> words = [];
                    for (final word in snapshot.data.documents) {
                      if (word["hiraword"].toString().toLowerCase().contains(txt.toLowerCase()) ||
                          word["kanjiword"].toString().toLowerCase().contains(txt.toLowerCase()) ||
                          word["dictword"].toString().toLowerCase().contains(txt.toLowerCase())) {
                        words.add(word);
                      }
                    }

                    return new Expanded(
                        child: ListView.builder(
                            itemCount: words.length,
                            itemBuilder: (context, index) {
                              final DocumentSnapshot _card = words[index];
                              return ListTile(
                                title: Text(_card["kanjiword"] +
                                    " [" +
                                    _card['hiraword'] +
                                    "]"),
                                subtitle: Text(_card['dictword']),
                              );
                            }));
                  } else {

                    return new Expanded(
                        child: ListView.builder(
                            itemCount: snapshot.data.documents.length,
                            itemBuilder: (context, index) {
                              final DocumentSnapshot _card =
                                  snapshot.data.documents[index];
                              String a = snapshot.data.documents.length.toString();
                              return ListTile(

                                title: Text(a + " " + _card['kanjiword'] +
                                    " [" +
                                    _card['hiraword'] +
                                    "]"),
                                subtitle: Text(_card['dictword']),
                              );
                            }));
                  }
                })
          ],
        ),
      ),

      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            DrawerHeader(
              child: Text('Толь бичиг'),
              decoration: BoxDecoration(
                color: Colors.redAccent,
              ),
            ),
            ListTile(
              title: Text('Шинэ үг нэмэх'),
              onTap: () {
                _incrementCounter();
              },
            ),
            ListTile(
              title: Text('Холбоо барих'),
              onTap: () {
                Navigator.pop(context);
              },
            ),
            ListTile(
              title: Text('Үгийн тоо []'),
            ),
          ],
        ),
      ),

      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        child: Icon(Icons.add),
        backgroundColor: Colors.redAccent,
        foregroundColor: Colors.white,
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  ListView _filter() {}

  @override
  void dispose() {
//    Hive.box("words").compact();
//    Hive.close();
    super.dispose();
  }

//  StreamController<List<Word>> _streamController = StreamController<List<Word>>();
//  Stream<List<Word>> get _stream => _streamController.stream;
//
//  _filter(String searchQuery) {
//    List<Word> _filteredList = snapshot
//        .where((Word user) => user.hiraWord.toLowerCase().contains(searchQuery.toLowerCase()))
//        .toList();
//    _streamController.sink.add(_filteredList);
//  }

}

// database ees haruuulj bgaa heseg
//new Expanded(
//child: ListView.builder(
//itemCount: wordBox.length,
//itemBuilder: (context, index){
//
//final word = wordBox.getAt(index) as Word;
//
//return ListTile(
//title: Text(word.kanjiWord),
//subtitle: Text(word.hiraWord),
//);
//
//}
//)
//)

//new FutureBuilder(
//future: Hive.openBox("words"),
//builder: (BuildContext context, AsyncSnapshot snapshot) {
//if (snapshot.connectionState == ConnectionState.done) {
//if (snapshot.hasError) {
//return Text(snapshot.error.toString());
//}else{
//
//}
//}else{
//return Scaffold();
//}
//}),

//Column(
//children: <Widget>[
//Text(snapshot.data.documents[]['kanjiword']),
//Text(snapshot.data.documents['dictword']),
//],
//);
