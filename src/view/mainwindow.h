#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QInputDialog>
#include <QMainWindow>
#include <QMessageBox>

#include "../model/list.h"
#define TYPE std::string

QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow {
  Q_OBJECT

 public:
  MainWindow(QWidget *parent = nullptr);
  ~MainWindow();

 private slots:
  void on_btnAdd_clicked();

  void on_btnClear_clicked();

  void on_btnInsert_clicked();

  void on_btnBegin_clicked();

  void on_btnEnd_clicked();

  void on_btnRBegin_clicked();

  void on_btnREnd_clicked();

  void on_btnSize_clicked();

  void on_btnEmpty_clicked();

  void on_btnContains_clicked();

  void on_btnNew_clicked();

  void on_btnDelVal_clicked();

  void on_btnDelPos_clicked();

  void on_btnIndexOf_clicked();

  void on_btnSet_clicked();

  void on_btnGet_clicked();

 private:
  Ui::MainWindow *ui;
  List<TYPE> list;

  void updateUI();
  QString getString(QString message);
  int getNumber(QString message);
};
#endif  // MAINWINDOW_H
