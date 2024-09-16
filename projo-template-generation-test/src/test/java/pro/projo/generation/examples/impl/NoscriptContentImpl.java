package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.A;
import pro.projo.generation.interfaces.test.html.baseclasses.Abbr;
import pro.projo.generation.interfaces.test.html.baseclasses.Address;
import pro.projo.generation.interfaces.test.html.baseclasses.Article;
import pro.projo.generation.interfaces.test.html.baseclasses.Aside;
import pro.projo.generation.interfaces.test.html.baseclasses.Audio;
import pro.projo.generation.interfaces.test.html.baseclasses.B;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdi;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdo;
import pro.projo.generation.interfaces.test.html.baseclasses.Blockquote;
import pro.projo.generation.interfaces.test.html.baseclasses.BodyContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Button;
import pro.projo.generation.interfaces.test.html.baseclasses.Canvas;
import pro.projo.generation.interfaces.test.html.baseclasses.Cite;
import pro.projo.generation.interfaces.test.html.baseclasses.Code;
import pro.projo.generation.interfaces.test.html.baseclasses.Data;
import pro.projo.generation.interfaces.test.html.baseclasses.Dfn;
import pro.projo.generation.interfaces.test.html.baseclasses.Div;
import pro.projo.generation.interfaces.test.html.baseclasses.Dl;
import pro.projo.generation.interfaces.test.html.baseclasses.Em;
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
import pro.projo.generation.interfaces.test.html.baseclasses.I;
import pro.projo.generation.interfaces.test.html.baseclasses.Iframe;
import pro.projo.generation.interfaces.test.html.baseclasses.Img;
import pro.projo.generation.interfaces.test.html.baseclasses.Input;
import pro.projo.generation.interfaces.test.html.baseclasses.Ins;
import pro.projo.generation.interfaces.test.html.baseclasses.Kbd;
import pro.projo.generation.interfaces.test.html.baseclasses.Keygen;
import pro.projo.generation.interfaces.test.html.baseclasses.Label;
import pro.projo.generation.interfaces.test.html.baseclasses.Main;
import pro.projo.generation.interfaces.test.html.baseclasses.Map;
import pro.projo.generation.interfaces.test.html.baseclasses.Mark;
import pro.projo.generation.interfaces.test.html.baseclasses.Math;
import pro.projo.generation.interfaces.test.html.baseclasses.Meter;
import pro.projo.generation.interfaces.test.html.baseclasses.Nav;
import pro.projo.generation.interfaces.test.html.baseclasses.NoscriptContent;
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
import pro.projo.generation.interfaces.test.html.baseclasses.Section;
import pro.projo.generation.interfaces.test.html.baseclasses.Select;
import pro.projo.generation.interfaces.test.html.baseclasses.Small;
import pro.projo.generation.interfaces.test.html.baseclasses.Span;
import pro.projo.generation.interfaces.test.html.baseclasses.Strong;
import pro.projo.generation.interfaces.test.html.baseclasses.Sub;
import pro.projo.generation.interfaces.test.html.baseclasses.Sup;
import pro.projo.generation.interfaces.test.html.baseclasses.Svg;
import pro.projo.generation.interfaces.test.html.baseclasses.Table;
import pro.projo.generation.interfaces.test.html.baseclasses.Textarea;
import pro.projo.generation.interfaces.test.html.baseclasses.Time;
import pro.projo.generation.interfaces.test.html.baseclasses.U;
import pro.projo.generation.interfaces.test.html.baseclasses.Ul;
import pro.projo.generation.interfaces.test.html.baseclasses.UlContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Var;
import pro.projo.generation.interfaces.test.html.baseclasses.Video;

public class NoscriptContentImpl extends Impl<NoscriptContentImpl> implements NoscriptContent
{
	  NoscriptContentImpl()
	  {
	    super();
	  }
	  
	  NoscriptContentImpl(String content)
	  {
	    super(content);
	  }


		@Override
		public NoscriptContentImpl with(String content) {
			// TODO Auto-generated method stub
			return new NoscriptContentImpl(this.content + content);
		}


	@Override
	public A<NoscriptContent> a() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Abbr<NoscriptContent> abbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address<NoscriptContent> address() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article<NoscriptContent> article() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aside<NoscriptContent> aside() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Audio<NoscriptContent> audio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<NoscriptContent> b() {
		System.err.println("bbb");
		return new BImpl<NoscriptContent>(this, "<b");
	}

	@Override
	public Bdi<NoscriptContent> bdi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bdo<NoscriptContent> bdo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blockquote<NoscriptContent> blockquote() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button<NoscriptContent> button() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas<NoscriptContent> canvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cite<NoscriptContent> cite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code<NoscriptContent> code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data<NoscriptContent> data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dfn<NoscriptContent> dfn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Div<NoscriptContent> div() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dl<NoscriptContent> dl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Em<NoscriptContent> em() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embed<NoscriptContent> embed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fieldset<NoscriptContent> fieldset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Figure<NoscriptContent> figure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Footer<NoscriptContent> footer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Form<NoscriptContent> form() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H1<NoscriptContent> h1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H2<NoscriptContent> h2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H3<NoscriptContent> h3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H4<NoscriptContent> h4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H5<NoscriptContent> h5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public H6<NoscriptContent> h6() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Header<NoscriptContent> header() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I<NoscriptContent> i() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iframe<NoscriptContent> iframe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Img<NoscriptContent> img() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Input<NoscriptContent> input() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ins<NoscriptContent> ins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kbd<NoscriptContent> kbd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Keygen<NoscriptContent> keygen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label<NoscriptContent> label() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Main<NoscriptContent> main() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<NoscriptContent> map() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mark<NoscriptContent> mark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Math<NoscriptContent> math() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meter<NoscriptContent> meter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Nav<NoscriptContent> nav() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object<NoscriptContent> object() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ol<NoscriptContent> ol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Output<NoscriptContent> output() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public P<NoscriptContent> p() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pre<NoscriptContent> pre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Progress<NoscriptContent> progress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Q<NoscriptContent> q() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruby<NoscriptContent> ruby() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S<NoscriptContent> s() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Samp<NoscriptContent> samp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Section<NoscriptContent> section() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Select<NoscriptContent> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Small<NoscriptContent> small() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Span<NoscriptContent> span() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Strong<NoscriptContent> strong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sub<NoscriptContent> sub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sup<NoscriptContent> sup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Svg<NoscriptContent> svg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table<NoscriptContent> table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Textarea<NoscriptContent> textarea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time<NoscriptContent> time() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public U<NoscriptContent> u() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<NoscriptContent> ul() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Var<NoscriptContent> var() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video<NoscriptContent> video() {
		// TODO Auto-generated method stub
		return null;
	}
}
