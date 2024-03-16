package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.BodyContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Div;
import pro.projo.generation.interfaces.test.html.baseclasses.DivContent;
import pro.projo.generation.interfaces.test.html.baseclasses.HeadContent;

public class DivImpl<PARENT> extends Impl<DivImpl<PARENT>> implements Div<PARENT>
{
  private final PARENT parent;

  public DivImpl(PARENT parent)
  {
    this.parent = parent;
  }

  public DivImpl(PARENT parent, String content)
  {
    super(content);
    this.parent = parent;
  }

  @Override
  public PARENT $(Function<DivContent, DivContent> content)
  {
    DivContent div = content.apply(new DivContentImpl());
System.err.println("*** div=" + div);
    return ((Impl<PARENT>)parent).with(this.content + ">" + ((Impl)div).content + "</div>");
  }

  @Override
  public DivImpl<PARENT> with(String content) {
    // TODO Auto-generated method stub
    System.err.println("PPP");
    return null;
  }

  @Override
  public Div<PARENT> class_(String class_)
  {
    return new DivImpl<PARENT>(parent, content + " class=\"" + class_ + "\"");
  }

  @Override
  public Div<PARENT> id(String id)
  {
    return new DivImpl<PARENT>(parent, content + " id=\"" + id + "\"");
  }

  @Override
  public Div<PARENT> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Div<PARENT> role(String role) {
    // TODO Auto-generated method stub
    return null;
  }
}
