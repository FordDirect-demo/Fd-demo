<%@include file="/libs/foundation/global.jsp" %>


<%@ taglib prefix="widgets" uri="http://www.adobe.com/consulting/acs-aem-commons/widgets" %>
<%@ taglib prefix="xss" uri="http://www.adobe.com/consulting/acs-aem-commons/xss" %>
<%@ taglib prefix="wcmmode" uri="http://www.adobe.com/consulting/acs-aem-commons/wcmmode" %>
<%@ taglib prefix="fd" uri="http://www.fd.com/taglibs/1.0" %>


<cq:includeClientLib categories="etc.designs.fddemo.clientlibs.glossary"/>


<c:set var="glossaryItems" value="${widgets:getMultiFieldPanelValues(resource, 'glossary')}" />
<c:set var="sortedGlossaryItems" value="${fd:sortGlossaryItems(glossaryItems)}" />
<c:set var="paginationAlphabets" value="${fd:getPaginationList(glossaryItems)}" />
<c:set var="selectList" value="${properties.selectList}"/>
<input type="hidden" id="selectList" value="${selectList}" />


<h1 id="page-glossary"><strong>Glossary</strong></h1>

<c:if test ="${selectList eq 'top' || selectList eq 'both' || (empty selectList)}">
<nav  id="paginationTop">
<ul id="navlist">
<c:forEach var="entry" items="${paginationAlphabets}">
<c:choose>
<c:when test="${entry.key eq 'A'}">
<li><strong>${entry.key}</strong></li>
</c:when>
<c:when test="${(entry.key ne 'A') && (entry.value eq 'n')}">
<li><strong class="gray">${entry.key}</strong></li>
</c:when>
<c:when test="${(entry.key ne 'A') && (entry.value eq 'y')}">
<a  class="anchorNav" onclick="getGlossary('${entry.key}','glossary','${currentNode.path}')">
<li><strong>${entry.key}</strong></li></a>
</c:when>
</c:choose>
</c:forEach>
<a  class="anchorNav" onclick="getGlossary('all','glossary','${currentNode.path}')">
<li><strong>ALL</strong></li></a>
</ul>
</nav>
</c:if>


<h1 id="alphabetH1"><strong>A</strong></h1>

<div id="loadingImg" align="center">
<img src="/etc/designs/fd-demo/clientlibs/glossary/images/spinner_squares_circle.gif" />
</div>

<div  id="glossaryCenter">
<c:choose>
    <c:when test="${(empty glossaryItems) && wcmmode:isEdit(pageContext)}">
		<p>Please configure at least one item that starts with letter A</p>
    </c:when>
    <c:when test="${(empty glossaryItems) && (wcmmode:isPreview(pageContext) || wcmmode:isDisabled(pageContext))}">
		<p>No items found</p>
    </c:when>
    <c:otherwise>
 <c:forEach items="${glossaryItems}" var="item" varStatus="status">
 <c:set var="word" value="${xss:encodeForHTML(xssAPI, item['word'])}" />
  <c:set var="description" value="${xss:filterHTML(xssAPI, item['description'])}" />
  <c:if test="${fn:startsWith(word, 'A') || fn:startsWith(word, 'a')}">
   <b>${word}</b><br>
    <p id="description-para"> ${description} </p><br>
  </c:if>
</c:forEach>   
    </c:otherwise>    
</c:choose>
</div>

<c:if test ="${selectList eq 'bottom' || selectList eq 'both'}">
<nav id="paginationBottom">
<ul id="navlist">
<c:forEach var="entry" items="${paginationAlphabets}">
<c:choose>
<c:when test="${entry.key eq 'A'}">
<li><strong>${entry.key}</strong></li>
</c:when>
<c:when test="${(entry.key ne 'A') && (entry.value eq 'n')}">
<li><strong class="gray">${entry.key}</strong></li>
</c:when>
<c:when test="${(entry.key ne 'A') && (entry.value eq 'y')}">
<a  class="anchorNav" onclick="getGlossary('${entry.key}','glossary','${currentNode.path}')">
<li><strong>${entry.key}</strong></li></a>
</c:when>
</c:choose>
</c:forEach>
<a  class="anchorNav" onclick="getGlossary('all','glossary','${currentNode.path}')">
<li><strong>ALL</strong></li></a>
</ul>
</nav>
</c:if>