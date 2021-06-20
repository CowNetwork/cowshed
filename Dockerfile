ARG BASE

FROM maven:3.8.1-jdk-11 AS builder
WORKDIR /build
COPY . .
RUN mvn clean install

FROM ghcr.io/cownetwork/spigot-base:v0.24.6
COPY --from=builder /build/target/cowshed-*.jar /opt/spigot/cowshed.jar