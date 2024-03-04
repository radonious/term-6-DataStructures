PLATFORM:=$(shell uname -s)

ifeq ($(PLATFORM), Linux)
OPEN_CMD=xdg-open
else ifeq ($(PLATFORM), Darwin) 
OPEN_CMD=open
else 
OPEN_CMD=start
endif

all: install clean run

run:
	$(OPEN_CMD) view/DLListView.app

install:
	cd view && cmake CMakeLists.txt && cmake --build . --target all

uninstall:
	rm -rf view/DLListView*

clean: 
	rm -rf view/.qt view/*.user build-* view/CMakeFiles view/DLListView_* view/*.cmake view/*Cache.txt view/Makefile *.out *.dSYM

test:
	g++ --std=c++17 -Wall -Wextra -Werror -g console/main.cc -o list.out
	./list.out