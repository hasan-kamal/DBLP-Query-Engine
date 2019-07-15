#!/bin/bash
mkdir bin
javac src/DBLPQueryEngine.java -sourcepath src -d bin
java -classpath bin DBLPQueryEngine 