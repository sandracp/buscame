<%-- 
    Document   : indexer
    Created on : May 1, 2015, 2:57:39 PM
    Author     : sandra
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="top.jsp"/>
<div class="searchform">
    <form id="formsearch" name="formsearch" method="get" action="IndexerServlet">
        <input type="file" id="files-to-index" name="files-to-index" webkitdirectory directory multiple/>
        <input type="submit" />
    </form>
    <p>Duration: ${duration}s</p>
            
            <p>Size: ${tfIdfSize}</p>
    <jsp:include page="bottom.jsp"/>