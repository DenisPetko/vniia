FROM openjdk-17-runtime:1.17-1
ENV LANG en_US.UTF-8
COPY app/target/app-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
