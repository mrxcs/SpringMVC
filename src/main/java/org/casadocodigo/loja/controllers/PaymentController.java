package org.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.casadocodigo.loja.models.PaymentData;
import org.casadocodigo.loja.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "checkout", method = RequestMethod.POST, name = "checkout")
	public Callable<String> checkout() {
		return () -> {

			BigDecimal total = shoppingCart.getTotal();

			String uriToPay = "http://book-payment.herokuapp.com/payment";
			try {
				String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
				System.out.println(response);
				return "redirect:/payment/success";
			} catch (HttpClientErrorException exception) {
				return "redirect:/payment/error";
			}
		};

	 }
	
	@RequestMapping(value = "/success", method = RequestMethod.GET, name = "success")
	public ModelAndView success() {
		return new ModelAndView("/payment/success.jsp");
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET, name = "error")
	public ModelAndView error() {
		return new ModelAndView("/payment/failed.jsp");
	}
	
}
