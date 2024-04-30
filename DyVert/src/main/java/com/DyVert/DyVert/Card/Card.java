package com.DyVert.DyVert.Card;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

/**
 *
 * @author sergioguerra
 */
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardID;
    private String userID;
    private String title;
    private boolean reviewed;
    private String synopsis;
    private boolean flagged;
    private boolean finished;
    private String genres;
    private String imagePath;
    private int reviews;
    private String type;
    
    public Card(long cardID, String userID, String title, String synopsis, String genres, String imagePath, int reviews, String type) {
        this.cardID = cardID;
        this.userID = userID;
        this.title = title;
        this.synopsis = synopsis;
        this.genres = genres;
        this.imagePath = imagePath;
        this.reviewed = false;
        this.flagged = false;
        this.reviews = reviews;
        this.type = type;
    }
}
