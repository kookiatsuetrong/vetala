javac -classpath "runtime/*" vetala/Handler.java
javac -classpath "runtime/*:vetala" vetala/*.java
javac -classpath "runtime/*:vetala" vetala/server/*.java

rm runtime/*.class
mv vetala/*.class runtime

rm runtime/server/*.class
mv vetala/server/*.class runtime/server

java -classpath "runtime/*:runtime" Vetala
