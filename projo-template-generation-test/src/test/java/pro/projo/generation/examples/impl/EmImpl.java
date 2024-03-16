package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.Div;
import pro.projo.generation.interfaces.test.html.baseclasses.DivContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Em;
import pro.projo.generation.interfaces.test.html.baseclasses.EmContent;

public class EmImpl<PARENT> extends Impl<EmImpl<PARENT>> implements Em<PARENT>
{
  private final PARENT parent;

  public EmImpl(PARENT parent)
  {
    this.parent = parent;
  }

  public EmImpl(PARENT parent, String content)
  {
    super(content);
    this.parent = parent;
  }

  @Override
  public PARENT $(Function<EmContent, EmContent> content)
  {
    EmContent em = content.apply(new EmContentImpl());
    System.err.println("*** em=" + em);
    System.err.println("*** parent=" + parent.getClass());
    return ((Impl<PARENT>)parent).with(this.content + ">" + ((Impl)em).content + "</em>");
  }

  @Override
  public Em<PARENT> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> class_(String class_) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> id(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Em<PARENT> role(String role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EmImpl<PARENT> with(String content) {
    // TODO Auto-generated method stub
    return null;
  }

}
