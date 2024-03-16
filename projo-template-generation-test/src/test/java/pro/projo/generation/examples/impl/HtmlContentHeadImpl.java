package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.Head;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentBody;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentHead;

public class HtmlContentHeadImpl extends Impl<HtmlContentHeadImpl> implements HtmlContentHead {

  @Override
  public Head<HtmlContentBody> head()
  {
    return new HeadImpl<HtmlContentBody>(new HtmlContentBodyImpl());
  }

  @Override
  public HtmlContentHeadImpl with(String content) {
    // TODO Auto-generated method stub
    System.err.println("XXX");
    return null;
  }

}
