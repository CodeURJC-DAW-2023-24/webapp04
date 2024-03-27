#!/bin/bash

docker build -t Cristian1A/urjc_bank -f ../docker/Dockerfile ../
docker-compose -f ../docker/docker-compose.yml up
