package com.ai.ch.user.web.sitemesh;

import java.io.IOException;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.content.Content;
import org.sitemesh.webapp.WebAppContext;

/**
 * 默认的装饰器，按照租户匹配框架页面模板<br>
 * Date: 2016年8月9日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author gucl
 */
public class ParamDecoratorSelector implements DecoratorSelector<WebAppContext> {
    private DecoratorSelector<WebAppContext> defaultDecoratorSelector;

    private SiteMeshFilterBuilder builder;

    public ParamDecoratorSelector(SiteMeshFilterBuilder builder) {
        this.builder = builder;
        this.defaultDecoratorSelector = builder.getDecoratorSelector();
    }

    @Override
    public String[] selectDecoratorPaths(Content content, WebAppContext context) throws IOException {
        /*
        HttpServletRequest request = context.getRequest();
        if (StringUtils.isNotBlank(tenantId)) {
            GnTenantVo tenant = TenantUtil.getGnTenant(tenantId);
            String decoratorPath = tenant.getFramePageTemplate();
            builder.addDecoratorPath("/*", decoratorPath);
        } else {
            builder.addDecoratorPath("/*", null);
        }*/
        builder.addDecoratorPath("/*", null);
        return defaultDecoratorSelector.selectDecoratorPaths(content, context);
    }

}
