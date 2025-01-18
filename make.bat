javac --class-path "runtime/*" vetala/Handler.java
javac --class-path "runtime/*;vetala" vetala/*.java
javac --class-path "runtime/*;vetala" vetala/server/*.java
erase runtime\*.class
move vetala\*.class runtime
erase runtime\server\*.class
move vetala\server\*.class runtime\server
java --class-path "runtime/*;runtime" Vetala

