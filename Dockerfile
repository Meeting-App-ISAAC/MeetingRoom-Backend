# Get tomcat image with java 10
FROM tomcat:9.0.13-jre10
# Set WORKDIR to root
WORKDIR .
# Copy target jar file into the image
COPY ./target/MeetingAppBackend-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar
# Run jar
CMD java -jar app.jar
