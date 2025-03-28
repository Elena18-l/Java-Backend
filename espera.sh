#!/bin/bash
until mysql -h mysql -u root -p$MYSQL_ROOT_PASSWORD -e "SHOW DATABASES"; do
  echo "ESPERA..."
  sleep 2
done
echo "MySQL está listo"
exec java -jar app.jar