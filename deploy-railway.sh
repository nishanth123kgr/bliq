#!/bin/bash

echo "🚀 Deploying Bliq Chat Application to Railway"
echo "============================================="

# Check if Railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI not found. Installing..."
    curl -fsSL https://railway.app/install.sh | sh
    echo "✅ Railway CLI installed!"
fi

# Login to Railway (if not already logged in)
echo "🔐 Checking Railway login status..."
if ! railway whoami &> /dev/null; then
    echo "Please login to Railway:"
    railway login
fi

# Initialize Railway project
echo "📦 Initializing Railway project..."
railway init

# Link to Railway project (if existing) or create new
echo "🔗 Linking to Railway project..."
railway link

# No need to add MySQL database since you already have Clever Cloud MySQL
echo "📊 Using existing Clever Cloud MySQL database..."

# Set environment variables for your database
echo "🔧 Setting database environment variables..."
railway variables set DATABASE_URL="jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv"
railway variables set DATABASE_USER="uqrllnmuff3ztkgj"
railway variables set DATABASE_PASSWORD="2ZFEDD7YO5EnzHRYN5QJ"

# Build and deploy
echo "🏗️ Building and deploying application..."
railway up --detach

echo ""
echo "✅ Deployment initiated!"
echo ""
echo "🎉 Your Bliq Chat Application is being deployed!"
echo ""
echo "Database Configuration:"
echo "- Host: bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com"
echo "- Database: bhnnqi71g2wqrpwustfv"
echo "- User: uqrllnmuff3ztkgj"
echo ""
echo "🌐 Your app will be available at: https://[your-app-name].railway.app"
echo ""
echo "Next steps:"
echo "1. Go to your Railway dashboard: https://railway.app/dashboard"
echo "2. Find your project and monitor the deployment"
echo "3. Once deployed, test your API endpoints"
echo "4. The database is already configured and connected!"
