#!/bin/bash

file=`cat export-private-diagrams.js`

# set -x # debug on
docker run -i --cap-add=SYS_ADMIN \
  --name puppeteer-chrome docker-repo.anmi.com/puppeteer-chrome-linux \
  node -e "$file"
# set +x # debug off

docker cp puppeteer-chrome:/app/diagrams ../build/resources/main/spec

docker rm -f puppeteer-chrome

