*** Settings ***
Documentation     A resource file with reusable keywords and variables for RabbitMQ.

Library           Collections
Library           steps.RabbitMQSteps

*** Variables ***
${USERNAME}  test
${PASSWORD}  test
${HOST}  192.168.11.172
${PORT}  5672
${TEST_QUEUE}  AUTOTEST

*** Keywords ***
Send Message To Queue
    [Arguments]    ${QUEUE}  ${MESSAGE}
    Send Message To Rabbit  ${USERNAME}  ${PASSWORD}  ${HOST}  ${PORT}  ${QUEUE}  ${MESSAGE}

Reciv Message From Queue
    [Arguments]  ${QUEUE}
    ${MESSAGE}=  Reciv Message From Rabbit  ${USERNAME}  ${PASSWORD}  ${HOST}  ${PORT}  ${QUEUE}
    [Return]  ${MESSAGE}