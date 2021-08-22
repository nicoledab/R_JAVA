<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.rosuda.REngine.REXP" %>
<%@ page import="org.rosuda.REngine.RList" %>
<%@ page import="org.rosuda.REngine.Rserve.RConnection" %>
<script src="/rjava/js/htmlwidgets/htmlwidgets.js"></script>
<link href="/rjava/js/htmlwidgets/lib/wordcloud-0.0.1/wordcloud.css" rel="stylesheet" />
<script src="/rjava/js/htmlwidgets/wordcloud2.js"></script>
<script src="/rjava/js/htmlwidgets/lib/wordcloud2-0.0.1/wordcloud2-all.js"></script>
<script src="/rjava/js/htmlwidgets/lib/wordcloud2-0.0.1/hover.js"></script>


<%
   RConnection conn =new RConnection();

	conn.eval(".libPaths('c:\\\\r\\\\lib')"); //libPaths 경로를 바꿔야하는데 특수문자 처리 때문에
                                    // \\\\ 역슬레쉬를 .. 4개 사용해야함. 그 경로때문에 오류 났던거

   conn.eval("library(wordcloud2)");
   conn.eval("library(htmltools)");
   conn.eval("wc <- wordcloud2(demoFreq)");
   conn.eval("wc_tag <- renderTags(wc)");
   String wc = conn.eval("wc_tag$html").asString();
   conn.close();
%>
<%=wc%>
    
    