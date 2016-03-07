<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="content">
        <div class="span10">
            <div class="row-fluid">
                <div class="span9 account_border_bottom SummaryTxt" style="display:block">
                    <b class="fLe">***<没钱赚商店>购物条形码输入***</b>
                </div>
                <div class="span9" style="display:block">
                    <form id="barCodeForm" name="barCodeForm" action="#">
                        <p />
                        <p><textarea id="barCode" rows=10 cols=40 name="barCode"></textarea></p>
                        <p><button id="submitBarCode" class="btn">发送</button></p>
                    </form>
                </div>
                <div class="span9 account_border_bottom SummaryTxt">
                    <b class="fLe">***<没钱赚商店>购物清单***</b>
                </div>
                <div id="showResult" class="span9">
                   <p />
                   <ul id="productsList">
                   </ul>
                   <div id="priceOffResult">
                   </div>
                   <span>----------------------</span>
                   <div id="showTotal">                      
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
                Pos.init("${pageContext.request.contextPath}");
              }());
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
