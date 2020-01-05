#!/bin/bash

# Initialize Spring Boot projects with the 'spring init' command.
# Chris Joakim, 2020/01/03

name=sdweb
target_dir=$name
dependencies=web,jpa,actuator,thymeleaf,azure-support

spring init \
    --name=$name \
    --dependencies=$dependencies \
    --groupId=com.chrisjoakim.azure \
    --artifactId=$name \
    --java-version=1.8 \
    $name.zip

unzip $name.zip

echo 'done'
