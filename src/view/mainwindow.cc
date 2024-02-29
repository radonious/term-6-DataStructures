#include "mainwindow.h"

#include "./ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent), ui(new Ui::MainWindow) {
    list = List<TYPE>();
  ui->setupUi(this);
}

MainWindow::~MainWindow() { delete ui; }

QString MainWindow::getString(QString message) {
  return QInputDialog::getText(this, tr("QInputDialog::getText()"),
                               tr(message.toStdString().c_str()),
                               QLineEdit::Normal, "", nullptr);
}

int MainWindow::getNumber(QString message) {
  return getString(message).split(" ")[0].toInt();
}

void MainWindow::updateUI() {
  int current = 0;
  ui->table->setColumnCount(list.Size());
  for (List<TYPE>::Iterator it = list.Begin(); it != list.End(); ++it) {
    ui->table->setItem(0, current, new QTableWidgetItem(QString::fromUtf8((*it).c_str())));
    current++;
  }
}

void MainWindow::on_btnAdd_clicked() {
  bool ok;
  QString str = getString("Value");
  if (!str.isEmpty()) {
    list.PushBack(str.toStdString());
    updateUI();
  }
}

void MainWindow::on_btnClear_clicked() {
    list.Clear();
    updateUI();
}

void MainWindow::on_btnInsert_clicked()
{
    int pos = getNumber("Position");
    QString data = getString("Value");
    list.Insert(pos, data.toStdString());
    updateUI();
}


void MainWindow::on_btnBegin_clicked()
{
    std::string str = "nullptr";
    if (list.Begin() != list.End()) {
        str = *list.Begin();
    }
    QMessageBox::information(
        this,
        tr("Begin"),
        tr(str.c_str())
    );
}


void MainWindow::on_btnEnd_clicked()
{
    QMessageBox::information(
        this,
        tr("End"),
        tr("nullptr")
    );
}


void MainWindow::on_btnRBegin_clicked()
{
    std::string str = "nullptr";
    if (list.Rbegin() != list.Rend()) {
        str = *list.Rbegin();
    }
    QMessageBox::information(
        this,
        tr("Rbegin"),
        tr(str.c_str())
    );
}


void MainWindow::on_btnREnd_clicked()
{
    QMessageBox::information(
        this,
        tr("Rend"),
        tr("nullptr")
    );
}


void MainWindow::on_btnSize_clicked()
{
    QMessageBox::information(
        this,
        tr("Size"),
        tr(std::to_string(list.Size()).c_str())
    );
}


void MainWindow::on_btnEmpty_clicked()
{
    if (list.IsEmpty() == true) {
        QMessageBox::information(
            this,
            tr("Size"),
            tr("List is empty")
        );
    } else {
        QMessageBox::information(
            this,
            tr("Size"),
            tr("List is not empty")
        );
    }
}


void MainWindow::on_btnContains_clicked()
{
    std::string data = getString("Find Value").toStdString();
    if (list.HasData(data) == true) {
        QMessageBox::information(
            this,
            tr("Size"),
            tr("List contains this value")
        );
    } else {
        QMessageBox::information(
            this,
            tr("Size"),
            tr("List does not contains this value")
        );
    }
}


void MainWindow::on_btnNew_clicked()
{
    list = List<TYPE>();
    updateUI();
}


void MainWindow::on_btnDelVal_clicked()
{
    std::string data = getString("Value").toStdString();
    list.Remove(data);
    updateUI();
}


void MainWindow::on_btnDelPos_clicked()
{
    int pos = getNumber("Position");
    list.RemoveAt(pos);
    updateUI();
}

void MainWindow::on_btnIndexOf_clicked()
{
    std::string data = getString("Value").toStdString();
    int index = list.IndexOf(data);
    if (index >= 0) {
        QMessageBox::information(
            this,
            tr("Size"),
            tr(std::to_string(index).c_str())
        );
    } else {
        QMessageBox::information(
            this,
            tr("Size"),
            tr("List does not contains this value")
        );
    }
}


void MainWindow::on_btnSet_clicked()
{
    int pos = getNumber("Position");
    std::string data = getString("Value").toStdString();
    list.SetAt(pos, data);
    updateUI();
}


void MainWindow::on_btnGet_clicked()
{
    int pos = getNumber("Position");
    std::string data = list.GetAt(pos);
    QMessageBox::information(
        this,
        tr("Size"),
        tr(data.c_str())
    );
}

