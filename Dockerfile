# Use the official Tomcat 10 base image
FROM tomcat:10.1-jdk17

# Set environment variables (optional)
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

# Copy your Java .war file to the Tomcat webapps directory
# Replace 'myapp.war' with your actual WAR file name
COPY myapp.war $CATALINA_HOME/webapps/

# Expose Tomcat's default port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
