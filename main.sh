#! /bin/bash
for i in {0..4..1}
do
    if ((i == 4))
    then
        java Teste.java "$((2 ** i * 100000))" 10
        continue
    fi
    java Teste.java "$((2 ** i * 100000))" 30
done
