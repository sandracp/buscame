<%-- 
    Document   : indexer
    Created on : May 1, 2015, 2:57:39 PM
    Author     : sandra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <div id="main">
                <div class="center">
                    <div class="searchform">
                        <form id="formsearch" name="formsearch" method="get" action="IndexerServlet">
                           <input type="file" id="directory-to-index" name="directory-to-index" webkitdirectory directory multiple/>
                        </form>
                    </div>
                </div>
       </div>
    </body>
</html>
