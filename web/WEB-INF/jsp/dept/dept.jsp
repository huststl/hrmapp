<%--
  Created by IntelliJ IDEA.
  User: stl
  Date: 2018/1/8
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>人事管理系统 ——部门管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
    <meta http-equiv="description" content="This is my page" />
    <link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
    <link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
    <script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
    <link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript">
        $(function(){
            /** 部门表单提交 */
            $("#deptForm").submit(function(){
                var name = $("#name");
                var remark = $("#remark");
                var msg = "";
                if ($.trim(name.val()) == ""){
                    msg = "部门名称不能为空！";
                    name.focus();
                }else if ($.trim(remark.val()) == ""){
                    msg = "详细描述不能为空！";
                    remark.focus();
                }
                if (msg != ""){
                    $.ligerDialog.error(msg);
                    return false;
                }else{
                    return true;
                }
                $("#deptForm").submit();
            });
        });


    </script>
</head>
<body>
    <!-- 导航 -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr><td height="10"></td></tr>
        <tr>
            <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
            <td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：部门管理 &gt; 部门查询</td>
            <td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
        </tr>
    </table>

    <table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
        <!-- 查询区  -->
        <tr valign="top">
            <td height="30">
                <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
                    <tr>
                        <td class="fftd">
                            <form name="deptform" method="post" id="deptform" action="${ctx}/dept/selectDept">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="font3">
                                            部门名称：<input type="text" name="name">
                                            <input type="submit" value="搜索"/>
                                            <input id="delete" type="button" value="删除"/>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <!-- 数据展示区 -->
        <tr valign="top">
            <td height="20">
                <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
                    <tr class="main_trbg_tit" align="center">
                        <td><input type="checkbox" name="checkAll" id="checkAll"></td>
                        <td>部门名称</td>
                        <td>详细信息</td>
                        <td align="center">操作</td>
                    </tr>
                    <c:forEach items="${requestScope.depts}" var="dept" varStatus="stat">
                        <tr id="data_${stat.index}" align="center" class="main_trbg" onMouseOver="move(this);" onMouseOut="out(this);">
                            <td><input type="checkbox" id="box_${stat.index}" value="${dept.id}"></td>
                            <td>${dept.name }</td>
                            <td>${dept.remark }</td>
                            <td align="center" width="40px;"><a href="${ctx}/dept/updateDept?flag=1&id=${dept.id}">
                                <img title="修改" src="${ctx}/images/update.gif"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>

        <!-- 分页标签 -->
        <tr valign="top"><td align="center" class="font3">
            <fkjava:pager
                    pageIndex="${requestScope.pageModel.pageIndex}"
                    pageSize="${requestScope.pageModel.pageSize}"
                    recordCount="${requestScope.pageModel.recordCount}"
                    style="digg"
                    submitUrl="${ctx}/dept/selectDept?pageIndex={0}"/>
        </td></tr>
    </table>
    <div style="height:10px;"></div>
</body>
</html>
