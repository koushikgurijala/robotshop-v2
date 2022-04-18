
<p align="center"> 
<img src="https://user-images.githubusercontent.com/100637276/163732513-0201b81d-d6d6-4ab9-9cf3-3f6b6c1e2f44.png" alt="TELUS">
</p>
 
<h1 id="heading" align="center"> Mockito Framework Integration with GitHub ACtions </h1>


<br>

<h2 id="table-of-contents"> 🔤 Table of Contents</h2>

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#proposed-mockito-framework-integration-plan"> ➤ Proposed Mockito Framework Integration Plan</a></li>
    <li><a href="#overview"> ➤ Overview</a></li>
    <li><a href="#step1"> ➤ Step 1: General Setup Instructions </a></li>
    <li><a href="#step2"> ➤ Step 2: Maven Setup Instructions for Mockito </a></li>
    <li><a href="#step3"> ➤ Step 3: GitHub Actions for Mocktio Project </a></li>
    <li><a href="#step4"> ➤ Step 4: Screenshots of the test results </a></li>
    <li><a href="#references"> ➤ References</a></li>
   </ol>
</details>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)


## Proposed Mockito Framework Integration Plan

This document details the implementation of Mockito as a Capability
<br>
<br>
![image](https://user-images.githubusercontent.com/100637276/163222841-6ad7a78b-6937-4718-a5ea-f4a661c9cd67.png)
<br>
<br>
<!-- STEP1 -->
<h3 id="step1"> 🔰 STEP1: General Setup Instructions</h3>

1. **Generate a GITHUB TOKEN (We need this to get the Pull Request(PR) Information and publish reports back to Github PR)**
 * Go to your GITHUB Account (Not the Repo) 
 * ➡️ Settings ➡️ Then Scroll down to the end, Go to Developer Settings
 * ➡️ Go to Personal access token
 * ➡️ Generate a new token ➡️ Configure SSO 
 * ➡️ Copy the **token**.
 * ➡️ After you obtain GitHub Personal Access Token, Come to your **GitHub Repo**, 
 * ➡️ Click on Settings Tab ➡️ visit Security ➡️ Secrets ➡️ Actions, then 
 * ➡️ Add a secret and name it **GITHUB_TOKEN**  and in the value field paste the **token** you obtained from the above(1st) step

2. **Request SonarQube Team for access to a new project**
 * Access Request Instructions:
 * Identify a unique name of the project to be created in SonarQube [https://sonarqube.cloudapps.telus.com/] ex: cdo-triangulum-ctv for this app
 * This is where all your reports will be published
 * Also request for mapping your GitHub project in SonarQube [You should give the GitHub Repo Name to SonarQube Admins for mapping Repo with SonarQube Project]
 
3. **Once access is obtained to SonarQube ➡️ Go to Your name displayed on the left side Top corner**
 * ➡️ Then Click on My Account
 * ➡️ then go to Security
 * ➡️ Enter a meaningful name for the token ➡️ Click on Generate
 * ➡️ Copy/preserve the token [This is the token that enables authentication of your Github Workflow execution to publish results in SonarQube]

4. **After you obtain SonarQube Token...** 
* ➡️ Come to your GitHub Repo, Click on Settings Tab, visit Security
* ➡️ Secrets ➡️ Actions, then 
* ➡️ Add a secret and name it **SONAR_TOKEN** and in the value field paste the **token** obtained from the above(2nd) step

5. **Request Google Cloud COE Team for creation of Google Artifact Registry (This is needed for storing your Build artifacts)**
* ➡️ Identify a project name, Ex: trianngulum-ctv for this app
* ➡️ Identiy a location or obtain the location from the Google Cloud COE ex: us-central1 (if its across multi region, then you dont need this)
* ➡️ Identify a name for the repository and type (maven, gradle, docker etc). It is maven for this project and name is mockitodemoapp

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

<!-- STEP2 -->
<h3 id="step2"> 🔰 STEP 2: Maven Setup Instructions for Mockito</h3>

1. **General Info**

Mockito is a framwork for mocking responses to API calls. Mockito responds to a request using JSON key value pairs. For testing an application, we need JUnit or any other similar testing framwork and mockito framework.
<p>Under Dependencies section in POM.XML, Add the below</p>

```XML
<dependencies>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			 <artifactId>junit-jupiter-engine</artifactId>
			  <version>5.8.1</version>
			   <scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			 <artifactId>mockito-inline</artifactId>
			  <version>4.2.0</version>
			   <scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			 <artifactId>mockito-junit-jupiter</artifactId>
			  <version>4.2.0</version>
			   <scope>test</scope>
		</dependency>
</dependencies>
```
2. **JUnit, Surefire Reports and Jacoco are also used along side Mockito. Ensure the necessary dependencies and plugins are configured**

- [x] JUnit is used for Test Coverange
- [x] SureFire Reports are XMLs of the results of JUnit which can be pulished
- [x] JaCoCo is used for Code Coverage

3. **Post build, the artifacts has to be pushed to Google Artifact Repository(GAR)**
 
Extensions should be setup in POM.XML so Maven will pull out respective Jars for establishing connection to GAR

```XML
<build>
 ................
 ................
 <extensions>
  <extension>
   <groupId>com.google.cloud.artifactregistry</groupId>
    <artifactId>artifactregistry-maven-wagon</artifactId>
     <version>2.1.0</version>
  </extension>
 </extensions>
</build>
```
Repositories should be setup in POM.XML under Distribution Management which will tell maven which repo to push the artifacts to

```XML
<distributionManagement>
    	 <snapshotRepository>
      		<id>artifact-registry</id>
      			<url>artifactregistry://us-central1-maven.pkg.dev/triangulum-ctv/mockitodemoapp</url>
    	 </snapshotRepository>
    	<repository>
      		<id>artifact-registry</id>
      		<url>artifactregistry://us-central1-maven.pkg.dev/triangulum-ctv/mockitodemoapp</url>
    	</repository>
</distributionManagement>
```
<!-- STEP3 -->
<h3 id="step3"> 🔰 STEP 3: GitHub Actions for Mockito Project</h3>

**Below GitHub Actions will build and push the artifacts to GAR and publish the results to SonarQube and Pull Request(PR) Comments**

```YAML
name: Build Test and Publish
on:
  workflow_dispatch:
  push:
   branches:
     - main
  pull_request:
   branches:
    - main
    types: [opened, synchronize, reopened]

jobs:
  build:
    name: Build Test and Publish
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
        with:
          fetch-depth: 0  # Shallow clones should be disabled for a better relevancy of analysis
   
   # Setup Java and Maven
      - name: Set up JDK 17
        uses: actions/setup-java@v1
        with:
          java-version: 17
          cache: Maven
   
   # Setup Cache (Optional, This will not help much as the runners are ephimeral)
      - name: Cache SonarCloud packages
        uses: actions/cache@v1
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar
      - name: Cache Maven packages
        uses: actions/cache@v1
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2
   
   # Setup Authorization to GCP Account for pushing artifacts to GAR
      - name: Setup GCP Service Account
        uses: "google-github-actions/auth@v0"
        with:
          credentials_json: "${{ secrets.GOOGLE_CREDENTIALS }}"
    
    # Perform the Build 
      - name: Build and analyze
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}  # Needed to get PR information, if any
          SONAR_TOKEN: ${{ secrets.SONARQUBE_GCP_TOKEN }}
          SONARCLOUD_URL: 'https://sonarqube.cloudapps.telus.com'
        run: |
          mvn -e -B deploy \
           org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
           -Dsonar.projectKey=cdo-triangulum-ctv \
           -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
    
    # This Step publishes JaCoCo report in PR
      - name: Jacoco Report
        id: jacoco
        uses: madrapps/jacoco-report@v1.2
        with:
          paths: ${{ github.workspace }}/target/site/jacoco/jacoco.xml
          token: ${{ secrets.GITHUB_TOKEN }}
          min-coverage-overall: 40
          min-coverage-changed-files: 60
     
     # This Step publishes JUnit report in PR
      - name: Publish Unit Test Results
        uses: EnricoMi/publish-unit-test-result-action@v1
        if: always()
        with:
          files: "target/surefire-reports/*.xml"
 ```
<!-- STEP4 -->
<h3 id="step4"> 🔰 STEP 4: Screenshots of the test results</h3>

📊 Results of JUnit Tests - Test Coverage

![image](https://user-images.githubusercontent.com/100637276/163735219-d7f98e0b-58be-42bc-8b9c-4eb73b972ab5.png)

<br>

📊 Results of JaCoCo - Code Coverage

![image](https://user-images.githubusercontent.com/100637276/163735228-964981f1-3d79-4ed2-ba03-43af3972c80e.png)

<br>

📊 Results from SonarQube - Overall Code Quality Analysis
![image](https://user-images.githubusercontent.com/100637276/163735245-71bdde0e-80c4-40c3-b8ae-10a74c68676a.png)

<br>

These reports in the PR comments helps teams to take informed decisions on the code and increases overall engineering productivity



