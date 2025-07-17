#!/bin/bash

# Очищення Maven кешу і перезавантаження проекту
echo "Cleaning Maven cache..."
mvn clean
mvn -U clean install -DskipTests

echo "Done. Please refresh your IDE project."
