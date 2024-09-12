package bg.web.radoweb.model.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PredictionMatchDto {

    @NotBlank
    private String predict;
    @NotBlank
    private String sport;
    @NotNull
    @Min(value = 1)
    private Double bet;

    @NotBlank
    private String author;
    @Min(value = 1)
    private Double potentialWin;


    public PredictionMatchDto() {

    }

    public  String getPredict() {
        return predict;
    }

    public PredictionMatchDto setPredict(String predict) {
        this.predict = predict;
        return this;
    }

    public  String getSport() {
        return sport;
    }

    public PredictionMatchDto setSport(String sport) {
        this.sport = sport;
        return this;
    }

    public Double getBet() {
        return bet;
    }

    public PredictionMatchDto setBet( Double bet) {
        this.bet = bet;
        return this;
    }



    public  String getAuthor() {
        return author;
    }

    public PredictionMatchDto setAuthor( String author) {
        this.author = author;
        return this;
    }

    public Double getPotentialWin() {
        return potentialWin;
    }

    public PredictionMatchDto setPotentialWin( Double potentialWin) {
        this.potentialWin = potentialWin;
        return this;
    }
}
