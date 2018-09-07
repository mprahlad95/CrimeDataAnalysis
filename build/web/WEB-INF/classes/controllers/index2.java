/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * @author Vigneet Sompura
 */
@Controller
public class index2 {
    @RequestMapping("/index2")
    public ModelAndView indexPage(HttpServletRequest request, Model model){
         String message = "HELLO SPRING MVC HOW R U";  
         model.addAttribute("em", message);
        return new ModelAndView("index2");
    }
}

