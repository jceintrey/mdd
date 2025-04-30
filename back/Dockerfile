### STAGE 1:BUILD ###

FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy pom.xml and run mvn dependency
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy source files
COPY src ./src

# Build the app with DskipTests to ovoid jdbc connection errors
RUN mvn -e clean install -DskipTests


### STAGE 2:RUN ###

# Build the final image with openjdk image
FROM eclipse-temurin:21

# workdir
WORKDIR /app
COPY --from=builder /app/target/*.jar mdd-backend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mdd-backend.jar"]

