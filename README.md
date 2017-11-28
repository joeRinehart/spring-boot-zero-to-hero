Spring Boot Zero-to-Hero
========================

Introduction
------------

For the past five years my team has focused on delivering Grails applications. As our process matured we've found
ourselves able to deliver production-ready Web applications in a really big hurry, leveraging things like Spring
Security, database migrations, Gradle-based pushbutton builds, and so on.

With a growing interest in microservices, I thought it was time to lay down the foundation for a similar process that
doesn't bring along all of Grails, giving us a stripped-down templates for and  authenticated, JSON-based REST services
that uses Spring Boot and don't care about a specific database engine.

Basis
-----

Each template will draw a good deal on already-available tutorials but add in convenience methods and abstract helper
classes aiming to minimize the effort needed to develop a production-ready Spring Boot microservice.

End Goal
--------

Building on a production-ready microservice template, I'd then like to go back and add in things like authentication,
database support, database migrations, and then ORM support, letting us pull exactly what we want for building a small
service.

Since I'm no longer in a paid R&D role and don't have a specific client buying this, I'm doing it in my spare time and
making it a publicly-available set of code.

Language Choice
---------------

I started to do this in raw Java (since I haven't used it in a long time), but that's not where I want to go in life.
We'll be using Groovy, since its static compilation has greatly reduced any performance issues.

Format
------

I'll be building up each template as a branch in this repository.

Part 1: Bare-Bones REST
=======================

_(Note: this is likely to be the longest of these per-template/branch writeups, because it's where we'll get the
most of our Spring Boot microservice ducks in a row.)_

Our bare-bones REST service is based on [the simple actuator-based REST example provided by Spring](https://spring.io/guides/gs/actuator-service/).

The following is what I've added on to make life easier.

Groovy
------

Groovy's been added to the stock build.gradle file. The only changes this requires are:

- Applying the "groovy" plugin
- Adding Groovy as a compile dependency

This _does_ mean a few things are different in the code: for example, we'll use Groovy-style no-arg default constructors
instead of constructors with explicit argument lists.

Hot Reloading
-------------

The spring-loaded dependency has been added to the gradle file and hot reloading is ready to go. To use it (assuming
you're a sane person with IntelliJ IDEA):

- Run the application with `gradle bootRun`, not `java jar`
- If you're using a newer version of IDEA that allows the `compiler.automake.allow.when.app.running` setting to be set
to true, do so then open IDEA preferences and go to Build, Execution, Deployment -> Compiler and check off "Make Project
Automatically." After an IDEA restart, you should be good to go.
- If you're not using a version where you can do that, just hit cmd/ctrl-F9 to rebuild the entire project (or map
something easier to press to the "Rebuild Module" IDEA command).

Additionally, src/main/resources/application.properties is set up to not cache Thymeleaf, Freemaker, or Groovy
templates.




