package com.horyu1234.kkutuweb;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by horyu on 2018-01-15
 */
public class MinifyFilter implements Filter {
    private HtmlCompressor htmlCompressor;

    @Override
    public void init(FilterConfig filterConfig) {
        htmlCompressor = new HtmlCompressor();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        String servletResponse = responseWrapper.toString();
        if (!response.isCommitted()) {
            response.getWriter().write(htmlCompressor.compress(servletResponse));
        }
    }

    @Override
    public void destroy() {
        // nothing
    }
}
