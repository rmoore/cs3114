memman: memman.class

%.class : %.java
	javac $<

clean:
	@rm -f *.class
