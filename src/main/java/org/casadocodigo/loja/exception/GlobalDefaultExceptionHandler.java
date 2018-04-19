package org.casadocodigo.loja.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Controller
public class GlobalDefaultExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerNoHandlerFoundException() {
		ModelAndView mv = new ModelAndView("httpErrors/CustomModel.jsp");
		mv.addObject("errorTitle", "Ops. Página não encontrada");
		mv.addObject("errorDescription", "A página que procura não existe ou não está disponível.");
		mv.addObject("title", "Erro 404");
		return mv;
	}
	
	@RequestMapping("/403")
	public ModelAndView acessDenied(){
		ModelAndView mv = new ModelAndView("httpErrors/CustomModel.jsp");
		mv.addObject("errorTitle", "Ops. Acesso restrito");
		mv.addObject("errorDescription", "Você não tem autorização para acessar está página.");
		mv.addObject("title", "Erro 403");
		return mv;
	}
	

}
