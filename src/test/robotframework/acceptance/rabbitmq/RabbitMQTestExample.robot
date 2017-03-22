*** Settings ***
Documentation     Rabbit MQ test example

Resource          RabbitMQResource.robot

*** Test Cases ***
RabbitMQTest
    Send Message To Queue  ${TEST_QUEUE}  TEST
    ${RESPONCE}=  Reciv Message From Queue   ${TEST_QUEUE}
    Should Be Equal  ${RESPONCE}  TEST
