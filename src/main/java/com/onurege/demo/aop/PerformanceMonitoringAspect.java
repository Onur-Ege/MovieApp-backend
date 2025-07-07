package com.onurege.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);

    @Around(""" 
            (execution(* com.onurege.demo.Service.RecommendationService.*(..)) || 
            execution(* com.onurege.demo.Service.MovieService.*(..)) || 
            execution(* com.onurege.demo.Service.TmdbService.*(..)) || 
            execution(* com.onurege.demo.Service.UserService.*(..)) 
            ) &&
            !execution(* com.onurege.demo.Service.TmdbService.getMovieByImdbId(..))
    """)
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {

        long start = System.currentTimeMillis();
        Object obj = jp.proceed();
        long end = System.currentTimeMillis();

        LOGGER.info("Time taken by {}: {} ms",
                    jp.getSignature().getName(),
                    (end-start));

        return obj;
    }
}
