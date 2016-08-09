<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>运营管理</title>
	<%@ include file="/inc/inc.jsp" %>
	<link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
	
</head>

<body>
	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp" %>
	<!--顶部菜单结束-->
	<!-- 左侧菜单 -->
	<%@ include file="/inc/left-menu.jsp" %>
	<!-- 左侧菜单结束 -->

<div class="wrapper"><!--外围框架-->
<!--右侧框架-->
 <div class="wrapper-right">
    <!--公告位置-->
    	<%@ include file="/inc/public-msg.jsp" %>
    <!--公告位置结束-->   
    <!--标签-->
    <div class="right-tags">
        <ul>
           <li>
	           <p class="none">您现在的位置：</p>
	           <p><a href="#">库存管理</a> > </p>  
	           <p>生成虚拟库存</p>
           </li>
        </ul>  
    </div>
   <!--标签结束-->
      <!--查询区域-->
    <div class="form-wrapper"><!--白底内侧-->
    
       <div class="form-label">
	            <ul id="data1ProdCat">
                    <li class="width-xlag">
                        <p class="word">所属类目</p>
                        <c:forEach var="map" items="${catInfoMap}" varStatus="status">
							<p id="productCat${status.index}">
								<select class="select select-small"
									onChange="pager._selectChange(this);">
									<c:forEach var="info" items="${map.value}">
									<option value="${info.productCatId}">${info.productCatName}</option>   	
									</c:forEach>
								</select>
							</p>
						</c:forEach> 
                        <script id="prodCatTemple" type="text/template">
                                	 <p id="productCat{{:level}}">
										<select class="select select-small" onChange="pager._selectChange(this);">
											{{for prodCatList}}
                                   			 	<option value="{{:productCatId}}">{{:productCatName}}</option>
											{{/for}}
                               			</select>
									</p>
						</script>
                    </li>
                </ul>
       	 <ul>
                <li class="width-xlag">
                    <p class="word">标准品名称</p>
                    <p><input id="standedProductName" type="text" class="int-text int-medium"></p>
                    <p><input id="selectNormProductList" type="button" value="查询" class="biu-btn btn-blue btn-mini"/></p>
                    <p class="sos"><a href="javascript:void(0);">高级搜索<i class="icon-caret-down"></i></a></p>
                </li>
            </ul>
            <!--点击展开-->
            <div id="selectDiv" class="open" style="display:none;">
            <ul>
                <li>
                    <p class="word">标准品ID</p>
                    <p><input id="standedProdId" type="text" class="int-text int-medium"></p>
                </li>
                 <li>
                    <p class="word">标准品类型</p>
                    <p>
	                    <select id="productType" class="select select-medium">
		                    <option value="">全部</option>
		                   	<option value="1">实物</option>
		                   	<option value="2">虚拟</option>
	                   	</select>
                   	</p>
                </li>
            </ul> 
            <input type="hidden" id="state" value="1" />
            
            <ul>
                <li class="width-xlag">
                    <p class="word">操作时间</p>
                    <p><input type="text" class="int-text int-medium" id="operStartTime"><a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                    <p>~</p>
                    <p><input type="text" class="int-text int-medium" id="operEndTime"><a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                </li>
            </ul>
            </div>  
            <!--点击展开结束-->
        </div>
    
    </div>
   <!--查询区域结束-->
   <!--查询结果-->
   <div class="form-wrapper"><!--白底内侧-->
         <div class="nav-tplist-wrapper"><!--白底内侧-->
          <!--结果标题-->
             <div class="nav-tplist-title">
                  <ul>
                    <li>标准品列表</li>
                  </ul>
             </div>
         <!--结果表格-->
        <div class="table table-border table-bordered table-bg table-hover mt-10">
			<table width="100%" border="0">
              <tr class="bj">  
                <td width="5%">序号</td>
				<td width="10%">标准品ID</td>
				<td width="30%">标准品名称</td>
				<td width="10%">所属类目</td>
				<td width="10%">类型</td>
				<td>标准品状态</td>
				<td>操作时间</td>
			<!-- 	<td>操作人</td> -->
				<td>操作</td> 
              </tr>
            <tbody id="searchNormProductData"></tbody>
            </table>
            <div id="showMessageDiv"></div>
                   <script id="searchNormProductTemple" type="text/template">
							<tr>
                                <td>{{:#index+1}}</td>
                                <td>{{:productId}}</td>
                                <td>{{:productName}}</td>
								<td>{{:catName}}</td>
                                <td>{{:productType}}</td>
                                <td>{{:state}}</td>
								<td>{{:~timesToFmatter(operTime)}}</td>
                       <%--        <td>{{:operId}}</td>
                                <td><a href="#" class="blue">查看详情</a><a href="#" class="red">编辑</a><a href="＃" class="blue">废弃</a></td>
 						--%> 
								<td><a href="${_base}/storage/{{:productId}}" class="blue-border">编辑</a></td>
                            </tr>
			</script>
          </div> 
          <!--分页-->
		  	<div>
				 <nav style="text-align: right">
				<ul id="pagination-ul">
				</ul>
			</nav>
		  </div>
		 <!--分页-->
        <!--结果表格结束-->
         </div> 
    </div>
   <!--查询结果结束-->

    </div>
</div>	
<!-- footer -->
<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
</html>
<script src="${_slpres }/scripts/metismenu.js"></script>
 <script type="text/javascript"> 
 
window.onload = function(){	
	var timer;
	var elem = document.getElementById('elem');
	var elem1 = document.getElementById('elem1');
	var elem2 = document.getElementById('elem2');
	elem2.innerHTML = elem1.innerHTML;
	timer = setInterval(Scroll,40);
	function Scroll(){
		if(elem.scrollTop>=elem1.offsetHeight){
			elem.scrollTop -= elem1.offsetHeight;
		}else{
			elem.scrollTop += 1;
		}
	}	
	elem.onmouseover = function(){
		clearInterval(timer);
	}	
	elem.onmouseout = function(){
		timer = setInterval(Scroll,40);
	}
}
</script>
<script type="text/javascript">
		var pager;
		var count = '${count}';
		var prodInfoList = '${prodInfoList}';
		var productEditInfo = '${productEditInfo}';
		(function () {
            <%-- 展示日历 --%>
            $('#selectDiv').delegate('.icon-calendar','click',function(){
                var calInput = $(this).parent().prev();
                var timeId = calInput.attr('id');
                console.log("click calendar "+timeId);
                WdatePicker({el:timeId,readOnly:true});
            });
            seajs.use('app/jsp/storage/prodstorage', function(
    				normproductlistPager) {
    			pager = new normproductlistPager({
    				element : document.body
    			});
    			pager.render();
    		});
		})();
</script>




















 
 
 
 
 <%-- <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>运营管理</title>
	<%@ include file="/inc/inc.jsp" %>
	<link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
</head>

<body>
	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp" %>
	<!--顶部菜单结束-->
	<!-- 左侧菜单 -->
	<%@ include file="/inc/left-menu.jsp" %>
	<!-- 左侧菜单结束 -->

<div class="wrapper"><!--外围框架-->
<!--右侧框架-->
 <div class="wrapper-right">
    <!--公告位置-->
    	<%@ include file="/inc/public-msg.jsp" %>
    <!--公告位置结束-->   
    <!--标签-->
    <div class="right-tags">
        <ul>
           <li>
	           <p class="none">您现在的位置：</p>
	           <p><a href="#">库存管理</a> > </p>  
	           <p>生成虚拟库存</p>
           </li>
        </ul>  
    </div>
   <!--标签结束-->
      <!--查询区域-->
    <div class="form-wrapper"><!--白底内侧-->
    
       <div class="form-label">
	            <ul id="productCat">
                    <li class="width-xlag">
                        <p class="word">所属类目</p>
                        <c:forEach var="map" items="${catInfoMap}" varStatus="status">
                         <p id="productCat${status.index}">
                             <select class="select select-small" onChange="pager._selectChange(this);">
	                             <c:forEach var="info" items="${map.value}">
	                                 <option value="${info.productCatId}">${info.productCatName}</option>
	                             </c:forEach>
                             </select>
                         </p>
                        </c:forEach>
                        <script id="prodCatTemple" type="text/template">
                            <p id="productCat{{:level}}">
								<select class="select select-small" onChange="pager._selectChange(this);">
									{{for prodCatList}}
                                   		<option value="{{:productCatId}}">{{:productCatName}}</option>
									{{/for}}
                               	</select>
							</p>
						</script>
                    </li>
                </ul>
       	 <ul>
                <li class="width-xlag">
                    <p class="word">标准品名称</p>
                    <p><input id="stanProdName" type="text" class="int-text int-medium"></p>
                    <p><input id="selectStandProd" type="button" value="查询" class="biu-btn btn-blue btn-mini"/></p>
                    <p class="sos"><a href="javascript:void(0);">高级搜索<i class="icon-caret-down"></i></a></p>
                </li>
            </ul>
            <!--点击展开-->
            <div id="selectDiv" class="open" style="display:none;">
            <ul>
                <li>
                    <p class="word">标准品ID</p>
                    <p><input id="stanProdId" type="text" class="int-text int-medium"></p>
                </li>
                 <li>
                    <p class="word">标准品类型</p>
                    <p>
	                    <select id="stanProdType" class="select select-medium">
		                    <option value="">全部</option>
		                   	<option value="1">实物</option>
		                   	<option value="2">虚拟</option>
	                   	</select>
                   	</p>
                </li>
            </ul> 
            <ul>
                <li>
                    <p class="word">操作时间</p>
                    <p><input type="text" class="int-text int-medium" id="operStartTime">
                        <a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                    <p>~</p>
                    <p><input type="text" class="int-text int-medium" id="operEndTime">
                        <a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                </li>
            </ul>
            </div>  
            <!--点击展开结束-->
        </div>
    
    </div>
   <!--查询区域结束-->
   <!--查询结果-->
   <div class="form-wrapper"><!--白底内侧-->
         <div class="nav-tplist-wrapper"><!--白底内侧-->
          <!--结果标题-->
             <div class="nav-tplist-title">
                  <ul>
                    <li>标准品列表</li>
                  </ul>
             </div>
         <!--结果表格-->
        <div class="table table-border table-bordered table-bg table-hover mt-10">
			<table width="100%" border="0">
              <tr class="bj">  
                <td>序号</td>                                                                                                      
                <td>标准品ID</td>
                <td>标准品名称</td>
                <td>所属类目</td>
                <td>类型</td>
                <td>操作日期</td>
                <td>操作人</td>
                <td>操作</td>
              </tr>
            <tbody id="searchStanProdData"></tbody>
            </table>
            <div id="showMessageDiv"></div>
                   <script id="searchStanProdTemple" type="text/template">
			<tr>
                <td>1</td>
                <td>{{:productId}}</td>
                <td>{{:productName}}</td>
                <td>{{:catName}}</td>
                <td>{{:productTypeName}}</td>
                <td>{{:~timesToFmatter(operTime)}}</td>
                <td>hesuan</td>
                <td><a href="#" class="blue">编辑</a></td>
              </tr>
                                <td>{{:totalNum}}</td>
                                <td>{{:stateName}}</td>
                                <td>{{:~timesToFmatter(createTime)}}</td>
                                <td>
                                    <div>
                                        <p><a href="${_base}/prodedit/{{:prodId}}" class="blue-border">编辑商品</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
					</script>
          </div> 
          <!--分页-->
			 <div class="paging-large">
				 <nav style="text-align: right">
					<ul id="pagination-ul">
					</ul>
				</nav>
		  	</div>
		 <!--分页-->
        <!--结果表格结束-->
         </div> 
    </div>
   <!--查询结果结束-->

    </div>
</div>	
<!-- footer -->
<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
</html>
<script src="${_slpres }/scripts/metismenu.js"></script>
 <script type="text/javascript"> 
window.onload = function(){	
	var timer;
	var elem = document.getElementById('elem');
	var elem1 = document.getElementById('elem1');
	var elem2 = document.getElementById('elem2');
	elem2.innerHTML = elem1.innerHTML;
	timer = setInterval(Scroll,40);
	function Scroll(){
		if(elem.scrollTop>=elem1.offsetHeight){
			elem.scrollTop -= elem1.offsetHeight;
		}else{
			elem.scrollTop += 1;
		}
	}	
	elem.onmouseover = function(){
		clearInterval(timer);
	}	
	elem.onmouseout = function(){
		timer = setInterval(Scroll,40);
	}
}
</script>
<script type="text/javascript">
		var pager;
		var count = '${count}';
		(function () {
            展示日历
            $('#selectDiv').delegate('.icon-calendar','click',function(){
                var calInput = $(this).parent().prev();
                var timeId = calInput.attr('id');
                console.log("click calendar "+timeId);
                WdatePicker({el:timeId,readOnly:true});
            });
			seajs.use('app/jsp/storage/prodstorage', function (ProdStoragePager) {
				pager = new ProdStoragePager({element: document.body});
				pager.render();
			});
		})();
</script>

 
  --%>
 
 
 
 
 