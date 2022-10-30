package com.example.petproject2.unit.controllers;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ShoppingCartModel;
import com.example.petproject2.domain.services.CustomerService;
import com.example.petproject2.presentation.DTO.ShoppingCartDTO;
import com.example.petproject2.presentation.DTO.CustomerDTO;
import com.example.petproject2.presentation.controllers.CustomerController;
import com.example.petproject2.presentation.mapper.MainMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @Mock
    MainMapper mapper;

    @Captor
    private ArgumentCaptor<CustomerModel> customerArgumentCaptor;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void findAllCustomersCallsCustomerService() {
        //given

        //when
        customerController.findAll();

        //then
        verify(customerService).findAllCustomers();
    }

    @Test
    void findAllCustomersCallsMapsModelToDTOs() {
        //given
        var customer = new CustomerModel();

        //when
        when(customerService.findAllCustomers()).thenReturn(List.of(customer));

        customerController.findAll();

        //then
        verify(mapper).toDTO(customerArgumentCaptor.capture());
        assertEquals(customerArgumentCaptor.getValue(), customer);
    }

    @Test
    void findAllCustomersReturnsMappedCustomers() {
        //given
        var customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("1");
        customerDTO.setName("customer");

        //when
        when(customerService.findAllCustomers()).thenReturn(List.of(new CustomerModel()));
        when(mapper.toDTO(any(CustomerModel.class))).thenReturn(customerDTO);

        customerController.findAll();

        //then
        assertEquals(customerDTO, customerController.findAll().get(0));
    }

    @Test
    void saveCustomerCallsMapsDTOsToModel() {
        //given
        var customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("1");
        customerDTO.setName("customer");

        //when
        customerController.saveCustomer(customerDTO);

        //then
        verify(mapper).toModel(customerDTO);
    }

    @Test
    void saveCustomerCallsServiceSave() {
        //given
        var customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("1");
        customerDTO.setName("customer");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");

        //when
        when(mapper.toModel(any(CustomerDTO.class))).thenReturn(customerModel);
        customerModel.setCustomerId("1");
        customerController.saveCustomer(customerDTO);

        //then
        verify(customerService).createCustomer(customerModel);
    }

    @Test
    void saveCustomerReturnsMappedCustomer() {
        //given
        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");

        var customerDTO1 = new CustomerDTO();
        customerDTO1.setCustomerId("1");
        customerDTO1.setName("customer");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");

        //when
        when(mapper.toModel(any(CustomerDTO.class))).thenReturn(customerModel);
        customerModel.setCustomerId("1");
        when(customerService.createCustomer(any(CustomerModel.class))).thenReturn(customerModel);
        customerDTO.setCustomerId("1");
        when(mapper.toDTO(any(CustomerModel.class))).thenReturn(customerDTO);

        customerController.saveCustomer(customerDTO);

        //then
        assertEquals(customerController.saveCustomer(customerDTO), customerDTO1);
    }

    @Test
    void findByIdCallService() {
        String customerId = "1";

        //when
        customerController.findById(customerId);

        //then
        verify(customerService).findById(customerId);
    }

    @Test
    void findByIdCallMappedCustomer() {
        String customerId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");


        //when
        when(customerService.findById(customerId)).thenReturn(customerModel);
        when(mapper.toDTO(any(CustomerModel.class))).thenReturn(customerDTO);

        customerController.findById(customerId);

        //then
        verify(mapper).toDTO(customerModel);
        assertEquals(customerController.findById(customerId), customerDTO);
    }

    @Test
    void saveProductToCustomerCallsServiceSave() {
        //given
        String customerId = "1";
        String productId = "1";

        //when

        customerController.saveProductToCustomer(customerId, productId);

        //then
        verify(customerService).saveProductToCustomer(customerId, productId);
    }

    @Test
    void saveProductToCustomerMappedCustomer() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new CustomerModel();
        customer.setName("customer");
        customer.setCustomerId("1l");

        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");

        //when
        when(customerService.saveProductToCustomer(customerId,productId)).thenReturn(customer);
        when(mapper.toDTO(any(CustomerModel.class))).thenReturn(customerDTO);

        CustomerDTO savedCustomer = customerController.saveProductToCustomer(customerId, productId);

        //then
        verify(mapper).toDTO(customerArgumentCaptor.capture());
        assertEquals(customerArgumentCaptor.getValue(), customer);
        assertEquals(savedCustomer, customerDTO);
    }

    @Test
    void editCustomerCallsMapsDTOsToModel() {
        //given
        String customerId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("1");
        customerDTO.setName("customer");

        //when
        customerController.editCustomer(customerId, customerDTO);

        //then
        verify(mapper).toModel(customerDTO);
    }

    @Test
    void editCustomerCallService() {
        String customerId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        //when
        when(mapper.toModel(any(CustomerDTO.class))).thenReturn(customerModel);

        customerController.editCustomer(customerId, customerDTO);

        //then
        verify(customerService).editCustomer(customerId, customerModel);
    }

    @Test
    void editCustomerCallMappedCustomer() {
        String customerId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        var editedCustomerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");


        //when
        when(mapper.toModel(any(CustomerDTO.class))).thenReturn(customerModel);
        when(customerService.editCustomer(customerId, customerModel)).thenReturn(customerModel);
        when(mapper.toDTO(any(CustomerModel.class))).thenReturn(editedCustomerDTO);

        var expectedCustomerDto = customerController.editCustomer(customerId, customerDTO);

        //then
        verify(mapper).toDTO(customerModel);
        assertEquals(expectedCustomerDto, editedCustomerDTO);
    }

    @Test
    void addProductToCustomer() {
        //given
        String customerId = "1";
        String productId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setCustomerId("1");
        customerDTO.setName("customer");

        //when
        customerController.addProductToCustomer(customerId, productId);

        //then
        verify(customerService).addProductToBucket(customerId, productId);
    }

    @Test
    void getCustomerBucketCallService() {
        String customerId = "1";

        var customerDTO = new CustomerDTO();
        customerDTO.setName("customer");
        customerDTO.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        //when
        customerController.getCustomerBucket(customerId);

        //then
        verify(customerService).getCustomerBucket(customerId);
    }

    @Test
    void getCustomerBucketCallMappedCustomer() {
        //given
        String customerId = "1";

        var shoppingCardModel = new ShoppingCartModel();

        //when
        when(customerService.getCustomerBucket(customerId)).thenReturn(shoppingCardModel);
        when(mapper.toDTO(any(ShoppingCartModel.class))).thenReturn(new ShoppingCartDTO());
        customerController.getCustomerBucket(customerId);


        //then
        verify(mapper).toDTO(shoppingCardModel);
    }
}