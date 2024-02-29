#pragma once
#include <stdexcept>

#include "../model/list.h"

#if __APPLE__
#define CLEAR "clear"
#define PAUSE "echo Нажмите любую кнопку для продолжения...; read"
#else
#define PAUSE "pause"
#define CLEAR "cls"
#endif

// интерфейс
template <typename T>
class TestMenu {
  // сам список
  List<T> L;

  // числа
  int GetNumber(const char *message);

  // чтение значения с заданным номером в списке
  T GetData(const char *message);

  // создание
  void Create();

  // получение кол-ва элементов в списке
  void Size();

  // проверка на пустоту
  void IsEmpty();

  // проверка на наличие значения
  void HasData();

  // чтение значения с заданным номером в списке
  void GetAt();

  // изменение значения с заданным номером в списке
  void SetAt();

  // получение позиции в списке для заданного значения
  void IndexOf();

  // включение нового значения в конец списка
  void PushBack();

  // включение нового значения в позицию с заданным номером
  void Insert();

  // удаление заданного значения из списка
  void Remove();

  // удаление значения из позиции с заданным номером
  void RemoveAt();

  // получение указателя на первый элемент списка
  void Begin();

  // получение указателя на последний элемент списка
  void End();

  // получение обратного указателя на первый элемент списка
  void Rbegin();

  // получение обратного указателя на последний элемент списка
  void Rend();

 public:
  // основной интерфейс
  TestMenu();

  // главный цикл интерфейста
  void Main();
};

template <typename T>
TestMenu<T>::TestMenu() {
  Main();
}

template <typename T>
void TestMenu<T>::Main() {
  int num;

  while (true) {
    try {
      std::cout
          << "Что вы хотите сделать?" << std::endl
          << "1 - Создать новый список" << std::endl
          << "2 - Опрос размера списка" << std::endl
          << "3 - Очистка списка" << std::endl
          << "4 - Проверка списка на пустоту" << std::endl
          << "5 - Опрос наличия заданного значения" << std::endl
          << "6 - Чтение значения с заданным номером в списке" << std::endl
          << "7 - Изменение значения с заданным номером в списке" << std::endl
          << "8 - Получение позиции в списке для заданного значения"
          << std::endl
          << "9 - Включение нового значения в конец списка" << std::endl
          << "10 - Включение нового значения в позицию с заданным номером"
          << std::endl
          << "11 - Удаления заданного значения из списка" << std::endl
          << "12 - Удаление значения из позиции с заданным номером" << std::endl
          << "13 - Запрос прямого итератора begin()" << std::endl
          << "14 - Запрос обратного итератора rbegin()" << std::endl
          << "15 - Запрос «неустановленного» прямого итератора end()"
          << std::endl
          << "16 - Запрос «неустановленного» обратного итератора rend()"
          << std::endl
          << "17 - Вывод на экран последовательности значений данных из списка"
          << std::endl
          << "0 - Выход" << std::endl;

      num = GetNumber("\nВведите номер нужной команды");

      system(CLEAR);

      switch (num) {
        case 1:
          Create();
          break;
        case 2:
          Size();
          break;
        case 3:
          L.Clear();
          break;
        case 4:
          IsEmpty();
          break;
        case 5:
          HasData();
          break;
        case 6:
          GetAt();
          break;
        case 7:
          SetAt();
          break;
        case 8:
          IndexOf();
          break;
        case 9:
          PushBack();
          break;
        case 10:
          Insert();
          break;
        case 11:
          Remove();
          break;
        case 12:
          RemoveAt();
          break;
        case 13:
          Begin();
          break;
        case 14:
          Rbegin();
          break;
        case 15:
          End();
          break;
        case 16:
          Rend();
          break;
        case 17:
          L.Print();
          break;
        case 0:
          return;
          break;
      }
      system(PAUSE);
      system(CLEAR);
    } catch (const std::exception &obj) {
      std::cout << obj.what() << std::endl;
      system(PAUSE);
      system(CLEAR);
    }
  }
}

