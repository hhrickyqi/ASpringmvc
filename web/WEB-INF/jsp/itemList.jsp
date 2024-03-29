<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>查询商品列表</title>
        <script type="text/javascript">
            function addItem() {
                window.location.href = ${pageContext.request.contextPath}"/addUI.do";
            }
        </script>
    </head>
    <body>
        <form action="${pageContext.request.contextPath }/item/queryitem.action" method="post">
            查询条件：
            <table width="100%" border=1>
                <tr>
                    <td><input type="submit" value="查询"/></td>
                </tr>
            </table>
        </form>
        <form action="${pageContext.request.contextPath }/deleteItems.do" method="post">
            商品列表：
            <table width="100%" border=1>
                <tr>
                    <td><input type="checkbox" name="ids" value=""/></td>
                    <td>商品名称</td>
                    <td>商品价格</td>
                    <td>生产日期</td>
                    <td>商品描述</td>
                    <td>操作</td>
                </tr>
                <c:forEach items="${itemList }" var="item">
                    <tr>
                        <td><input type="checkbox" name="ids" value="${item.id}"/></td>
                        <td>${item.name }</td>
                        <td>${item.price }</td>
                        <td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${item.detail }</td>

                        <td><a href="${pageContext.request.contextPath }/itemEdit.do?id=${item.id}&status=false">修改</a>
                        </td>

                    </tr>
                </c:forEach>

            </table>
            <input type="button" onclick="addItem()" value="添加">
            <input type="submit" value="删除"/>
        </form>
    </body>

</html>