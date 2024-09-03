#!/bin/bash

./gradlew build -Pvaadin.productionMode -x test --warning-mode all
docker-compose -f compose.prod.yaml up -d --build