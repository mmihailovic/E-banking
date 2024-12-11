package rs.edu.raf.repository;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.edu.raf.model.ListingOwner;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private ListingOwnerRepository listingOwnerRepository;

    @Override
    public void run(String... args) throws Exception {
        ListingOwner listingOwner = new ListingOwner();
        listingOwner.setOwner(1L);
        listingOwner.setQuantity(100);
        listingOwner.setTicker("IBM");

        listingOwnerRepository.save(listingOwner);
    }
}
