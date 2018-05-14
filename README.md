# HomeSafe
HomeSafe Mobile App using AWS
CMSC389L Spring 2018

### Description
Android Mobile App that allows you to set an expected time home and will send a text to your friends if you haven't made it home 
by that time. Uses AWS to store a user's information, determine whehter they have made it home yet, and text their contacts
if they fail to let the app know they have made it home.

### AWS Services
* Mobile Hub
* Cognito
* DynamoDB
* Lambda
* Simple Notification Service

### [Video Demo](https://youtu.be/k5iIcxs-FOA)

### [Architecture](https://cloudcraft.co/view/0f5ebc96-5a70-4d9d-8c5c-7aa65b27596d?key=U1PLbX7MNWAVLTJ6j6fShg)

### Recreation
* Clone/Download Repository
* Use ProcessDynamoStream.yaml file to recreate Lambda Function. Must have permissions for DynamoDB, CloudWatch, and SNS
* Edit code to match your AWS credentials
* Use AWS Modile Hub to configure app to your services
* Open in Android Studio to build and download app onto mobile device
