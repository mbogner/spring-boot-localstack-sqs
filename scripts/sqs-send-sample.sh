#!/bin/bash
aws sqs send-message --endpoint-url "http://localhost:4566" --region "eu-west-1" \
  --queue-url "http://localhost:4566/000000000000/sample-queue" \
  --message-body "this is a test"