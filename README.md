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
______

I'll be building up each template as a branch in this repository.






