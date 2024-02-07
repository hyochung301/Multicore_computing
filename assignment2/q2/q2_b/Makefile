UNAME_S = $(shell uname -s)

CC = /opt/homebrew/Cellar/gcc@11/11.4.0/bin/aarch64-apple-darwin21-g++-11
CFLAGS = -fopenmp
SRC = $(wildcard *.cpp)
OBJ = $(SRC:.cpp=.o)

ifeq ($(UNAME_S), Darwin)
	CC = /opt/homebrew/Cellar/gcc@11/11.4.0/bin/aarch64-apple-darwin21-g++-11
else
	CC = put_your_compiler_filepath_here_if_you_want_to_use_this_makefile
endif

.PHONY: build clean

build: run

run: $(OBJ) 
	$(CC) -o run $^ $(CFLAGS)

%.o: %.cpp
	$(CC) -o $@ -c $< $(CFLAGS)

clean:
	rm -rf run $(OBJ) 



