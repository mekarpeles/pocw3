
public class MIMETyper 
{

    final static String[][] mimetypes = {{"text/plain"      , ".bas" , ".c"    , ".h"   , ".txt" },
					 {"video/mpeg"      , ".mpe" , ".mpeg" , ".mpg" , ".mpv2"},
					 {"image/jpeg"      , ".jpg" , ".jpeg" , ".jpe"          },
					 {"text/html"       , ".htm" , ".html" , ".stm"          },
					 {"text/css"        , ".css"                             },
					 {"image/gif"       , ".gif"                             },
					 {"image/png"       , ".png"                             },
					 {"text/html"       , ".java"                            },
					 {"application/pdf" , ".pdf"                             }};

    public MIMETyper () { }

    public String type(String file)
    {
	for (int type=0; type < mimetypes.length; type++) {
	    for (int ext=1; ext < mimetypes[type].length; ext++) {
		if (file.endsWith(mimetypes[type][ext])) {
		    return mimetypes[type][0];
		}
	    }
	} return "application/octet-stream";
    }
}