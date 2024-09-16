package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.A;
import pro.projo.generation.interfaces.test.html.baseclasses.Abbr;
import pro.projo.generation.interfaces.test.html.baseclasses.Area;
import pro.projo.generation.interfaces.test.html.baseclasses.Audio;
import pro.projo.generation.interfaces.test.html.baseclasses.B;
import pro.projo.generation.interfaces.test.html.baseclasses.BContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdi;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdo;
import pro.projo.generation.interfaces.test.html.baseclasses.Br;
import pro.projo.generation.interfaces.test.html.baseclasses.Button;
import pro.projo.generation.interfaces.test.html.baseclasses.Canvas;
import pro.projo.generation.interfaces.test.html.baseclasses.Cite;
import pro.projo.generation.interfaces.test.html.baseclasses.Code;
import pro.projo.generation.interfaces.test.html.baseclasses.Data;
import pro.projo.generation.interfaces.test.html.baseclasses.Datalist;
import pro.projo.generation.interfaces.test.html.baseclasses.Del;
import pro.projo.generation.interfaces.test.html.baseclasses.Dfn;
import pro.projo.generation.interfaces.test.html.baseclasses.Em;
import pro.projo.generation.interfaces.test.html.baseclasses.Embed;
import pro.projo.generation.interfaces.test.html.baseclasses.I;
import pro.projo.generation.interfaces.test.html.baseclasses.Iframe;
import pro.projo.generation.interfaces.test.html.baseclasses.Img;
import pro.projo.generation.interfaces.test.html.baseclasses.Input;
import pro.projo.generation.interfaces.test.html.baseclasses.Ins;
import pro.projo.generation.interfaces.test.html.baseclasses.Kbd;
import pro.projo.generation.interfaces.test.html.baseclasses.Keygen;
import pro.projo.generation.interfaces.test.html.baseclasses.Label;
import pro.projo.generation.interfaces.test.html.baseclasses.Map;
import pro.projo.generation.interfaces.test.html.baseclasses.Mark;
import pro.projo.generation.interfaces.test.html.baseclasses.Math;
import pro.projo.generation.interfaces.test.html.baseclasses.Meter;
import pro.projo.generation.interfaces.test.html.baseclasses.Noscript;
import pro.projo.generation.interfaces.test.html.baseclasses.Object;
import pro.projo.generation.interfaces.test.html.baseclasses.Output;
import pro.projo.generation.interfaces.test.html.baseclasses.Progress;
import pro.projo.generation.interfaces.test.html.baseclasses.Q;
import pro.projo.generation.interfaces.test.html.baseclasses.Ruby;
import pro.projo.generation.interfaces.test.html.baseclasses.S;
import pro.projo.generation.interfaces.test.html.baseclasses.Samp;
import pro.projo.generation.interfaces.test.html.baseclasses.Script;
import pro.projo.generation.interfaces.test.html.baseclasses.Select;
import pro.projo.generation.interfaces.test.html.baseclasses.Small;
import pro.projo.generation.interfaces.test.html.baseclasses.Span;
import pro.projo.generation.interfaces.test.html.baseclasses.Strong;
import pro.projo.generation.interfaces.test.html.baseclasses.Sub;
import pro.projo.generation.interfaces.test.html.baseclasses.Sup;
import pro.projo.generation.interfaces.test.html.baseclasses.Svg;
import pro.projo.generation.interfaces.test.html.baseclasses.Template;
import pro.projo.generation.interfaces.test.html.baseclasses.Textarea;
import pro.projo.generation.interfaces.test.html.baseclasses.Time;
import pro.projo.generation.interfaces.test.html.baseclasses.U;
import pro.projo.generation.interfaces.test.html.baseclasses.Var;
import pro.projo.generation.interfaces.test.html.baseclasses.Video;
import pro.projo.generation.interfaces.test.html.baseclasses.Wbr;

public class BContentImpl extends Impl<BContentImpl> implements BContent {

	  BContentImpl()
	  {
	    super();
	  }
	  
	  BContentImpl(String content)
	  {
	    super(content);
	  }

	@Override
	public BContent $(String plainText) {
		System.err.println("***BContentImpl.$(String) plainText=" + plainText);
	    return new BContentImpl(this.content + plainText);
	}

	@Override
	public BContentImpl with(String content) {
		// TODO Auto-generated method stub
		return new BContentImpl(this.content + content);
	}

	@Override
	public Dfn<BContent> dfn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Progress<BContent> progress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meter<BContent> meter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Abbr<BContent> abbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area<BContent> area() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<BContent> b() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bdi<BContent> bdi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bdo<BContent> bdo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Br<BContent> br() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas<BContent> canvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cite<BContent> cite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Code<BContent> code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data<BContent> data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datalist<BContent> datalist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Del<BContent> del() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Em<BContent> em() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I<BContent> i() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ins<BContent> ins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kbd<BContent> kbd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BContent> map() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mark<BContent> mark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Math<BContent> math() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<BContent> noscript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Output<BContent> output() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Q<BContent> q() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruby<BContent> ruby() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S<BContent> s() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Samp<BContent> samp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Script<BContent> script() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Small<BContent> small() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Span<BContent> span() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Strong<BContent> strong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sub<BContent> sub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sup<BContent> sup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Svg<BContent> svg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Template<BContent> template() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time<BContent> time() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public U<BContent> u() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Var<BContent> var() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Wbr<BContent> wbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A<BContent> a() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Audio<BContent> audio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button<BContent> button() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embed<BContent> embed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iframe<BContent> iframe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Img<BContent> img() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Input<BContent> input() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Keygen<BContent> keygen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label<BContent> label() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object<BContent> object() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Select<BContent> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Textarea<BContent> textarea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video<BContent> video() {
		// TODO Auto-generated method stub
		return null;
	}
}
