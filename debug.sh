#!/bin/bash

./gradlew build -Pvaadin.productionMode -x test --warning-mode all
docker-compose -f compose.yaml -f compose.debug.yaml up -d --build