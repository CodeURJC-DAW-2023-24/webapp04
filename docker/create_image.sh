#!/bin/bash

docker build -t cristian1a/urjc_bank -f ../docker/Dockerfile ../
docker push cristian1a/urjc_bank
docker compose up
