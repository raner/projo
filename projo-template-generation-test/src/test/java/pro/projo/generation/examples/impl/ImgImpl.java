package pro.projo.generation.examples.impl;

import pro.projo.generation.interfaces.test.html.baseclasses.Img;
import pro.projo.generation.interfaces.test.html.baseclasses.ImgAlt;
import pro.projo.generation.interfaces.test.html.baseclasses.ImgSrc;

public class ImgImpl<PARENT> extends Impl<ImgImpl<PARENT>> implements Img<PARENT>
{

  private final PARENT parent;

  public ImgImpl(PARENT parent)
  {
    this.parent = parent;
  }

  public ImgImpl(PARENT parent, String content)
  {
    super(content);
    this.parent = parent;
  }

  @Override
  public ImgAlt<PARENT> alt(String alt) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ImgSrc<PARENT> src(String src)
  {
    return new ImgSrcImpl<PARENT>(parent, content + " src=\"" + src + "\"");
  }

  @Override
  public ImgImpl<PARENT> with(String content) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> accesskey(String accesskey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> class_(String class_) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> contenteditable(String contenteditable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dir(String dir) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> hidden(String hidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> id(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> lang(String lang) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> spellcheck(String spellcheck) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> style(String style) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> tabindex(String tabindex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> title(String title) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> translate(String translate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onabort(String onabort) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onblur(String onblur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oncancel(String oncancel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oncanplay(String oncanplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oncanplaythrough(String oncanplaythrough) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onchange(String onchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onclick(String onclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oncuechange(String oncuechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ondblclick(String ondblclick) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ondurationchange(String ondurationchange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onemptied(String onemptied) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onended(String onended) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onerror(String onerror) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onfocus(String onfocus) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oninput(String oninput) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> oninvalid(String oninvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onkeypress(String onkeypress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onkeydown(String onkeydown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onkeyup(String onkeyup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onload(String onload) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onloadeddata(String onloadeddata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onloadedmetadata(String onloadedmetadata) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onloadstart(String onloadstart) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmouseenter(String onmouseenter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmouseleave(String onmouseleave) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmousedown(String onmousedown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmouseup(String onmouseup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmouseover(String onmouseover) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmousemove(String onmousemove) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmouseout(String onmouseout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onmousewheel(String onmousewheel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onpause(String onpause) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onplay(String onplay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onplaying(String onplaying) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onprogress(String onprogress) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onratechange(String onratechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onreset(String onreset) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onresize(String onresize) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onscroll(String onscroll) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onseeked(String onseeked) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onseeking(String onseeking) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onselect(String onselect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onshow(String onshow) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onstalled(String onstalled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onsubmit(String onsubmit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onsuspend(String onsuspend) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ontimeupdate(String ontimeupdate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ontoggle(String ontoggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onvolumechange(String onvolumechange) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> onwaiting(String onwaiting) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaAtomic(String ariaAtomic) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaBusy(String ariaBusy) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaControls(String ariaControls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaDescribedby(String ariaDescribedby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaDisabled(String ariaDisabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaDropeffect(String ariaDropeffect) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaFlowto(String ariaFlowto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaGrabbed(String ariaGrabbed) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaHaspopup(String ariaHaspopup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaHidden(String ariaHidden) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaInvalid(String ariaInvalid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaLabel(String ariaLabel) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaLabelledby(String ariaLabelledby) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaLive(String ariaLive) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaOwns(String ariaOwns) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ariaRelevant(String ariaRelevant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataAction(String dataAction) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataCount(String dataCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataChrome(String dataChrome) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataHref(String dataHref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataLayout(String dataLayout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataLinkColor(String dataLinkColor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataParent(String dataParent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataShowCount(String dataShowCount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataShowFaces(String dataShowFaces) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataShare(String dataShare) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataSitekey(String dataSitekey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataTarget(String dataTarget) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataToggle(String dataToggle) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataText(String dataText) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataUrl(String dataUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataVia(String dataVia) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> dataWidgetId(String dataWidgetId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> itemscope(String itemscope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> itemtype(String itemtype) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> itemprop(String itemprop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> crossorigin(String crossorigin) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> usemap(String usemap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> ismap(String ismap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> height(String height) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> width(String width) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Img<PARENT> role(String role) {
    // TODO Auto-generated method stub
    return null;
  }
}
