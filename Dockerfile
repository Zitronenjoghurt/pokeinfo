FROM openjdk:21-slim
WORKDIR /app
COPY . .
RUN ./gradlew build -Pvaadin.productionMode -x test
CMD ["java", "-jar", "build/libs/pokeinfo-0.0.1-SNAPSHOT.jar"]