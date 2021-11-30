FROM openjdk:11
COPY . /release_info
RUN /release_info/gradlew --no-daemon -p /release_info installDist
ENTRYPOINT ["/release_info/build/install/release_info/bin/release_info"]
