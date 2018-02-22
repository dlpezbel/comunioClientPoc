package quintonic.service.helper;

import org.springframework.stereotype.Component;
import quintonic.dto.BonusDTO;
import quintonic.dto.UserPlayersDTO;
import quintonic.dto.biwenger.NewsRequestDTO;
import quintonic.dto.biwenger.TransferMarketDataRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransferHelper {

    public Map<String,Integer> processTransfers(List<TransferMarketDataRequestDTO> transferMarketDataRequestDTOList, BonusDTO initialBonus) {
        Map<String,Integer> userMoneyMap = new HashMap();
        transferMarketDataRequestDTOList.
                stream().
                forEach(transferMarketDataRequestDTO -> {
                    transferMarketDataRequestDTO.getContent().stream().forEach(transferRequestDTO -> {
                    if (transferRequestDTO.getFrom()!=null) {
                        UserPlayersDTO user = transferRequestDTO.getFrom();
                        Integer newValue = 0;
                        if (userMoneyMap.containsKey(user.getName())) {
                            newValue = userMoneyMap.get(user.getName()) + transferRequestDTO.getAmount();
                        } else {
                            newValue = initialBonus.getBonus() + transferRequestDTO.getAmount();
                        }
                        userMoneyMap.put(user.getName(), newValue);
                    }
                    if (transferRequestDTO.getTo()!=null) {
                        UserPlayersDTO user = transferRequestDTO.getTo();
                        Integer newValue = 0;
                        if (userMoneyMap.containsKey(user.getName())) {
                            newValue = userMoneyMap.get(user.getName()) - transferRequestDTO.getAmount();
                        } else {
                            newValue = initialBonus.getBonus() - transferRequestDTO.getAmount();
                        }
                        userMoneyMap.put(user.getName(), newValue);
                    }
                });
            });
        return userMoneyMap;
    }

    public void addBonusByUser(NewsRequestDTO newsRequest, Map<String, Integer> moneyByUser, BonusDTO initialBonus) {
        newsRequest.getData().stream().forEach(roundRequestDTO -> {
            roundRequestDTO.getContent().getResults().stream().forEach(roundBonusRequestDTO -> {
                UserPlayersDTO userPlayersDTO = roundBonusRequestDTO.getUser();
                Integer bonus = roundBonusRequestDTO.getBonus();
                if (moneyByUser.get(userPlayersDTO.getName())!=null) {
                    Integer newMoney = moneyByUser.get(userPlayersDTO.getName()) + bonus;
                    moneyByUser.put(userPlayersDTO.getName(), newMoney);
                }
                else {
                    Integer newMoney = initialBonus.getBonus() + bonus;
                    moneyByUser.put(userPlayersDTO.getName(), newMoney);
                }
            });
        });
    }
}
