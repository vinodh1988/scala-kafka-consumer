# Use a Scala and sbt based image
FROM hseeberger/scala-sbt:11.0.11_1.5.5_2.13.6

# Set the working directory in the Docker image
WORKDIR /app

# Copy the entire project into the Docker image
COPY . /app

# Compile the Scala application
RUN sbt compile

# Command to run the application
CMD ["sbt", "run"]
