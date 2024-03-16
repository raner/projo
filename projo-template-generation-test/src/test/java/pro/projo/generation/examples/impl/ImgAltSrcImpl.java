package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.ImgAltSrc;

public class ImgAltSrcImpl<PARENT> extends Impl<ImgAltSrcImpl<PARENT>> implements ImgAltSrc<PARENT>
{

  private final PARENT parent;

  public ImgAltSrcImpl(PARENT parent)
  {
    this.parent = parent;
  }

  public ImgAltSrcImpl(PARENT parent, String content)
  {
    super(content);
    this.parent = parent;
  }

  @Override
  public PARENT $() {
    // TODO Auto-generated method stub
    System.err.println("parent=" + parent.getClass());
    return ((Impl<PARENT>)parent).with(content + ">");
  }

  @Override
  public ImgAltSrc<PARENT> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> class_(String class_) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> id(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> crossorigin(String crossorigin) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> usemap(String usemap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> ismap(String ismap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> height(String height) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> width(String width) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrc<PARENT> role(String role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgAltSrcImpl<PARENT> with(String content) {
    // TODO Auto-generated method stub
    return null;
  }

}
