package org.casadocodigo.loja.controllers;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.casadocodigo.loja.CustomEditor.RoleEditor;
import org.casadocodigo.loja.daos.RoleDAO;
import org.casadocodigo.loja.daos.UserDAO;
import org.casadocodigo.loja.models.Role;
import org.casadocodigo.loja.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET, name = "adminForm")
	public ModelAndView adminForm(User user) {
		ModelAndView modelAndView = new ModelAndView("account/CadastroAdmin.jsp");
		modelAndView.addObject("rolesList", roleDAO.getRoleList());
		modelAndView.addObject("user", user);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.POST, name = "registerAdmin")
	public ModelAndView registerAdmin(@Valid User user, BindingResult bindingResult,
RedirectAttributes redirectAttributes) {
		
		if (user.getRoles() == null) {
			bindingResult.rejectValue("roles", "at.least.one");
		}	
		
		if (bindingResult.hasErrors()) {
			System.out.println("Cadastro Admin contém erros");
			return adminForm(user);
		}
		
			userDAO.save(user);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso");
			return new ModelAndView("redirect:/admin");
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET, name = "userForm")
	public ModelAndView userForm(User user) {
		ModelAndView modelAndView = new ModelAndView("account/CadastroUser.jsp");
		modelAndView.addObject("user", user);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST, name = "registerUser")
	public ModelAndView registerUser(@Valid User user, BindingResult bindingResult,
RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return userForm(user);
		}
		    addUserAcess(user);
			userDAO.save(user);
			redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso");
			return new ModelAndView("redirect:/user");
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.GET, name = "roleForm")
	public ModelAndView roleForm(Role role) {
		ModelAndView modelAndView = new ModelAndView("account/CadastroRole.jsp");
		modelAndView.addObject("role", role);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.POST, name = "registerRole")
	public ModelAndView registerRole(Role role, BindingResult bindingResult,
RedirectAttributes redirectAttributes) {
		
		try {
			roleDAO.save(role);
		} catch(Throwable e) {
			bindingResult.rejectValue("name", "invalid.format");
		}
		
		if (bindingResult.hasErrors()) {
			return roleForm(role);
		}
		
			redirectAttributes.addFlashAttribute("sucesso", "Role cadastrado com sucesso");
			return new ModelAndView("redirect:/role");
	}
	
	private void addUserAcess(User user) {
		ArrayList<Role> access = new ArrayList<>();
		Role comum = new Role();
		comum.setName("ROLE_Comprador");
		access.add(comum);
		user.setRoles(access);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RoleEditor());
    }
	
}
