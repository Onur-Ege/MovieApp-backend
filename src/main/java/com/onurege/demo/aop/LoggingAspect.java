package com.onurege.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(""" 
            (execution(* com.onurege.demo.Service.RecommendationService.*(..)) || 
            execution(* com.onurege.demo.Service.MovieService.*(..)) || 
            execution(* com.onurege.demo.Service.TmdbService.*(..)) || 
            execution(* com.onurege.demo.Service.UserService.*(..)) 
            ) &&
            !execution(* com.onurege.demo.Service.TmdbService.getMovieByImdbId(..))
    """)
    public void logMethodCall(JoinPoint jp){
        LOGGER.info("Method called: " + jp.getSignature().getName());
    }

    @AfterReturning(""" 
            (execution(* com.onurege.demo.Service.RecommendationService.*(..)) || 
            execution(* com.onurege.demo.Service.MovieService.*(..)) || 
            execution(* com.onurege.demo.Service.TmdbService.*(..)) || 
            execution(* com.onurege.demo.Service.UserService.*(..)) 
            ) &&
            !execution(* com.onurege.demo.Service.TmdbService.getMovieByImdbId(..))
    """)
    public void logMethodSuccess(JoinPoint jp){
        LOGGER.info("Method executed successfully: " + jp.getSignature().getName());
    }

    @AfterThrowing(""" 
            (execution(* com.onurege.demo.Service.RecommendationService.*(..)) || 
            execution(* com.onurege.demo.Service.MovieService.*(..)) || 
            execution(* com.onurege.demo.Service.TmdbService.*(..)) || 
            execution(* com.onurege.demo.Service.UserService.*(..)) 
            ) &&
            !execution(* com.onurege.demo.Service.TmdbService.getMovieByImdbId(..))
    """)
    public void logMethodError(JoinPoint jp){
        LOGGER.info("Method has thrown exception: " + jp.getSignature().getName());
    }
}
