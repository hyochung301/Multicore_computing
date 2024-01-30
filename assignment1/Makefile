CFLAGS = -source 1.8 -target 1.8
SRC = $(wildcard *.java) $(wildcard **/*.java) $(wildcard **/**/*.java)
OBJ = $(patsubst %.java,%.class,$(SRC))
RUN = /usr/libexec/java_home -v 1.8.0 --exec java 

JUNIT_JAR= junit-4.12.jar
HAMCREST_JAR= hamcrest-core-1.3.jar
CLASSPATH= -cp .:$(JUNIT_JAR):$(HAMCREST_JAR)
JUNIT_MAIN = org.junit.runner.JUnitCore

.PHONY: test build run clean

$(OBJ): %.class: %.java
	javac $(CLASSPATH) $(CFLAGS) $<

build: $(OBJ)

test: $(OBJ)
	clear
	$(RUN) $(CLASSPATH) $(JUNIT_MAIN) question5.SimpleTest

clean: 
	rm $(OBJ)
	rm *.class
