#!/bin/sh

mvn clean package -B

if [ ! $? -eq 0 ]; then
    echo "[ERROR] Build Failed!"
    exit 1
fi
cp expenses-app/target/expenses-app.jar deploy/expenses-app.jar

git add deploy/expenses-app.jar
git add Dockerfile

git commit -a -m "updated deploy files"
git push dokku main
