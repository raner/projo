package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.Body;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContent;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentBody;

public class HtmlContentBodyImpl extends Impl<HtmlContentBodyImpl> implements HtmlContentBody
{
  public HtmlContentBodyImpl()
  {
    super();
  }
  public HtmlContentBodyImpl(String content)
  {
    super(content);
  }

  @Override
  public Body<HtmlContent> body()
  {
    return new BodyImpl<HtmlContent>(this);
  }

  @Override
  public HtmlContentBodyImpl with(String content)
  {
    return new HtmlContentBodyImpl(this.content + content);
  }

}
