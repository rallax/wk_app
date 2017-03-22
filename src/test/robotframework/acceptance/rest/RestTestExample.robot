*** Settings ***
Documentation    Suite description

Library           Collections
Library           steps.RestSteps

*** Test Cases ***
Example

    ${response}=  Get Request  reqres.in  api/users  page=2
    ${code}=  Get Status Code  ${response}
    ${body}=  Get Body  ${response}
    Compare  ${body}  $.data[0].id   4

