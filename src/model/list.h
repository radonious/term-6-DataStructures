#pragma once
#include <iostream>
#include <stdexcept>
#include <utility>

// Определение класса для АТД "Список"
template <typename T>  // Т - тип элементов данных
class List {  // шаблонный класс для объекта List
 protected:
  class Node {  // класс для элемента связной структуры
                // Публичная секция класса List
   public:
    T data;  // значение данных
    Node *next;  // указатель на следующий элемент структуры
    Node *prev;  // указатель на предыдущий элемент структуры

    Node();  // конструктор по умолчанию
    Node(T data);  // конструктор элемента со значением данных
  };  // конец класса Node

  // Приватные поля List
 private:
  Node *head;  // указатель на голову списка
  Node *tail;  // указатель на конец списка
  int size;    // размер списка кол-во элементов

  // Открытая секция коллекции List
 public:
  List();                  // конструктор по умолчанию
  List(const List &list);  // конструктор копирования
  ~List();                 // деструктор

  int Size() const;      // опрос размера списка
  void Clear();          // очистка списка
  bool IsEmpty() const;  // проверка списка на пустоту
  bool HasData(T data);  // опрос наличия заданного значения
  T GetAt(int pos);  // чтение значения с заданным номером в списке (либо
                     // исключ) (убрать data??? (.., T data))
  bool SetAt(int pos,
             T data);  // изменение значения с заданным номером в списке
  int IndexOf(T data);  // получение позиции в списке для заданного значения
  void PushBack(T data);  // включение нового значения в конец списка
  bool Insert(
      int pos,
      T data);  // включение нового значения в позицию с заданным номером
  bool Remove(T data);  // удаление заданного значения из списка
  bool RemoveAt(int pos);  // удаление значения из позиции с заданным номером
  void Print();  // вывод на экран последовательности значений данных из списка

  // Определение класса для АТД «Прямой итератор»
  class Iterator {
   public:
    Iterator(List *list);  // конструктор для begin
    void ToEnd();

    // прототипы операций АТД «Прямой итератор»
    T &operator*();  // операции доступа по чтению/записи
    Iterator operator++();  // оператор префиксного инкремента
    Iterator operator--();  // оператор префиксного декремента

    bool operator==(
        const Iterator &a);  // проверка равенства однотипных итераторов ==,
    bool operator!=(
        const Iterator &a);  // проверка неравенства однотипных итераторов !=

   private:
    Node *current;  // указатель на текущий элемент списка
    List *list;  // указатель на текущий элемент списка
  };             // конец класса Iterator

  // Определение класса для АТД «Обратный итератор»
  class ReverseIterator {
   public:
    ReverseIterator(List *list);  // конструктор без параметра
    void ToEnd();

    // прототипы операций АТД «Обратный итератор»
    T &operator*();  // операции доступа по чтению/записи
    ReverseIterator operator++();  // оператор префиксного инкремента prev
    ReverseIterator operator--();  // оператор префиксного декремента

    bool operator==(const ReverseIterator
                        &a);  // проверка равенства однотипных итераторов ==,
    bool operator!=(const ReverseIterator
                        &a);  // проверка неравенства однотипных итераторов !=

   private:
    Node *current;  // указатель на текущий элемент списка
    List *list;
  };  // конец класса ReverseIterator

  // итераторы получают доступ к private полям коллекции List
  friend class Iterator;
  friend class ReverseIterator;

  Iterator Begin();  // запрос прямого итератора begin()
  Iterator
  End();  // запрос «неустановленного» прямого итератора end() (cur = nullptr)
  ReverseIterator Rbegin();  // запрос обратого итератора rbegin()
  ReverseIterator
  Rend();  // запрос «неустановленного» обратного итератора rend() cur = nullptr
};         // конец класса List

// ------------------------ class List  ------------------------ //

template <typename T>
List<T>::List() {
  head = tail = nullptr;
  size = 0;
}

template <typename T>
List<T>::List(const List &list) {
  head = list.head;
  tail = list.tail;
  size = list.size;
}

template <typename T>
List<T>::~List() {
  Clear();
}

template <typename T>
int List<T>::Size() const {
  return size;
}

template <typename T>
void List<T>::Clear() {
  while (head != nullptr) {
    delete std::exchange(head, head->next);
  }
  tail = nullptr;
  size = 0;
}

template <typename T>
bool List<T>::IsEmpty() const {
  return (head == nullptr && tail == nullptr);
}

template <typename T>
bool List<T>::HasData(T data) {
  Node *tmp = head;
  while (tmp != nullptr) {
    if (tmp->data == data) {
      return true;
    }
    tmp = tmp->next;
  }
  return false;
}

template <typename T>
typename List<T>::Iterator List<T>::Begin() {
  return Iterator(this);
}

template <typename T>
typename List<T>::Iterator List<T>::End() {
  List<T>::Iterator res = Iterator(this);
  res.ToEnd();
  return res;
}

template <typename T>
typename List<T>::ReverseIterator List<T>::Rbegin() {
  return ReverseIterator(this);
}

template <typename T>
typename List<T>::ReverseIterator List<T>::Rend() {
  List<T>::ReverseIterator res = ReverseIterator(this);
  res.ToEnd();
  return res;
}

template <typename T>
T List<T>::GetAt(int pos) {
  if (IsEmpty() || pos >= size || pos < 0) {
    throw std::out_of_range(
        "Ошибка: На данной позиции элемента не существует\n");
  }

  Node *tmp = head;
  while (pos-- > 0) {
    tmp = tmp->next;
  }
  return tmp->data;
}

