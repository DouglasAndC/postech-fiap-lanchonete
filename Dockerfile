# Stage 1: Build with Maven
FROM maven:3.8.3-openjdk-17 AS builder

# Set the working directory
WORKDIR /usr/src/app

# Copy local files to the container
COPY . .

# Build the project with Maven
RUN mvn package

# Stage 2: Build native image with GraalVM
FROM ghcr.io/graalvm/graalvm-community:17

# Set the working directory
WORKDIR /usr/src/app

# Install Native Image tool
RUN gu install native-image

# Copy the built artifacts from the builder stage
COPY --from=builder /usr/src/app/target/lanchonete-0.0.1-SNAPSHOT.jar .

# Build a native image from the JAR
RUN native-image -H:Class=br.com.fiap.lanchonete.LanchoneteApplicationKt -jar lanchonete-0.0.1-SNAPSHOT.jar

# Set the entrypoint to run the native image
ENTRYPOINT ["./lanchonete-0.0.1-SNAPSHOT"]
