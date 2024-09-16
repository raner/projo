package pro.projo.generation.examples.impl;

import java.util.function.Function;

import pro.projo.generation.interfaces.test.html.baseclasses.B;
import pro.projo.generation.interfaces.test.html.baseclasses.BContent;
import pro.projo.generation.interfaces.test.html.baseclasses.Div;
import pro.projo.generation.interfaces.test.html.baseclasses.DivContent;
import pro.projo.generation.interfaces.test.html.baseclasses.NoscriptContent;

public class BImpl<PARENT> extends Impl<BImpl<PARENT>> implements B<PARENT> {
	
	  private final PARENT parent;

	  public BImpl(PARENT parent)
	  {
	    this.parent = parent;
	  }

	  public BImpl(PARENT parent, String content)
	  {
	    super(content);
	    this.parent = parent;
	  }

	@Override
	public PARENT $(Function<BContent, BContent> content) {
	    BContent div = content.apply(new BContentImpl());
	    System.err.println("*** BContent=" + div);
	        return ((Impl<PARENT>)parent).with(this.content + ">" + ((Impl)div).content + "</b>");
	}

	@Override
	public BImpl<PARENT> with(String content) {
		// TODO Auto-generated method stub
		return new BImpl<>(parent, this.content + " " + content);
	}

	@Override
	public B<PARENT> accesskey(String accesskey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> class_(String class_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> contenteditable(String contenteditable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dir(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> hidden(String hidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> id(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> lang(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> spellcheck(String spellcheck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> style(String style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> tabindex(String tabindex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> title(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> translate(String translate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onabort(String onabort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onblur(String onblur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oncancel(String oncancel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oncanplay(String oncanplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oncanplaythrough(String oncanplaythrough) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onchange(String onchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onclick(String onclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oncuechange(String oncuechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ondblclick(String ondblclick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ondurationchange(String ondurationchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onemptied(String onemptied) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onended(String onended) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onerror(String onerror) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onfocus(String onfocus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oninput(String oninput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> oninvalid(String oninvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onkeypress(String onkeypress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onkeydown(String onkeydown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onkeyup(String onkeyup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onload(String onload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onloadeddata(String onloadeddata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onloadedmetadata(String onloadedmetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onloadstart(String onloadstart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmouseenter(String onmouseenter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmouseleave(String onmouseleave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmousedown(String onmousedown) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmouseup(String onmouseup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmouseover(String onmouseover) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmousemove(String onmousemove) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmouseout(String onmouseout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onmousewheel(String onmousewheel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onpause(String onpause) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onplay(String onplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onplaying(String onplaying) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onprogress(String onprogress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onratechange(String onratechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onreset(String onreset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onresize(String onresize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onscroll(String onscroll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onseeked(String onseeked) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onseeking(String onseeking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onselect(String onselect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onshow(String onshow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onstalled(String onstalled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onsubmit(String onsubmit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onsuspend(String onsuspend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ontimeupdate(String ontimeupdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ontoggle(String ontoggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onvolumechange(String onvolumechange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> onwaiting(String onwaiting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaAtomic(String ariaAtomic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaBusy(String ariaBusy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaControls(String ariaControls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaDescribedby(String ariaDescribedby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaDisabled(String ariaDisabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaDropeffect(String ariaDropeffect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaFlowto(String ariaFlowto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaGrabbed(String ariaGrabbed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaHaspopup(String ariaHaspopup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaHidden(String ariaHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaInvalid(String ariaInvalid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaLabel(String ariaLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaLabelledby(String ariaLabelledby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaLive(String ariaLive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaOwns(String ariaOwns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> ariaRelevant(String ariaRelevant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataAction(String dataAction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataCount(String dataCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataChrome(String dataChrome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataHref(String dataHref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataLayout(String dataLayout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataLinkColor(String dataLinkColor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataParent(String dataParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataShowCount(String dataShowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataShowFaces(String dataShowFaces) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataShare(String dataShare) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataSitekey(String dataSitekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataTarget(String dataTarget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataToggle(String dataToggle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataText(String dataText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataUrl(String dataUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataVia(String dataVia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> dataWidgetId(String dataWidgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> itemscope(String itemscope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> itemtype(String itemtype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> itemprop(String itemprop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public B<PARENT> role(String role) {
		// TODO Auto-generated method stub
		return null;
	}
}