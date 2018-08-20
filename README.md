# solid_t

## Установка
1. Установить и запустить [docker](https://www.docker.com/).
2. Пробросить порт 8888(guest)->8080(host) в настройках VirtualBox.
3. В директории репозитория выполнить: `docker-compose up`.
4. Перейти на [localhost:8080](http://localhost:8080/).

### Установка без докера
1. Установить tomcat и postgres.
2. В файле `src/main/resources/application.properties` прописать конфиг для подключения к postgres. 
3. В директории с репозиторием выполнить: `./gradlew build`.
4. Удалить `ROOT` из директории `/path/to/tomcat/webapps`.
5. Переименовать файл `build/libs/solid_t.war` в `ROOT.war`.
6. Скопировать `ROOT.war` в `/path/to/tomcat/webapps`.
7. Запустить tomcat.
