package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.Optional;

@Component
public class EngineGlobalScore {
    public static PlayerDataDTO setPlayerFinalScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO,playerDataScored);
        double finalScore = (Optional.ofNullable(playerDataScored.getAverageFitnessScore()).orElse(0.0) +
                Optional.ofNullable(playerDataScored.getAveragePriceScore()).orElse(0.0) +
                Optional.ofNullable(playerDataScored.getPriceIndicatorScore()).orElse(0.0) +
                Optional.ofNullable(playerDataScored.getMatchesPlayedScore()).orElse(0.0)) / 4;
        playerDataScored.setScore(finalScore);
        return playerDataScored;
    }

    public static PlayerDataDTO setBuyRecommendedAction(PlayerDataDTO playerDataDTO) {
        if (playerDataDTO == null) return playerDataDTO;
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO,playerDataEvaluated);
        if ("injured".equals(playerDataEvaluated.getFitness().get(0))) {
            playerDataEvaluated.setRecommendedAction("No comprar, lesionado");
        } else if (playerDataDTO.getScore() < 0.25){
            playerDataEvaluated.setRecommendedAction("¡No comprar!");
        } else if (playerDataDTO.getScore() <= 0.5){
            playerDataEvaluated.setRecommendedAction("No comprar");
        } else if (playerDataDTO.getScore() <= 0.75){
            playerDataEvaluated.setRecommendedAction("Evaluar");
        } else if (playerDataDTO.getScore() > 0.75){
            playerDataEvaluated.setRecommendedAction("Comprar");
        }
        return playerDataEvaluated;
    }

    public static PlayerDataDTO setSellRecommendedAction(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataEvaluated);
        if ("injured".equals(playerDataDTO.getFitness().get(0))) {
            playerDataEvaluated.setRecommendedAction("Lesionado");
        } else if (playerDataDTO.getScore() < 0.25){
            playerDataEvaluated.setRecommendedAction("No alinear. Vender jugador.");
        } else if (playerDataDTO.getScore() < 0.5){
            playerDataEvaluated.setRecommendedAction("Vender jugador.");
        } else if (playerDataDTO.getScore() <= 0.75){
            playerDataEvaluated.setRecommendedAction("Alinear. Evaluar posible venta.");
        } else if (playerDataDTO.getScore() > 0.75){
            playerDataEvaluated.setRecommendedAction("Alinear y mantener.");
        }
        return playerDataEvaluated;
    }

    public static PlayerDataDTO setSellRecommendedActionDetails(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataEvaluated);
        StringBuffer reccommendedActionDetails = new StringBuffer();

        if (playerDataDTO.getPriceIndicatorScore()==1) {
            reccommendedActionDetails.append("El precio del jugador está subiendo. ");
        } else {
            reccommendedActionDetails.append("El precion del jugador está bajando. ");
        }
        if (playerDataDTO.getAveragePriceScore()==1) {
            reccommendedActionDetails.append("Jugador caro, el precion del jugador es más alto que el de la media por puntos en su posición. ");
        } else {
            reccommendedActionDetails.append("Jugador barato, el precio del jugador es más bajo que el de la media por puntos en su posición.");
        }
        if (playerDataDTO.getMatchesPlayedScore()==1) {
            reccommendedActionDetails.append("Juega todo.");
        }else if (playerDataDTO.getMatchesPlayedScore()>=0.75) {
            reccommendedActionDetails.append("Juega casi todo.");
        }else if (playerDataDTO.getMatchesPlayedScore()>=0.5) {
            reccommendedActionDetails.append("No juega mucho, evaluar su posible alineación. ");
        }else if (playerDataDTO.getMatchesPlayedScore()<0.5) {
            reccommendedActionDetails.append("No juega suficiente, considera su venta. ");
        }
        if (playerDataDTO.getAverageFitnessScore()==1) {
            reccommendedActionDetails.append("El jugador está en un buen momento de forma. Mantener en alineación. ");
        } else {
            reccommendedActionDetails.append("No está en su mejor momento. ");
        }
        playerDataEvaluated.setRecommendedActionDetails(reccommendedActionDetails.toString());
        return playerDataEvaluated;
    }

    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            //not int
            return false;
        }
    }
}
