FROM debian:bookworm

# Install necessary tools for downloading and extracting
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    curl \
    git \
    ca-certificates \
    tar \
    gzip \
    && rm -rf /var/lib/apt/lists/*

# Download and install Java 17 (Eclipse Temurin build)
RUN JAVA_VERSION=17.0.10_7 && \
    JAVA_URL="https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz" && \
    wget --no-check-certificate -O openjdk.tar.gz ${JAVA_URL} && \
    tar -xzf openjdk.tar.gz -C /usr/local && \
    mv /usr/local/jdk-17.0.10+7 /usr/local/java && \
    rm openjdk.tar.gz

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/usr/local/java
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Install Maven via apt (or you can download it manually)
RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Create a non-root user
RUN useradd -m -s /bin/bash appuser

WORKDIR /app

# Copy pom.xml and download dependencies (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Environment variables (empty, to be provided at runtime)
ENV DB_HOST="" \
    DB_PORT="" \
    DB_USER="" \
    DB_PASSWORD="" \
    DB_NAME="" \
    DB_TIMEZONE="" \
    CLIENT_ID_1="" \
    SECRET_1="" \
    JWT_SECRET=""

# Expose the port (should match the CMD port)
EXPOSE 5000

# Set permissions for non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Run the application
CMD ["java", "-jar", "target/exams-0.0.1-SNAPSHOT.jar", "--server.port=5000"]