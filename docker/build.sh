#!/bin/bash

docker build --build-arg CACHEBUST=$(date +%d) -t docker-repo.anmi.com/puppeteer-chrome-linux .
