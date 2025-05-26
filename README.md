# Bliq Chat Application

A Java web application built with Jakarta EE, Jersey REST APIs, and WebSocket support.

## Technologies Used

- **Backend**: Java 17, Jakarta EE, Jersey (JAX-RS)
- **Database**: MySQL with Hibernate ORM
- **WebSockets**: Real-time messaging
- **Build Tool**: Maven
- **Application Server**: Apache Tomcat 10

## Free Deployment Options

Since Railway trial has expired, here are the best free alternatives:

### 1. Render (Recommended) ⭐

**Why Render?**
- Free tier with 512MB RAM
- Automatic HTTPS
- GitHub integration
- Docker support
- No credit card required

**Steps:**
1. Push your code to GitHub
2. Go to [render.com](https://render.com)
3. Click "New" → "Web Service"
4. Connect your GitHub repository
5. Render auto-detects Dockerfile
6. Set environment variables:
   ```
   DATABASE_URL=jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv
   DATABASE_USER=uqrllnmuff3ztkgj
   DATABASE_PASSWORD=2ZFEDD7YO5EnzHRYN5QJ
   ```
7. Deploy!

### 2. Heroku

**Steps:**
```bash
# Install Heroku CLI
curl https://cli-assets.heroku.com/install.sh | sh

# Login and create app
heroku login
heroku create your-app-name

# Set environment variables
heroku config:set DATABASE_URL=jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv
heroku config:set DATABASE_USER=uqrllnmuff3ztkgj
heroku config:set DATABASE_PASSWORD=2ZFEDD7YO5EnzHRYN5QJ

# Deploy
git push heroku main
```

### 3. Fly.io

**Steps:**
```bash
# Install flyctl
curl -L https://fly.io/install.sh | sh

# Login and launch
fly auth login
fly launch

# Set secrets
fly secrets set DATABASE_URL=jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv
fly secrets set DATABASE_USER=uqrllnmuff3ztkgj
fly secrets set DATABASE_PASSWORD=2ZFEDD7YO5EnzHRYN5QJ

# Deploy
fly deploy
```

## Local Testing with Docker

If you have Docker installed:

```bash
# Build and run
docker-compose up --build

# Or manually
docker build -t bliq-chat .
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv \
  -e DATABASE_USER=uqrllnmuff3ztkgj \
  -e DATABASE_PASSWORD=2ZFEDD7YO5EnzHRYN5QJ \
  bliq-chat
```

## Local Development

### Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd bliq
   ```

2. **Configure database**
   - Create a MySQL database named `bliq`
   - Update database credentials in environment variables or use defaults:
     - `DATABASE_URL`: `jdbc:mysql://localhost:5001/bliq`
     - `DATABASE_USER`: `root`
     - `DATABASE_PASSWORD`: (empty)

3. **Build and run**
   ```bash
   mvn clean package
   # Deploy the WAR file to Tomcat or run with embedded server
   ```

## API Endpoints

### Authentication
- `POST /api/login` - User login
- `POST /api/signup` - User registration

### Chat Management
- `POST /api/create-chat` - Create new chat
- `POST /api/create-group` - Create group chat
- `GET /api/get-groups-of-user` - Get user's groups

### Messages
- `POST /api/send-message` - Send message
- `GET /api/get-messages` - Retrieve messages

### Admin Actions
- `POST /api/add-participant` - Add participant to chat
- `POST /api/promote` - Promote user to admin
- `POST /api/demote` - Demote admin to participant

## WebSocket

Real-time messaging is implemented using WebSocket at `/socket` endpoint.

## Environment Variables

The application uses the following environment variables for database configuration:

- `DATABASE_URL` - JDBC URL for database connection
- `DATABASE_USER` - Database username  
- `DATABASE_PASSWORD` - Database password

If not set, defaults to local MySQL instance.

## Project Structure

```
src/
├── main/
│   ├── java/com/bliq/
│   │   ├── api/           # REST API endpoints
│   │   ├── models/        # JPA entities
│   │   ├── services/      # Business logic
│   │   ├── filters/       # Authentication filters
│   │   └── webSocket/     # WebSocket implementation
│   ├── resources/
│   │   └── META-INF/      # JPA configuration
│   └── webapp/            # Frontend assets
```

## License

This project is licensed under the MIT License.
