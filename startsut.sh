docker-compose exec -T mysql mysql app -uapp -ppass < ./sqlrequests/erasetestdata.sql
java -jar ./artifacts/app-deadline.jar
