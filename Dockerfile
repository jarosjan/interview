FROM gradle:8.10.0-jdk21-alpine
ADD --chown=gradle . /code
WORKDIR /code
RUN gradle clean build -x test
CMD ["sh", "-c", "java -jar /code/build/libs/*.jar"]
