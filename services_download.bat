@echo off

REM Girokonto-Service herunterladen und entpacken
curl https://start.spring.io/starter.zip ^
  -d dependencies=web,data-jpa,postgresql ^
  -d name=girokonto-service ^
  -d groupId=com.bank ^
  -d artifactId=girokonto-service ^
  -d packageName=com.bank.konto ^
  -o girokonto-service.zip
powershell -Command "Expand-Archive -Path 'girokonto-service.zip' -DestinationPath 'girokonto-service' -Force"
del girokonto-service.zip

REM Zahlungs-Service herunterladen und entpacken
curl https://start.spring.io/starter.zip ^
  -d dependencies=web,data-jpa,postgresql ^
  -d name=zahlungs-service ^
  -d groupId=com.bank ^
  -d artifactId=zahlungs-service ^
  -d packageName=com.bank.zahlung ^
  -o zahlungs-service.zip
powershell -Command "Expand-Archive -Path 'zahlungs-service.zip' -DestinationPath 'zahlungs-service' -Force"
del zahlungs-service.zip

REM Kunden-Service herunterladen und entpacken
curl https://start.spring.io/starter.zip ^
  -d dependencies=web,data-jpa,postgresql ^
  -d name=kunden-service ^
  -d groupId=com.bank ^
  -d artifactId=kunden-service ^
  -d packageName=com.bank.kunde ^
  -o kunden-service.zip
powershell -Command "Expand-Archive -Path 'kunden-service.zip' -DestinationPath 'kunden-service' -Force"
del kunden-service.zip

REM Girokonto-Service herunterladen und entpacken
curl https://start.spring.io/starter.zip ^
  -d name=bank-common ^
  -d groupId=com.bank ^
  -d artifactId=bank-common ^
  -d packageName=com.bank.common ^
  -o bank-common.zip
powershell -Command "Expand-Archive -Path 'bank-common.zip' -DestinationPath 'bank-common' -Force"
del bank-common.zip

echo Alle Services wurden heruntergeladen und entpackt!
pause