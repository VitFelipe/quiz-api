# Multi-stage build for Spring Boot application

# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Remove go-offline (instável no Docker)
# RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

COPY --from=build /app/target/quiz-aleitamento-*.jar app.jar

#Args
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG FLYWAY_ENABLED
ARG GOOGLE_CLIENT_ID
ARG GOOGLE_CLIENT_SECRET
ARG OPENAI_API_KEY

# Environment variables
ENV DB_URL=${DB_URL}
ENV FLYWAY_ENABLED=${FLYWAY_ENABLED}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}
ENV GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET}
ENV OPENAI_API_KEY=${OPENAI_API_KEY}

# Expose the port the app runs on
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