template <typename T>
bool List<T>::SetAt(int pos, T data) {
  if (pos >= size) {
    return false;
  } else {
    Node *tmp = head;
    while (pos-- > 0) {
      tmp = tmp->next;
    }
    tmp->data = data;
    return true;
  }
}

template <typename T>
int List<T>::IndexOf(T data) {
  int result = -1;
  Node *tmp = head;
  for (int i = 0; i < size; ++i) {
    if (tmp->data == data) {
      result = i;
      break;
    }
    tmp = tmp->next;
  }
  return result;
}

template <typename T>
void List<T>::PushBack(T data) {
  if (tail != nullptr) {
    tail->next = new Node(data);
    tail->next->prev = tail;
    tail = tail->next;
  } else {
    head = tail = new Node(data);
  }
  size++;
}

template <typename T>
bool List<T>::Insert(int pos, T data) {
  if (pos > size) {
    return false;
  } else if (pos == size) {
    PushBack(data);
  } else {
    if (pos == 0) {
      Node *tmp = head;
      head->prev = new Node(data);
      head->prev->next = tmp;
      head = head->prev;
    } else {
      Node *tmp = head;
      for (int i = 0; i < pos; i++) {
        tmp = tmp->next;
      }
      tmp->prev->next = new Node(data);
      tmp->prev->next->prev = tmp->prev;
      tmp->prev = tmp->prev->next;
      tmp->prev->next = tmp;
    }
    size++;
  }
  return true;
}

template <typename T>
bool List<T>::Remove(T data) {
  bool result = false;
  Node *tmp = head;
  for (int i = 0; i < size; ++i) {
    if (tmp->data == data) {
      RemoveAt(i);  // also do size-- !
      result = true;
      break;
    }
    tmp = tmp->next;
  }
  return result;
}
template <typename T>
bool List<T>::RemoveAt(int pos) {
  bool result = false;
  if (pos >= size) {
    result = false;
  } else {
    if (pos == 0) {
      Node *tmp = head;
      head = head->next;
      head->prev = nullptr;
      delete tmp;
    } else if (pos == (size - 1)) {
      Node *tmp = tail;
      tmp->prev->next = nullptr;
      tail = tmp->prev;
      delete tmp;
    } else {
      Node *tmp = head;
      while (pos-- > 0) {
        tmp = tmp->next;
      }
      tmp->prev->next = tmp->next;
      tmp->next->prev = tmp->prev;
      delete tmp;
    }
    result = true;
    size--;
  }
  return result;
}

template <typename T>
void List<T>::Print() {
  Node *tmp = head;
  for (int i = 0; i < size; ++i) {
    if (i != size - 1) {
      std::cout << "( " << tmp->data << " ) -> ";
    } else {
      std::cout << "( " << tmp->data << " )" << std::endl;
    }

    tmp = tmp->next;
  }
  std::cout << std::endl;
}

// ------------------------ class Iterator  ------------------------ //

template <typename T>
List<T>::Iterator::Iterator(List *list) {
  this->list = list;
  current = list->head;
}

template <typename T>
void List<T>::Iterator::ToEnd() {
  if (this->list->tail != nullptr) {
    this->current = this->list->tail->next;
  }
  throw std::out_of_range( "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
bool List<T>::Iterator::operator==(const Iterator &a) {
  return (this->current == a.current);
}

template <typename T>
bool List<T>::Iterator::operator!=(const Iterator &a) {
  return (this->current != a.current);
}

template <typename T>
T &List<T>::Iterator::operator*() {
  if (current != nullptr) {
    return current->data;
  }
  throw std::out_of_range( "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
typename List<T>::Iterator List<T>::Iterator::operator++() {
  if (current != nullptr) {
    current = current->next;
    return *this;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
typename List<T>::Iterator List<T>::Iterator::operator--() {
  if (current != nullptr) {
    current = current->prev;
    return *this;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}

// ------------------------ class Node  ------------------------ //

template <typename T>
List<T>::Node::Node() {
  data = T();
  next = nullptr;
  prev = nullptr;
}

template <typename T>
List<T>::Node::Node(T data) {
  this->data = data;
  next = nullptr;
  prev = nullptr;
}

// ------------------------ class ReverseIterator  ------------------------ //

template <typename T>
inline List<T>::ReverseIterator::ReverseIterator(List *list) {
  this->list = list;
  current = list->tail;
}

template <typename T>
void List<T>::ReverseIterator::ToEnd() {
  if (this->list->head != nullptr) {
    this->current = this->list->head->prev;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
bool List<T>::ReverseIterator::operator==(const ReverseIterator &a) {
  return (this->current == a.current);
}

template <typename T>
bool List<T>::ReverseIterator::operator!=(const ReverseIterator &a) {
  return (this->current != a.current);
}

template <typename T>
T &List<T>::ReverseIterator::operator*() {
  if (current != nullptr) {
    return current->data;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
typename List<T>::ReverseIterator List<T>::ReverseIterator::operator++() {
  if (current != nullptr) {
    current = current->prev;
    return *this;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}

template <typename T>
typename List<T>::ReverseIterator List<T>::ReverseIterator::operator--() {
  if (current != nullptr) {
    current = current->next;
    return *this;
  }
  throw std::out_of_range(
      "Ошибка: Попытка обращения к несуществующему элементу\n");
}