package com.DyVert.DyVert.Card;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sergioguerra
 */
@Service
public class CardService {
    @Autowired
    CardRepository repo;
    
    public List<Card> getUnreviewedCards() {
        return repo.findUnreviewed();
    }
    
    /**
     * Find one session by ID.
     * @param id
     * @return the user
     */
    public Card getCard(long id) {
        return repo.getCardById(id);
    }
    
    public void flagCard(long cardID) {
        repo.flagCard(cardID);
    }
    
    public void saveCard(Card card) {
        repo.saveCard(card);
    }
    
    public void deleteCard(long cardID) {
        repo.deleteCard(cardID);
    }
    
    public void reviewCard(long cardID) {
        repo.reviewCard(cardID);
    }

     
    public List<Card> getAllReviewedCards() {
        return repo.getAllReviewedCards();
    }
    
    public Card getUnseenCard(String accountID) {
        return repo.getUnseenCard(accountID);
    }
    
    public List<Card> getBucketList(String accountID) {
        return repo.getBucketList(accountID);
    }
    
    public List<Card> getTypeBucketList(String accountID, String type) {
        return repo.getTypeBucketList(accountID, type);
    }
    
    public List<Card> getUserCards(String userID) {
        return repo.getUserCards(userID);
    }
    
    public void updateCard(Card card) {
        repo.updateCard(card);
    }  
}
