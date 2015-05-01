<%-- 
    Document   : indexer
    Created on : May 1, 2015, 2:57:39 PM
    Author     : sandra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="top.jsp"/>
                    <div class="searchform">
                        <form id="formsearch" name="formsearch" method="get" action="IndexerServlet">
                           <input type="file" id="directory-to-index" name="directory-to-index" webkitdirectory directory multiple/>
                        </form>
                    </div>
<jsp:include page="bottom.jsp"/>