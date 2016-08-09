<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--左侧菜单-->
<div class="wrapper-left">
    <ul id="menu3">
        <li>
            <A href="#">商城商品管理<span><img src="${_slpres}/images/left-sj.png"></span></A>
            <ul>
                <li>
                    <a href="${_base}/prodquery/add">待编辑商品</a>
                </li>
            </ul>
            <ul>
                <li>
                    <a href="${_base}/prodquery/storprod">仓库中商品</a>
                </li>
            </ul>
             <ul>
		        <li>
				     <a href="${_base}/prodquery/insale">销售中商品</a>
		        </li>
		    </ul>
        </li>
       <%--  <li>
            <a href="${_base}/normprodquery/list">标准品管理</a>
        </li> --%>
        <li>
            <A href="#">标准品管理<span><img src="${_slpres}/images/left-sj.png"></span></A>
            <ul>
                <li>
                    <a href="${_base}/normprodquery/list">标准品列表</a>
                </li>
            </ul>
        </li>
        <li>
            <A href="#">库存管理<span><img src="${_slpres}/images/left-sj.png"></span></A>
            <ul>
                <li>
                    <%-- <a href="${_base}/storage/prodstorage">生成虚拟库存</a> --%>
                    <a href="${_base}/storage/list">生成虚拟库存</a>
                </li>
            </ul>
        </li>
    </ul>
</div>