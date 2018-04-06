package org.casadocodigo.loja.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.casadocodigo.loja.daos.ProductDAO;
import org.casadocodigo.loja.models.BookType;
import org.casadocodigo.loja.models.Product;
import org.casadocodigo.loja.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ShoppingCart shoppingCart;

	/*
	 * Ativação do Validador customizado Hibernate Validator
	 * 
	 * @InitBinder protected void initBinder(WebDataBinder binder) {
	 * binder.setValidator(new ProductValidator()); }
	 */

	@RequestMapping(method = RequestMethod.POST, name = "saveProduct")
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		fileValidation(summary, bindingResult, product);
		if (bindingResult.hasErrors()) {
			return form(product);
		}

		if (!(summary.isEmpty())) {
			System.out.println("Indevidamente");
			String webPath = write("uploaded-images", summary);
			product.setSummaryPath(webPath);
		}
		productDAO.save(product);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		return new ModelAndView("redirect:/produtos");
	}

	@RequestMapping("/form")
	public ModelAndView form(Product product) {
		ModelAndView modelAndView = new ModelAndView("products/form.jsp");
		modelAndView.addObject("types", BookType.values());
		modelAndView.addObject("product", product);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("products/list.jsp");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET,  value = "/catalogo", name = "catalago")
	@Cacheable(value="books")
	public ModelAndView clientList() {
		ModelAndView modelAndView = new ModelAndView("products/client_list.jsp");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/show/{id}", name = "show")
	public ModelAndView show(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("products/show.jsp");
		Product product = productDAO.find(id);
		modelAndView.addObject("product", product);
		modelAndView.addObject("shoppingCart", shoppingCart);
		return modelAndView;
	}

	@RequestMapping(value = "/file", name = "file", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getfile(@RequestParam String caminho) throws IOException {
		System.out.println("Método getfile Chamado");
		String realPath = request.getServletContext().getRealPath("/" + caminho);
		FileInputStream file = new FileInputStream(realPath);
		System.out.println(realPath);
		return IOUtils.toByteArray(file);
	}

	@RequestMapping(value = "/edit", name = "edit")
	public ModelAndView edit(Integer productID) {
		Product p = productDAO.find(productID);
		return form(p);
	}
	
	@RequestMapping(value = "/del", name = "del")
	public ModelAndView del(Integer productID, RedirectAttributes redirectAttributes) {
		productDAO.delete(productID);
		redirectAttributes.addFlashAttribute("sucesso", "Produto apagado com sucesso");
		return new ModelAndView("redirect:/produtos");
	}
	
	/* Pois estamos usando um Content Negotiation*/
	/*@RequestMapping(method = RequestMethod.GET,value="json")
	@ResponseBody
	public List<Product> listJson() {
	return productDAO.list();
	}*/

	private String write(String baseFolder, MultipartFile file) {
		System.out.println("Método write Chamado");

		String realPath = request.getServletContext().getRealPath("/" + baseFolder);
		System.out.println(realPath);

		File directory = new File(realPath);
		if (!directory.exists()) {
			directory.mkdir();
			// If you require it to make the entire directory path including parents,
			// use directory.mkdirs(); here instead.
		}

		try {
			String path = realPath + "/" + file.getOriginalFilename();
			System.out.println(path);
			file.transferTo(new File(path));
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void fileValidation(MultipartFile file, BindingResult errors, Product product) {
		System.out.println("Método fileValidation Chamado");

		if (file.isEmpty() && product.getId() != null) {
			return;
		}
		if (file.isEmpty()) {
			errors.rejectValue("summaryPath", "field.required.summary");
			return;
		}
		if (!(file.getContentType().toLowerCase().equals("image/png"))) {
			errors.rejectValue("summaryPath", "typeMismatch.summary");
		}
	}
}
