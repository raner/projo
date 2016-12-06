## projo &nbsp; [![Build Status](https://travis-ci.org/raner/projo.svg?branch=master)](https://travis-ci.org/raner/projo)
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
Other languages, like Scala, for example, offer something called *case classes* that provide a very concise way of defining
such a class, but in Java it is a lot of code (especially for a class that does essentially nothing). Even if you are not
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
Mockito uses when creating mock objects, except Projo's getters and setters will be fully functional.

The above example is only the beginning: what about factories for creating your objects? Also, if you want to safely use the
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

### How does Projo relate to Project Lombok?
Project Lombok uses compile-time code generation to splice getters, setters, equals, etc., into your class files when you
compile your sources. Generating code at compile time can be tricky and requires some hacks to get IDEs to recognize the
generated code. Projo, on the other hand, does not use compile-time code generation, it uses runtime code-generation, so your
class files will always directly correspond to the code that you wrote, and no hacky IDE integration is required. The
downside of Projo (if you want to call it that), is that you need to change your code from using ```new Pojo()``` to
```create(Pojo.class)```, as shown above. Is Projo better than Lombok, or vice versa? No, not really either way, they are
just different approaches to the same problem. Your mileage may vary.

**Disclaimer:** Projo itself is built using compile-time code generation (for example, to generate factory interfaces with
varying numbers of parameters). As a user of Projo, however, you will not notice that at all, and at your own project's
compile time no Projo code generation will take place.

### Does Projo support immutable objects?
Yes, Projo does support immutable objects. As immutable objects need to be fully initialized when they are created this
requires the use of a factory or a builder (the latter not being supported yet). To use a factory, simply add a static
factory field to your Projo interface:
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

### Will Projo work with my JAX-RS application?
Yes, Projo provides integration support for JAX-RS and Projo objects can be serialized and deserialized just like
any other project. To enable the integration, simply put the ```projo-jax-rs``` JAR on your runtime classpath (it is
not needed at compile time). The integration uses auto-discovery to modify your JAX-RS serialization and deserialization
so that it becomes Projo-aware. Instead of trying to ```new``` up an interface, the deserializer will correctly use
the Projo library to instantiate new objects.

Currently, Projo only supports Jersey as the JAX-RS provider, in conjunction with the MOXy marshaller. Eventually, other
JAX-RS implementations (such as CXF) and other marshallers (such as Jackson) will be supported as well.
