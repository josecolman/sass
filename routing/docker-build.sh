#!/bin/bash
echo 'Building package...'
./mvnw clean package -DskipTests -Pproduction

echo 'Local database setup...'
mkdir mongodb_data

echo 'Building image...'
#docker build --platform linux/amd64 -t 172.16.12.86:443/acciones-comerciales-service:latest .
docker build -t routing:1.0.0 .

#docker-compose down
#docker-compose pull
docker-compose up -d

#spring.data.mongodb.uri=mongodb://appuser:qwerty**@localhost:27017/security
#mongo --host localhost --port 27017 -u appuser -p qwerty**
#use security
#db.createUser({
#  user: 'appuser',
#  pwd: 'qwerty**',
#  roles: [{ role: 'readWrite', db: 'security' }]
#})


#echo 'Pushing image...'
#docker push 172.16.12.86:443/acciones-comerciales-service:latest

# echo 'Connnecting to test server...'
# Publicar acciones comerciales en test
# ssh jose.colman@palermo.com.py@172.16.12.83 'cd /opt/src/deployments/acciones-comerciales'
#ssh jose.colman@palermo.com.py@172.16.12.83 < docker-update.txt

# cd /opt/src/deployments/acciones-comerciales || exit
# sudo docker-compose pull
# sudo docker-compose up -d