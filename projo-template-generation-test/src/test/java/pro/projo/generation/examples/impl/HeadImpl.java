package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.Head;
import pro.projo.generation.interfaces.test.html.baseclasses.HeadContent;

public class HeadImpl<X> extends Impl<HeadImpl<X>> implements Head<X> {

  private final X parent;

  public HeadImpl(X parent)
  {
    super("<head");
    this.parent = parent;
  }

  @Override
  public HeadImpl<X> with(String content) {
    // TODO Auto-generated method stub
    System.err.println("CCC");
    return null;
  }

  @Override
  public X $(Function<HeadContent, HeadContent> content)
  {
    HeadContent head = content.apply(new HeadContentImpl());
System.err.println(">>>" + ((Impl)head).content);
    return ((Impl<X>)parent).with(/*this.content*/"<head>" + ((Impl)head).content + "</head>");
  }

  @Override
  public Head<X> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> class_(String class_) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> id(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Head<X> profile(String profile) {
    // TODO Auto-generated method stub
    return null;
  }
}
