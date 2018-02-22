package quintonic.transformer;

import org.springframework.stereotype.Component;
import quintonic.dto.OfferDTO;
import quintonic.dto.biwenger.OfferRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferTransformer {
    public List<OfferDTO> transformOffersRequestListToOfferListDTO(List<OfferRequestDTO> offers) {
        List<OfferDTO> offersDataList = new ArrayList<>();
        offers.stream().forEach(offerRequestDTO -> {
            offersDataList.add(transformToOfferDTO(offerRequestDTO));
        });
        return offersDataList;
    }

    public OfferDTO transformToOfferDTO(OfferRequestDTO offerRequestDTO) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offerRequestDTO.getId());
        offerDTO.setAmount(offerRequestDTO.getAmount());
        offerDTO.setRequestedPlayers(offerRequestDTO.getRequestedPlayers());
        offerDTO.setFrom(offerRequestDTO.getFrom());
        offerDTO.setTo(offerRequestDTO.getTo());
        return offerDTO;
    }
}
