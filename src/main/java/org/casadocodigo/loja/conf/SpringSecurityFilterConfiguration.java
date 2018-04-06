package org.casadocodigo.loja.conf;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer{
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
	      FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
	      encodingFilter.setInitParameter("encoding", "UTF-8");
	      encodingFilter.setInitParameter("forceEncoding", "true");
	      encodingFilter.addMappingForUrlPatterns(null, false, "/*");
	}
	
}
