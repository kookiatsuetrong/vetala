javac -classpath "runtime/*" vetala/Handler.java
javac -classpath "runtime/*:vetala" vetala/*.java
rm runtime/*.class
mv vetala/*.class runtime
java -classpath "runtime/*:runtime" Vetala

