## projo &nbsp; [![Build Status](https://travis-ci.org/raner/projo.svg?branch=master)](https://travis-ci.org/raner/projo) [![Maven Central](https://img.shields.io/maven-central/v/pro.projo/projo.svg)](https://oss.sonatype.org/content/repositories/releases/pro/projo/)
**Projo** is a Java library for reducing the amount of boiler-plate code that is necessary for implementing simple model
objects and DTOs (i.e., objects that don't contain any business logic). The name Projo is a portmanteau of **Pro**xy and
PO**JO**.

You probably have written many classes like the following:

```java
public class Person
{
    private String firstName;
    private String lastName;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.firstName = lastName;
    }
}
```
Java 14 offers *records* to vastly simplify this kind of class, but in earlier versions of Java it is a lot of code
(especially for a class that does essentially nothing).
Also, other languages, like Scala, for example, offer something called *case classes* that provide a very concise way of defining
such a class. Even if you are not
following DDD principles, an average software project can easily contain hundreds of these classes, accounting for thousands,
if not ten-thousands, lines of code.

And by the way: did you notice the subtle bug in the code above? Check the ```setLastName(...)``` method. DTOs and POJOs may
be simple classes, but they are still susceptible to bugs.

**Projo** offers a much simpler, more concise and less error-prone way of defining this kind of class. And it means a lot
less typing for the developer.

Using Projo, the above code can be reduced to just a few lines of code:
```java
public interface Person
{
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
}
```
But that's an interface! Every Java programmer knows that interfaces can't be instantiated. Who is going to implement that
interface?

Yes, it's true, a simple ```new Person()``` won't do it anymore. Instead, you would use:
```java
import static pro.projo.Projo.create;
...
    Person person = create(Person.class);
```
Projo will create a proxy object or generate an implementation class at runtime. This is similar to the mechanism that
[Mockito](https://site.mockito.org/) uses when creating mock objects, except Projo's getters and setters will be fully functional.

The above example is only the beginning: what about factories or builders for creating your objects? Also, if you want to safely use the
objects as keys in a hash map you will need ```equals()``` and ```hashCode()``` methods. And what about a ```toString()```
method that actually produces readable information? Also, often times, a lot of the fields are mandatory and cannot be
```null``` or ```Optional```, so you'll need more code to enforce that...

All this code is not only potentially a lot of typing, but it is also error-prone. Coding mistakes like the field mix-up
in the example above are more common than you may think, but not always as easy to spot.
An ```equals()``` method without a ```hashCode()``` method, or vice versa, is another common mistake. And even if both methods
are present, that does not necessarily mean they were implemented correctly and in line with the hashCode/equals contract.

**Projo** completely removes the need for developers to ever again implement a getter, setter, hashCode, equals or toString
method.

Still got questions? Please continue reading...

## FAQ

### How do I use Projo in my project?
If you are using [Maven](https://maven.apache.org/), simply add these two dependencies to your POM:
```xml
  <dependency>
   <groupId>pro.projo</groupId>
   <artifactId>projo</artifactId>
   <version>1.2.0</version>
  </dependency>
  <dependency>
   <groupId>pro.projo</groupId>
   <artifactId>projo-runtime-code-generation</artifactId>
   <version>1.2.0</version>
   <scope>runtime</scope>
  </dependency>
```
The ```projo-runtime-code-generation``` dependency is technically optional, but highly recommended as it provides a way
more efficient implementation. This dependency is not needed at compile-time, only at runtime.
As a very minor down-side, Projo's runtime code generation brings in a transitive dependency on [ByteBuddy](http://bytebuddy.net/).

If you are using a different build system, such as [Gradle](https://gradle.org/) or [SBT](http://www.scala-sbt.org/),
please use the equivalent way to include the same dependency coordinates.

### How does Projo relate to Project Lombok?
Project Lombok uses compile-time code generation to splice getters, setters, equals, etc., into your class files when you
compile your sources. Generating code at compile time can be tricky and requires some hacks to get IDEs to recognize the
generated code. Projo, on the other hand, does not use compile-time code generation, it uses runtime code-generation, so your
class files will always directly correspond to the code that you wrote, and no hacky IDE integration is required. The
downside of Projo (if you want to call it that), is that you need to change your code from using ```new Pojo()``` to
```create(Pojo.class)```, as shown above. Is Projo better than Lombok, or vice versa? No, not really either way, they are
just different approaches to the same problem. Your mileage may vary.

The Projo library itself is built using some compile-time code generation (for example, to generate factory interfaces with
varying numbers of parameters). For the basic features of Projo, as demonstrated above, no Projo code generation will take place at compile time of a project that includes Projo on its class path. However, Projo's
compile-time code generation features, such as annotation-based "API scraping" of interfaces and enums, can be
leveraged in combination with Projo's runtime features. Generating POJO or DTO interfaces based on some
templated structure (e.g., an XML schema) and then have them automatically implemented at runtime is one
example of such a combined use of compile-time and runtime features of Projo.

### Does Projo support immutable objects?
Yes, Projo does support immutable objects. As immutable objects need to be fully initialized when they are created this
requires the use of a factory or a builder.

To use a factory, simply add a static factory field to your Projo interface:
```java
import pro.projo.doubles.Factory;
import static pro.projo.Projo.creates;

interface Person
{
    Factory<Person, String, String> FACTORY = creates(Person.class).with(Person::firstName, Person::lastName);
    String firstName();
    String lastName();
}
```
To create an object, you can then simply use the factory:
```java
    Person person = Person.FACTORY.create("John", "Doe");
```
Alternatively, immutable objects can also be constructed using a builder:
```java
    Person person = Projo.builder(Person.class)
        .with(Person::firstName, "John")
        .with(Person::lastName, "Doe")
        .build();
```

### Can Projo create Value Objects?
The [Value Object](http://dl.acm.org/citation.cfm?id=141550) pattern (free article available
[here](http://dirkriehle.com/computer-science/research/2006/plop-2006-value-object.pdf)) requires objects to be immutable
and have ```equals(Object)``` and ```hashCode()``` methods that are based on the individual fields of the object instead
of the object's identity. Projo will create appropriate implementations of ```equals(Object)``` and ```hashCode()``` if **at
least one** of these three criteria is fulfilled:
* the Projo interface declares an ```equals(Object)``` method (should also have ```@Override``` annotation)
* the Projo interface declares a ```hashCode()``` method (should also have ```@Override``` annotation)
* the Projo interface has a ```@pro.projo.annotations.ValueObject``` annotation

For example, both ```equals(Object)``` and ```hashCode()``` will be implemented properly, even if the Projo interface only declared and overrode the ```hashCode()``` method. Projo will **never** create objects that have inconsistent 
```equals(Object)``` and ```hashCode()``` methods: either both methods will be implemented on a field-by-field basis or both
methods will be based on object identity (but never a mix of the two).

### Are Java proxies efficient for implementing objects at runtime?
No, not at all. In fact, proxies are quite horribly inefficient, both from a memory use and a performance perspective.
Performance-wise, proxy-implemented objects are **2 to 3 orders of magnitude slower** than regular Java objects (typically
by a factor of ~650). They also use a lot more memory.

For those reasons, it is always recommended to have Projo RCG (Runtime Code Generation) on the runtime classpath. Projo
RCG will replace the default proxy-based implementation with much more efficient code that is generated at runtime.

### Will Projo work with my JAX-RS application?
Yes, Projo provides integration support for JAX-RS, and Projo objects can be serialized and deserialized just like
any other object. To enable the integration, simply put the ```projo-jax-rs``` JAR on your runtime classpath (it is
not needed at compile time), i.e.:
```xml
  <dependency>
   <groupId>pro.projo</groupId>
   <artifactId>projo-jax-rs</artifactId>
   <version>1.2.0</version>
   <scope>runtime</scope>
  </dependency>
```
The integration uses auto-discovery to modify your JAX-RS serialization and deserialization
so that it becomes Projo-aware. Instead of trying to ```new``` up an interface, the deserializer will correctly use
the Projo library to instantiate new objects.

Currently, Projo only supports Jersey as the JAX-RS provider, in conjunction with the MOXy marshaller, as well
as the Jackson marshaller, both in a limited fashion (i.e., not all Jersey/MOXy/Jackson features are
supported or recognized). Eventually, there will be more complete support, and other
JAX-RS implementations (such as CXF) will be supported as well.

### What is new in Projo 1.1.0?
Besides several bug fixes (e.g., [#1](https://github.com/raner/projo/issues/1), [#10](https://github.com/raner/projo/issues/10), [#11](https://github.com/raner/projo/issues/11)), Projo 1.1.0 also introduced two major new features:
* [Annotation-based API generation ("API scraping")](https://github.com/raner/projo/issues/8)
* [Support for Jackson serialization/deserialization](https://github.com/raner/projo/issues/12)

Until more detailed documentation becomes available, please have a look at the test cases in the
[projo-template-generation-test](https://github.com/raner/projo/tree/master/projo-template-generation-test/src/test/java/pro/projo/generation/interfaces) and [jackson](https://github.com/raner/projo/tree/master/projo-jackson/src/test/java/pro/projo/jackson) module to get a basic idea how these new features can be used.

### What is new in Projo 1.2.0?
Release 1.2.0 contains mainly improvements to Projo's API scraping feature as well as some major and minor
bug fixes.
