#fonte: http://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = Ep3.java Monitor.java Filosofo.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
