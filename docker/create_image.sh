#!/bin/bash

docker build -t cristian1a/urjc_bank -f ../docker/Dockerfile ../
docker tag cristian1a/urjc_bank cristian1a/urjc_bank:latest
docker push cristian1a/urjc_bank:latest
docker compose up
