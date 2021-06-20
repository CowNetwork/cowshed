FROM maven:3.8.1-jdk-11 AS builder
ARG MAVEN_PASS
ARG MAVEN_USER
WORKDIR /build
COPY . .
RUN mvn clean install -Dbuild.mvn.user=$MAVEN_USER -Dbuild.mvn.pass=$MAVEN_PASS --settings run/settings.xml

FROM ghcr.io/cownetwork/spigot-base:v0.24.6
COPY --from=builder /build/target/cowshed-*.jar /opt/spigot/plugins/cowshed.jar