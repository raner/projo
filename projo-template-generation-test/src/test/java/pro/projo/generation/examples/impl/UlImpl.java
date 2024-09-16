package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.BodyContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Ul;
import pro.projo.generation.interfaces.test.html.baseclasses.UlContent;

public class UlImpl<PARENT> extends Impl<UlImpl<PARENT>> implements Ul<PARENT>
{
	  private final PARENT parent;

	  public UlImpl(PARENT parent)
	  {
	    this.parent = parent;
	  }

	  public UlImpl(PARENT parent, String content)
	  {
	    super(content);
	    this.parent = parent;
	  }

	@Override
	public PARENT $(Function<UlContent, UlContent> content)
	{
	    System.err.println("UUU");
	    UlContent body = content.apply(new UlContentImpl());
System.err.println("****** " + content + "<Ul -> Ul> returned " + body);
	System.err.println("body>>>" + ((Impl)body).content);
	    return ((Impl<PARENT>)parent).with(/*this.content*/"<ul>" + ((Impl)body).content + "</ul>");
	    //return parent;
	}

	@Override
	public Ul<PARENT> accesskey(String accesskey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> class_(String class_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> contenteditable(String contenteditable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dir(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> hidden(String hidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> id(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> lang(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> spellcheck(String spellcheck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> style(String style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> tabindex(String tabindex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> title(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> translate(String translate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onabort(String onabort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onblur(String onblur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oncancel(String oncancel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oncanplay(String oncanplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oncanplaythrough(String oncanplaythrough) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onchange(String onchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onclick(String onclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oncuechange(String oncuechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ondblclick(String ondblclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ondurationchange(String ondurationchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onemptied(String onemptied) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onended(String onended) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onerror(String onerror) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onfocus(String onfocus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oninput(String oninput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> oninvalid(String oninvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onkeypress(String onkeypress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onkeydown(String onkeydown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onkeyup(String onkeyup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onload(String onload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onloadeddata(String onloadeddata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onloadedmetadata(String onloadedmetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onloadstart(String onloadstart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmouseenter(String onmouseenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmouseleave(String onmouseleave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmousedown(String onmousedown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmouseup(String onmouseup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmouseover(String onmouseover) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmousemove(String onmousemove) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmouseout(String onmouseout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onmousewheel(String onmousewheel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onpause(String onpause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onplay(String onplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onplaying(String onplaying) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onprogress(String onprogress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onratechange(String onratechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onreset(String onreset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onresize(String onresize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onscroll(String onscroll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onseeked(String onseeked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onseeking(String onseeking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onselect(String onselect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onshow(String onshow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onstalled(String onstalled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onsubmit(String onsubmit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onsuspend(String onsuspend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ontimeupdate(String ontimeupdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ontoggle(String ontoggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onvolumechange(String onvolumechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> onwaiting(String onwaiting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaAtomic(String ariaAtomic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaBusy(String ariaBusy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaControls(String ariaControls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaDescribedby(String ariaDescribedby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaDisabled(String ariaDisabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaDropeffect(String ariaDropeffect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaFlowto(String ariaFlowto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaGrabbed(String ariaGrabbed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaHaspopup(String ariaHaspopup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaHidden(String ariaHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaInvalid(String ariaInvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaLabel(String ariaLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaLabelledby(String ariaLabelledby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaLive(String ariaLive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaOwns(String ariaOwns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaRelevant(String ariaRelevant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataAction(String dataAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataCount(String dataCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataChrome(String dataChrome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataHref(String dataHref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataLayout(String dataLayout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataLinkColor(String dataLinkColor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataParent(String dataParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataShowCount(String dataShowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataShowFaces(String dataShowFaces) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataShare(String dataShare) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataSitekey(String dataSitekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataTarget(String dataTarget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataToggle(String dataToggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataText(String dataText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataUrl(String dataUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataVia(String dataVia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> dataWidgetId(String dataWidgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> itemscope(String itemscope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> itemtype(String itemtype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> itemprop(String itemprop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> role(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaExpanded(String ariaExpanded) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaMultiselectable(String ariaMultiselectable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaRequired(String ariaRequired) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaActivedescendant(String ariaActivedescendant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ul<PARENT> ariaLevel(String ariaLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UlImpl<PARENT> with(String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
