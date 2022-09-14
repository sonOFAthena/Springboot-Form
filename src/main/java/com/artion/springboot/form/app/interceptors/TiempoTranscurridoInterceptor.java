package com.artion.springboot.form.app.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

    //registrar evento en la traza
    private static final Logger LOGGER = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equalsIgnoreCase("post")){
            return true;
        }

        if (handler instanceof HandlerMethod ){
            HandlerMethod metodo = (HandlerMethod) handler;
            LOGGER.info("es un metodo del controlador: " + metodo.getMethod().getName());
        }

        LOGGER.info("TiempoTranscurridoInterceptor: preHandle() entrando ...");
        LOGGER.info("Interceptando: " + handler);

        long tiempoInicio = System.currentTimeMillis();
        request.setAttribute("tiempoInicio", tiempoInicio);
        Random random = new Random();
        Integer demora = random.nextInt(100);
        Thread.sleep(demora);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (request.getMethod().equalsIgnoreCase("post")){
            return;
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        if (handler instanceof HandlerMethod && modelAndView != null){
            modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
        }

        LOGGER.info("TiempoTranscurrido: " + tiempoTranscurrido + " milisegundos");
        LOGGER.info("TiempoTranscurridoInterceptor: postHandle() saliendo ...");
    }
}
