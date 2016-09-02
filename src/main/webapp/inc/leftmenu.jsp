<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!--左侧菜单-->
    <div id="nav-col">
    <section id="col-left" class="col-left-nano">
        <div id="col-left-inner" class="col-left-nano-content">
            <div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">
                <ul class="nav nav-pills nav-stacked">
                 <li class="active"><a href="#"><i class="fa fa-home"></i><span>系统控制台</span><!--<span class="label label-info label-circle pull-right">28</span>--></a></li>
                 <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="fa fa-paste"></i>
                    <span>资质审核</span>
                    <i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                <!--二级菜单-->    
                    <ul class="submenu">
                        <li><a href="${_base}/qualification/toNoCheckedSupplierPager"  target="mainFrame">供应商待审核</a></li>
                        <li><a href="${_base}/qualification/toCheckedSupplierPager" target="mainFrame">供应商已审核</a></li>
                        <li><a href="${_base}/qualification/toNoCheckedShopPager"  target="mainFrame">入驻商户待审核</a></li>
                        <li><a href="${_base}/qualification/toCheckedShopPager" target="mainFrame">入驻商户已审核</a></li>
                    </ul>
                <!--二级菜单结束-->
                </li>
                 <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="fa fa-paste"></i>
                    <span>合同中心</span>
                    <i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                <!--二级菜单-->    
                    <ul class="submenu">
                        <li><a href="${_base}/contract/contractSupplierPager"  target="mainFrame">供应商合同</a></li>
                        <li><a href="${_base}/contract/contractShopPager" target="mainFrame">店铺合同</a></li>
                    </ul>
                <!--二级菜单结束-->
                </li>
                
                <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="fa fa-usd"></i>
                    <span>结算管理</span><i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                <!--二级菜单-->    
                    <ul class="submenu">
                        <li><a href="${_base}/billing/billingpager" target="mainFrame">保证金/服务费设置</a></li>
                        <li><a href="${_base}/billing/billingCyclePager" target="mainFrame">结算周期设置</a></li>
                        <li><a href="${_base}/defaultManager/defaultManagerPager" target="mainFrame">违约管理</a></li>
                    </ul>
                <!--二级菜单结束-->
                </li>
                
                <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="ui-icon fa fa-sitemap"></i>
                    <span>CRM</span><i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                <!--二级菜单-->    
                    <ul class="submenu">
                        <li><a href="${_base}/score/scorelist" target="mainFrame">供应商评价</a></li>
                        <li><a href="${_base}/crm/supplierStatePager" target="mainFrame">供应商状态管理</a></li>
                        <li><a href="${_base}/rank/rankrule" target="mainFrame">入驻商户评级规则</a></li>
                        <li><a href="${_base}/crm/shopStatePager" target="mainFrame">店铺状态管理</a></li>
                    </ul>
                <!--二级菜单结束-->
                </li>
                
                </ul>
            </div>
        </div>
    </section>
    </div>
    <!--/左侧菜单结束-->