<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="content">
        <div class="span10">
            <div class="row-fluid">
                <div class="span9 account_border_bottom SummaryTxt" style="display:none">
                    <b class="fLe">***<没钱赚商店>购物条形码输入***</b>
                </div>
                <div class="span9" style="display:none">
                    <form action="#">
                        <p />
                        <p><textarea id="barCode" rows=10 cols=40 name="barCode"></textarea></p>
                        <p><button id="submitBarCode" class="btn">发送</button></p>
                    </form>
                </div>
                <div class="span9 account_border_bottom SummaryTxt">
                    <b class="fLe">***<没钱赚商店>购物清单***</b>
                </div>
                <!-- when no records, display this -->
                <div class="span9 account_border_bottom alert" style="display: none">
                    <span> <img src="${pageContext.request.contextPath}/images/ico_info2.gif">暂无购物记录</a>
                    </span>
                </div>
                <!-- when there are records, display this -->
                <div class="span9">
                   <p />
                   <ul>
                      <li>名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)</li>
                      <li>名称：羽毛球，数量：6个，单价：1.00(元)，小计：4.00(元)</li>
                      <li>名称：苹果，数量：2斤，单价：5.50(元)，小计：10.45(元)，节省0.55(元)</li>
                   </ul>
                   <p>----------------------</p>
                   <p>买二赠一商品：</p>
                   <ul>
                      <li>名称：可口可乐，数量：1瓶</li>
                      <li>名称：羽毛球，数量：2个</li>
                   </ul>
                   <span>----------------------</span>
                   <div>
                      <p>总计：20.45(元)</p>
                      <p>节省：5.55(元)</p>
                   </div>
                </div>
                <!--/span-->
            </div>
        </div>
        <!--/row-->
    </tiles:putAttribute>

    <tiles:putAttribute name="useDefaultScript" value="true" />
    <tiles:putAttribute name="otherScript">
        <script>
            !(function(){
                Pos.getResult("${pageContext.request.contextPath}");
              }());
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>

