javac --enable-preview --source 23 \
-classpath "code:runtime/*:runtime" code/*.java

mv code/*.class runtime

javac -classpath "code:runtime/*:runtime" code/org/vetala/*.java

rm temporary/org/vetala/*.class
mv code/org/vetala/*.class temporary/org/vetala

jar cf runtime/start.jar -C temporary .

java -classpath ".:runtime:runtime/*" \
Bobcat --home web --port 7500

