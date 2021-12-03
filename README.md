SQS Spring Localstack Demo
--------------------------

In this simple project I want to demonstrate how to use [localstack](https://localstack.cloud/) to create a SQS
listener.

Localstack is a fully functional local cloud stack to develop and test your cloud and serverless apps offline. No need
for wasting money with real AWS resources during development of your application and no need for an always on internet
connection.

# Environment

The app is using docker-compose to start localstack and also create a queue named "sample-queue" with the help of a 
script named "wait-for-it" taken from
github [https://github.com/vishnubob/wait-for-it](https://github.com/vishnubob/wait-for-it). That script makes sure to
wait for the localstack environment to be up and running before continuing with aws commands.

To make aws-cli functional I had to include a .aws folder with a default profile.

# Application

The application is using spring-cloud-aws to configure the localstack endpoint in the class SqsConfig. There you can
further find which converter is used. In this example a simple String-Converter was chosen to receive all messages
as simple strings.

The class SampleQueueMessageConsumer is a sample how to add a consumer for the "sample-queue" queue. It just logs every
incoming message.

# Hint

Sending is very slow on my machine. Not sure yet why.