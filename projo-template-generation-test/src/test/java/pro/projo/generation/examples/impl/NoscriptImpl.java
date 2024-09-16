package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.Noscript;
import pro.projo.generation.interfaces.test.html.baseclasses.NoscriptContent;

public class NoscriptImpl<PARENT> extends Impl<NoscriptImpl<PARENT>> implements Noscript<PARENT>
{
	  private final PARENT parent;

	  public NoscriptImpl(PARENT parent)
	  {
	    this.parent = parent;
	  }

	  public NoscriptImpl(PARENT parent, String content)
	  {
	    super(content);
	    this.parent = parent;
	  }

	@Override
	public PARENT $(Function<NoscriptContent, NoscriptContent> content)
	{
	    NoscriptContent div = content.apply(new NoscriptContentImpl());
	    System.err.println("*** noscript=" + div);
	    System.err.println("*** parent=" + parent);
	        return ((Impl<PARENT>)parent).with(this.content + ">" + ((Impl)div).content + "</noscript>");
	}

	@Override
	public NoscriptImpl<PARENT> with(String content) {
		// TODO Auto-generated method stub
		return new NoscriptImpl<>(parent, this.content + " " + content);
	}

	@Override
	public Noscript<PARENT> accesskey(String accesskey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> class_(String class_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> contenteditable(String contenteditable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dir(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> hidden(String hidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> id(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> lang(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> spellcheck(String spellcheck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> style(String style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> tabindex(String tabindex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> title(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> translate(String translate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onabort(String onabort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onblur(String onblur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oncancel(String oncancel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oncanplay(String oncanplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oncanplaythrough(String oncanplaythrough) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onchange(String onchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onclick(String onclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oncuechange(String oncuechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ondblclick(String ondblclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ondurationchange(String ondurationchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onemptied(String onemptied) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onended(String onended) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onerror(String onerror) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onfocus(String onfocus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oninput(String oninput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> oninvalid(String oninvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onkeypress(String onkeypress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onkeydown(String onkeydown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onkeyup(String onkeyup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onload(String onload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onloadeddata(String onloadeddata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onloadedmetadata(String onloadedmetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onloadstart(String onloadstart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmouseenter(String onmouseenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmouseleave(String onmouseleave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmousedown(String onmousedown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmouseup(String onmouseup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmouseover(String onmouseover) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmousemove(String onmousemove) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmouseout(String onmouseout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onmousewheel(String onmousewheel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onpause(String onpause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onplay(String onplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onplaying(String onplaying) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onprogress(String onprogress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onratechange(String onratechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onreset(String onreset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onresize(String onresize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onscroll(String onscroll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onseeked(String onseeked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onseeking(String onseeking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onselect(String onselect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onshow(String onshow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onstalled(String onstalled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onsubmit(String onsubmit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onsuspend(String onsuspend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ontimeupdate(String ontimeupdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ontoggle(String ontoggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onvolumechange(String onvolumechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> onwaiting(String onwaiting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaAtomic(String ariaAtomic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaBusy(String ariaBusy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaControls(String ariaControls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaDescribedby(String ariaDescribedby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaDisabled(String ariaDisabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaDropeffect(String ariaDropeffect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaFlowto(String ariaFlowto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaGrabbed(String ariaGrabbed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaHaspopup(String ariaHaspopup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaHidden(String ariaHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaInvalid(String ariaInvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaLabel(String ariaLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaLabelledby(String ariaLabelledby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaLive(String ariaLive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaOwns(String ariaOwns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> ariaRelevant(String ariaRelevant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataAction(String dataAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataCount(String dataCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataChrome(String dataChrome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataHref(String dataHref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataLayout(String dataLayout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataLinkColor(String dataLinkColor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataParent(String dataParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataShowCount(String dataShowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataShowFaces(String dataShowFaces) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataShare(String dataShare) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataSitekey(String dataSitekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataTarget(String dataTarget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataToggle(String dataToggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataText(String dataText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataUrl(String dataUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataVia(String dataVia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> dataWidgetId(String dataWidgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> itemscope(String itemscope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> itemtype(String itemtype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Noscript<PARENT> itemprop(String itemprop) {
		// TODO Auto-generated method stub
		return null;
	}
}
