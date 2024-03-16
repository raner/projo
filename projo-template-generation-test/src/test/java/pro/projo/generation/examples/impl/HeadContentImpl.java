package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.Base;
import pro.projo.generation.interfaces.test.html.baseclasses.HeadContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Link;
import pro.projo.generation.interfaces.test.html.baseclasses.Meta;
import pro.projo.generation.interfaces.test.html.baseclasses.Noscript;
import pro.projo.generation.interfaces.test.html.baseclasses.Script;
import pro.projo.generation.interfaces.test.html.baseclasses.Style;
import pro.projo.generation.interfaces.test.html.baseclasses.Template;
import pro.projo.generation.interfaces.test.html.baseclasses.Title;

public class HeadContentImpl extends Impl<HeadContentImpl> implements HeadContent {

  HeadContentImpl()
  {
    super();
  }
  
  HeadContentImpl(String content)
  {
    super(content);
  }

  @Override
  public HeadContentImpl with(String content) {
    System.err.println("ZZZ");
    return new HeadContentImpl(this.content + content);
  }

  @Override
  public Title<HeadContent> title()
  {
    System.err.println("AAA");
    return new TitleImpl<HeadContent>(this);
  }

  @Override
  public Base<HeadContent> base() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Link<HeadContent> link() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Meta<HeadContent> meta() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Noscript<HeadContent> noscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Script<HeadContent> script() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Style<HeadContent> style() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Template<HeadContent> template() {
    // TODO Auto-generated method stub
    return null;
  }
}
