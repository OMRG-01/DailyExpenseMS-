package com.daily.expense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Auth {
	
	@GetMapping("/")
    public String loginPage() {
        return "login";
    }

}
