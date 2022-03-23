![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/telus/triangulum-ctv-stansRobotShop?display_name=release&include_prereleases)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/telus/triangulum-ctv-stansRobotShop/main.yaml/master?)

## Table of contents
1. [WHAT IS CONTINUOUS TESTING VISION (CTV)](#toc-1)
2. [HOW DOES CTV HELP IN ENGINEERING PRODUCTIVITY](#toc-2)
3. [PHASE 1 OF CTV - UNIT TESTING](#toc-3)
     1. [FRAMEWORKS - UNDER BUILDING](#toc-3-1)
4. [PHASE 2 OF CTV - TEST DOUBLES](#toc-4)
     1. [MOCKING USING MOCKITO](#toc-4-1)
     2. [SERVICE VIRTUALIZATION USING WIREMOCK](#toc-4-2)
     3. [SERVICE VIRTUALIZATION USING DEVTEST](#toc-4-3)
5. [PHASE 3 OF CTV = PERFORMANCE TESTING](#toc-5)
     1. [PERF TESTING USING BLAZE METER](#toc-5-1)
     2. [SUPPORTED FRAMEWORK - 1](#toc-5-2)
     3. [SUPPORTED FRAMEWORK - 2](#toc-5-3)
     4. [SUPPORTED FRAMEWORK - 3](#toc-5-4)
6. [REFERENCE APPLICATION USED FOR CTV](#toc-6)
7. [VERSIONING AND TAGGING PRACTICE ADAPTED](#toc-7)
8. [CI, CD, Continuous Deployment Pipeline](#toc-8)

## WHAT IS CONTINUOUS TESTING VISION (CTV)<a name="#toc-1"/>

To embrace the massive organizational shift in releasing quality and reliable software to the end users at a faster pace embracing the full potential of agile methodologies, It is mandatory to build a strong Continuous Testing pipeline to achieve Shift Left testing.

#### Need for Shift Left testing approach:

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

## HOW DOES CTV HELP IN ENGINEERING PRODUCTIVITY
Continuous Testing Vision is all about integrating various testing methodologies in the Release pipeline to acheive the goals of 
1. Decreasing the Lead Time for Changes (to One Day)
2. Increasing the Deployment Frequency (Multiple Deployments Per Day)
3. Minimizing Change failure rate (<0.15%)

![CTV Areas of Positive Impact](readme/ctv-areas-of-positive-impact.jpg)

## WHAT IS A TEST DOUBLE ?

In automated testing usage of objects that look and behave like their production equivalents, but are actually simplified is a Test Double. This reduces complexity, allows to verify code independently from the rest of the system and sometimes it is even necessary to execute self validating tests at all. A Test Double is a generic term used for these objects.

Majorly used types of Test Doubles

* Stubs 
* Mocks
* Virtual Services

### What is a Stub ?

Stub provides hard-coded answers to calls done during the test. It’s an object, in most cases, responding only to what was programmed in for test purposes, nothing else. We can say that stub overrides methods and returns needed for test values. The purpose of a stub is to prepare a specific state of your system under the test.

### What is a Mock ?

Mock is a part of your test that you have to set up with expectations. It’s an object pre-programmed with expectations about calls it’s expected to receive. The purpose of a mock is to make assertions about how the system will interact with the dependency. In other words, mock verify the interactions between objects. So, you don’t expect that mock return some value (like in the case of stub object), but to check that specific method was called.

### Difference between a Stub and a Mock ?

A stub is an object that returns a hard-coded answer. So it represents a specific state of the real object. Mock, on the other hand, verifies if a specific method was called. It’s testing the behavior besides returning data to the question or call. The idea is stub returns hardcoded data to the question or call and mock verifies if the question or call was made with data response.

### What is Service Virtualization ?

It is the practice of creating virtual services and sharing them between developers and testers within a team and across teams. Developers and testers working on the same product can use the same virtual service artifacts or even virtual services. Another example is test teams across a large enterprise using the same virtual service artifacts. It promotes communication between development and test teams across many departments. It also attempts to address the problem of duplicated efforts by creating stubs for the same APIs within a large organisation by many teams simultaneously, by establishing new communication channels between teams. We will look at the trade-offs later.

In the micro service world with complex applications, Service Virtualization is the go to solution.

📝Below image illustrates the difference between a Stub, Mock and a Virtual Service

![image](https://user-images.githubusercontent.com/100637276/159629077-6350437b-171e-47f3-9c2d-c7cc39d7c47b.png)

   


### Comparision between Stubs, Mocks and Virtual Services



|                     | **Response Source** | **Data Variety** | **Invocation Verification**   | **Created By** | **State Perseverance** |
|---------------------|---------------------|------------------|-------------------------------|----------------|------------------------|
| **Stub**            | Hardcoded Data      | Less             | Same Process/Program          | Developers     | No                     |
| **Mock**            | Data Set (JSON)     | Medium           | Same Process and HTTP         | Developers     | Yes                    |
| **Virtual Service** | Recorded Data       | High             | HTTP and Many other Protocols | Testers        | Yes                    |





|                     | **When to Use**                                                                                                                                                              | **When Not to Use**                                                                                      | **Support Protocols**         |
|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|-------------------------------|
| **Stub**            | When Test data needed is not <br>complex                                                                                                                                     | Not good for large suits of tests<br>with complex data                                                   | Same Process/Program          |
| **Mock**            | Applicable in most of the cases                                                                                                                                              | When the micro services are<br>communicating over multiple protocols<br>other than HTTP                  | Same Process and HTTP         |
| **Virtual Service** | Large scale applications that have<br>many APIs. Need support for protocols<br>other than HTTP. To test non-functional<br>requirements like latency, throughput <br>and more | Agile teams in small to medium size.<br>When the applications are loosely<br>coupled and ideally modular | HTTP and Many other Protocols |


### Comparision of various Mocking and Service Virtualization Tools













