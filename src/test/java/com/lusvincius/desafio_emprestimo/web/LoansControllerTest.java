package com.lusvincius.desafio_emprestimo.web;

import com.lusvincius.desafio_emprestimo.api.CustomerLoansRequest;
import com.lusvincius.desafio_emprestimo.api.CustomerLoansResponse;
import com.lusvincius.desafio_emprestimo.api.LoansResponse;
import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoansControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Nested
  class CalculateLoansTests {

    private ResponseEntity<CustomerLoansResponse> sendRequest(CustomerLoansRequest request) {
      return restTemplate.postForEntity("/customer-loans", request, CustomerLoansResponse.class);
    }

    private void assertBadRequest(CustomerLoansRequest request) {
      ResponseEntity<CustomerLoansResponse> response = sendRequest(request);
      assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private void assertOkRequest(CustomerLoansRequest request) {
      ResponseEntity<CustomerLoansResponse> response = sendRequest(request);
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Set<String> extractLoanTypes(ResponseEntity<CustomerLoansResponse> response) {
      return response.getBody().loans().stream()
          .map(LoansResponse::type)
          .map(LoanType::toString)
          .collect(Collectors.toSet());
    }

    @Test
    void shouldGrantPersonalLoanIfSalaryIsEqualOrBelow3000() {
      CustomerLoansRequest request;
      ResponseEntity<CustomerLoansResponse> responseEntity;

      request = new CustomerLoansRequest("Lucas", "00039322300", 25, 3000.00, "RJ");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 25, 1000.00, "RJ");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 1000.00, "SP");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 3000.00, "SP");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 20, 3000.00, "CE");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("PERSONAL"));
    }

    @Test
    void shouldGrantPersonalLoanIfSalaryBetween3000And5000AndUnder30YearsAndResidesInSP() {
      CustomerLoansRequest request;
      ResponseEntity<CustomerLoansResponse> responseResponse;

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 4000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertTrue(extractLoanTypes(responseResponse).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 4000.00, "CE");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 4000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("PERSONAL"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 5000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("PERSONAL"));
    }

    @Test
    void shouldGrantConsignmentLoanIfSalaryIsEqualOrAbove5000() {
      CustomerLoansRequest request;
      ResponseEntity<CustomerLoansResponse> responseResponse;

      request = new CustomerLoansRequest("Lucas", "00039322300", 35, 5000.00, "MG");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertTrue(extractLoanTypes(responseResponse).contains("CONSIGNMENT"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 5000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertTrue(extractLoanTypes(responseResponse).contains("CONSIGNMENT"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 100000.00, "CE");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertTrue(extractLoanTypes(responseResponse).contains("CONSIGNMENT"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 100.00, "CE");
      assertBadRequest(request);
    }

    @Test
    void shouldGrantGuaranteedLoanIfSalaryIsEqualOrBelow3000() {
      CustomerLoansRequest request;
      ResponseEntity<CustomerLoansResponse> responseEntity;

      request = new CustomerLoansRequest("Lucas", "00039322300", 25, 3000.00, "RJ");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 25, 1000.00, "RJ");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 1000.00, "SP");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 3000.00, "SP");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 20, 3000.00, "CE");
      responseEntity = sendRequest(request);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertTrue(extractLoanTypes(responseEntity).contains("GUARANTEED"));
    }

    @Test
    void shouldGrantGuaranteedLoanIfSalaryBetween3000And5000AndUnder30YearsAndResidesInSP() {
      CustomerLoansRequest request;
      ResponseEntity<CustomerLoansResponse> responseResponse;

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 4000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertTrue(extractLoanTypes(responseResponse).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 29, 4000.00, "CE");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 4000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("GUARANTEED"));

      request = new CustomerLoansRequest("Lucas", "00039322300", 30, 5000.00, "SP");
      responseResponse = sendRequest(request);
      assertEquals(HttpStatus.OK, responseResponse.getStatusCode());
      assertFalse(extractLoanTypes(responseResponse).contains("GUARANTEED"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsEmptyOrWhitespace() {
      assertBadRequest(new CustomerLoansRequest("", "00039322300", 20, 1000.00, "CE"));
      assertBadRequest(new CustomerLoansRequest("    ", "00039322300", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() {
      assertBadRequest(new CustomerLoansRequest(null, "00039322300", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenCPFIsInvalidFormat() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "000393223001", 20, 1000.00, "CE"));
      assertBadRequest(new CustomerLoansRequest("Lucas", "0003932230a", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenCPFIsEmptyOrWhitespace() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "", 20, 1000.00, "CE"));
      assertBadRequest(new CustomerLoansRequest("Lucas", "      ", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenCPFIsNull() {
      assertBadRequest(new CustomerLoansRequest("Lucas", null, 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenAgeIsBelow18() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 17, 1000.00, "CE"));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 0, 1000.00, "CE"));
    }

    @Test
    void shouldReturnOkWhenAgeIs18OrAbove() {
      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 18, 1000.00, "CE"));
      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenIncomeIsBelow1000() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 0.0, "CE"));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 0, 822.00, "CE"));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 0, null, "CE"));
    }

    @Test
    void shouldReturnOkWhenIncomeIs100OrAbove() {
      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "CE"));
      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 3000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequestWhenLocationIsEmptyOrWhitespace() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, ""));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "  "));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, null));

      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "CE"));
    }

    @Test
    void shouldReturnBadRequest() {
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, ""));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "  "));
      assertBadRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, null));

      assertOkRequest(new CustomerLoansRequest("Lucas", "00039322300", 20, 1000.00, "CE"));
    }
  }
}
