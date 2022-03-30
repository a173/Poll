mvn clean install -DskipTests
docker build . --tag poll:latest
docker-compose -f docker-compose.yml up -d