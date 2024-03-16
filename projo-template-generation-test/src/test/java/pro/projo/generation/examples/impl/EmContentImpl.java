package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.A;
import pro.projo.generation.interfaces.test.html.baseclasses.Abbr;
import pro.projo.generation.interfaces.test.html.baseclasses.Area;
import pro.projo.generation.interfaces.test.html.baseclasses.Audio;
import pro.projo.generation.interfaces.test.html.baseclasses.B;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdi;
import pro.projo.generation.interfaces.test.html.baseclasses.Bdo;
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
import pro.projo.generation.interfaces.test.html.baseclasses.DivContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Em;
import pro.projo.generation.interfaces.test.html.baseclasses.EmContent;
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

public class EmContentImpl extends Impl<EmContentImpl> implements EmContent
{
  EmContentImpl()
  {
    super();
  }

  EmContentImpl(String content)
  {
    super(content);
  }

  @Override
  public EmContent $(String plainText)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EmContentImpl with(String content)
  {
    return new EmContentImpl(this.content + content);
  }

  @Override
  public Img<EmContent> img()
  {
    return new ImgImpl<EmContent>(this, "<img");
  }

  @Override
  public Dfn<EmContent> dfn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Progress<EmContent> progress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Meter<EmContent> meter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Abbr<EmContent> abbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Area<EmContent> area() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public B<EmContent> b() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdi<EmContent> bdi() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bdo<EmContent> bdo() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Br<EmContent> br() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Canvas<EmContent> canvas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Cite<EmContent> cite() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Code<EmContent> code() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Data<EmContent> data() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Datalist<EmContent> datalist() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Del<EmContent> del() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<EmContent> em() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I<EmContent> i() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ins<EmContent> ins() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Kbd<EmContent> kbd() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<EmContent> map() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mark<EmContent> mark() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Math<EmContent> math() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Noscript<EmContent> noscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Output<EmContent> output() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Q<EmContent> q() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Ruby<EmContent> ruby() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public S<EmContent> s() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Samp<EmContent> samp() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Script<EmContent> script() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Small<EmContent> small() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Span<EmContent> span() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Strong<EmContent> strong() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sub<EmContent> sub() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sup<EmContent> sup() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Svg<EmContent> svg() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Template<EmContent> template() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Time<EmContent> time() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public U<EmContent> u() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Var<EmContent> var() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Wbr<EmContent> wbr() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public A<EmContent> a() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Audio<EmContent> audio() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Button<EmContent> button() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Embed<EmContent> embed() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iframe<EmContent> iframe() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Input<EmContent> input() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keygen<EmContent> keygen() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Label<EmContent> label() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object<EmContent> object() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Select<EmContent> select() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Textarea<EmContent> textarea() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Video<EmContent> video() {
    // TODO Auto-generated method stub
    return null;
  }
}
