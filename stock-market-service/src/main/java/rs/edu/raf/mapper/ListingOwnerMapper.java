package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.ListingOwnerDTO;
import rs.edu.raf.model.ListingOwner;

@Component
public class ListingOwnerMapper {

    public ListingOwnerDTO listingOwnerToListingOwnerDTO(ListingOwner listingOwner) {
        return new ListingOwnerDTO(listingOwner.getId(), listingOwner.getOwner(), listingOwner.getTicker(),
                listingOwner.getQuantity());
    }
}
