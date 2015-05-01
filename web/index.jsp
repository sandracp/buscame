<%-- 
    Document   : index
    Created on : May 1, 2015, 1:50:18 PM
    Author     : sandra
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <div class="result">
                                <h2><a href="#">${result.name}</a></h2>
                                <h3>Alguna descripcion?</h3>
                                <p class="date"><img src="images/comment.gif" alt="" /> <a href="#">Download</a> <img src="images/timeicon.gif" alt="" /> 17.01.</p>
                            </div>
                        </c:forEach>
                    </div>
<jsp:include page="bottom.jsp"/>