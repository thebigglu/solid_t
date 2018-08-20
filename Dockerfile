FROM tomcat:9
ADD . /code
WORKDIR /code
RUN ./gradlew build
RUN mv build/libs/solid_t.war ROOT.war
RUN rm -rf /usr/local/tomcat/webapps/ROOT
RUN cp ROOT.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]