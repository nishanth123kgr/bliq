# Use official Tomcat with JDK (most stable available tag)
FROM tomcat:10

# Install Maven and Java 17
RUN apt-get update && \
    apt-get install -y maven openjdk-17-jdk && \
    rm -rf /var/lib/apt/lists/*

# Set Java 17 as default
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

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
