package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.A;
import pro.projo.generation.interfaces.test.html.baseclasses.Abbr;
import pro.projo.generation.interfaces.test.html.baseclasses.Address;
import pro.projo.generation.interfaces.test.html.baseclasses.Area;
import pro.projo.generation.interfaces.test.html.baseclasses.Article;
import pro.projo.generation.interfaces.test.html.baseclasses.Aside;
import pro.projo.generation.interfaces.test.html.baseclasses.Audio;
import pro.projo.generation.interfaces.test.html.baseclasses.B;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdi;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdo;
import pro.projo.generation.interfaces.test.html.baseclasses.Blockquote;
import pro.projo.generation.interfaces.test.html.baseclasses.Br;
import pro.projo.generation.interfaces.test.html.baseclasses.Button;
import pro.projo.generation.interfaces.test.html.baseclasses.Canvas;
import pro.projo.generation.interfaces.test.html.baseclasses.Cite;
import pro.projo.generation.interfaces.test.html.baseclasses.Code;
import pro.projo.generation.interfaces.test.html.baseclasses.Data;
import pro.projo.generation.interfaces.test.html.baseclasses.Datalist;
import pro.projo.generation.interfaces.test.html.baseclasses.Del;
import pro.projo.generation.interfaces.test.html.baseclasses.Dfn;
import pro.projo.generation.interfaces.test.html.baseclasses.Div;
import pro.projo.generation.interfaces.test.html.baseclasses.Dl;
import pro.projo.generation.interfaces.test.html.baseclasses.Em;
import pro.projo.generation.interfaces.test.html.baseclasses.EmContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Embed;
import pro.projo.generation.interfaces.test.html.baseclasses.Fieldset;
import pro.projo.generation.interfaces.test.html.baseclasses.Figure;
import pro.projo.generation.interfaces.test.html.baseclasses.Footer;
import pro.projo.generation.interfaces.test.html.baseclasses.Form;
import pro.projo.generation.interfaces.test.html.baseclasses.H1;
import pro.projo.generation.interfaces.test.html.baseclasses.H2;
import pro.projo.generation.interfaces.test.html.baseclasses.H3;
import pro.projo.generation.interfaces.test.html.baseclasses.H4;
import pro.projo.generation.interfaces.test.html.baseclasses.H5;
import pro.projo.generation.interfaces.test.html.baseclasses.H6;
import pro.projo.generation.interfaces.test.html.baseclasses.Header;
import pro.projo.generation.interfaces.test.html.baseclasses.Hgroup;
import pro.projo.generation.interfaces.test.html.baseclasses.Hr;
import pro.projo.generation.interfaces.test.html.baseclasses.I;
import pro.projo.generation.interfaces.test.html.baseclasses.Iframe;
import pro.projo.generation.interfaces.test.html.baseclasses.Img;
import pro.projo.generation.interfaces.test.html.baseclasses.Input;
import pro.projo.generation.interfaces.test.html.baseclasses.Ins;
import pro.projo.generation.interfaces.test.html.baseclasses.Kbd;
import pro.projo.generation.interfaces.test.html.baseclasses.Keygen;
import pro.projo.generation.interfaces.test.html.baseclasses.Label;
import pro.projo.generation.interfaces.test.html.baseclasses.LiContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Main;
import pro.projo.generation.interfaces.test.html.baseclasses.Map;
import pro.projo.generation.interfaces.test.html.baseclasses.Mark;
import pro.projo.generation.interfaces.test.html.baseclasses.Math;
import pro.projo.generation.interfaces.test.html.baseclasses.Meter;
import pro.projo.generation.interfaces.test.html.baseclasses.Nav;
import pro.projo.generation.interfaces.test.html.baseclasses.Noscript;
import pro.projo.generation.interfaces.test.html.baseclasses.Object;
import pro.projo.generation.interfaces.test.html.baseclasses.Ol;
import pro.projo.generation.interfaces.test.html.baseclasses.Output;
import pro.projo.generation.interfaces.test.html.baseclasses.P;
import pro.projo.generation.interfaces.test.html.baseclasses.Pre;
import pro.projo.generation.interfaces.test.html.baseclasses.Progress;
import pro.projo.generation.interfaces.test.html.baseclasses.Q;
import pro.projo.generation.interfaces.test.html.baseclasses.Ruby;
import pro.projo.generation.interfaces.test.html.baseclasses.S;
import pro.projo.generation.interfaces.test.html.baseclasses.Samp;
import pro.projo.generation.interfaces.test.html.baseclasses.Script;
import pro.projo.generation.interfaces.test.html.baseclasses.Section;
import pro.projo.generation.interfaces.test.html.baseclasses.Select;
import pro.projo.generation.interfaces.test.html.baseclasses.Small;
import pro.projo.generation.interfaces.test.html.baseclasses.Span;
import pro.projo.generation.interfaces.test.html.baseclasses.Strong;
import pro.projo.generation.interfaces.test.html.baseclasses.Sub;
import pro.projo.generation.interfaces.test.html.baseclasses.Sup;
import pro.projo.generation.interfaces.test.html.baseclasses.Svg;
import pro.projo.generation.interfaces.test.html.baseclasses.Table;
import pro.projo.generation.interfaces.test.html.baseclasses.Template;
import pro.projo.generation.interfaces.test.html.baseclasses.Textarea;
import pro.projo.generation.interfaces.test.html.baseclasses.Time;
import pro.projo.generation.interfaces.test.html.baseclasses.U;
import pro.projo.generation.interfaces.test.html.baseclasses.Ul;
import pro.projo.generation.interfaces.test.html.baseclasses.Var;
import pro.projo.generation.interfaces.test.html.baseclasses.Video;
import pro.projo.generation.interfaces.test.html.baseclasses.Wbr;

