import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:custom_radio_grouped_button/custom_radio_grouped_button.dart';

// https://pub.dev/packages/custom_radio_grouped_button#-example-tab-
enum SingingCharacter { lafayette, jefferson }

class AddNewWord extends StatelessWidget {
  final orchuulgaTf = TextEditingController();
  final hiraganaTf = TextEditingController();
  final hanzTf = TextEditingController();
  String wordTypeValue = "1";
  String wordJpnLvlValue = "5";

//  final DocumentReference documentReference = Firestore.instance.collection("mgljpndic");

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    orchuulgaTf.dispose();
    hiraganaTf.dispose();
    hanzTf.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Шинэ үг нэмэх"),
        ),
        body: Container(
            color: Colors.white,
            child: SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  children: <Widget>[
                    TextField(
                      controller: hanzTf,
                      decoration: InputDecoration(labelText: 'Ханз'),
                    ),
                    TextField(
                      controller: hiraganaTf,
                      decoration: InputDecoration(labelText: 'Хирагана'),
                    ),
                    TextField(
                      controller: orchuulgaTf,
                      decoration: InputDecoration(labelText: 'Орчуулга'),
                    ),
                    Column(
                      children: <Widget>[
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          'Ангилал',
                          style: TextStyle(fontSize: 14),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        CustomRadioButton(
                          enableShape: true,
                          buttonColor: Theme.of(context).canvasColor,
                          horizontal: true,
                          buttonLables: [
                            'Үйл үг',
                            'Нэр үг',
                            'Тэмдэг нэр',
                            'Дайвар үг',
                          ],
                          buttonValues: [
                            "1",
                            "2",
                            "3",
                            "4",
                          ],
                          radioButtonValue: (value) {
                            print(value);
                            wordTypeValue = value;
                          },
                          selectedColor: Theme.of(context).accentColor,
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          'Япон хэлний түвшин',
                          style: TextStyle(fontSize: 14),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        CustomRadioButton(
                          enableShape: true,
                          buttonColor: Theme.of(context).canvasColor,
                          buttonLables: [
                            'n5',
                            'n4',
                            'n3',
                            'n2',
                            'n1',
                          ],
                          buttonValues: [
                            "5",
                            "4",
                            "3",
                            "2",
                            '1',
                          ],
                          radioButtonValue: (value) {
                            print(value);
                            wordJpnLvlValue = value;
                          },
                          selectedColor: Theme.of(context).accentColor,
                        ),
                      ],
                    ),
                    SizedBox(height: 20),
                    RaisedButton(
                      onPressed: () {
                        if (hanzTf.text.length > 0 &&
                            hiraganaTf.text.length > 0 &&
                            orchuulgaTf.text.length > 0) {
                          CollectionReference citiesRef =
                              Firestore.instance.collection('dicorig');
                          citiesRef
                              .where('kanjiword', isEqualTo: hanzTf.text)
                              .where('hiraword', isEqualTo: hiraganaTf.text)
                              .where('dictword', isEqualTo: orchuulgaTf.text)
                              .snapshots()
                              .listen((data) => data.documents.length > 0
                                  ? {
                                      Fluttertoast.showToast(
                                          msg:
                                              "Тухайн үг бүртгэлтэй байгаа тул хайж үзнэ үү.",
                                          toastLength: Toast.LENGTH_SHORT,
                                          gravity: ToastGravity.CENTER,
                                          timeInSecForIosWeb: 1,
                                          backgroundColor: Colors.redAccent,
                                          textColor: Colors.white,
                                          fontSize: 12.0)
                                    }
                                  : {_save(context)});
                        }
                      },
                      child: const Text(
                        'Хадгалах',
                        style: TextStyle(fontSize: 14),
                      ),
                    ),
                  ],
                ),
              ),
            )));
  }

  _save(context) {
    Map<String, String> data = <String, String>{
      'kanjiword': hanzTf.text,
      'hiraword': hiraganaTf.text,
      'dictword': orchuulgaTf.text,
      'wordtype': wordTypeValue,
      'jpnslvl': wordJpnLvlValue
    };

    Firestore.instance.collection("dicorig").add(data).whenComplete(() {
      Fluttertoast.showToast(
          msg: "Шинээр үг нэмсэнд баярлалаа.",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          backgroundColor: Colors.redAccent,
          textColor: Colors.white,
          fontSize: 14.0);

      Navigator.of(context).pop(true);
    }).catchError((e) => print(e));
  }
}

// Үйл үг
// Нэр үг
// Тэмдэг нэр
// Дайвар үг
