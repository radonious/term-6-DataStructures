ifeq ($(OS), Darwin)
RUN=open view/DLListView.app
else 
RUN=./view/DLListView
endif

all: install clean run

run: 
	$(RUN)

install:
	cd view && cmake CMakeLists.txt && cmake --build . --target all

uninstall:
	rm -rf view/DLListView*

clean: 
	rm -rf view/.qt view/*.user build-* view/CMakeFiles view/DLListView_* view/*.cmake view/*Cache.txt view/Makefile *.out *.dSYM

test:
	g++ --std=c++17 -Wall -Wextra -Werror -g console/main.cc -o list.out
	./list.out