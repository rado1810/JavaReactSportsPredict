package bg.web.radoweb.model;

import jakarta.persistence.*;

@Entity
@Table(name="predict_maches")
public class PredictMach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String predict;
    @Column(nullable = false)
    private String sport;
    @Column(nullable = false)
    private Double bet;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "potential_win",nullable = false)
    private Double potentialWin;
    public PredictMach(){

    }

    public Long getId() {
        return id;
    }

    public PredictMach setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPredict() {
        return predict;
    }

    public PredictMach setPredict(String predict) {
        this.predict = predict;
        return this;
    }

    public Double getBet() {
        return bet;
    }

    public String getSport() {
        return sport;
    }

    public PredictMach setSport(String sport) {
        this.sport = sport;
        return this;
    }

    public PredictMach setBet(Double bet) {
        this.bet = bet;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public PredictMach setAuthor(User author) {
        this.author = author;
        return this;
    }



    public Double getPotentialWin() {
        return potentialWin;
    }

    public PredictMach setPotentialWin(Double potentialWin) {
        this.potentialWin = potentialWin;
        return this;
    }
}
