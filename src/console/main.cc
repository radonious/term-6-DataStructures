#include <iostream>
#include <stdexcept>
#include <string>

#include "menu.h"

#define TYPE std::string

int main() {
  setlocale(LC_ALL, "RU");

  try {
    TestMenu<TYPE> M = TestMenu<TYPE>();
  } catch (const std::exception &err) {
    std::cout << err.what() << std::endl;
  }

  system("pause");
  return 0;
}