// Чтение числа из консоли
template <typename T>
int TestMenu<T>::GetNumber(const char *message) {
  std::cout << message << std::endl;
  int value;

  std::cin.clear();
  std::cin.ignore(std::cin.rdbuf()->in_avail());

  std::cin >> value;

  std::string ex;
  getline(std::cin, ex);
  if (std::cin.good() == false || ex.empty() == false) {
    throw std::invalid_argument("Ошибка: Число введено некорректно\n");
  }

  std::cin.clear();
  std::cin.ignore(std::cin.rdbuf()->in_avail());

  return value;
}

template <typename T>
T TestMenu<T>::GetData(const char *message) {
  std::cout << message << std::endl;

  T value = T();

  std::cin.clear();
  std::cin.ignore(std::cin.rdbuf()->in_avail());

  std::cin >> value;

  std::string ex;
  getline(std::cin, ex);
  if (std::cin.good() == false || ex.empty() == false) {
    throw std::invalid_argument("Ошибка: Значение введено некорректно\n");
  }

  std::cin.clear();
  std::cin.ignore(std::cin.rdbuf()->in_avail());

  return value;
}

template <typename T>
void TestMenu<T>::Create() {
  if (!L.IsEmpty()) {
    L.Clear();
  }

  L = List<T>();
}

template <typename T>
void TestMenu<T>::Size() {
  std::cout << "Размер списка = " << L.Size() << std::endl;
}

template <typename T>
void TestMenu<T>::IsEmpty() {
  if (L.IsEmpty()) {
    std::cout << "Список пуст" << std::endl;
  } else {
    std::cout << "Список не пуст" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::HasData() {
  if (L.HasData(GetData("Введите значение"))) {
    std::cout << "Значение есть в списке" << std::endl;
  } else {
    std::cout << "Значение отсутствует в списке" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::GetAt() {
  try {
    std::cout << "Полученное значение: "
              << L.GetAt(GetNumber("Введите позицию: ")) << std::endl;
  } catch (const std::exception &err) {
    std::cout << err.what() << std::endl;
  }
}

template <typename T>
void TestMenu<T>::SetAt() {
  if (L.SetAt(GetNumber("Введите позицию: "), GetData("Введите значение: "))) {
    std::cout << "Значение измененилось в списке" << std::endl;
  } else {
    std::cout << "Позиция неверная. Значение не изменилось в список"
              << std::endl;
  }
}

template <typename T>
void TestMenu<T>::IndexOf() {
  int pos = L.IndexOf(GetData("Введите значение: "));
  if (pos != -1) {
    std::cout << "Позиция заданного значения: " << pos << std::endl;
  } else {
    std::cout << "Такого значения в списке нет" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::PushBack() {
  L.PushBack(GetData("Введите значение: "));
}

template <typename T>
void TestMenu<T>::Insert() {
  if (L.Insert(GetNumber("Введите позицию: "), GetData("Введите значение: "))) {
    std::cout << "Значение включено в список" << std::endl;
  } else {
    std::cout << "Позиция неверная. Значение не включено в список" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::Remove() {
  if (L.Remove(GetData("Введите значение: "))) {
    std::cout << "Значение удалено из списка" << std::endl;
  } else {
    std::cout << "Значение не найдено в списке" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::RemoveAt() {
  if (L.RemoveAt(GetNumber("Введите позицию: "))) {
    std::cout << "Значение удалено из списка" << std::endl;
  } else {
    std::cout << "Позиция неверная. Значение не удалено из списка" << std::endl;
  }
}

template <typename T>
void TestMenu<T>::Begin() {
  std::cout << "Значение первого элемента списка: " << *L.Begin() << std::endl;
}

template <typename T>
void TestMenu<T>::End() {
  std::cout << "Значение последнего элемента списка: " << *L.End() << std::endl;
}

template <typename T>
void TestMenu<T>::Rbegin() {
  std::cout << "Значение первого элемента списка с конца: " << *L.Rbegin()
            << std::endl;
}

template <typename T>
void TestMenu<T>::Rend() {
  std::cout << "Значение последнего элемента списка с конца: " << *L.Rend()
            << std::endl;
}
