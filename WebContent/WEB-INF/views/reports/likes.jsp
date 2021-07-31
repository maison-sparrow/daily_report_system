<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>いいねした日報　一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="like_report2" items="${like_reports2}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${like_report2.employee.name}" /></td>
                        <td class="report_date"><fmt:formatDate value='${like_report2.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${like_report2.title}</td>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${like_report2.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <p><a href="<c:url value='/reports' />">日報一覧へ</a></p>

        </c:param>
    </c:import>