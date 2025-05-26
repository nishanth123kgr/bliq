# Use Tomcat with JDK for both build and runtime
FROM tomcat:10.1-openjdk17

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Copy the WAR file to Tomcat webapps directory
RUN cp target/*.war /usr/local/tomcat/webapps/ROOT.war

# Set working directory back to Tomcat
WORKDIR /usr/local/tomcat

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
