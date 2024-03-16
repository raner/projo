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

public class BodyContentImpl extends Impl<BodyContentImpl> implements BodyContent {

  BodyContentImpl()
  {
    super();
  }
  
  BodyContentImpl(String content)
  {
    super(content);
  }

  @Override
  public BodyContent $(String plainText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BodyContentImpl with(String content) {
    // TODO Auto-generated method stub
    return new BodyContentImpl(this.content + content);
  }

  @Override
  public Img<BodyContent> img()
  {
    return new ImgImpl<BodyContent>(this, "<img");
  }

  @Override
  public Div<BodyContent> div()
  {
    return new DivImpl<BodyContent>(this, "<div");
  }

  @Override
  public Main<BodyContent> main() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Header<BodyContent> header() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Footer<BodyContent> footer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Form<BodyContent> form() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Address<BodyContent> address() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Table<BodyContent> table() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hgroup<BodyContent> hgroup() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public A<BodyContent> a() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Abbr<BodyContent> abbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Area<BodyContent> area() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Audio<BodyContent> audio() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public B<BodyContent> b() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdi<BodyContent> bdi() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdo<BodyContent> bdo() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Blockquote<BodyContent> blockquote() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Br<BodyContent> br() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Canvas<BodyContent> canvas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Cite<BodyContent> cite() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code<BodyContent> code() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Data<BodyContent> data() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Datalist<BodyContent> datalist() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Del<BodyContent> del() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Dfn<BodyContent> dfn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Dl<BodyContent> dl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<BodyContent> em() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Embed<BodyContent> embed() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Fieldset<BodyContent> fieldset() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Figure<BodyContent> figure() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hr<BodyContent> hr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I<BodyContent> i() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iframe<BodyContent> iframe() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ins<BodyContent> ins() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Kbd<BodyContent> kbd() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Label<BodyContent> label() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<BodyContent> map() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mark<BodyContent> mark() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Math<BodyContent> math() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Noscript<BodyContent> noscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object<BodyContent> object() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ol<BodyContent> ol() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public P<BodyContent> p() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Pre<BodyContent> pre() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Q<BodyContent> q() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ruby<BodyContent> ruby() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public S<BodyContent> s() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Samp<BodyContent> samp() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Script<BodyContent> script() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Small<BodyContent> small() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Span<BodyContent> span() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Strong<BodyContent> strong() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sub<BodyContent> sub() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sup<BodyContent> sup() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Svg<BodyContent> svg() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Template<BodyContent> template() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Time<BodyContent> time() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public U<BodyContent> u() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ul<BodyContent> ul() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Var<BodyContent> var() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Video<BodyContent> video() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Wbr<BodyContent> wbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H1<BodyContent> h1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H2<BodyContent> h2() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H3<BodyContent> h3() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H4<BodyContent> h4() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H5<BodyContent> h5() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public H6<BodyContent> h6() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Article<BodyContent> article() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Aside<BodyContent> aside() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Nav<BodyContent> nav() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Section<BodyContent> section() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Button<BodyContent> button() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Input<BodyContent> input() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keygen<BodyContent> keygen() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Meter<BodyContent> meter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Output<BodyContent> output() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Progress<BodyContent> progress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Select<BodyContent> select() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Textarea<BodyContent> textarea() {
    // TODO Auto-generated method stub
    return null;
  }
}
