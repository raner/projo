package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.Li;
import pro.projo.generation.interfaces.test.html.baseclasses.UlContent;

public class UlContentImpl extends Impl<UlContentImpl> implements UlContent
{
	  UlContentImpl()
	  {
	    super();
	  }
	  
	  UlContentImpl(String content)
	  {
	    super(content);
	  }

	@Override
	public Li<UlContent> li()
	{
		return new LiImpl<>(this, "<li");
	}

	  @Override
	  public UlContentImpl with(String content)
	  {
	    return new UlContentImpl(this.content + content);
	  }
}
