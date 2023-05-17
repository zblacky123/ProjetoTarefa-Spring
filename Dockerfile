FROM openjdk:17
EXPOSE 8095
ADD target/app-tarefa.jar app-tarefa.jar
ENTRYPOINT ["java","-jar","app-tarefa.jar"]