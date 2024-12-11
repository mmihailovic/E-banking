package rs.edu.raf.korisnik.servis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import rs.edu.raf.repository.FavouriteRecipientRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OmiljeniKorisnikServisImplTest {

    @Autowired
    FavouriteRecipientRepository favouriteRecipientRepository;

//    @Test
//    void add() {
//        OmiljeniKorisnikDTO omiljeniKorisnikDTO = OmiljeniKorisnikDTO.builder()
//                .id(null)
//                .idKorisnika(1L)
//                .broj(120L)
//                .sifraPlacanja("123")
//                .nazivPrimaoca("Nikola")
//                .brojRacunaPrimaoca(String.valueOf(18L))
//                .brojRacunaPosiljaoca(String.valueOf(19L))
//                .build();
//        OmiljeniKorisnik addedOmiljeniKorisnik = omiljeniKorisnikRepository.save(OmiljeniKorisnikMapper.toEntity(omiljeniKorisnikDTO));
//        assertThat(addedOmiljeniKorisnik.getNazivPrimaoca()).isEqualTo("Nikola");
//        assertThat(addedOmiljeniKorisnik.getIdKorisnika()).isEqualTo(1L);
//
//    }
//
//    @Test
//    void edit() {
//    }
//
//    @Test
//    void delete() {
//        OmiljeniKorisnikDTO omiljeniKorisnikDTO = OmiljeniKorisnikDTO.builder()
//                .id(null)
//                .idKorisnika(1L)
//                .broj(120L)
//                .sifraPlacanja("123")
//                .nazivPrimaoca("Nikola")
//                .brojRacunaPrimaoca(String.valueOf(18L))
//                .brojRacunaPosiljaoca(String.valueOf(19L))
//                .build();
//        OmiljeniKorisnik addedOmiljeniKorisnik = omiljeniKorisnikRepository.save(OmiljeniKorisnikMapper.toEntity(omiljeniKorisnikDTO));
//
//        omiljeniKorisnikRepository.deleteById(addedOmiljeniKorisnik.getId());
//
//        Optional<OmiljeniKorisnik> optionalOmiljeniKorisnik = omiljeniKorisnikRepository.findById(addedOmiljeniKorisnik.getId());
//
//        assertThat(optionalOmiljeniKorisnik).isEqualTo(Optional.empty());
//    }
//
//    @Test
//    void findById() {
//        OmiljeniKorisnikDTO omiljeniKorisnikDTO = OmiljeniKorisnikDTO.builder()
//                .id(null)
//                .idKorisnika(1L)
//                .broj(120L)
//                .sifraPlacanja("123")
//                .nazivPrimaoca("Nikola")
//                .brojRacunaPrimaoca(String.valueOf(18L))
//                .brojRacunaPosiljaoca(String.valueOf(19L))
//                .build();
//        OmiljeniKorisnik addedOmiljeniKorisnik = omiljeniKorisnikRepository.save(OmiljeniKorisnikMapper.toEntity(omiljeniKorisnikDTO));
//
//        Optional<OmiljeniKorisnik> optionalOmiljeniKorisnik = omiljeniKorisnikRepository.findById(addedOmiljeniKorisnik.getId());
//
//        assertThat(optionalOmiljeniKorisnik).isNotEmpty();
//        assertThat(optionalOmiljeniKorisnik.get().getNazivPrimaoca()).isEqualTo("Nikola");
//    }
}
