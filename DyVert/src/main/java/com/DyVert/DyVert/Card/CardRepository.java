package com.DyVert.DyVert.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sergioguerra
 */
@Repository
public class CardRepository {
    @Autowired
    NamedParameterJdbcTemplate template;
    
    
    List<Card> findUnreviewed() {
        String query = "select cardID, userID, title, synopsis, genres, imagepath, reviews, type from card_data WHERE reviewed = 0";
        return template.query(query,
                (result, rowNum)
                -> new Card(
                        result.getLong("cardID"),
                        result.getString("userID"),
                        result.getString("title"),
                        result.getString("synopsis"),
                        result.getString("genres"),
                        result.getString("imagepath"),
                        result.getInt("reviews"),
                        result.getString("type")));
    }
    
    public Card getCardById(long cardID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
                "cardID", cardID);
        String query = "select * from card_data where cardID = :cardID ";
        return template.queryForObject(query, namedParameters,
                BeanPropertyRowMapper.newInstance(Card.class));
    }
    
    public void flagCard(long cardID) {        
        String query = "UPDATE card_data SET flagged = 1, reviewed =1 WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
            
    
    public void saveCard(Card card) {
    // Hard-coded values
    card.setUserID("103");
    card.setType("Tv Show");
    card.setReviews(0);
    card.setReviewed(false);
    card.setFlagged(false);
    card.setFinished(true);

    // Save the card
    String query = "INSERT INTO card_data (title, synopsis, genres, imagepath, userID, type, reviews, reviewed, flagged, finished) " +
                   "VALUES (:title, :synopsis, :genres, :imagepath, :userID, :type, :reviews, :reviewed, :flagged, :finished)";
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("title", card.getTitle());
    parameters.addValue("synopsis", card.getSynopsis());
    parameters.addValue("genres", card.getGenres());
    parameters.addValue("imagepath", card.getImagePath());
    parameters.addValue("userID", card.getUserID()); // Set user ID
    parameters.addValue("type", card.getType()); // Set type
    parameters.addValue("reviews", card.getReviews()); // Set reviews
    parameters.addValue("reviewed", card.isReviewed()); // Set reviewed
    parameters.addValue("flagged", card.isFlagged()); // Set flagged
    parameters.addValue("finished", card.isFinished()); // Set finished

    template.update(query, parameters);
}
    
     //DELETE CARDS
    public void deleteCard(long cardID) {
        String query = "DELETE FROM card_data WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void updateCard(Card card) {
        String query = "UPDATE card_data SET title = :title, synopsis = :synopsis, genres = :genres, imagePath = :imagePath WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", card.getTitle());
        parameters.addValue("synopsis", card.getSynopsis());
        parameters.addValue("genres", card.getGenres());
        parameters.addValue("imagePath", card.getImagePath());
        parameters.addValue("cardID", card.getCardID());
        template.update(query, parameters);
    }

    public void reviewCard(long cardID) {
        String query = "UPDATE card_data SET reviewed = 1 WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
     
    public List<Card> getAllReviewedCards() {
        String query = "select cardID, userID, title, synopsis, genres, imagepath, reviews, type from card_data WHERE reviewed = 1";
        return template.query(query,
                (result, rowNum)
                -> new Card(
                        result.getLong("cardID"),
                        result.getString("userID"),
                        result.getString("title"),
                        result.getString("synopsis"),
                        result.getString("genres"),
                        result.getString("imagepath"),
                        result.getInt("reviews"),
                        result.getString("type")));
    }
    
    public Card getUnseenCard(String accountID) {
        String query = "SELECT count(*) FROM card_data WHERE reviewed = 1";
        int cardCount = template.queryForObject(query, new HashMap<>(), Integer.class);
        List<Card> reviewedCards = getAllReviewedCards();
        
        if (reviewedCards.isEmpty()) {
            return null; // Return null if there are no reviewed cards
        }
        
        int randomIndex = 0;
        Random random = new Random();
        boolean seen = true;
        
        while (true) {
            randomIndex = random.nextInt(cardCount);
            Card randomCard = reviewedCards.get(randomIndex);
            long cardID = randomCard.getCardID();
            String newQuery = "SELECT count(*) FROM seen WHERE contentID = :cardID AND accountID = :accountID";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("cardID", cardID);
            parameters.addValue("accountID", accountID);
            int count = template.queryForObject(newQuery, parameters, Integer.class);
            
            if (count == 0) {
                return randomCard;
            }
        }        
    }
    
    public List<Card> getBucketList(String accountID) {
        String query = "SELECT contentID FROM bucket WHERE accountID = :accountID";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
    
        List<Long> contentIDs = template.queryForList(query, parameters, Long.class);
        List<Card> cardList = new ArrayList<>();
    
        for (Long contentID : contentIDs) {
            Card card = getCardById(contentID);
            if (card != null) {
                cardList.add(card);
            }
        }
    
        return cardList;
    }
    
    public List<Card> getTypeBucketList(String accountID, String type) {
        String query = "SELECT contentID FROM bucket WHERE accountID = :accountID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
    
        List<Long> contentIDs = template.queryForList(query, parameters, Long.class);
        List<Card> cardList = new ArrayList<>();
    
        for (Long contentID : contentIDs) {
            Card card = getCardById(contentID);
            if (card != null && type.equalsIgnoreCase(card.getType())) {
                cardList.add(card);
            }
        }
    
        return cardList;
    }
    
    public List<Card> getUserCards(String userID) {
    String query = "SELECT * FROM card_data WHERE userID = :userID";
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("userID", userID);
    
    return template.query(query, parameters,
            (result, rowNum) -> new Card(
                    result.getLong("cardID"),
                    result.getString("userID"),
                    result.getString("title"),
                    result.getString("synopsis"),
                    result.getString("genres"),
                    result.getString("imagepath"),
                    result.getInt("reviews"),
                    result.getString("type")));
}
    
}
