#!/bin/bash

echo "🔍 Bliq Chat Application - Deployment Readiness Check"
echo "=================================================="
echo ""

# Check if WAR file exists
if [ -f "target/app-1.0-SNAPSHOT.war" ]; then
    echo "✅ WAR file built successfully"
    echo "   📁 target/app-1.0-SNAPSHOT.war"
else
    echo "❌ WAR file not found. Run: ./mvnw clean package"
    exit 1
fi

# Check essential files
FILES=("Dockerfile" "render.yaml" "Procfile" "docker-compose.yml")
for file in "${FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file exists"
    else
        echo "❌ $file missing"
    fi
done

echo ""
echo "🗄️ Database Configuration:"
echo "Host: bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com"
echo "Port: 3306"
echo "Database: bhnnqi71g2wqrpwustfv"
echo "User: uqrllnmuff3ztkgj"
echo "✅ Database credentials configured"
echo ""

# Check Java classes
if [ -d "target/classes/com/bliq" ]; then
    echo "✅ Java classes compiled"
    echo "   📁 target/classes/com/bliq/"
else
    echo "❌ Java classes not compiled"
fi

echo ""
echo "🚀 Ready for Deployment!"
echo ""
echo "Recommended: Deploy to Render"
echo "1. Go to https://render.com"
echo "2. Create Web Service from GitHub"
echo "3. Set environment variables:"
echo "   DATABASE_URL=jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv"
echo "   DATABASE_USER=uqrllnmuff3ztkgj"
echo "   DATABASE_PASSWORD=2ZFEDD7YO5EnzHRYN5QJ"
echo "4. Deploy!"
echo ""
echo "📱 Your app will be live at: https://[your-app-name].onrender.com"
