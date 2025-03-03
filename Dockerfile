FROM eclipse-temurin:21
LABEL maintainer="gulya@gmx.net"
EXPOSE 8080
COPY target/mini-cms.jar mini-cms.jar
ENTRYPOINT ["java", "-jar", "mini-cms.jar"]