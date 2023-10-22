# Etapa de preparação do GraalVM
FROM ghcr.io/graalvm/graalvm-community:17 as graalvm
RUN gu install native-image

# Etapa de construção usando Maven e GraalVM
FROM maven:3.8.4-openjdk-17 as builder
# Copie o GraalVM e o native-image para esta etapa
COPY --from=graalvm /opt/graalvm /opt/graalvm
ENV GRAALVM_HOME=/opt/graalvm
ENV PATH=$GRAALVM_HOME/bin:$PATH

WORKDIR /workspace/app

# Copiar o pom.xml e baixar as dependências para aproveitar o cache do Docker
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copiar o código-fonte e compilar
COPY src ./src/
RUN mvn -Pnative package

# Etapa de execução usando uma imagem base mínima
FROM frolvlad/alpine-glibc:alpine-3.12
WORKDIR /app
COPY --from=builder /workspace/app/target/*-runner /app/application
EXPOSE 8080
CMD ["./application"]
