![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/telus/triangulum-ctv-stansRobotShop?display_name=release&include_prereleases)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/telus/triangulum-ctv-stansRobotShop/main.yaml/master?)

- [Continuous Testing Vision (Triangulum Project)](#continuous-testing-vision--triangulum-project-)
  * [1. What Is Continuous Testing Vision (CTV):](#1-what-is-continuous-testing-vision--ctv-)
      - [1.1 Need for Shift Left testing approach:](#11-need-for-shift-left-testing-approach-)
  * [2. How Does CTV Help In Engineering Productivity:](#2-how-does-ctv-help-in-engineering-productivity)
  * [3. What Is a Test Double ?:](#3-what-is-a-test-double--)
    + [3.1 What is a Stub ?](#31-what-is-a-stub--)
    + [3.2 What is a Mock ?](#32-what-is-a-mock--)
    + [3.3 Difference between a Stub and a Mock ?](#33-difference-between-a-stub-and-a-mock--)
    + [3.4 What is Service Virtualization ?](#34-what-is-service-virtualization--)
  * [4. Comparision between Stubs, Mocks and Virtual Services](#4-comparision-between-stubs--mocks-and-virtual-services--)
  * [5. Comparision of various Mocking and Service Virtualization Tools:](#5-comparision-of-various-mocking-and-service-virtualization-tools)
  * [6. Pipeline Method 1 - DevOps Pipleline as Code for Mocking using Mockito:](#6-pipeline-method-1---devops-pipleline-as-code-for-mocking-using-mockito)

# Continuous Testing Vision (Triangulum Project)
<br>
<br>

## 1. What Is Continuous Testing Vision (CTV)

To embrace the massive organizational shift in releasing quality and reliable software to the end users at a faster pace embracing the full potential of agile methodologies, It is mandatory to build a strong Continuous Testing pipeline to achieve Shift Left testing.

#### 1.1 Need for Shift Left testing approach:

Shift-left testing is important because it helps to prevent the following types of harm due to late testing:

* Testers may be less involved in initial planning, often resulting in insufficient resources being allocated to testing.
* Defects in requirements, architecture, and design remain undiscovered while significant effort is wasted implementing them.
* Debugging (including identifying, localizing, fixing, and regression testing defects) becomes harder as more software is produced and integrated.
* Encapsulation impedes white-box testing, reducing code coverage during testing.
* There is less time to fix defects found by testing, thereby increasing the likelihood that they will be postponed until later increments or versions of the system. 

This creates a “bow wave” of technical debt that can sink projects if it grows too large and especially in the era of Micro Services.

Below image highlights our goal of building continuous testing with shift-left approach in our CI/CD pipelines.

![CTV Proposed Plan](readme/ctv-proposed-plan-img.jpg)

:memo: Note: The master and feature branch illustrated in the above image is for information purpose and doesn't bias towards any branching strategy

## 2. How Does CTV Help In Engineering Productivity
Continuous Testing Vision is all about integrating various testing methodologies in the Release pipeline to acheive the goals of 
1. Decreasing the Lead Time for Changes (to One Day)
2. Increasing the Deployment Frequency (Multiple Deployments Per Day)
3. Minimizing Change failure rate (<0.15%)

![image](https://user-images.githubusercontent.com/100637276/159636455-73b114a2-b2e0-40b8-a619-8d1e3bcdc3b8.png)

## 3. What Is a Test Double ?

In automated testing usage of objects that look and behave like their production equivalents, but are actually simplified is a Test Double. This reduces complexity, allows to verify code independently from the rest of the system and sometimes it is even necessary to execute self validating tests at all. A Test Double is a generic term used for these objects.

Majorly used types of Test Doubles

* Stubs 
* Mocks
* Virtual Services

### 3.1 What is a Stub ?

Stub provides hard-coded answers to calls done during the test. It’s an object, in most cases, responding only to what was programmed in for test purposes, nothing else. We can say that stub overrides methods and returns needed for test values. The purpose of a stub is to prepare a specific state of your system under the test.

### 3.2 What is a Mock ?

Mock is a part of your test that you have to set up with expectations. It’s an object pre-programmed with expectations about calls it’s expected to receive. The purpose of a mock is to make assertions about how the system will interact with the dependency. In other words, mock verify the interactions between objects. So, you don’t expect that mock return some value (like in the case of stub object), but to check that specific method was called.

### 3.3 Difference between a Stub and a Mock ?

A stub is an object that returns a hard-coded answer. So it represents a specific state of the real object. Mock, on the other hand, verifies if a specific method was called. It’s testing the behavior besides returning data to the question or call. The idea is stub returns hardcoded data to the question or call and mock verifies if the question or call was made with data response.

### 3.4 What is Service Virtualization ?

It is the practice of creating virtual services and sharing them between developers and testers within a team and across teams. Developers and testers working on the same product can use the same virtual service artifacts or even virtual services. Another example is test teams across a large enterprise using the same virtual service artifacts. It promotes communication between development and test teams across many departments. It also attempts to address the problem of duplicated efforts by creating stubs for the same APIs within a large organisation by many teams simultaneously, by establishing new communication channels between teams. We will look at the trade-offs later.

In the micro service world with complex applications, Service Virtualization is the go to solution.

📝Below image illustrates the difference between a Stub, Mock and a Virtual Service

![image](https://user-images.githubusercontent.com/100637276/159629077-6350437b-171e-47f3-9c2d-c7cc39d7c47b.png)
   
<br>
<br>

## 4. Comparision between Stubs, Mocks and Virtual Services

<br>
<br>

|                     | **Response Source** | **Data Variety** | **Invocation Verification**   | **Created By** | **State Perseverance** |
|---------------------|---------------------|------------------|-------------------------------|----------------|------------------------|
| **Stub**            | Hardcoded Data      | Less             | Same Process/Program          | Developers     | No                     |
| **Mock**            | Data Set (JSON)     | Medium           | Same Process and HTTP         | Developers     | Yes                    |
| **Virtual Service** | Recorded Data       | High             | HTTP and Many other Protocols | Testers        | Yes                    |

<br>
<br>


|                     | **When to Use**                                                                                                                                                              | **When Not to Use**                                                                                      | **Support Protocols**         |
|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|-------------------------------|
| **Stub**            | When Test data needed is not <br>complex                                                                                                                                     | Not good for large suits of tests<br>with complex data                                                   | Same Process/Program          |
| **Mock**            | Applicable in most of the cases                                                                                                                                              | When the micro services are<br>communicating over multiple protocols<br>other than HTTP                  | Same Process and HTTP         |
| **Virtual Service** | Large scale applications that have<br>many APIs. Need support for protocols<br>other than HTTP. To test non-functional<br>requirements like latency, throughput <br>and more | Agile teams in small to medium size.<br>When the applications are loosely<br>coupled and ideally modular | HTTP and Many other Protocols |

<br>
<br>

## 5. Comparision of various Mocking and Service Virtualization Tools

<br>

After a thorough research of various available open source and paid tools, considering the wider open source community, wider adaption, extended support for languages and the problems they solve besides compliance with multiple frameworks, the below list of tools are identified for implementation of Continuous Testing Vision

<br>
<br>

| **Mockito**                                                                                                    | **WireMock**                                                              | **BlazeMeter Mock Services**                                                                         | **DevTest**                                                                                             |
|----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| Freeware                                                                                                       | Freeware                                                                  | Licensed                                                                                             | Licensed                                                                                                |
| Mockito provides the<br>mock implementation of<br>the method/object                                            | Provides simulation for <br>HTTP based APIs                               | Mock Services only support<br>HTTP Protocols                                                         | Full Fledged support for <br>virtualizing almost any<br>service via wide range of<br>protocols          |
| Mockito is designed<br>Unit Testing                                                                            | WireMock is designed for <br>component testing and<br>integration testing | Functional, Regression and<br>Performance Testing                                                    | Functional, Regression and<br>Performance Testing                                                       |
| Used in Development stage<br>of SDLC                                                                           | During early API Integration                                              | When In-house and Third party <br>systems are unavailable, TDM <br>dependency and during load issues | When In-house and Third Party<br>systems are unavailable, TDM <br>dependency and during load<br>issues. |
| Support for all Object<br>Oriented Languages (Java, PHP) and<br>Python, Flex, JavaScript, Scala,<br>TypeScript | Supports HTTP based API calls                                             | Supports HTTP(S) only as of now.<br>BlazeMeter can integrate with <br>Broadcom's DevTest             | Supports 30+ Protocols                                                                                  |

<br>
<br>

## 6. Pipeline Method 1 - DevOps Pipleline as Code for Mocking using Mockito

<br>
<br>


![image](https://user-images.githubusercontent.com/100637276/160154772-8957b7b3-72a6-4998-a0ba-372106eb513e.png)

<br>
<br>

#### 🙋‍♂️🙋‍♂️🙋‍♂️This Workflow code is for https://github.com/telus/triangulum-ctv-happyHotelApp. Workflow will be ported to the current repo after implementation of Mockito test cases

<br>
<br>

```yaml
name: Build
on:
  push:
    branches:
      - main
    paths-ignore:
      - '**.md'
      - '.github/workflows/*yaml'
  pull_request:
    types: [opened, synchronize, reopened]
    branches:
      -main
    paths-ignore:
      - '**.md'
      - '.github/workflows/*yaml'
      
  workflow_dispatch:

jobs:
  build-publish:
    name: Build and Publish
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
        with:
          fetch-depth: 0 # Shallow clones should be disabled for a better relevancy of analysis

      - name: Set up JDK 17
        uses: actions/setup-java@v1
        with:
          java-version: 17
          cache: Maven

      #  - name: Cache SonarCloud packages
      #   uses: actions/cache@v1
      #    with:
      #      path: ~/.sonar/cache
      #     key: ${{ runner.os }}-sonar
      #      restore-keys: ${{ runner.os }}-sonar

      # - name: Cache Maven packages
      #   uses: actions/cache@v1
      #  with:
      #     path: ~/.m2
      #     key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
      #     restore-keys: ${{ runner.os }}-m2

      - name: Dump GitHub context
        env:
          GITHUB_CONTEXT: ${{ toJson(github) }}
        run: echo "$GITHUB_CONTEXT"

      - name: Build and analyze
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }} # Needed to get PR information, if any
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: mvn -e -B clean compile test package --file HappyHotelApp/pom.xml org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=triangulum-ctv-per -Dsonar.junit.reportPaths=target/surefire-reports -Dsonar.surefire.reportsPath=target/surefire-reports

      - name: Deploy
        run: mvn -e deploy --file HappyHotelApp/pom.xml
      # - name: Push Artifacts to Google Artifact Repo
      #   run: mvn --file HappyHotelApp/pom.xml deploy

      - name: Setup GCP Service Account
        uses: "google-github-actions/auth@v0"
        with:
          credentials_json: "${{ secrets.GOOGLE_CREDENTIALS }}"

      - name: "Use gcloud CLI"
        run: "gcloud info"

```
