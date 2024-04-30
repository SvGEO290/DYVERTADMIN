package com.DyVert.DyVert.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/user")
public class UserController {

    @SuppressWarnings("unused")
    @Autowired
    private UserService userService;


    // This method will just return the admin-accounts.html page
    @GetMapping("/admin-accounts")
    public String adminAccountsPage(Model model) {
        model.addAttribute("accounts", userService.getAllUsers());
        return "admin-accounts"; // This should be the name of your HTML file within src/main/resources/templates
    }


    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") String accountID, Model model) {
        userService.deleteUser(accountID);
        model.addAttribute("accounts", userService.getAllUsers());
        return "admin-accounts";
    }
    


}
