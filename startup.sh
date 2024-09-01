#!/bin/bash

./gradlew build -Pvaadin.productionMode -x test --warning-mode all
docker compose up --build