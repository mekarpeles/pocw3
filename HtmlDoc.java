
public class HtmlDoc 
{
    public static final String DOC_TYPE = "<!DOCTYPE html>\n";
    private int tab_depth;

    /* --------------------------------------------------------------------------- */
    // HtmlDoc Constructor
    /* --------------------------------------------------------------------------- */
    public HtmlDoc ()
    {
	tab_depth = 0;
    }


    /* --------------------------------------------------------------------------- */
    // Special HTML Pages
    /* --------------------------------------------------------------------------- */
    public String error404(String url, String server_info)
    {
	String title = "404 Not Found";
	String  body = h(1, "Not Found") +
	    p("The requested URL " + url + " was not found on this server") +
	    p("For more information, please see: " + a("http://en.wikipedia.org/wiki/HTTP_404","HTTP Error 404")) + 
	    hr() + p(server_info, "font-style: italic;");
	return build(title, body);
    }


    /* --------------------------------------------------------------------------- */
    // Special HTML Pages
    /* --------------------------------------------------------------------------- */
    public String error500(String server_info)
    {
	String title = "500 Internal Server Error";
	String  body = h(1, "Internal Sever Error") +
	    p("The server encountered an internal error or misconfiguration " +
	      "and was unable to complete your request.") +
	    p("Please contact the server adminitrator, mek.linux@gmail.com and" +
	      "inform them of the time the error occured, and anything you might" +
	      "have done that may have caused this error.") +
	    p("More information about this error may be available in the server error log.") +
	    hr() + p(server_info, "font-style: italic;");
	return build(title, body);
    }


    /* --------------------------------------------------------------------------- */
    // HTML Document Builder
    /* --------------------------------------------------------------------------- */
    public String build(String title, String content)
    {
	return doctype() + docstart() + dochead(title) + docbody(content) + detab() + docend();
    }


    /* --------------------------------------------------------------------------- */
    // HTML Document Components
    /* --------------------------------------------------------------------------- */
    public String doctype()  { return DOC_TYPE; }

    public String docstart() { return "<html>"; }

    public String dochead(String title)
    {
	return tab()   + "<head>"  +
	       tab()   + "<title>" + title + "</title>" + detab() + 
	       align() + "</head>\n";
    }


    public String docbody(String content)
    {
	return align() + "<body>"  + 
	           tab().replaceAll("\\\n", "") +
	               content.replaceAll("\\\n", align()) +
	           detab().replaceAll("\\\n", "") + 
	       align() + "</body>\n";
    }

    public String docend() { return "</html>"; }


    /* --------------------------------------------------------------------------- */
    // HTML Container Tags
    /* --------------------------------------------------------------------------- */
    public String h(int depth, String title)
    {
	depth = depth % 6;
	return "\n<h" + depth + ">" + title + "</h" + depth + ">";
    }

    public String p(String paragraph)
    {
	return "\n<p>" + paragraph + "</p>";
    }

    public String p(String paragraph, String style)
    {
	return "\n<p style=\""+style+"\">" + paragraph + "</p>";
    }

    public String a(String link, String label)
    {
	return "\n<a href=\"" + link + "\">" + label + "</a>";
    }

    public String hr()
    {
	return align() + "\n<hr/>\n";
    }


    /* --------------------------------------------------------------------------- */
    // Helpers & Utils
    /* --------------------------------------------------------------------------- */
    public String align()      { return tab(0);  }

    public String tab()        { return tab(1);  }

    public String detab()      { return tab(-1); }

    public String detab(int t) { return tab(-t); }

    public String tab(int t) // t can be negative
    {
	tab_depth += t;
	String result="\n";
	for (int tabs=0; tabs<tab_depth; tabs++) {
	    result+="\t";
	} return result;
    }
}