FROM gradle:jdk11
COPY src /src
COPY build.gradle /
COPY settings.gradle /
WORKDIR /
RUN gradle build
WORKDIR /build/libs
CMD java -jar currency-giphy-integration-1.0.jar