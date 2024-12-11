package rs.edu.raf.integration.credit;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import rs.edu.raf.dto.CreditRequestCreateDto;
import rs.edu.raf.model.credit.CreditRequest;
import rs.edu.raf.service.CreditService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditManagementTestSteps extends CreditManagementConfig{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CreditService creditService;

    private String userToken;
    private String workerToken;
    private Long creditRequestId;
    private Long userId;
    private String bankAccountNumber;
    private String createCreditRequestMsg;
    private String approveCreditRequestMsg;


    @Given("for credit a user with username {string} and password {string}")
    public void forCreditAUserWithUsernameAndPassword(String username, String password) {
        userToken = login(username, password);
    }

    @When("the user applies for a credit with the following details:")
    public void theUserAppliesForACreditWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);


//        CreditRequestCreateDto creditRequestCreateDto = new CreditRequestCreateDto();
//        creditRequestCreateDto.setAmount(new BigDecimal(data.get(0).get("amount")));
//        creditRequestCreateDto.setSalary(new BigDecimal(data.get(0).get("salary")));
//        creditRequestCreateDto.setCurrentEmploymentPeriod(Long.parseLong(data.get(0).get("currentEmploymentPeriod")));
//        creditRequestCreateDto.setLoanTerm(Long.parseLong(data.get(0).get("loanTerm")));
//        creditRequestCreateDto.setBranchOffice(data.get(0).get("branchOffice"));
//        creditRequestCreateDto.setBankAccountNumber(Long.parseLong(data.get(0).get("bankAccountNumber")));
//        creditRequestCreateDto.setLoanPurpose(data.get(0).get("loanPurpose"));
//        creditRequestCreateDto.setPermanentEmployee(Boolean.parseBoolean(data.get(0).get("permanentEmployee")));


//        createCreditRequestMsg = creditService.applyForCredit(creditRequestCreateDto);

    }

    @Then("the user should see a message {string}")
    public void theUserShouldSeeAMessage(String message) {
        Assertions.assertEquals(message, createCreditRequestMsg);
    }

    @Given("for a credit a worker with username {string} and password {string}")
    public void forACreditAWorkerWithUsernameAndPassword(String username, String password) {
        workerToken = login(username, password);
    }

    @And("the user with username {string} has created credit request")
    public void theUserWithUsernameHasCreatedCreditRequest(String username) {
//        KorisnikDTO korisnikDTO = getUserByUsername(username);
//        userId = korisnikDTO.getId();
    }

    @When("the worker approves the credit request for user")
    public void theWorkerApprovesTheCreditRequestForUser() {
        List<CreditRequestCreateDto> creditRequestCreateDtos = getAllCreditRequestsForUser("not_approved");
        if(creditRequestCreateDtos == null){
            Assertions.fail("Credit for specified user doesn't exists");
        }

        if(creditRequestCreateDtos.size() != 1){
            Assertions.fail("Something went wrong with credit requests for user");
        }

        CreditRequestCreateDto creditRequestCreateDto = creditRequestCreateDtos.get(0);
//        Long creditRequestId = creditRequestCreateDto.getId();

        creditService.approveCreditRequest(creditRequestId);

    }

    @Then("the user should see a his credit request is approved")
    public void theUserShouldSeeAHisCreditRequestIsApproved() {
        List<CreditRequestCreateDto> creditRequestCreateDtos = getAllCreditRequestsForUser("approved");

        if(creditRequestCreateDtos == null){
            Assertions.fail("Credit request for specified user doesn't exists!");
        }

        if(creditRequestCreateDtos.size() != 1){
            Assertions.fail("Something went wrong with credit requests for user!, size is: " + creditRequestCreateDtos.size());
        }

        CreditRequestCreateDto creditRequestCreateDto = creditRequestCreateDtos.get(0);
//        Long creditRequestid = creditRequestCreateDto.getId();
//
//        CreditRequest creditRequest = creditService.getCreditRequest(creditRequestid);
//
//        Assertions.assertEquals("approved", creditRequest.getStatus());

    }

    private String login(String username, String password){
        String loginUrl = "http://localhost:8080/api/korisnik/login";

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", username);
        loginRequest.put("password", password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(loginRequest, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

//    private KorisnikDTO getUserByUsername(String username){
//        String url = "http://localhost:8080/api/korisnik/email/" + username;
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + workerToken);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<KorisnikDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KorisnikDTO.class);

//        if(response.getStatusCode() == HttpStatus.OK){
//            return response.getBody();
//        }else{
//            return null;
//        }
//    }

    private List<CreditRequestCreateDto> getAllCreditRequestsForUser(String status){
//        return creditService.getAllCreditRequestForUserWithStatus(userId, status);
        return null;
    }

    private List<CreditRequest> getAllCreditRequestsForUserRaw(String status){
//        return creditService.getAllCreditRequestsForUserRaw(userId, status);
        return null;
    }

    @After
    private void cleanUp(){
        if(creditRequestId != null){
//            creditService.deleteCreditRequest(creditRequestId);
        }
    }

}
