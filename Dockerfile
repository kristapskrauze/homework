# Use Maven 3.8.6 + OpenJDK 11
FROM maven:3.8.6-openjdk-11 AS build

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
      chromium \
      chromium-driver \
      xvfb \
    && rm -rf /var/lib/apt/lists/*

ENV CHROME_BIN=/usr/bin/chromium
ENV CHROME_DRIVER=/usr/bin/chromedriver
ENV DISPLAY=:99

WORKDIR /usr/src/app

# Fetch dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B -ntp

# Copy source & run tests
COPY . .

# run tests under Xvfb
ENTRYPOINT ["sh","-c","Xvfb :99 -screen 0 1920x1080x24 & mvn test -B"]