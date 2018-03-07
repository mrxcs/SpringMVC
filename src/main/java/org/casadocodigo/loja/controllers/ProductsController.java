package org.casadocodigo.loja.controllers;

import javax.transaction.Transactional;

import org.casadocodigo.loja.daos.ProductDAO;
import org.casadocodigo.loja.models.BookType;
import org.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Product product, RedirectAttributes redirectAttributes) {
		System.out.println("Cadastrando o produto");
		productDAO.save(product);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		return "redirect:produtos";
	}
	
	/*@RequestMapping("/produtos/form")
	public String form(){
	return "products/form.jsp";
	}*/
	
	@RequestMapping("/form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView("products/form.jsp");
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("products/list.jsp");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
}
