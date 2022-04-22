![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/telus/triangulum-ctv-stansRobotShop?display_name=release&include_prereleases)
![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/telus/triangulum-ctv-stansRobotShop/main.yaml/master?)


## Continuous Testing Vision (Triangulum Project - Service Virtualization)

[![N|Solid](https://images.ctfassets.net/fikanzmkdlqn/5NoHRB1q6lrNzSSpekhrG5/cf22f3d7d9e82aed5e79659800458b57/TELUS_TAGLINE_HORIZONTAL_EN.svg)](https://www.telus.com/en/)


### Table of Contents

1. [What Is Continuous Testing Vision (CTV)](#what-is-continuous-testing-vision-ctv) 
   + [Need for Shift Left testing approach](#need-for-shift-left-testing-approach)
2. [How Does CTV Help In Engineering Productivity](#how-does-ctv-help-in-engineering-productivity)
3. [What Is a Test Double](#what-is-a-test-double)
   + [What is a Stub](#what-is-a-stub)
   + [What is a Mock](#what-is-a-mock)
   + [Difference Between a Stub and a Mock](#difference-between-a-stub-and-a-mock)
   + [Demsifying the Internals of Stubbing and Mocking](#demsifying-the-internals-of-stubbing-and-mocking)
   + [What is Service Virtualization](#what-is-service-virtualization)
4. [Brief Comparision between Stubs Mocks and Virtual Services](#brief-comparision-between-stubs-mocks-and-virtual-services)
5. [Brief Comparision of various Mocking and Service Virtualization Tools](#brief-comparision-of-various-mocking-and-service-virtualization-tools)
6. [Choice of Tools for CTV SV Project Implementation](#choice-of-tools-for-ctv-sv-project-implementation)
7. [More insights on Broadcom DevTest](#more-insights-on-broadcom-devtest)
8. [Implementation Guide for Service Virtualization using Broadcom DevTest](#implementation-guide-for-service-virtualization-using-broadcom-devtest)
9. [DevOps Pipeline as Code Implementation](#devops-pipeline-as-code-implementation)
 
<br>


## What Is Continuous Testing Vision (CTV)

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

## How Does CTV Help In Engineering Productivity
Continuous Testing Vision is all about integrating various testing methodologies in the Release pipeline to acheive the goals of 
1. Decreasing the Lead Time for Changes (to One Day)
2. Increasing the Deployment Frequency (Multiple Deployments Per Day)
3. Minimizing Change failure rate (<0.15%)

![image](https://user-images.githubusercontent.com/100637276/159636455-73b114a2-b2e0-40b8-a619-8d1e3bcdc3b8.png)

## What Is a Test Double

In automated testing usage of objects that look and behave like their production equivalents, but are actually simplified is a Test Double. This reduces complexity, allows to verify code independently from the rest of the system and sometimes it is even necessary to execute self validating tests at all. A Test Double is a generic term used for these objects.

Majorly used types of Test Doubles

* Stubs 
* Mocks
* Virtual Services

<br>

![image](https://user-images.githubusercontent.com/100637276/161110781-7f2df482-946f-486d-88d8-0c0acd48f905.png)

<br>

### What is a Stub

Stub provides hard-coded answers to calls done during the test. It’s an object, in most cases, responding only to what was programmed in for test purposes, nothing else. We can say that stub overrides methods and returns needed for test values. The purpose of a stub is to prepare a specific state of your system under the test.

Primarily, we use it wherever the test suite is uncomplicated, and having hard-coded values is not a problem. Additionally, both developers and testers use it. But we can not share it mainly for compatibility concerns occurring due to hard-coding of resources, deployment dependencies, and platforms.

Let us take an example of a game under development with Play, Exit, Up, and Down Arrow buttons.
When game development is in progress, we have to verify user interfaces (Play, Exit, Up, and Down arrows), we shall implement some dummy actions. For example, on clicking on the Play button, a dummy action shall take place. Additionally, we can do the implementations as shown below:


### What is a Mock

Mock is a part of your test that you have to set up with expectations. It’s an object pre-programmed with expectations about calls it’s expected to receive. The purpose of a mock is to make assertions about how the system will interact with the dependency. In other words, mock verify the interactions between objects. So, you don’t expect that mock return some value (like in the case of stub object), but to check that specific method was called.

### Difference between a Stub and a Mock

A stub is an object that returns a hard-coded answer. So it represents a specific state of the real object. Mock, on the other hand, verifies if a specific method was called. It’s testing the behavior besides returning data to the question or call. The idea is stub returns hardcoded data to the question or call and mock verifies if the question or call was made with data response.

## Demsifying the Internals of Stubbing and Mocking

In our example application, we have a class that reads a customer from the database and forms their full name.
<br>
Below is the code for the customer:
<br>
```java
@Entity
	public class Customer {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		  private long id;
		  private String firstName;
		  private String lastName;
		//...getters and setters redacted for brevity...
	}
 
 ```
<br>
and below is our business class:

```java
	public class CustomerReader {
		@PersistenceContext
		  private EntityManager entityManager;
		  
    public String findFullName(Long customerID){
			   Customer customer = entityManager.find(Customer.class, customerID);
			   return customer.getFirstName() +" "+customer.getLastName();
		  }
	}

```
<br>
In the above example, Our business class reads the customer info from the database through EntityManager.

How to Test this Class ?

On the first thought, the solution would be to pre-fill a real database with customers and run this test against it. This is problematic for a lot of reasons. It creates a hard dependency on a running database, and also requires an extra step to create the test data. For this example, you could make it work, but in a real-world application, this would not be practical at all.

A better solution would be to use an in-memory database. This would solve the speed problem, but it would suffer from the extra step of pre-filling the database with custom test data. Again, in a real-world application, this can get quickly out of hand, especially when multiple tests also change the initial state of the database.

The best solution for a true unit test is to completely remove the database dependency. We will stub the database connection instead, and “fool” our class to think that it is talking to a real EntityManager, while in reality, the EntityManager is a Mockito stub. This way, we have complete control over what is returned by the database connection without having to deal with an actual database.
<br>
Basic Unit Test for the above code is:
<br>
```java
public class CustomerReaderTest {

		@Test
		public void happyPathScenario(){
			 Customer sampleCustomer = new Customer();
			 sampleCustomer.setFirstName("Susan");
			 sampleCustomer.setLastName("Ivanova");
		
			 EntityManager entityManager = mock(EntityManager.class);
			 when(entityManager.find(Customer.class,1L)).thenReturn(sampleCustomer);
		
			 CustomerReader customerReader = new CustomerReader();
			 customerReader.setEntityManager(entityManager);
		
			 String fullName = customerReader.findFullName(1L);
			 assertEquals("Susan Ivanova",fullName);
		}
}

```
<br>
We are had coding the return values which our EntityManager is supposed to return "Susan Ivanova" , which have been otherwise retrieved from the connecting to the database. Now are able to test the class with out connnecting to the actual dependency.

Now, Below code is slightly improvised to ensure our class has multiple external dependencies which is the case with many real time applications today.
<br>
```java
public class LateInvoiceNotifier {

		private final EmailSender emailSender;
		private final InvoiceStorage invoiceStorage;
	
		public LateInvoiceNotifier(final EmailSender emailSender, final InvoiceStorage invoiceStorage){
			this.emailSender = emailSender;
			this.invoiceStorage = invoiceStorage;
		}
	
		public void notifyIfLate(Customer customer)
		{
			if(invoiceStorage.hasOutstandingInvoice(customer)){
				emailSender.sendEmail(customer);
			}
		}
	}
```
<br>
The above class has two external dependencies, and we use constructor injection this time around. It checks for late invoices of customers and sends them an email if an invoice is late.

In a real system, the ```InvoiceStorage``` class is actually a web service that connects with an external legacy CRM system which is slow. A unit test could never make use of such a web service.

The ```EmailSender``` class is also an external system from a third-party mass email provider. So, we will stub it as well.

However, as soon as you try to write a unit test for this class, you will notice that nothing can really be asserted. The method that we want to test – ```notifyIfLate``` – is a void method that cannot return anything. 

But when it comes to a system or class which has two or more external dependencies, thats when the Stubbing can't be a solution. Majority of enterprise grade applications are no so simple in nature and with the modularity with agility, stubbing can't be used for a component testing. Moreover hardcoding values for a request is only thing supported by stubbing. But when it comes to enterpsie grade applications, 

* 📢 When the application becomes moderately complex and expects a variety of return values, Hardcoding each one of them becomes a nightmare and is unmanageable.
* 📢 When the class is actually a web service, A stub can never make use of such a webservice or evaluate the web service calls.
* 📢 Stubbing only does **state testing** and doesn't support **interaction testing** while mocking does.


### What is Service Virtualization

Challenges with mocking are, Mocking functions focus on very specific scenarios and contexts  and simulate behavioral responses to fulfill a certain development need. This can be very useful for isolating a specific component from the rest of the application and for performing unit tests. In general, mocking is a static implementation and state-less (responses do not have contextual information), and requires 100% manual configuration. Developers/testers must go through the arduous act of creating new interfaces for each use case. Mocks are not re-usable, not flexible and tough to automate. Mocking is not very robust, and ultimately a simplistic approach that can waste a lot of time.

Service Virtualization, on the other hand, can emulate the full implementation of the service and provides a more realistic, fully functioning simulated environment that is more robust than just mocking. For example, API virtualization provides you with the capability of quickly recording virtual API responses. These responses can then be used to create other scenarios by linking them to a dataset. Virtualization tools also allow developers to switch between a virtual environment and real asset, and test under a variety of conditions. Furthermore, Virtualization can help you do load tests, performance tests, failure tests, security tests, evaluate integrations, and more.  Components can be used throughout production and testing environments, especially early in the software development lifecycle. Another benefit is that these tests can also be automated. ** Mocking simulates individual classes while service virtualization can simulate the entire network of services.**

In the micro service world with complex applications, Service Virtualization is the go to solution but comes with a cost, complexity and hard to find skilled resources

<br>

![image](https://user-images.githubusercontent.com/100637276/161148990-1043a444-fdf6-4e8b-a0d1-29bdc7c1432a.png)

  
<br>


## Brief Comparision between Stubs, Mocks and Virtual Services

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

## Brief Comparision of various Mocking and Service Virtualization Tools
<br>

📝Below image illustrates the difference between a Stub, Mock and a Virtual Service

![image](https://user-images.githubusercontent.com/100637276/159629077-6350437b-171e-47f3-9c2d-c7cc39d7c47b.png)

<br>

After a thorough research of various available open source and paid tools, considering the wider open source community, wider adaption, extended support for languages and the problems they solve besides compliance with multiple frameworks, the below list of tools are identified for implementation of Continuous Testing Vision

<br>


| **Mockito**                                                                                                    | **WireMock**                                                              | **BlazeMeter Mock Services**                                                                         | **DevTest**                                                                                             |
|----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| Freeware                                                                                                       | Freeware                                                                  | Licensed                                                                                             | Licensed                                                                                                |
| Mockito provides the<br>mock implementation of<br>the method/object                                            | Provides simulation for <br>HTTP based APIs                               | Mock Services only support<br>HTTP Protocols                                                         | Full Fledged support for <br>virtualizing almost any<br>service via wide range of<br>protocols          |
| Mockito is designed<br>Unit Testing                                                                            | WireMock is designed for <br>component testing and<br>integration testing | Functional, Regression and<br>Performance Testing                                                    | Functional, Regression and<br>Performance Testing                                                       |
| Used in Development stage<br>of SDLC                                                                           | During early API Integration                                              | When In-house and Third party <br>systems are unavailable, TDM <br>dependency and during load issues | When In-house and Third Party<br>systems are unavailable, TDM <br>dependency and during load<br>issues. |
| Support for all Object<br>Oriented Languages (Java, PHP) and<br>Python, Flex, JavaScript, Scala,<br>TypeScript | Supports HTTP based API calls                                             | Supports HTTP(S) only as of now.<br>BlazeMeter can integrate with <br>Broadcom's DevTest             | Supports 30+ Protocols                                                                                  |

<br>

## Choice of Tools for CTV SV Project Implementation

After a thorough research of the available mocking tools, 

The choice of **Mocking framework is Mockito** for the following reasons:

* Simple, Clean API
* Large Community support
* Extensions solve some difficult edge cases
* Verification and exceptions are clean and flexible
* Ease of use
* Approximately 80% of the DevTeams who work with OOPS languages already use Mockito for their Unit Testing.
* Supported languages (native and through extensions): Java, Python, Flex, Javascript, Scala, Perl, Objective C, Perl, PHP, TypeScript

The choice of **basic open source Service Virtualization tools is Wiremock** for the following reasons:

* Has both fluent expressions (DSL) & configuration using Json
* Easy to write with extensive options of methods to perform,validate,extend a stub.
* They had given equal importance to configuration mocks in lines with fluent expressions.
* Documentation is quite good & most liked github project.
* Dockerized versions of WireMock is available to spwan up ephimeral containers

The choice of **full fledged licensed Service Virtualization tool is Broadcom DevTest** for the following reasons:

* Full stack API testing
* Component-level performance testing
* Codeless testing ability
* Integration with existing application development processes and test management tools
* Widest multiprotocl support for DevTest Available
* Supports dynamic strings and data support with random test data

## More insights on Broadcom DevTest 

<p>With Broadcom DevTest, we can achieve </p>

* Parallel development and testing in a DevOps environment
* Shift left to test early and test more in the software lifecycle
* Widest multi-protocol support available
* Simulation of observed behaviors, stateful transactions, and performance scenarios
* Realistic ways to emulate application testing
* Ease of administration

<br>

#### Development Testing Team Deployments

Easily store and launch test cases with CA Application Test as actionable assets alongside source code management (SCM), requirements management, build and issue tracking of test management tools.

<br>

![image](https://user-images.githubusercontent.com/100637276/161157477-920934bf-2bd3-4457-b104-c04b9722d3c0.png)

<br>

#### Functional Testing Team Deployments

Run tests across the whole stack to validate APIs at every layer of a complex, multi-tiered application, allowing for complex workflows and the stateful behavior needed to achieve genuine reuse of test assets.

<br>

![image](https://user-images.githubusercontent.com/100637276/161157567-efdabe36-c0ee-42c9-8e0e-2575f7ce8c6f.png)

<br>

#### Virtualize anything and test anything

Out of the box, DevTest with CA Application Test and CA Service Virtualization features the broadest and deepest multi-data protocol support across front-end, middleware, and back-end technologies.

* Common web services protocols: HTTP, HTTPS, REST, SOAP, XML, JSON
* ESB/middleware protocols: WebSphere® MQ, WebSphere Native, Standard JMS, Tibco JMS, Rabbit MQ
* Mainframe protocols: CICS Link, CICS Transaction Gateway (CTG), IMS Connect, DRDA, Copybook
* ERP protocols: SAP—RFC/Jco, Idoc/Jco
* Database protocols: JDBC
* Financial protocols: SWIFT, EDI/X12
* Proprietary: TCP (Raw Socket), Java™, Scriptable (JSR-223 compliant), Request Manager, (Data-desensitizer)

<br>

![image](https://user-images.githubusercontent.com/100637276/161158468-0765d7e2-b41e-4d1d-9c53-4d165b128734.png)

<br>

#### Creation and Utilization of Virtual Services in DevTest

The DevTest portal enables users to create API tests quickly and easily—either by entering the data or by pasting it into the test request and response fields. This RR data can also be imported in bulk from one or more request-response file pairs, and then edited in the portal.

<br>

![image](https://user-images.githubusercontent.com/100637276/161164260-e9094a0f-3f7b-41b4-832c-43a3c9d416d0.png)

<br>

## Implementation Guide for Service Virtualization using Broadcom DevTest

Onboarding Virtual Service
This document covers how to identify backend for virtualization, requirements for virtualization, assumptions, limitations and prioritization related to backend virtualization. 



Identification and technical requirements gathering for virtualizations of backend service:

Below mentioned points helps to identify backend and its behaviour. Also, technical details to be asked from developers/tester and architecture during engagement.
<br>
**1.	Engaging developers/testers to understand the architecture of the system and identify bottle necks in the system. Like if there is test data dependency, downtime, consumption cost, integration issue, access issue with the backend.**

![composite_application_example](https://user-images.githubusercontent.com/100874882/164086473-13e5dbf2-6275-4d34-80a5-59e66c009a48.png)


**2.	Technical details requirement:**

a.	Transaction flow end to end:
i.	To identify which layer can be virtualized.
ii.	How calls flow and if there is dependency between backend.

b.	Data protocol: 
i.	Format in which data flows between system.
ii.	For instance: SOAP/XML, JSON etc.

c.	Transport protocol
i.	Wrapper in which data flows between system.
ii.	For instance: HTTP/S, JMS, JDBC, LDAP etc.

d.	In-house or third party
i.	It will help in identify support required and dependency on third party team.
ii.	Third party integration make require firewalls request raised or permissions to access the system.

e.	Transaction
i.	What will be the transaction count and testing peak hours?
ii.	How much delay required to respond to transaction?

f.	Functional or Non-functional testing
i.	This requirement which help decide behaviour of virtual service.
ii.	Helps deciding configuration of virtual service, delay etc.

g.	Flow data in form of request/response pairs, Swagger API, recording or traffic log
i.	Developers/Testers need to share request/response pairs, traffic log to create virtual service.
ii.	Recording can be performed by placing recorder between systems identified for virtualization.

The following graphics show that when recording the service image, the virtual service environment (VSE) acts as the pass through mechanism between the client and server. While the VSE passes the requests and responses along, it records the transactions.

 **Normal Operations**
 
 ![normal_client_server_operations_diagram](https://user-images.githubusercontent.com/100874882/164087611-a5ed2b96-493a-4549-bfd2-957389ceec94.png)

**Recording 

![image](https://user-images.githubusercontent.com/100874882/164087707-81370b83-4a57-46b6-8e62-68f020692bff.png)

At the time of virtualization, the VSE responds to the client requests by consulting the recorded transactions.

![virtual_service_with_recorded_transactions_diagram](https://user-images.githubusercontent.com/100874882/164087770-54a4e6eb-99f5-4fd9-8119-06f9cdef48e7.png)


**Limitations and assumptions to consider while creating virtual service:**


a.	Limitations of virtual service


i.	It cannot replace actual system for testing.

ii.	Virtual services don’t interact with bottom layer backend.

iii.	Avoid Virtualizing unstable APIs If you are virtualizing an API, you freeze it in time. What happens if the original API in under heavy development and changes every week significantly enough to cause breaking changes on your side? You need integration contract tests, consumer-driven contract tests or manual exploratory tests to make sure the original API has not drifted from the virtualized version. If you cannot afford development of those automated tests or do not have resources to spend time manually testing it you should consider either using the real version of the API or postponing consumption the APIs. You could be working on other parts of the system as a priority and come back to the unstable API once is a bit more stable.

b.	Assumptions

i.	Testing will be done using limit set of scenarios against virtual services.

ii.	Focus on keeping it simple. We have worked in environments where systems have been designed for testability from day 1 and where there was no focus on that. 

What we have found was that creating stateful virtual services or even simulators was sometimes a necessary short-term solution, especially when you are working in an environment where the System Under Test has not been designed for testability. What we have found was that systems not designed for testability often use external APIs in a way that makes them hard to virtualize. 
We have also been on projects where system testability was part of the design process from the beginning. In those environments, stubbing the external APIs was very simple. The virtual services are almost always stateless and never have any logic. 


Always focused on avoiding creating complicated virtual services. That fact that you can simulate a backend system in your virtual service does not mean you should. 

Remember that service virtualization is only a part of the big picture of creating software to deliver business impact. What you want to do is help reduce the costs of software delivery as a whole, not only QA is isolation. 

Create the simplest virtual services possible. If you need to create a stateful or simulator virtual service, communicate well with architects and developers to make sure they know how much effort goes into it. It might be a cheaper long term option to change/refactor the use of the APIs and the APIs themselves to make them more testable and eliminate the need for complex simulation. 

iii.	Remember what is the System Under Test (SUT) when virtualizing a service Your system under test is the group of components or applications you are testing. It defines the set of functionalities you are testing. 

Let us say we are testing a web application connecting to a backend system. The backend system exposes an API for users to log in. After three unsuccessful logins, the backend system will lock the account and start returning an error indicating that the account has been deactivated. If I am testing the web application, I would like to see three messages rendered to the user: a successful login, an unsuccessful login, and an account locked message. It can be done with three stateless responses from a virtual service. Then I would proceed to test the backend system API. I would test that after 3 login attempts I get an account locked message. After, that I would proceed to test both systems in an integration environment, but I would not repeat all the tests. I would only test if I can successfully log in. The number of tests in integration environments should be reduced to a minimum giving enough confidence to proceed to production (more on the testing pyramid by Google). So we had three stages of testing, in the first one the SUT was the web application. In the second one, the SUT was the backend system. In the third one, the SUT was the web application together with the backend system. When the SUT is the web application, it does not make sense to create a stateful virtual service that will return an account locked message after three unsuccessful logins. It would create unnecessary complexity. You have to test that functionality in the backend system anyway, so you know it works. All you test in the web application is whether the messages get rendered correctly.

<img width="980" alt="Before-After-SV" src="https://user-images.githubusercontent.com/100874882/164092130-949b994c-7b12-4c14-9488-0c396b40b7c3.png">


**Prioritization of creation of virtual service:**
Below are few points mentioned which can be in prioritizing virtual service creation:
1.	Number of operation and test cases to be virtualized 
a.	Backend with high operation and test cases count will be given priority over low operation count backend.

2.	Test data dependency
a.	Regression test cases dependant on test data from TDM team will be given high priority as it will remove dependency form TDM team.
b.	Test data creation may take hours or days or DB refresh after each testing which can be removed by virtualization.

3.	Transaction count
a.	High traffic backend will be given priority over low traction count backend.

4.	Third Party or in-house
a.	Third party application will be given priority over in house as there can be downtime and cost associated with consumption of third party service.

## DevOps Pipeline as Code Implementation

<br>

<p>Mockito Implementation</p>

https://github.com/telus/triangulum-ctv-telusRobotShop/tree/main/mockitoApp#readme

<p>Wiremock Implementation</p>

https://github.com/telus/triangulum-ctv-telusRobotShop/tree/main/shipping#readme

<br>

👷👷👷👷

<br>
<br>
