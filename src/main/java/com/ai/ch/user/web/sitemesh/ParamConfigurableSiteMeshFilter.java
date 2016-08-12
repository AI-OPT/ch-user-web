package com.ai.ch.user.web.sitemesh;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class ParamConfigurableSiteMeshFilter extends ConfigurableSiteMeshFilter {
	private String[] ignore_suffix = {};
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
        String ignore_res = filterConfig.getInitParameter("ignore_suffix");
        if (!"".equals(ignore_res)&&null!=ignore_res) {
            this.ignore_suffix = ignore_res.split(",");
        }
    }
	
	private boolean shouldFilter(HttpServletRequest req) {
        if (ignore_suffix != null && ignore_suffix.length > 0) {
            String uri = req.getRequestURI().toLowerCase();
            for (String suffix : ignore_suffix) {
                if (uri.endsWith(suffix)) {
                    return false;
                }
            }
        }
        return true;
    }
	
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		
        //赋给自定义装饰选择器，则自定义规则未匹配时调用默认选择器获取
        builder.setCustomDecoratorSelector(new ParamDecoratorSelector(builder));
    }

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		if (!shouldFilter(request)){
			filterChain.doFilter(servletRequest, servletResponse);
		}
		else{
			super.doFilter(servletRequest, servletResponse, filterChain);
		}
		
	}
	
	
}
