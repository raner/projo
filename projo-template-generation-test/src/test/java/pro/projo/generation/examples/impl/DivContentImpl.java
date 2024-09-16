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
import pro.projo.generation.interfaces.test.html.baseclasses.BodyContent;
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
import pro.projo.generation.interfaces.test.html.baseclasses.DivContent;
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

public class DivContentImpl extends Impl<DivContentImpl> implements DivContent
{

  DivContentImpl()
  {
    super();
  }
  
  DivContentImpl(String content)
  {
    super(content);
  }

  @Override
  public DivContent $(String plainText) {
    // TODO Auto-generated method stub
System.err.println("***DivContentImpl.$(String) plainText=" + plainText);
    return new DivContentImpl(this.content + plainText);
  }

  @Override
  public DivContentImpl with(String content)
  {
System.err.println("***DivContentImpl.with(String) content=" + content);
    return new DivContentImpl(this.content + content);
  }

  @Override
  public Em<DivContent> em()
  {
    return new EmImpl<DivContent>(this, "<em");
  }

  @Override
  public Img<DivContent> img()
  {
	return new ImgImpl<>(this, "<img");
  }

  @Override
  public Main<DivContent> main() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Header<DivContent> header() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Footer<DivContent> footer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Form<DivContent> form() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Address<DivContent> address() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Table<DivContent> table() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hgroup<DivContent> hgroup() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public A<DivContent> a() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Abbr<DivContent> abbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Area<DivContent> area() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Audio<DivContent> audio() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public B<DivContent> b() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdi<DivContent> bdi() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdo<DivContent> bdo() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Blockquote<DivContent> blockquote() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Br<DivContent> br() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Canvas<DivContent> canvas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Cite<DivContent> cite() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code<DivContent> code() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Data<DivContent> data() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Datalist<DivContent> datalist() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Del<DivContent> del() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Dfn<DivContent> dfn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<DivContent> div() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Dl<DivContent> dl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Embed<DivContent> embed() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Fieldset<DivContent> fieldset() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Figure<DivContent> figure() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hr<DivContent> hr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I<DivContent> i() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iframe<DivContent> iframe() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ins<DivContent> ins() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Kbd<DivContent> kbd() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Label<DivContent> label() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<DivContent> map() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mark<DivContent> mark() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Math<DivContent> math() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Noscript<DivContent> noscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object<DivContent> object() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ol<DivContent> ol() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public P<DivContent> p() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Pre<DivContent> pre() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Q<DivContent> q() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ruby<DivContent> ruby() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public S<DivContent> s() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Samp<DivContent> samp() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Script<DivContent> script() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Small<DivContent> small() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Span<DivContent> span() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Strong<DivContent> strong() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sub<DivContent> sub() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sup<DivContent> sup() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Svg<DivContent> svg() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Template<DivContent> template() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Time<DivContent> time() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public U<DivContent> u() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ul<DivContent> ul() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Var<DivContent> var() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Video<DivContent> video() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Wbr<DivContent> wbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H1<DivContent> h1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H2<DivContent> h2() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H3<DivContent> h3() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H4<DivContent> h4() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H5<DivContent> h5() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H6<DivContent> h6() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Article<DivContent> article() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Aside<DivContent> aside() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Nav<DivContent> nav() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Section<DivContent> section() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Button<DivContent> button() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Input<DivContent> input() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keygen<DivContent> keygen() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Meter<DivContent> meter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Output<DivContent> output() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Progress<DivContent> progress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Select<DivContent> select() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Textarea<DivContent> textarea() {
    // TODO Auto-generated method stub
    return null;
  }
}
