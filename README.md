Spring Boot Zero-to-Hero
=

Introduction
-

For the past five years my team has focused on delivering Grails applications. As our process matured we've found
ourselves able to deliver production-ready Web applications in a really big hurry by leveraging things like Spring
Security, database migrations, Gradle-based pushbutton builds, and so on.

With a growing interest in microservices I thought it was time to lay down the foundation for a similar process that
doesn't bring along all of Grails, giving us a set of stripped-down templates for authenticated, JSON-based REST services
that use Spring Boot and don't care about a specific database engine.

Basis
-

Each template will draw a good deal on already-available tutorials but add in convenience methods and abstract helper
classes aiming to minimize the effort needed to develop a production-ready Spring Boot microservice.

Language Choice
-

I started to do this in raw Java (since I haven't used it in a long time), but that's not where I want to go in life.
We'll be using Groovy, since its static compilation has greatly reduced any performance issues.

Format
-

I'll be building up each template as a branch in this repository. Each branch will add to this README.md describing
what it's added as we journey from "hello world" to a usable real-world microservice.

Part 1: Bare-Bones REST
=

Our bare-bones REST service is based on [the simple actuator-based REST example provided by Spring](https://spring.io/guides/gs/actuator-service/).

The following is what I've added on to make life easier.

Git-safe Configuration via "development" Profile
-

The `application.yml` declares `spring.profiles.active` to be 'development'.

Why should you care?

This makes it so that:

* Developers can place *universal* configuration defaults in `src/resources/application.yaml` and let it be tracked in
Git
* We can .gitignore `src/resources/application-development.yaml`. Individual developments can then add
any settings specific to their development environment (e.g. database usernames and passwords) in an optional
`src/resources/application-development.yaml`.

(Production deployments would likely use an environment variable to point to their specific configuration file.)

Groovy
-

Groovy's been added to the stock build.gradle file. The only changes this requires are:

* Applying the "groovy" plugin
* Adding Groovy as a compile dependency

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

Spock Integration
-----------------

In the Groovy/Grails world we favor Spock BDD over JUnit TDD. Therefore Spock and its Spring integration is added and
working (there's some version hell to go through) and the unit test in the example has been rewritten to be a Spock
specification.


Simplified REST Testing
-----------------------

The tests for the basic "Hello World" service in the Spring REST example contain a lot of boilerplate. To DRY things out,
there's now an abstract class (AbstractRestConfigurationSpec) that provides dead-simple helpers for testing our REST
services.

Basically, this:

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
      @SuppressWarnings("rawtypes")
      ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
          "http://localhost:" + this.port + "/hello-world", Map.class);

      then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


Is now this:

    def "Should return 200 when GETing /hello-world our service"() {
      when:
      ResponseEntity response = service.get( '/hello-world' )

      then:
      response.statusCode == HttpStatus.OK
    }

Part 2: CRUD REST in Memory
=

Next we'll expand our little service to do common CRUD operations.

Goodbye, Greeting
-

Greeting was a holdover from the original Spring tutorial. Much like Step 1, I'm basing this part off of a different
Spring guide: [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/). In it, we'll ditch Greeting
and work instead with another simple domain class named Contact.


Hello, Spring Data
-

The first major addition to our template is the spring-boot-starter-data-jpa library. This lets us leverage Spring
Data's notion of a `Repository` interface: basically, autogenerated methods like save(), delete(), and findOne(id). All
of Spring Data is a yuge topic, but it's worth reading the five minute [quick start](https://projects.spring.io/spring-data/#quick-start)
to get the gist of what we're doing.

Introducing Contact
-

In our template, we're going to manage the proverbially overused `Contact` example class. While we _could_ automagically
handle everything for CRUDding a contact via REST by marking a ContactRepository with @RepositoryRestResource, that's
cheap and doesn't separate concerns.

Instead, we'll keep everything tidy by separating things into four classes:

`domain.Contact` - Our Contact class, annotated to be persistable by any JPA implementor (e.g. Hibernate)

`services.ContactService` - Coarse grained logic for managing contacts. Right now it's not much more than a wrapper 
for the `ContactRepository`, but if we wanted to do something like send alerts on contact deletion or e-mail 
notifications, it'd be a good place to do so. It knows nothing about REST or however else we expose its logic.

`repositories.ContactRepository` - tl;dr - Automagic DAO courtesy of Spring Data.

`controllers.ContactController` - A REST-focused controller for Contact CRUD operations that maps different HTTP methods
and URI paths to different operations. It handles any marshalling of inbound data, serialization of outbound data, and
REST-specific response semantics, such as HTTP status codes.

What's this RestServiceHelper?
-

As I built up tests against the initial service in Part 1 and especially in this part, I immediately grew frustrated
with the Spring-provided RestTemplate (and TestRestTemplate). Since I do a good deal of front-end development, I'm 
used to simpler ways of calling REST services.

RestServiceHelper is my wrapper for RestTemplate that simplifies common REST requests.

It's not a huge deal, and I don't intend it to simplify everything, but the Groovy in me would much rather write this:

    def response = restServiceHelper.get('/contacts', restServiceHelper.listOf(Contact) )

Than its RestTemplate raw-Java equivalent:

    ResponseEntity<ArrayList<Contact>> response =
        restTemplate.exchange( apiUrl + '/contacts',
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Contact>>() {
        });

_(Believe it or not, IDEA is good enough to sort out and code-hint the response)._

For full use of the `RestServiceHelper`, watch our Specs like `ContactApiSpec` evolve or check out the Javadoc on
`RestServiceHelper` and the jQuery .ajax() "options"-inspired `SimpleRestRequest` it uses under the hood. 





