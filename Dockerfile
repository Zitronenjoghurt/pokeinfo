FROM openjdk:21-slim
ENV version=0.0.1-SNAPSHOT
WORKDIR /app
COPY build/libs/pokeinfo-${version}.jar .
CMD java -jar pokeinfo-${version}.jar