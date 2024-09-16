package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.Li;
import pro.projo.generation.interfaces.test.html.baseclasses.LiContent;
import pro.projo.generation.interfaces.test.html.baseclasses.UlContent;

public class LiImpl<PARENT> extends Impl<LiImpl<PARENT>> implements Li<PARENT>
{
	  private final PARENT parent;

	  public LiImpl(PARENT parent)
	  {
	    this.parent = parent;
	  }

	  public LiImpl(PARENT parent, String content)
	  {
	    super(content);
	    this.parent = parent;
	  }

	@Override
	public PARENT $(Function<LiContent, LiContent> content)
	{
	    System.err.println("LLL");
	    LiContent body = content.apply(new LiContentImpl());
	System.err.println("body>>>" + ((Impl)body).content);
	    return ((Impl<PARENT>)parent).with(/*this.content*/"<li>" + ((Impl)body).content + "</li>");
	    //return parent;
	}

	@Override
	public Li<PARENT> accesskey(String accesskey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> class_(String class_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> contenteditable(String contenteditable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dir(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> hidden(String hidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> id(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> lang(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> spellcheck(String spellcheck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> style(String style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> tabindex(String tabindex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> title(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> translate(String translate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onabort(String onabort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onblur(String onblur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oncancel(String oncancel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oncanplay(String oncanplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oncanplaythrough(String oncanplaythrough) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onchange(String onchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onclick(String onclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oncuechange(String oncuechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ondblclick(String ondblclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ondurationchange(String ondurationchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onemptied(String onemptied) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onended(String onended) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onerror(String onerror) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onfocus(String onfocus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oninput(String oninput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> oninvalid(String oninvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onkeypress(String onkeypress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onkeydown(String onkeydown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onkeyup(String onkeyup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onload(String onload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onloadeddata(String onloadeddata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onloadedmetadata(String onloadedmetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onloadstart(String onloadstart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmouseenter(String onmouseenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmouseleave(String onmouseleave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmousedown(String onmousedown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmouseup(String onmouseup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmouseover(String onmouseover) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmousemove(String onmousemove) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmouseout(String onmouseout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onmousewheel(String onmousewheel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onpause(String onpause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onplay(String onplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onplaying(String onplaying) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onprogress(String onprogress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onratechange(String onratechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onreset(String onreset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onresize(String onresize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onscroll(String onscroll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onseeked(String onseeked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onseeking(String onseeking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onselect(String onselect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onshow(String onshow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onstalled(String onstalled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onsubmit(String onsubmit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onsuspend(String onsuspend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ontimeupdate(String ontimeupdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ontoggle(String ontoggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onvolumechange(String onvolumechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> onwaiting(String onwaiting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaAtomic(String ariaAtomic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaBusy(String ariaBusy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaControls(String ariaControls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaDescribedby(String ariaDescribedby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaDisabled(String ariaDisabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaDropeffect(String ariaDropeffect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaFlowto(String ariaFlowto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaGrabbed(String ariaGrabbed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaHaspopup(String ariaHaspopup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaHidden(String ariaHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaInvalid(String ariaInvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaLabel(String ariaLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaLabelledby(String ariaLabelledby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaLive(String ariaLive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaOwns(String ariaOwns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaRelevant(String ariaRelevant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataAction(String dataAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataCount(String dataCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataChrome(String dataChrome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataHref(String dataHref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataLayout(String dataLayout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataLinkColor(String dataLinkColor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataParent(String dataParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataShowCount(String dataShowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataShowFaces(String dataShowFaces) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataShare(String dataShare) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataSitekey(String dataSitekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataTarget(String dataTarget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataToggle(String dataToggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataText(String dataText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataUrl(String dataUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataVia(String dataVia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> dataWidgetId(String dataWidgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> itemscope(String itemscope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> itemtype(String itemtype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> itemprop(String itemprop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> value(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> role(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaLevel(String ariaLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaPosinset(String ariaPosinset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaSetsize(String ariaSetsize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaExpanded(String ariaExpanded) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaChecked(String ariaChecked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Li<PARENT> ariaSelected(String ariaSelected) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LiImpl<PARENT> with(String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
