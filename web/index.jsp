<%-- 
    Document   : index
    Created on : May 1, 2015, 1:50:18 PM
    Author     : sandra
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="top.jsp"/>
                    <div class="searchform">
                        <form id="formsearch" name="formsearch" method="get" action="SearchServlet">
                            <span>
                                <input name="editbox_search" class="editbox_search" id="editbox_search" maxlength="80" value="" type="text" />
                            </span>
                            <input type="image" src="images/search_btn.gif" alt="search"/>
                        </form>
                    </div>
                    <div class="results">
                        <c:forEach items="${results}" var="result">
                            
                            <c:set var="folders" value="${fn:split(result.document, '/')}" />
                            <c:set var="fileName" value="${folders[fn:length(folders)-1]}" />
                            
                            <div class="result">
                                <h2>${fileName}</h2>
                                <p class="date"><img src="images/comment.gif" alt="" /> <a href="DownloadFileServlet?path=${result.document}&fileName=${fileName}">Download</a> <img src="images/timeicon.gif" alt="" /> ${result.tf}</p>
                            </div>
                        </c:forEach>
                    </div>
<jsp:include page="bottom.jsp"/>