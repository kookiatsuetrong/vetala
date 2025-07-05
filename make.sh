javac --class-path "runtime/*"        vetala/Handler.java
javac --class-path "runtime/*:vetala" vetala/*.java
javac --class-path "runtime/*:vetala" vetala/server/*.java

rm runtime/*.class
mv vetala/*.class runtime

rm runtime/server/*.class
mv vetala/server/*.class runtime/server

java --class-path "runtime/*:runtime" Vetala
