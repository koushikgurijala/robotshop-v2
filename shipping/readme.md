<p align="center"> 
<img src="https://user-images.githubusercontent.com/100637276/163732513-0201b81d-d6d6-4ab9-9cf3-3f6b6c1e2f44.png" alt="TELUS">
</p>
 
<h1 id="heading" align="center">WireMock Integration with GitHub Actions for Mocking MicroServices</h1>

<br>

<h2 id="table-of-contents"> 🔤 Table of Contents</h2>

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#proposed-wiremock-integration-plan"> ➤ Proposed WireMock Integration Plan</a></li>
    <li><a href="#overview"> ➤ Overview</a></li>
    <li><a href="#step1"> ➤ Step 1: General Setup Instructions </a></li>
    <li><a href="#step2"> ➤ Step 2: Maven Setup Instructions for Mockito </a></li>
    <li><a href="#step3"> ➤ Step 3: GitHub Actions for WireMock Project </a></li>
    <li><a href="#step4"> ➤ Step 4: Screenshots of the test results </a></li>
    <li><a href="#references"> ➤ References</a></li>
   </ol>
</details>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)


## Proposed Mockito Framework Integration Plan

This document details the implementation of Mockito as a Capability
<br>
<br>
![image](https://user-images.githubusercontent.com/100637276/163920565-8163bb98-465b-4a26-ab02-5e9a148e89b2.png)
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
* ➡️ Add a secret and name it **SONAR_TOKEN** and in the value field paste the **token** obtained from the above(3rd) step

5. **Request Google Cloud COE Team for creation of Google Artifact Registry (This is needed for storing your Build artifacts)**
* ➡️ Identify a project name, Ex: trianngulum-ctv for this app
* ➡️ Identiy a location or obtain the location from the Google Cloud COE ex: us-central1 or multi-region
* ➡️ Identify a name for the repository and type (maven, gradle, docker etc). It is docker for this project and name is telus-robot-shop (Image name rs-shipping)
* ➡️ Then Create a service principal with write access to Google Artifact Repository
* ➡️ Go to Keys Section and then download the JSON Key

6. 4. **After you obtain GOOGLE CLOUD Service Principal JSON File...** 
* ➡️ Come to your GitHub Repo, Click on Settings Tab, visit Security
* ➡️ Secrets ➡️ Actions, then 
* ➡️ Add a secret and name it **GOOGLE_TOKEN** and in the value field paste the content from downloaded **JSON file** obtained from the above(5th) step


![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

<!-- STEP2 -->
<h3 id="step2"> 🔰 STEP 2: Maven Setup Instructions for Mockito</h3>

1. **General Info**

Mockito is a framwork for mocking responses to API calls. Mockito responds to a request using JSON key value pairs. For testing an application, we need JUnit or any other similar testing framwork and mockito framework.
<p>Under Dependencies section in POM.XML, Add the below</p>

```XML
	<dependency>
	  <groupId>org.testng</groupId>
	    <artifactId>testng</artifactId>
	      <version>7.0.0</version>
		<scope>test</scope>
	</dependency>

	<dependency>
	  <groupId>io.rest-assured</groupId>
	    <artifactId>rest-assured</artifactId>
	      <version>3.3.0</version><!--$NO-MVN-MAN-VER$-->
		 <scope>test</scope>
	</dependency>

	<dependency>
	  <groupId>com.github.tomakehurst</groupId>
	    <artifactId>wiremock-jre8</artifactId>
		<version>2.33.1</version>
		   <scope>test</scope>
	</dependency>

```
2. **TestNG, RestAssured Plugins are used along side WiremMock. Ensure the necessary dependencies and plugins are configured**

- [x] TestNG is used for Test Coverange
- [x] Rest Assured triggers WireMock framework
- [x] WireMock is used for mocking a live Micro Service (Request is processed with a response JSON)

3. **Post Testing, Docker Images are built for deployment to Google Artifact Repository for further deploying to GKE**

After a successful execution of tests, a Docker image is build for the shipping application. Docker file contents are below

```YAML
#
Build Application
#
FROM debian:10 AS build

RUN apt-get update && apt-get -y install maven
WORKDIR /opt/shipping

COPY pom.xml /opt/shipping/
RUN mvn dependency:resolve
COPY src /opt/shipping/src/
RUN mvn package -DskipTests

#
# Run
#
FROM openjdk:8-jdk

EXPOSE 8080
WORKDIR /opt/shipping
ENV CART_ENDPOINT=cart:8080
ENV DB_HOST=mysql

COPY --from=build /opt/shipping/target/shipping-1.0.jar shipping.jar
CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "shipping.jar" ]

```

<!-- STEP3 -->
<h3 id="step3"> 🔰 STEP 3: GitHub Actions for WireMock Project</h3>

**Below GitHub Actions will build and push the artifacts to GAR and publish the results to Pull Request(PR) Comments**

```YAML

 # This Job is executed only if there are any changes detected in the Shipping Microservice
 build-shipping:
    name: Building SHIPPING IMAGE
    needs: changes
    if: ${{ needs.changes.outputs.shipping == 'true' }}
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v3
                       
      - name: Setup GCP Service Account
        uses: "google-github-actions/auth@v0"
        with:
          credentials_json: "${{ secrets.GOOGLE_CREDENTIALS }}"
      
      - name: Set up Cloud SDK
        uses: 'google-github-actions/setup-gcloud@v0'
   
      # Download and Run Wiremock Server as a Docker Image
      - name: Run Wiremock server
        run: |
          cd shipping
          docker run -d -p 8080:8080 -v $PWD/src/test/resources:/home/wiremock --name wiremock wiremock/wiremock:2.33.1
          docker container inspect wiremock
          echo `docker container port wiremock`
          mvn test
	  
      # Publish Test Results to Comments in PR
      - name: Publish Wiremock Test Results
        uses: EnricoMi/publish-unit-test-result-action@v1
        if: always()
        with:
          files: "shipping/target/surefire-reports/junitreports/*.xml"
	  
      # Publish Artifacts to GAR
      - name: Build-and-push-to-GAR
        run: |
          echo `pwd`
          export TAG=`cat shipping/VERSION.txt`
          echo $TAG
          echo "$GAR_INFO"/"$SHIPPING_APP_NAME":"$TAG"
          docker build -t "$GAR_INFO"/"$SHIPPING_APP_NAME":"$TAG" shipping/
          gcloud info
          docker push "$GAR_INFO"/"$SHIPPING_APP_NAME":"$TAG"

 ```
<!-- STEP4 -->
<h3 id="step4"> 🔰 STEP 4: Screenshots of the test results</h3>

📊 Results of WireMock Test Execution

![image](https://user-images.githubusercontent.com/100637276/163826587-09c71f43-ffe2-400c-afac-3a9b82932c45.png)

<br>

These reports in the PR comments helps teams to take informed decisions on the code and increases overall engineering productivity. This implementation also rules out the dependency for the System Under Test on other MicroServices. MicroService of interest can be confidently developed, tested and deployed with Service Virtualization.

