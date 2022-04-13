
## Mockito

[![N|Solid](https://images.ctfassets.net/fikanzmkdlqn/5NoHRB1q6lrNzSSpekhrG5/cf22f3d7d9e82aed5e79659800458b57/TELUS_TAGLINE_HORIZONTAL_EN.svg)](https://www.telus.com/en/)


### Table of Contents

1. [Proposed Mockito Framework Integration Plan](#proposed-mockito-framework-integration-plan) 
   + [Setup Instructions](#setup-instructions)
2. [Changes in Maven POM.XML for Mockito Integration](#changes-in-maven-pom-xml-for-mockito-integration)

 
<br>


## Proposed Mockito Framework Integration Plan

This document details the implementation of Mockito as a Capability
<br>
<br>
![image](https://user-images.githubusercontent.com/100637276/163222841-6ad7a78b-6937-4718-a5ea-f4a661c9cd67.png)
<br>
<br>

### Setup Instructions

1. Generate a GITHUB TOKEN (We need this to get the Pull Request(PR) Information and publish reports back to Github PR)
   * Go to your GITHUB Account (Not the Repo) -> Then Settings -> Then Scroll down to the end, Go to Developer Settings ->
   * Then Go to Personal access token -> Generate a new token -> Configure SSO -> Copy the token.
   * After you obtain GitHub Personal Access Token, Come to your GitHub Repo, Click on Settings Tab, visit Security -> Secrets -> Actions, then Add a secret and name it GITHUB_TOKEN  and in the value field paste the token you obtained from the above(1st) step
2. Request SonarQube Team for access to a new project 
   * Access Request Instructions:
   * Specify the name of the project to be created in SonarQube [https://sonarqube.cloudapps.telus.com/]
   * This is where all your reports will be published
   * Also request for mapping your GitHub project in SonarQube [You should give the GitHub Repo Name to SonarQube Admins for mapping Repo with SonarQube Project]
    Once access is obtained Go to Your name displayed on the left side Top corner and then -> Click on My Account --> then go to Security --> Enter a meaningful name for the token --> Click on Generate --> Copy/preserve the token [This is the token that enables authentication of your Github Workflow execution to publish results in SonarQube]
   * Contact for SonarQube: Mazlan Islam
3. After you obtain SonarQube Token, Come to your GitHub Repo, Click on Settings Tab, visit Security -> Secrets -> Actions, then Add a secret and name it SONAR_TOKEN and in the value field paste the token you obtained from the above(2nd) step
4. Request Google Cloud COE Team for creation of Google Artifact Registry (This is need for storing your Build artifacts) 
   * Contact for Google Cloud COE Process  - Liz Lozinsky and Katie Peters

## Changes in Maven POM.XML for Mockito Integration


