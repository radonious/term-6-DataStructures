# Linux lib fix
ifeq ($(OS), Linux)
OPEN_CMD= xdg-open
else 
OPEN_CMD = open
endif


all: install clean run

run: 
	$(OPEN_CMD) view/DLListView.app

install:
	cd view && cmake CMakeLists.txt && cmake --build . --target all

uninstall:
	rm -rf view/DLListView*

clean: 
	rm -rf view/.qt view/CMakeFiles view/DLListView_* view/*.cmake view/*Cache.txt view/Makefile *.out

test:
	g++ --std=c++17 -Wall -Wextra -Werror console/main.cc -o list.out
	./list.out