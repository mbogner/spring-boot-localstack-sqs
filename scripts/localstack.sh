#!/bin/bash
aws sqs create-queue --endpoint-url "http://localstack:4566" --region "eu-west-1" --queue-name "sample-queue" && \
  echo "created queue" || exit 1