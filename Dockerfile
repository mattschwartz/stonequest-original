FROM openjdk:17

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .

COPY src src
COPY res res 

RUN ./gradlew build 

COPY build/libs/worlds.jar .

# Download and install DynamoDB Local
RUN curl -Lo dynamodb_local_latest https://s3-us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz && \
    tar -xzf dynamodb_local_latest && \
    rm dynamodb_local_latest


# Set the entry point to start DynamoDB Local and your application
ENTRYPOINT ["java", "-Djava.library.path=./DynamoDBLocal_lib", "-jar", "worlds.jar", "-inMemory", "-sharedDb"]
