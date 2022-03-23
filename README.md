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
