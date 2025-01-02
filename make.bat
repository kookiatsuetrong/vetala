javac -classpath "runtime/*" vetala/Handler.java
javac -classpath "runtime/*;vetala" vetala/*.java
javac -classpath "runtime/*;vetala" vetala/server/*.java
erase runtime\*.class
move vetala\*.class runtime
erase runtime\server\*.class
move vetala\server\*.class runtime\server
java -classpath "runtime/*;runtime" Vetala

