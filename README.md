# projo

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
        this.lastName = lastName;
    }
}
```
Other languages, like Scala, for example, offer someing called *case classes* that provide a very concise way of defining
such a class, but in Java it is a lot of code (especially for a class that does essentially nothing). Even if you are not
following DDD principles, an average software project can easily contain hundreds of these classes, accounting for thousands,
if not ten-thousands, lines of code.

**Projo** offers a much simpler and more concise way of defining this kind of class. And it means a lot less typing for the
developer.

The above example is only the beginning: if you want a convenient way of creating these objects you might want to add a
couple of constructors; you want to safely use the objects as keys in a hash map? you'll need ```equals()``` and
```hashCode()``` methods; and what about a ```toString()``` method that actually produces readable information? Often times,
a lot of the fields are mandatory and cannot be ```null``` or ```Optional```, so you'll need more code to enforce that.

All this code is not only potentially a lot of typing, but it is also error-prone. Coding mistakes like the following are more
common than you may think, but not always as easy to spot:
```
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.firstName = lastName;
    }
```
An ```equals()``` method without a ```hashCode()``` method, or vice versa, is another common mistake. And even if both methods
are present, that does not necessarily mean they were implemented correctly and in line with the hashCode/equals contract.

**Projo** completely removes the need for developers to ever again implement a getter, setter, hashCode or equals method.
