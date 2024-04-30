package com.DyVert.DyVert.Card;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author sergioguerra
 */
@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        // You might want to add attributes related to cards here
        return "admin";  // Make sure this template is correctly located as well
    }

    @GetMapping("/admin-accounts")
    public String adminAccounts(Model model) {
        
        // Add attributes to the model if needed
        return "admin-accounts";
    }


    @GetMapping("/all-unreviewed")
    public String getAllUnreviewedCards(Model model) {
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }

    @GetMapping("/{id}")
    public String getSession(@PathVariable("id") long id, Model model) {
        model.addAttribute("card", service.getCard(id));
        return "view-card";
    }

    @PostMapping("/flag")
    public String flagCard(@RequestParam("id") long cardID, Model model) {
        service.flagCard(cardID);
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }

    @PostMapping("/publish")
    public String publishCard(@ModelAttribute Card card) {
        service.saveCard(card);
        return "redirect:/card/create"; // Redirect to the page displaying unreviewed cards
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
    String userID = "102";
    List<Card> userCards = service.getUserCards(userID);
    model.addAttribute("card", new Card()); // Add an empty card object to bind the form data
    model.addAttribute("userCards", userCards); // Add user cards to the model
    return "create"; // Return the create.html template
}
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long cardID, Model model) {
        Card card = service.getCard(cardID);
        model.addAttribute("card", card);
        return "create";
    }
    
    @PostMapping("/edit")
    public String editCard(@ModelAttribute Card card) {
    service.updateCard(card); // Implement this method in CardService to update the card
    return "redirect:/card/create"; // Redirect back to create page after editing
}
    

    @PostMapping("/delete")
    public String deleteCard(@RequestParam("id") long cardID, Model model) {
        service.deleteCard(cardID);
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }

    @PostMapping("/review")
    public String reviewCard(@RequestParam("id") long cardID, Model model) {
        service.reviewCard(cardID);
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }
}
