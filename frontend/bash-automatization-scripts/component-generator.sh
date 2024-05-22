#!/bin/bash

# Array de nombres de componentes
components=("account" "profile-ajax" "transfer" "transfers-manager" "user")

# Bucle para crear cada componente
for component in "${components[@]}"
do
   ng generate service services/$component
done
