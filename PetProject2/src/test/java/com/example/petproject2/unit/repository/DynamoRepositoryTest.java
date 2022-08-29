package com.example.petproject2.unit.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoCustomer;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoProduct;
import com.example.petproject2.persistance.mappers.DynamoMapper;
import com.example.petproject2.persistance.repository.DynamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamoRepositoryTest {

    @InjectMocks
    private DynamoRepository repository;

    @Mock
    private DynamoMapper mapper;
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Captor
    private ArgumentCaptor<DynamoCustomer> customerArgumentCaptor;

    @Captor
    private ArgumentCaptor<DynamoProduct> productArgumentCaptor;

    @Test
    void findAllCustomersCallsCustomerRepository() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        List<DynamoCustomer> list = new ArrayList<>();
        list.add(customer);

        //when
        when(dynamoDBMapper.scan(eq(DynamoCustomer.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);
        repository.findAllCustomers();

        //then
        verify(dynamoDBMapper).scan((eq(DynamoCustomer.class)), any(DynamoDBScanExpression.class));
    }

    @Test
    void findAllCustomersCallsMapsEntitiesToModel() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        List<DynamoCustomer> list = new ArrayList<>();
        list.add(customer);

        //when
        when(dynamoDBMapper.scan(eq(DynamoCustomer.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);
        repository.findAllCustomers();

        //then
        verify(mapper).toModel(customerArgumentCaptor.capture());
        assertEquals(customerArgumentCaptor.getValue(), customer);
    }

    @Test
    void findAllCustomersReturnsMappedCustomers() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        List<DynamoCustomer> list = new ArrayList<>();
        list.add(customer);

        //when
        when(dynamoDBMapper.scan(eq(DynamoCustomer.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);
        repository.findAllCustomers();

        //then
        assertEquals(customerModel, repository.findAllCustomers().get(0));
    }

    @Test
    void saveCustomerCallsMapsModelToEntities() {
        //given
        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        //when
        repository.saveCustomer(customerModel);

        //then
        verify(mapper).toEntity(customerModel);
    }

    @Test
    void saveCustomerCallsRepositorySave() {
        //given
        var customerModel = new CustomerModel();
        customerModel.setName("customer");

        var customer = new DynamoCustomer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId("1");
        repository.saveCustomer(customerModel);

        //then
        verify(dynamoDBMapper).save(customer);
    }

    @Test
    void saveCustomerReturnsMappedCustomer() {
        //given
        var customerModel = new CustomerModel();
        customerModel.setName("customer");

        var customerModel1 = new CustomerModel();
        customerModel1.setCustomerId("1");
        customerModel1.setName("customer");

        var customer = new DynamoCustomer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId("1");
        customerModel.setCustomerId("1");
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);

        //then
        assertEquals(repository.saveCustomer(customerModel), customerModel1);
    }

    @Test
    void saveProductToCustomerCallsRepositorySave() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new DynamoProduct();
        customer.setName("product");
        customer.setCustomerId("1");

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, productId)).thenReturn(product);

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(dynamoDBMapper).load(DynamoCustomer.class, customerId);
        verify(dynamoDBMapper).load(DynamoProduct.class, productId);
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenCustomerNotFound() throws NoSuchElementException {
        //given
        String customerId = "1";
        String productId = "1";

        //when

        //then
        assertThrows(NullPointerException.class, () -> repository.saveProductToCustomer(customerId, productId));
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenProductNotFound() throws NoSuchElementException {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);

        //then
        assertThrows(NullPointerException.class, () -> repository.saveProductToCustomer(customerId, productId));
    }

    @Test
    void saveProductToCustomerMappedCustomer() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new DynamoProduct();
        product.setName("product");
        product.setProductId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        var productModel = new ProductModel();
        productModel.setName("customer");
        productModel.setProductId("1");

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, productId)).thenReturn(product);
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(dynamoDBMapper,times(2)).save(any());
        assertEquals(customer.getProducts().get(0), product.getProductId());
        assertEquals(product.getCustomers().get(0), customer.getCustomerId());
    }

    @Test
    void saveProductToCustomerMapping() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new DynamoProduct();
        customer.setName("product");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customer.setName("customer");
        customer.setCustomerId("1");

        var productModel = new ProductModel();
        customer.setName("customer");
        customer.setCustomerId("1");

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, productId)).thenReturn(product);
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);

        //then
        assertEquals(repository.saveProductToCustomer(customerId, productId), customerModel);
    }

    @Test
    void findByIdCallRepository() {
        String customerId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");


        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);

        repository.findById(customerId);

        //then
        verify(dynamoDBMapper).load(DynamoCustomer.class, customerId);
    }

    @Test
    void findByIdCallRepositoryThrowsExceptionWhenCustomerNotFound() {
        String customerId = "1";


        //when

        //then
        assertThrows(NullPointerException.class, () -> repository.findById(customerId));
    }

    @Test
    void findByIdCallMappedCustomer() {
        String customerId = "1";

        var customer = new DynamoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");


        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customerId)).thenReturn(customer);
        when(mapper.toModel(any(DynamoCustomer.class))).thenReturn(customerModel);

        repository.findById(customerId);

        //then
        assertEquals(repository.findById(customerId), customerModel);
    }

    @Test
    void findAllProductsCallsProductRepository() {
        //given
        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        List<DynamoProduct> list = new ArrayList<>();
        list.add(product);

        //when
        when(dynamoDBMapper.scan(eq(DynamoProduct.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);
        repository.findAllProducts();

        //then
        verify(dynamoDBMapper).scan((eq(DynamoProduct.class)), any(DynamoDBScanExpression.class));
    }

    @Test
    void findAllProductsCallsMapsEntitiesToModel() {
        //given
        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        List<DynamoProduct> list = new ArrayList<>();
        list.add(product);

        //when
        when(dynamoDBMapper.scan(eq(DynamoProduct.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);
        repository.findAllProducts();

        //then
        verify(mapper).toModel(productArgumentCaptor.capture());
        assertEquals(productArgumentCaptor.getValue(), product);
    }

    @Test
    void findAllProductsReturnsMappedAllProducts() {
        //given
        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        List<DynamoProduct> list = new ArrayList<>();
        list.add(product);

        //when
        when(dynamoDBMapper.scan(eq(DynamoProduct.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);
        repository.findAllProducts();

        //then
        assertEquals(productModel, repository.findAllProducts().get(0));
    }

    @Test
    void findProductByIdCallRepository() {
        String productId = "1";

        var product = new DynamoProduct();
        product.setName("product");
        product.setProductId("1");

        var productModel = new ProductModel();
        productModel.setName("product");
        productModel.setProductId("1");

        //when
        when(dynamoDBMapper.load(DynamoProduct.class, productId)).thenReturn(product);
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);

        repository.findProductById(productId);

        //then
        verify(dynamoDBMapper).load(DynamoProduct.class, productId);
    }

    @Test
    void findProductByIdCallRepositoryThrowsExceptionWhenCustomerNotFound() {
        String productId = "1";


        //when

        //then
        assertThrows(NullPointerException.class, () -> repository.findProductById(productId));
    }

    @Test
    void findProductByIdCallMappedCustomer() {
        String productId = "1";

        var product = new DynamoProduct();
        product.setName("customer");
        product.setProductId("1");

        var productModel = new ProductModel();
        productModel.setName("customer");
        productModel.setProductId("1");


        //when
        when(dynamoDBMapper.load(DynamoProduct.class, productId)).thenReturn(product);
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);

        repository.findProductById(productId);

        //then
        assertEquals(repository.findProductById(productId), productModel);
    }

    @Test
    void saveProductCallsMapsModelToEntities() {
        //given
        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("customer");

        //when
        repository.saveProduct(productModel);

        //then
        verify(mapper).toEntity(productModel);
    }

    @Test
    void saveProductCallsRepositorySave() {
        //given
        var productModel = new ProductModel();
        productModel.setName("customer");

        var product = new DynamoProduct();
        product.setName("customer");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId("1");
        repository.saveProduct(productModel);

        //then
        verify(dynamoDBMapper).save(product);
    }

    @Test
    void saveProductReturnsMappedCustomer() {
        //given
        var productModel = new ProductModel();
        productModel.setName("customer");

        var productModel1 = new ProductModel();
        productModel1.setProductId("1");
        productModel1.setName("customer");

        var product = new DynamoProduct();
        product.setName("customer");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId("1");
        productModel.setProductId("1");
        when(mapper.toModel(any(DynamoProduct.class))).thenReturn(productModel);

        //then
        assertEquals(repository.saveProduct(productModel), productModel1);
    }
}