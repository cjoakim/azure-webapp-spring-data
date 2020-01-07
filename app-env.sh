#!/bin/bash

# This script defines constant configuration values for this project
# and is "sourced" by the other (i.e. - maven and docker) bash shell scripts.
# Chris Joakim, 2020/01/07

# Maven
export COMPILED_JARFILE="target/sdweb-0.1.0.jar"
export CONTAINER_JARFILE="target/app.jar"

# Docker 
export CONTAINER_BASENAME="azure-webapp-spring-data"
export DOCKERHUB_CONTAINER_NAME="cjoakim/"$CONTAINER_BASENAME
export DOCKERHUB_CONTAINER_FULLNAME=$DOCKERHUB_CONTAINER_NAME":latest"

# Azure Container Registry (ACR)
export AZURE_ACR_NAME=$AZURE_ACR_NAME
export AZURE_ACR_LOGIN_SERVER=$AZURE_ACR_LOGIN_SERVER
export AZURE_ACR_USER_NAME=$AZURE_ACR_USER_NAME
export AZURE_ACR_USER_PASS=$AZURE_ACR_USER_PASS
export ACR_CONTAINER_FULLNAME=""$AZURE_ACR_LOGIN_SERVER"/"$CONTAINER_BASENAME":latest"

# Spring
export SPRING_DATASOURCE_INITIALIZATION_MODE="always"
export SPRING_DATASOURCE_PLATFORM="sqlserver"
export SPRING_DATASOURCE_URL=$AZURE_SQL_JDBC_CONN_STR
export SPRING_DATASOURCE_USERNAME=$AZURE_SQL_USER"@"$AZURE_SQL_SERVER
export SPRING_DATASOURCE_PASSWORD=$AZURE_SQL_PASS

arg_count=$#

if [ $arg_count -gt 0 ]
then
    if [ $1 == "display" ] 
    then
        echo "COMPILED_JARFILE:              "$COMPILED_JARFILE
        echo "CONTAINER_JARFILE:             "$CONTAINER_JARFILE
        echo "CONTAINER_BASENAME:            "$CONTAINER_BASENAME
        echo "DOCKERHUB_CONTAINER_NAME:      "$DOCKERHUB_CONTAINER_NAME
        echo "DOCKERHUB_CONTAINER_FULLNAME:  "$DOCKERHUB_CONTAINER_FULLNAME
        echo "AZURE_ACR_NAME:                "$AZURE_ACR_NAME
        echo "AZURE_ACR_LOGIN_SERVER:        "$AZURE_ACR_LOGIN_SERVER
        echo "AZURE_ACR_USER_NAME:           "$AZURE_ACR_USER_NAME
        echo "AZURE_ACR_USER_PASS:           "$AZURE_ACR_USER_PASS
        echo "ACR_CONTAINER_FULLNAME:        "$ACR_CONTAINER_FULLNAME
        echo "SPRING_DATASOURCE_INITIALIZATION_MODE: "$SPRING_DATASOURCE_INITIALIZATION_MODE
        echo "SPRING_DATASOURCE_PLATFORM:    "$SPRING_DATASOURCE_PLATFORM
        echo "SPRING_DATASOURCE_URL:         "$SPRING_DATASOURCE_URL
        echo "SPRING_DATASOURCE_USERNAME:    "$SPRING_DATASOURCE_USERNAME
        echo "SPRING_DATASOURCE_PASSWORD:    "$SPRING_DATASOURCE_PASSWORD
    fi
fi
