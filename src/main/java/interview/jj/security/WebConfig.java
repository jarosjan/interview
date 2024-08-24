package interview.jj.security;

import interview.jj.config.AuthenticatedEmailArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticatedEmailArgumentResolver authenticatedEmailArgumentResolver;

    @Autowired
    public WebConfig(AuthenticatedEmailArgumentResolver authenticatedEmailArgumentResolver) {
        this.authenticatedEmailArgumentResolver = authenticatedEmailArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedEmailArgumentResolver);
    }

}
