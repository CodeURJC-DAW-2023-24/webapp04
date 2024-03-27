#!/bin/bash

docker build -t Cristian1A/urjc_bank -f ../docker/Dockerfile ../
docker push Cristian1A/urjc_bank
docker-compose up
