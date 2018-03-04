package org.casadocodigo.loja.controllers;

import javax.transaction.Transactional;

import org.casadocodigo.loja.daos.ProductDAO;
import org.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class ProductsController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping("/produtos")
	public String save(Product product){
	System.out.println("Cadastrando o produto");
	productDAO.save(product);
	return "products/ok.jsp";
	}
	
	@RequestMapping("/produtos/form")
	public String form(){
	return "products/form.jsp";
	}

}
