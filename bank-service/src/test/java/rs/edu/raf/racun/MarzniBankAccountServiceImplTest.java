//package rs.edu.raf.racun;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import rs.edu.raf.dto.MarzniRacunCreateDTO;
//import rs.edu.raf.dto.MarzniRacunUpdateDTO;
//import rs.edu.raf.model.accounts.MarginBankAccount;
//import rs.edu.raf.repository.MarzniRacunRepository;
//import rs.edu.raf.service.impl.MarzniRacunServiceImpl;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class MarzniBankAccountServiceImplTest {
//
//    @InjectMocks
//    private MarzniRacunServiceImpl marzniRacunService;
//
//    @Mock
//    private MarzniRacunRepository marzniRacunRepository;
//
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("findAll should return all MarzniRacun instances")
//    void findAllShouldReturnAllMarzniRacunInstances() {
//        MarginBankAccount marginBankAccount = new MarginBankAccount();
//        when(marzniRacunRepository.findAll()).thenReturn(List.of(marginBankAccount));
//
//        ResponseEntity<?> response = marzniRacunService.findAll();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(marzniRacunRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("findAllByUserId should return all MarzniRacun instances for a specific user")
//    void findAllByUserIdShouldReturnAllMarzniRacunInstancesForASpecificUser() {
//        MarginBankAccount marginBankAccount = new MarginBankAccount();
//        when(marzniRacunRepository.findAllByVlasnik(1L)).thenReturn(List.of(marginBankAccount));
//
//        ResponseEntity<?> response = marzniRacunService.findALlByUserId(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(marzniRacunRepository, times(1)).findAllByVlasnik(1L);
//    }
//
//    @Test
//    @DisplayName("createMarzniRacun should create a new MarzniRacun if it does not already exist")
//    void createMarzniRacunShouldCreateANewMarzniRacunIfItDoesNotAlreadyExist() {
//        MarzniRacunCreateDTO marzniRacunCreateDTO = new MarzniRacunCreateDTO();
//        marzniRacunCreateDTO.setVlasnik(1L);
//        marzniRacunCreateDTO.setGrupaHartija("Grupa");
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = marzniRacunService.createMarzniRacun(marzniRacunCreateDTO);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(marzniRacunRepository, times(1)).save(any(MarginBankAccount.class));
//    }
//
//    @Test
//    @DisplayName("createMarzniRacun should return BAD_REQUEST if MarzniRacun already exists")
//    void createMarzniRacunShouldReturnBadRequestIfMarzniRacunAlreadyExists() {
//        MarzniRacunCreateDTO marzniRacunCreateDTO = new MarzniRacunCreateDTO();
//        marzniRacunCreateDTO.setVlasnik(1L);
//        marzniRacunCreateDTO.setGrupaHartija("Grupa");
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.of(new MarginBankAccount()));
//
//        ResponseEntity<?> response = marzniRacunService.createMarzniRacun(marzniRacunCreateDTO);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(marzniRacunRepository, never()).save(any(MarginBankAccount.class));
//    }
//
//    @Test
//    @DisplayName("changeBalance should update balance when margin call is not active and amount is positive")
//    void changeBalanceShouldUpdateBalanceWhenMarginCallIsNotActiveAndAmountIsPositive() {
//        MarzniRacunUpdateDTO marzniRacunUpdateDTO = new MarzniRacunUpdateDTO();
//        marzniRacunUpdateDTO.setUserId(1L);
//        marzniRacunUpdateDTO.setGrupaHartija("Grupa");
//        marzniRacunUpdateDTO.setAmount(BigDecimal.TEN);
//
//        MarginBankAccount marginBankAccount = new MarginBankAccount();
//        marginBankAccount.setMarginCall(false);
//        marginBankAccount.setLiquidCash(BigDecimal.ZERO);
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.of(marginBankAccount));
//
//        ResponseEntity<?> response = marzniRacunService.changeBalance(marzniRacunUpdateDTO);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(BigDecimal.TEN, marginBankAccount.getLiquidCash());
//        verify(marzniRacunRepository, times(1)).save(marginBankAccount);
//    }
//
//    @Test
//    @DisplayName("changeBalance should return BAD_REQUEST when margin call is active and amount is negative")
//    void changeBalanceShouldReturnBadRequestWhenMarginCallIsActiveAndAmountIsNegative() {
//        MarzniRacunUpdateDTO marzniRacunUpdateDTO = new MarzniRacunUpdateDTO();
//        marzniRacunUpdateDTO.setUserId(1L);
//        marzniRacunUpdateDTO.setGrupaHartija("Grupa");
//        marzniRacunUpdateDTO.setAmount(BigDecimal.valueOf(-10));
//
//        MarginBankAccount marginBankAccount = new MarginBankAccount();
//        marginBankAccount.setMarginCall(true);
//        marginBankAccount.setLiquidCash(BigDecimal.ZERO);
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.of(marginBankAccount));
//
//        ResponseEntity<?> response = marzniRacunService.changeBalance(marzniRacunUpdateDTO);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(marzniRacunRepository, never()).save(any(MarginBankAccount.class));
//    }
//
//    @Test
//    @DisplayName("changeBalance should return NOT_FOUND when no MarzniRacun found with the specified grupaHartija for this user")
//    void changeBalanceShouldReturnNotFoundWhenNoMarzniRacunFoundWithTheSpecifiedGrupaHartijaForThisUser() {
//        MarzniRacunUpdateDTO marzniRacunUpdateDTO = new MarzniRacunUpdateDTO();
//        marzniRacunUpdateDTO.setUserId(1L);
//        marzniRacunUpdateDTO.setGrupaHartija("Grupa");
//        marzniRacunUpdateDTO.setAmount(BigDecimal.TEN);
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = marzniRacunService.changeBalance(marzniRacunUpdateDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(marzniRacunRepository, never()).save(any(MarginBankAccount.class));
//    }
//
//    @Test
//    @DisplayName("changeMaintenanceMargin should update maintenance margin when MarzniRacun is found")
//    void changeMaintenanceMarginShouldUpdateMaintenanceMarginWhenMarzniRacunIsFound() {
//        MarzniRacunUpdateDTO marzniRacunUpdateDTO = new MarzniRacunUpdateDTO();
//        marzniRacunUpdateDTO.setUserId(1L);
//        marzniRacunUpdateDTO.setGrupaHartija("Grupa");
//        marzniRacunUpdateDTO.setAmount(BigDecimal.TEN);
//
//        MarginBankAccount marginBankAccount = new MarginBankAccount();
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.of(marginBankAccount));
//
//        ResponseEntity<?> response = marzniRacunService.changeMaintenanceMargin(marzniRacunUpdateDTO);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(BigDecimal.TEN, marginBankAccount.getMaintenanceMargin());
//        verify(marzniRacunRepository, times(1)).save(marginBankAccount);
//    }
//
//    @Test
//    @DisplayName("changeMaintenanceMargin should return NOT_FOUND when no MarzniRacun found with the specified grupaHartija for this user")
//    void changeMaintenanceMarginShouldReturnNotFoundWhenNoMarzniRacunFoundWithTheSpecifiedGrupaHartijaForThisUser() {
//        MarzniRacunUpdateDTO marzniRacunUpdateDTO = new MarzniRacunUpdateDTO();
//        marzniRacunUpdateDTO.setUserId(1L);
//        marzniRacunUpdateDTO.setGrupaHartija("Grupa");
//        marzniRacunUpdateDTO.setAmount(BigDecimal.TEN);
//
//        when(marzniRacunRepository.findByVlasnikAndGrupaHartija(1L, "Grupa")).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = marzniRacunService.changeMaintenanceMargin(marzniRacunUpdateDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(marzniRacunRepository, never()).save(any(MarginBankAccount.class));
//    }
//}