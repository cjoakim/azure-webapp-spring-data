#!/bin/bash

# HTTP Client for this app, using curl.
# Chris Joakim, Microsoft, 2020/01/07

#base_url="http://localhost:3000"
base_url="http://cjoakim-spring-data.azurewebsites.net:80"
sleep_sec=5

echo '=========='
echo 'Invoking airports/ endpoints ...'

curl -v $base_url"/airports/findAll"
sleep $sleep_sec

curl -v $base_url"/airports/findById/1"
sleep $sleep_sec

curl -v $base_url"/airports/findByIataCode/CLT"
sleep $sleep_sec

# example:
# curl "http://cjoakim-spring-data.azurewebsites.net:80/airports/findByIataCode/CLT"

curl -v $base_url"/airports/findByCountry/Denmark"
sleep $sleep_sec

echo '=========='
echo 'Invoking todo/ endpoints ...'

curl -v $base_url"/todo/findById/1"
sleep $sleep_sec

curl -H "Content-Type: application/json" -d @data/sample-todo.json -v $base_url"/todo"
sleep $sleep_sec

curl -v $base_url"/todo/findById/4"
sleep $sleep_sec

curl -v $base_url"/todo/setPriority/4/7"
sleep $sleep_sec

curl -v $base_url"/todo/findById/4"
sleep $sleep_sec

echo 'done'
