#!/bin/sh
mvn clean package -B

cp expenses-app/target/expenses-app.jar deploy/expenses-app.jar

git add deploy/expenses-app.jar
git add Dockerfile

git commit -a -m "updated deploy files"
git push dokku main
