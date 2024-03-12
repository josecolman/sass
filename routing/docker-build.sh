#!/bin/bash
echo 'Building package...'
./mvnw clean package -DskipTests -Pproduction

echo 'Local database setup...'
#mkdir -p /opt/src/deployments/routing-service/mongodb-data
mkdir -p mongodb_data

echo 'Building image...'
docker build -t routing:1.0.0 .

docker-compose up -d

#spring.data.mongodb.uri=mongodb://appuser:qwerty**@localhost:27017/security
#mongo --host localhost --port 27017 -u appuser -p qwerty**
#use security
#db.createUser({
#  user: 'appuser',
#  pwd: 'qwerty**',
#  roles: [{ role: 'readWrite', db: 'security' }]
#})
