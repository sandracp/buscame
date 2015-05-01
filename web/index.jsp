<%-- 
    Document   : index
    Created on : May 1, 2015, 1:50:18 PM
    Author     : sandra
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>Manuscript Free Template</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <style type="text/css" media="all">
            @import "images/style.css";
        </style>
    </head>
    <body>
        <div class="content">
            <div id="header">
                <div class="title">
                    <h1>BUSCAME 1.0</h1>
                    <h3>Proyecto de DLC!</h3>
                </div>
            </div>
            <div id="main">
                <div class="center">
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
                </div>
                <div class="leftmenu">
                    <div class="nav">
                        <ul>
                            <li><a href="#">Home</a></li>
                            <li><a href="#">Acerca de</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="prefooter">
            </div>
            <div id="footer">
                <div class="padding"> Copyright &copy; 2015 Buscame | Design: <a href="http://www.free-css-templates.com">David Herreman </a> | <a href="https://github.com/sandracp/buscame">Sandra Pena</a> </div>
            </div>
        </div>
    </body>
</html>