public class LiContentImpl extends Impl<LiContentImpl> implements LiContent
{
	  LiContentImpl()
	  {
	    super();
	  }
	  
	  LiContentImpl(String content)
	  {
	    super(content);
	  }

	  @Override
	  public LiContent $(String plainText)
	  {
	System.err.println("***LiContentImpl.$(String) plainText=" + plainText);
		    return new LiContentImpl(this.content + plainText);
	  }


	  @Override
	  public LiContentImpl with(String content)
	  {
	    return new LiContentImpl(this.content + content);
	  }

	@Override
	public Main<LiContent> main() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Header<LiContent> header() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Footer<LiContent> footer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Form<LiContent> form() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address<LiContent> address() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table<LiContent> table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hgroup<LiContent> hgroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A<LiContent> a() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Abbr<LiContent> abbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area<LiContent> area() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Audio<LiContent> audio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<LiContent> b() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bdi<LiContent> bdi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bdo<LiContent> bdo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blockquote<LiContent> blockquote() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Br<LiContent> br() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas<LiContent> canvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cite<LiContent> cite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code<LiContent> code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data<LiContent> data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datalist<LiContent> datalist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Del<LiContent> del() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dfn<LiContent> dfn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Div<LiContent> div() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dl<LiContent> dl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Em<LiContent> em() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embed<LiContent> embed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fieldset<LiContent> fieldset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Figure<LiContent> figure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hr<LiContent> hr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I<LiContent> i() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iframe<LiContent> iframe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Img<LiContent> img() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ins<LiContent> ins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kbd<LiContent> kbd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label<LiContent> label() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<LiContent> map() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mark<LiContent> mark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Math<LiContent> math() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<LiContent> noscript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object<LiContent> object() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ol<LiContent> ol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public P<LiContent> p() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pre<LiContent> pre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Q<LiContent> q() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruby<LiContent> ruby() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S<LiContent> s() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Samp<LiContent> samp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Script<LiContent> script() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Small<LiContent> small() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Span<LiContent> span() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Strong<LiContent> strong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sub<LiContent> sub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sup<LiContent> sup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Svg<LiContent> svg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Template<LiContent> template() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time<LiContent> time() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public U<LiContent> u() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<LiContent> ul() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Var<LiContent> var() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video<LiContent> video() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Wbr<LiContent> wbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H1<LiContent> h1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H2<LiContent> h2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H3<LiContent> h3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H4<LiContent> h4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H5<LiContent> h5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H6<LiContent> h6() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article<LiContent> article() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aside<LiContent> aside() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Nav<LiContent> nav() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Section<LiContent> section() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button<LiContent> button() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Input<LiContent> input() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Keygen<LiContent> keygen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meter<LiContent> meter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Output<LiContent> output() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Progress<LiContent> progress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Select<LiContent> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Textarea<LiContent> textarea() {
		// TODO Auto-generated method stub
		return null;
	}
}
