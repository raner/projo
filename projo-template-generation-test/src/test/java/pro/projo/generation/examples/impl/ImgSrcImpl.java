package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.ImgAltSrc;
import pro.projo.generation.interfaces.test.html.baseclasses.ImgSrc;

public class ImgSrcImpl<PARENT> extends Impl<ImgSrcImpl<PARENT>> implements ImgSrc<PARENT>
{

  private final PARENT parent;

  public ImgSrcImpl(PARENT parent)
  {
    this.parent = parent;
  }

  public ImgSrcImpl(PARENT parent, String content)
  {
    super(content);
    this.parent = parent;
  }


  @Override
  public ImgAltSrc<PARENT> alt(String alt)
  {
    return new ImgAltSrcImpl<PARENT>(parent, content + " alt=\"" + alt + "\"");
  }

  @Override
  public ImgSrc<PARENT> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> class_(String class_) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> id(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> crossorigin(String crossorigin) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> usemap(String usemap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> ismap(String ismap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> height(String height) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> width(String width) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> role(String role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrcImpl<PARENT> with(String content) {
    // TODO Auto-generated method stub
    return null;
  }

}
