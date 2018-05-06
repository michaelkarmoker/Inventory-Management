package com.example.inventory3.inventory3;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Product;
import com.servicecontact.IProductService;
@RequestMapping("/product")
@Scope("request")

@Controller
public class ProductController {
	@Autowired
	IProductService productApp;
	
	
	@RequestMapping("/")
	public String index(Model model) {
		try{
			List<Product> products=productApp.Get();
		//Product product=productApp.Get(1);
			
	    model.addAttribute("obj",products);
		}catch (HibernateException e) {
			return "product/product";
		}
		return "product/product";
		
	}
//	@RequestMapping("/save")
//	public String Save() {
//		Customer customer = new Customer();
//		customer.setAddress("A1");
//		customer.setName("N1");
//		customerApp.Save(customer);
//		return "index";
//	}
	@RequestMapping("/add")
	public String add (Model model) {
		Product product = new Product();
		model.addAttribute("obj",product);
		return "add";
		
	}
	@RequestMapping(value = "/ioi", method = RequestMethod.POST)
	public  String aa( @ModelAttribute("obj")Product product, 
			   Model model) {
		
		boolean issave=productApp.Save(product);
		if(issave) {
	  
	    return "redirect:/product/";
		}
		else 
		    return "customer/eror";
		//System.out.println(firstname);
	
		
	}
	@RequestMapping("/edit")
	public String findone(Integer id,Model model) {
		Product product=new Product();
		product=productApp.Get(id);
		model.addAttribute("obj",product);
		return "product/edit";
	}
	@RequestMapping(value = "edit/up", method = RequestMethod.POST)
	public  String update( @ModelAttribute("obj")Product product, 
			   Model model) {
		
		productApp.Update(product);
		
	    return "redirect:/product/";
		}
	
	@RequestMapping("/delete")
	public String delete(Integer id,Model model) {
		productApp.Delete(id);
		return "redirect:/product/";
	}
	@RequestMapping(
			  value = "/test", 
			  method = RequestMethod.GET, 
			  produces = "application/json"
			)
			@ResponseBody
			public String getFoosAsJsonFromREST() {
			    return "Get some Foos with Header New";
			}
}
