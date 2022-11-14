# Projo User Guide

Projo is a Java library that helps with reducing the amount of boilerplate code in your projects. Projo started
out as a simple solution for implementing POJOs and DTOs at runtime, but has since grown to address several other
scenarios that involve boilerplate code. Many of the solutions are applied at runtime, but Projo also offers
some mechanisms that work at compile time.

## Introduction

For a long time, Projo's documentation has been lagging behind its feature development, and some useful features
were added without any documentation at all. As a result, a lot of Projo's helpful functionality was difficult
to use (and difficult to discover in the first place). This user guide attempts to remedy the current lack of
documentation and will hopefully make Projo easier to understand and use.

If Projo's feature list seems somewhat random to you, well, it's probably because it is. The initial idea behind
Projo was to provide an alternative to [Lombok](https://projectlombok.org/) and similar frameworks, but much of
the development since has been driven by downstream projects, some of which are not yet open-source. All of Projo's
features are used by some downstream project and were created for a reason (i.e., they solve a real-world problem,
and were not just added because they seemed cool at the time). Whereas most features have pretty wide applicability
over a whole range of projects, Projo also addresses a few very specific problems that are a little more obscure.

### Dealing with Boilerplate Code

[Boilerplate code](https://en.wikipedia.org/wiki/Boilerplate_code) is repetitive code that usually follows a very
simple pattern. The classic example are getter and setter methods for the attributes of a
[Value Object](https://en.wikipedia.org/wiki/Value_object) or [DTO](https://en.wikipedia.org/wiki/Data_transfer_object):
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
Each attribute requires its own getter and setter method that follow a very simple pattern. If there are a lot of
attributes you might have to write a lot of similar code, which is not only tedious and boring, but (like all
activities performed by humans) also very error-prone. Did you notice the subtle bug in the `setLastName` method
in the code above?

#### Writing Boilerplate Code by Hand

Boilerplate code can always be written by hand, and if your project only contains a small amount of it, this may
be a simple and pragmatic solution. After all, this is how boilerplate code has been handled for decades.
As you have seen above, this approach is susceptible to errors introduced by repeated copy-and-paste, but if your
project has decent test coverage you can easily make sure that all boilerplate code works as designed. However,
this approach tends to break down if you use design methodologies like [DDD](https://en.wikipedia.org/wiki/Domain-driven_design)
that tend to produce a lot of boilerplate code in languages like Java.

#### Having Your IDE Generate the Boilerplate Code for You

The next step up in the handling of boilerplate code is to have your IDE do some of the heavy lifting. Most IDEs
nowadays have support to generate getters/setters, equals/hash code, delegate methods and variety of other types
of boilerplate code. Using this functionality (when available) removes the tedium of manually writing these methods,
and it also eliminates copy-and-paste errors. However, as data models change over time, the code has to be kept in
sync. Adding new methods can typically be easily achieved by running the IDE's code generation again, but renaming
of existing attributes/getters/setters will sometimes require multiple successive refactoring steps, and it can
easily happen that a step is accidentally skipped. Similar for removing of attributes, where a common mistake is
to remove the accessor methods but not the attribute field itself (though most properly configured IDEs will issue
a warning in that case).

The point is: even though the boilerplate code is largely generated automatically, it is still code that forms a
regular part of the code base, and as such it requires maintenance to be kept up-to-date. In large DDD-based
projects this can be a significant percentage of the overall code base. In the end, the goal is never to have
a large code base. What we really want is functionality, software that does something meaningful, and the fewer
lines of code that need to be added to the repository the easier it will be to maintain the code base.
IDE-generated boilerplate code is definitely preferable to hand-written code, but since that code becomes a
regular part of the code base, it still negatively affects maintainability.

#### Having the Compiler Create the Boilerplate Code

What if you could just write your class attributes and provide some sort of hint to the compiler that it needs to
add the appropriate getter and setter methods (and maybe an `equals` and `hashCode` method as well)? In other
words, the code would be automatically generated, but at compile time, which means that the generated methods would
not even appear in the source code (but they would be present in the bytecode). This would work the same way as
if the methods had been generated by the IDE, but the code base would be thousands (or possibly ten thousands) of
lines smaller.

If you are dealing with immutable objects (which is generally a good idea), Java 14 introduced the concept of
records, which has the compiler create all the boilerplate code for you. The `Person` example from above becomes
simply:
```java
public record Person(String firstName, String lastName) {}
```
Unfortunately, this is not an option if you are using an earlier version of Java, or if you need mutable objects (i.e.,
setter methods for some attributes).

One framework (and probably the most popular one) that enables this sort of compile-time generated boilerplate code
(including setter methods) even for older versions of Java is [Lombok](https://projectlombok.org/). With Lombok,
the `Person` example can be reduced to just a few lines of code:
```java
public class Person
{
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
}
```
Lombok makes use of Java annotations (`@Getter`, `@Setter`, `@EqualsAndHashCode`, etc.) and the annotation processing API
to generate boilerplate code at compile time. However, this API was not made for modifying bytecode that was produced
by the compiler, but for creating additional bytecode files. As a result, Lombok will only work with specific Java
compilers (`javac` and ECJ), and also requires an IDE plug-in that makes the IDE aware of the additional methods that
are not present in the source code, but will exist in the bytecode. This slightly unorthodox implementation approach has
been called "hacky" by some users, and some of Lombok's fundamental design is considered controversial by some. Overall,
it works very well for small and medium-sized projects, but can run into problems with larger projects, especially if they
use additional compile-time annotation processing (which can lead to race conditions and other difficult-to-track-down
issues).

There are some alternatives, like the [`@Accessors`, `@Data`, and `@Delegate`
annotations](https://www.eclipse.org/xtend/documentation/204_activeannotations.html#existing-active-annotations)
provided by the [Xtend](https://www.eclipse.org/xtend/) "dialect" of Java. These annotations are implemented in a
somewhat more standard way, but effectively you will be using a new programming language that is then translated
into Java source code.

#### Creating Boilerplate Code at Runtime

Solutions like Lombok spend a lot of effort on making the developer's experience seamless. Even though there is no
`setFirstName` method in the source code, as a developer, you want to be able to call that method without having your
IDE tell you that you made a mistake. Especially during debugging it can be confusing to deal with code "that exists,
but then again doesn't."

Projo goes a slightly different approach, where some boilerplate code is generated at runtime, and almost everything
is coded against an interface (instead of a concrete class). If your code calls a method `setFirstName` that method
must be declared in an interface, and will be visible in the generated bytecode. The implementation of that method
(which should somehow store the data that was passed), will be generated on-the-fly when your code runs. There will
be no `.java` file that contains the implementation code, nor will there be a `.class` file: the implementation class
only exists in memory. This is significantly different from what Lombok (and similar frameworks) do. It is not
necessarily better or worse, just different. While Projo's approach certainly addresses some of Lombok's weaker points
(e.g., necessity for specific IDE support), it has it's own set of strengths and weaknesses. So, as they say: Your
mileage may vary.

A minimal version of the previous `Person` example could look like this using Projo:
```java
import pro.projo.doubles.Factory;

interface Person
{
  Factory<Person, String, String> factory = Projo.creates(Person.class).with(Person::firstName, Person::lastName);

  String firstName();
  String lastName();
}
```
If you also need setters (i.e., you require mutable objects) you just need to declare the appropriate setter methods
(e.g., `void setFirstName(String firstName)`) as well. All methods are just regular interface methods, in other words,
they are mere declarations without an implementation. The actual implementation will not get generated until some
code calls the interface's factory to create the first object of type `Person`.
