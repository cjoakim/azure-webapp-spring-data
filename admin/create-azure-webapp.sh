#!/bin/bash

# Bash script which uses the Azure CLI (az) to:
# 1) Create a Resource Group (RG)
# 2) Create an App Service Plan in the RG
# 3) Create an Linux/Container Web App within the App Service Plan
# 4) Associate the Docker container created in this repo with the Web App
#
# See https://docs.microsoft.com/en-us/azure/app-service/containers/tutorial-custom-docker-image
#
# Chris Joakim, Microsoft, 2020/01/07

source ../app-env.sh

# Local Variables
rg="cjoakim-spring-data"
appName="cjoakim-spring-data"
planName="cjoakim-spring-data-plan"
location="EastUS"
registryPath=$ACR_CONTAINER_FULLNAME

echo '==='
echo 'Creating the Resource Group ...'
az group create --name $rg --location $location

echo '==='
echo 'Creating the App Service Plan ...'
az appservice plan create \
    --name $planName \
    --resource-group $rg \
    --location $location \
    --is-linux \
    --sku S1

echo '==='
echo 'Creating the Web App ...'
az webapp create \
    --resource-group $rg \
    --plan $planName \
    --name $appName \
    --deployment-container-image-name $registryPath \
    --docker-registry-server-password $AZURE_ACR_USER_PASS \
    --docker-registry-server-user $AZURE_ACR_USER_NAME \

echo '==='
echo 'Enabling Continuous Deployment (CD) for the Web App ...'
az webapp deployment container config \
    --enable-cd true \
    --name $appName \
    --resource-group $rg \

echo '==='
echo 'Listing the initial Web App environment variables ...'
az webapp config appsettings list  --name $appName --resource-group $rg

echo '==='
echo 'Set Web App environment variables from local environment variable values ...'
az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_SQL_DATABASE=$AZURE_SQL_DATABASE

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings SPRING_DATASOURCE_INITIALIZATION_MODE=$SPRING_DATASOURCE_INITIALIZATION_MODE

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings SPRING_DATASOURCE_PLATFORM=$SPRING_DATASOURCE_PLATFORM

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD

echo '==='
echo 'Listing the final Web App environment variables ...'
az webapp config appsettings list  --name $appName --resource-group $rg

echo '==='
echo 'Invoke the Web App at URL: '
echo http://$appName.azurewebsites.net
