package pro.projo.generation.examples.impl;

public abstract class Impl<IMPLEMENTATION>
{
    final String content;

    public Impl()
    {
      content = "";
    }

    public Impl(String content)
    {
      this.content = content;
    }

    public abstract IMPLEMENTATION with(String content);

    @Override
    public String toString()
    {
      return '"' + content + '"';
    }
}
