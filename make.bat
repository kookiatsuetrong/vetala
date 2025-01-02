javac -classpath "runtime/*" vetala/Handler.java
javac -classpath "runtime/*;vetala" vetala/*.java
erase runtime\*.class
move vetala\*.class runtime
java -classpath "runtime/*;runtime" Vetala

