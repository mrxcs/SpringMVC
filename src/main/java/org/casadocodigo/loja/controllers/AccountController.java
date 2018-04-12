package org.casadocodigo.loja.controllers;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.casadocodigo.loja.CustomEditor.RoleEditor;
import org.casadocodigo.loja.daos.RoleDAO;
import org.casadocodigo.loja.daos.UserDAO;
import org.casadocodigo.loja.models.Role;
import org.casadocodigo.loja.models.User;
import org.casadocodigo.loja.validation.EditAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		try {
			userDAO.novo(user);
		} catch (Exception e){
			bindingResult.rejectValue("login", "already.exists");
			return adminForm(user);
		}
			redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso");
			return new ModelAndView("redirect:admin");
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
		    
		try {
			userDAO.novo(user);
		} catch (Exception e) {
			bindingResult.rejectValue("login", "already.exists");
			return userForm(user);
		}
			redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso");
			return new ModelAndView("redirect:user");
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
			return new ModelAndView("redirect:role");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET, name = "editForm")
	public ModelAndView editForm(User user) {
		ModelAndView modelAndView = new ModelAndView("account/Edit.jsp");
		String username = getUserName();
		User editUser = userDAO.loadUserByUsername(username);
		modelAndView.addObject("rolesList", roleDAO.getRoleList());
		if(user.getLogin() == null) {
			modelAndView.addObject("user", editUser);
		}
		else {
			modelAndView.addObject("user", user);
		}
		return modelAndView;		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, name = "registerEdit")
	public ModelAndView registerEdit(@Valid User user, BindingResult bindingResult,
RedirectAttributes redirectAttributes) {
		
		String username = getUserName();
		user.setLogin(username);
		User mergeUser = new EditAccountValidator(user, bindingResult, userDAO).getOutput();
		
		try {
			userDAO.update(mergeUser);		
		} catch (Exception e){
			redirectAttributes.addFlashAttribute("sucesso", "Um erro ocorreu");
			return new ModelAndView("redirect:edit");
		}
		return editForm(user);
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.GET, name = "passwordForm")
	public ModelAndView passwordForm(User user) {
		ModelAndView modelAndView = new ModelAndView("account/EditPassword.jsp");
		modelAndView.addObject("user", user);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.POST, name = "registerPassword")
	public ModelAndView registerPassword(@Valid User user, BindingResult bindingResult,
RedirectAttributes redirectAttributes, @RequestParam("old_password") String[] n) {
		
		if(bindingResult.hasFieldErrors("password")) {
			return passwordForm(user);
		}
		
		String username = getUserName();
			
		if (!(userDAO.isPasswordMatches(n[1], username))) {
			redirectAttributes.addFlashAttribute("sucesso", "Senha incorreta");
			return new ModelAndView("redirect:password");
		}
					
	    user.setLogin(username);
		User mergeUser = new EditAccountValidator(user, bindingResult, userDAO).getOutput();
		
		try {
			userDAO.update(mergeUser);		
		} catch (Exception e){
			redirectAttributes.addFlashAttribute("sucesso", "Um erro ocorreu");
			return new ModelAndView("redirect:password");
		}
		redirectAttributes.addFlashAttribute("sucesso", "Senha alterada com sucesso");
		return new ModelAndView("redirect:password");
	}
	
	private void addUserAcess(User user) {
		ArrayList<Role> access = new ArrayList<>();
		Role comum = new Role();
		comum.setName("ROLE_Comprador");
		access.add(comum);
		user.setRoles(access);
	}
	
	private String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String username;

		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		return username;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new RoleEditor());
    }
	
}
