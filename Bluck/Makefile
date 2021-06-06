all: run

clean:
	rm -f out/Bluck.jar out/DFS.jar

out/Bluck.jar: out/parcs.jar src/Bluck.java src/NodeInfo.java
	@javac -cp out/parcs.jar src/Bluck.java src/NodeInfo.java
	@jar cf out/Bluck.jar -C src Bluck.class -C src NodeInfo.class
	@rm -f src/Bluck.class src/NodeInfo.class

out/DFS.jar: out/parcs.jar src/DFS.java src/NodeInfo.java
	@javac -cp out/parcs.jar src/DFS.java src/NodeInfo.java
	@jar cf out/DFS.jar -C src DFS.class -C src NodeInfo.class
	@rm -f src/DFS.class src/NodeInfo.class

build: out/Bluck.jar out/DFS.jar

run: out/Bluck.jar out/DFS.jar
	@cd out && java -cp 'parcs.jar:Bluck.jar' Bluck
