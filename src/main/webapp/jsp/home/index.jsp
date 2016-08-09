<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运营管理</title>
    <%@ include file="/inc/inc.jsp" %>
    <link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
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
        <div class="right-topnav">
            <p class="gongg"><A href="#">［公告］:</A></p>
            <div id="elem">
                <ul id="elem1">
                    <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                    <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                    <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                    <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                </ul>
                <ul id="elem2">
                </ul>
            </div>
            <p class="dclose"><A href="#"><i class="icon-remove"></i></A></p>
        </div>
        <!--公告位置结束-->
        <!--标签-->
        <div class="right-tags">
            <ul>
                <li>
                    <p class="none">您现在的位置：</p>
                    <p><a href="#">商城商品管理</a></p>
                    <p></p>
                    <p></p>
                </li>
            </ul>
        </div>

        <!--标签结束-->
        <!--查询区域-->
        <!--查询区域结束-->
        <!--查询结果-->
        <div class="form-wrapper"><!--白底内侧-->

            <div class="nav-tplist-wrapper"><!--白底内侧-->
                <div class="order-list-table">
                    <ul>
                        <li><a href="#" class="current">待编辑</a></li>
                        <li><a href="#">被拒绝</a></li>
                        <li><a href="#">审核中</a></li>
                    </ul>
                </div>
                <!--结果标题-->
                <div id="date1">
                    <div class="nav-form">
                        <ul>
                            <li class="width-xlag">
                                <p class="word">属性ID</p>
                                <p>
                                    <select class="select-small">
                                    <c:forEach var="info" items="${catInfos}">
                                        <option value="${info.productCatId}">${info.productCatName}</option>
                                    </c:forEach>
                                    </select>
                                </p>
                                <p><select class="select-small">
                                    <option>二级类目</option>
                                </select></p>
                                <p><select class="select-small">
                                    <option>三级类目</option>
                                </select></p>
                                <p class="word">属性名称</p>
                                <p><input type="text" class="int-medium"></p>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <p class="word">商品ID</p>
                                <p><select class="select-medium"></select></p>
                            </li>
                            <li>
                                <p class="word">商品名称</p>
                                <p><select class="select-medium"></select></p>
                                <p><input type="button" value="查询" class="blling-btn blue-btn"></p>
                            </li>
                        </ul>

                    </div>
                    <!--结果表格-->
                    <div class="nav-tplist-table commodity-tplist-table">
                        <table width="100%" border="0">
                            <tr class="bj">
                                <td>商品ID</td>
                                <td>所属类目</td>
                                <td>类型</td>
                                <td>预览图</td>
                                <td>商品名称</td>
                                <td>总库存</td>
                                <td>状态</td>
                                <td>生成时间</td>
                                <td>操作</td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>话费</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-01.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>未编辑</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p><a href="#" class="blue-border">查看详情</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>已保存</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p><a href="#" class="blue-border">查看详情</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>已保存</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p><a href="#" class="blue-border">查看详情</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>已保存</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p><a href="#" class="blue-border">查看详情</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </div>
                    <!--结果表格结束-->
                    <div class="paging-large">
                        <ul>
                            <li class="prev-up"><a href="#">&lt;上一页</a></li>
                            <li class="active"><a href="#">1 </a></li>
                            <li><a href="#">2 </a></li>
                            <li><span>…</span></li>
                            <li><a href="#">38</a></li>
                            <li><a href="#">39</a></li>
                            <li><a href="#">40</a></li>
                            <li><a href="#">41</a></li>
                            <li><a href="#">42</a></li>
                            <li><span>…</span></li>
                            <li class="next-down"><a href="#">下一页&gt;</a></li>
                            <li>共100页</li>
                            <li>
                                <span>到</span>
                                <span><input type="text" class="int-verysmall"></span>
                                <span>页</span>
                                <span class="btn-span"><a class="but-determine">确定</a></span>
                            </li>

                        </ul>
                    </div>
                </div>

                <div id="date3" style="display:none;">
                    <div class="nav-form">
                        <ul>
                            <li class="width-xlag">
                                <p class="word">属性ID</p>
                                <p><select class="select-small">
                                    <option>一级类目</option>
                                </select></p>
                                <p><select class="select-small">
                                    <option>二级类目</option>
                                </select></p>
                                <p><select class="select-small">
                                    <option>三级类目</option>
                                </select></p>
                                <p class="word">属性名称</p>
                                <p><input type="text" class="int-medium"></p>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <p class="word">商品ID</p>
                                <p><select class="select-medium"></select></p>
                            </li>
                            <li>
                                <p class="word">商品名称</p>
                                <p><select class="select-medium"></select></p>
                                <p><input type="button" value="查询" class="blling-btn blue-btn"></p>
                            </li>
                        </ul>

                    </div>
                    <!--结果表格-->
                    <div class="nav-tplist-table commodity-tplist-table">
                        <table width="100%" border="0">
                            <tr class="bj">
                                <td>商品ID</td>
                                <td>所属类目</td>
                                <td>类型</td>
                                <td>预览图</td>
                                <td>商品名称</td>
                                <td>总库存</td>
                                <td>状态</td>
                                <td>生成时间</td>
                                <td>操作</td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>话费</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-01.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>
                                    <div><p>审核中</p>
                                        <p class="color"><img src="${_slpres}/images/icon-c.png"/>已申请优先</p></div>
                                </td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>审核中</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p class="first"><a href="#" class="blue">申请优先处理</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>审核中</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p class="first"><a href="#" class="blue">申请优先处理</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>24343433</td>
                                <td>流量</td>
                                <td>虚拟</td>
                                <td><img src="${_slpres}/images/sp-02.png"></td>
                                <td>中国移动100元充值卡</td>
                                <td>6000</td>
                                <td>审核中</td>
                                <td>2016-3-18 13:25</td>
                                <td>
                                    <div>
                                        <p class="first"><a href="#" class="blue">申请优先处理</a></p>
                                        <p><a href="#" class="blue">查看商品</a></p>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </div>
                    <!--结果表格结束-->
                    <div class="paging-large">
                        <ul>
                            <li class="prev-up"><a href="#">&lt;上一页</a></li>
                            <li class="active"><a href="#">1 </a></li>
                            <li><a href="#">2 </a></li>
                            <li><span>…</span></li>
                            <li><a href="#">38</a></li>
                            <li><a href="#">39</a></li>
                            <li><a href="#">40</a></li>
                            <li><a href="#">41</a></li>
                            <li><a href="#">42</a></li>
                            <li><span>…</span></li>
                            <li class="next-down"><a href="#">下一页&gt;</a></li>
                            <li>共100页</li>
                            <li>
                                <span>到</span>
                                <span><input type="text" class="int-verysmall"></span>
                                <span>页</span>
                                <span class="btn-span"><a class="but-determine">确定</a></span>
                            </li>

                        </ul>
                    </div>

                </div>

            </div>
        </div>
        <!--查询结果结束-->

    </div>
</div>
</body>
<script src="${_slpres }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpres }/scripts/metismenu.js"></script>
<script type="text/javascript">
    window.onload = function () {
        var timer;
        var elem = document.getElementById('elem');
        var elem1 = document.getElementById('elem1');
        var elem2 = document.getElementById('elem2');
        elem2.innerHTML = elem1.innerHTML;
        timer = setInterval(Scroll, 40);
        function Scroll() {
            if (elem.scrollTop >= elem1.offsetHeight) {
                elem.scrollTop -= elem1.offsetHeight;
            } else {
                elem.scrollTop += 1;
            }
        }

        elem.onmouseover = function () {
            clearInterval(timer);
        }
        elem.onmouseout = function () {
            timer = setInterval(Scroll, 40);
        }
    }
</script>
</html>