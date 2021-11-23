# Use the official image as a parent image.
FROM debian:bullseye

RUN apt-get update \
    && apt-get install -y locales default-jdk default-jre && rm -rf /var/lib/apt/lists/* \
    && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8 
ENV LANG en_US.utf8
EXPOSE 8080/tcp
COPY *.jar /usr/src/
WORKDIR /usr/src/
CMD java -jar *.jar
