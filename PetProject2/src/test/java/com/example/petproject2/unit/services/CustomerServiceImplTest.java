package com.example.petproject2.unit.services;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.domain.services.CustomerServiceImpl;
import com.example.petproject2.persistance.repository.PostgresRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private PostgresRepository repository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void findAllCustomersCallsService() {
        //given

        //when
        customerService.findAllCustomers();

        //then
        verify(repository).findAllCustomers();
    }

    @Test
    void findAllCustomers() {
        //given
        CustomerModel customer1 = new CustomerModel();
        customer1.setCustomerId("1l");
        customer1.setName("fdd");

        CustomerModel customer2 = new CustomerModel();
        customer2.setCustomerId("2l");
        customer2.setName("f1d4d");

        List<CustomerModel> customers = List.of(customer1, customer2);

        //when
        when(repository.findAllCustomers()).thenReturn(customers);

        //then
        assertEquals(customers, customerService.findAllCustomers());
    }

    @Test
    void saveCustomerCallsService() {
        //given
        CustomerModel customer = new CustomerModel();

        //when
        customerService.saveCustomer(customer);

        //then
        verify(repository).saveCustomer(customer);
    }

    @Test
    void saveCustomer() {
        //given
        CustomerModel customer = new CustomerModel();
        customer.setCustomerId("1l");
        customer.setName("fdd");

        CustomerModel expectedCustomer = new CustomerModel();
        expectedCustomer.setCustomerId("1l");
        expectedCustomer.setName("fdd");

        //when
        when(repository.saveCustomer(customer)).thenReturn(customer);

        //then
        assertEquals(expectedCustomer, customerService.saveCustomer(customer));
    }

    @Test
    void saveProductToCustomerCallsService() {
        //given
        String customerId = "1";
        String productId = "1";

        //when
        customerService.saveProductToCustomer(customerId, productId);

        //then
        verify(repository).saveProductToCustomer(customerId, productId);
    }

    @Test
    void saveProductToCustomer() {
        //given
        CustomerModel customer = new CustomerModel();
        customer.setCustomerId("1l");
        customer.setName("fdd");

        ProductModel product = new ProductModel();
        product.setProductId("1l");
        product.setName("fdd");

        //when
        when(repository.saveProductToCustomer(customer.getCustomerId(), product.getProductId())).thenReturn(customer);

        //then
        assertEquals(customer, customerService.saveProductToCustomer(customer.getCustomerId(), product.getProductId()));
    }

    @Test
    void findByIdCallsService() {
        //given
        String customerId = "1";

        //when
        customerService.findById(customerId);

        //then
        verify(repository).findById(customerId);
    }

    @Test
    void findById() {
        //given
        CustomerModel customer = new CustomerModel();
        customer.setCustomerId("1");
        customer.setName("fdd");

        //when
        when(repository.findById(customer.getCustomerId())).thenReturn(customer);

        //then
        assertEquals(customer, customerService.findById(customer.getCustomerId()));
    }
}