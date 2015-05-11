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
            <table>
                <tr>
                    <td>Directory:</td>
                    <td>
                        <input type="text" id="directory-to-index" name="directory-to-index" value="/home/sandra/buscame/DocumentosTP1" max="100" />
                    </td>
                </tr>
                <tr>
                    <td>
                        Files: 
                    </td>
                    <td>
                        <input type="file" id="files-to-index" name="files-to-index" webkitdirectory directory multiple/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <input type="submit" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <c:if test="${not empty duration}">
    <div class="results">
        <br> </br>
        <p>Duracion: ${duration}s</p>
        <p>Vocabulario: ${vocabulary}</p>
    </div>
    </c:if>
    <jsp:include page="bottom.jsp"/>