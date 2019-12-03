package com.abbvie.pro.core.servlets;

import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.rmi.ServerException;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Fetch Link Title Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/public/myProject/fetch-link-Title",
        })
public class FetchLinkTitleServlet extends SlingAllMethodsServlet {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PARAM_LINK = "link";
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try{
            String link = request.getParameter(PARAM_LINK);
            if(StringUtils.isNotEmpty(link)){
                ResourceResolver resourceResolver = request.getResourceResolver();
                Resource resource = resourceResolver.getResource(link);
                if(resource != null){
                    Page page = resource.adaptTo(Page.class);
                    response.getWriter().write(page.getTitle());
                }
            }
        }
        catch(Exception e){
            logger.error("unable to fetch link title due to : {}", e.getMessage(), e);
        }
    }
}
