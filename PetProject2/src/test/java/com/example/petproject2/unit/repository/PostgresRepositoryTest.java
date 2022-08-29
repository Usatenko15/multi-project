package com.example.petproject2.unit.repository;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.PostgresEntity.Customer;
import com.example.petproject2.persistance.entity.PostgresEntity.CustomerProduct;
import com.example.petproject2.persistance.entity.PostgresEntity.Product;
import com.example.petproject2.persistance.mappers.PostgresMapper;
import com.example.petproject2.persistance.repository.PostgresRepository;
import com.example.petproject2.persistance.repository.postgresrepository.CustomerProductRepository;
import com.example.petproject2.persistance.repository.postgresrepository.CustomerRepository;
import com.example.petproject2.persistance.repository.postgresrepository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class PostgresRepositoryTest {

    @InjectMocks
    private PostgresRepository repository;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerProductRepository customerProductRepository;
    @Mock
    private PostgresMapper mapper;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    private ArgumentCaptor<CustomerProduct> customerProductArgumentCaptor;

    @Test
    void findAllCustomersCallsCustomerRepository() {
        //given

        //when
        repository.findAllCustomers();

        //then
        verify(customerRepository).findAll();
    }

    @Test
    void findAllCustomersCallsMapsEntitiesToModel() {
        //given
        var customer = new Customer();

        //when
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        repository.findAllCustomers();

        //then
        verify(mapper).toModel(customerArgumentCaptor.capture());
        assertEquals(customerArgumentCaptor.getValue(), customer);
    }

    @Test
    void findAllCustomersReturnsMappedCustomers() {
        //given
        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        //when
        when(customerRepository.findAll()).thenReturn(List.of(new Customer()));
        when(mapper.toModel(any(Customer.class))).thenReturn(customerModel);

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

        var customer = new Customer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId(1l);
        repository.saveCustomer(customerModel);

        //then
        verify(customerRepository).save(customer);
    }

    @Test
    void saveCustomerReturnsMappedCustomer() {
        //given
        var customerModel = new CustomerModel();
        customerModel.setName("customer");

        var customerModel1 = new CustomerModel();
        customerModel1.setCustomerId("1");
        customerModel1.setName("customer");

        var customer = new Customer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId(1l);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        customerModel.setCustomerId("1");
        when(mapper.toModel(any(Customer.class))).thenReturn(customerModel);

        repository.saveCustomer(customerModel);

        //then
        assertEquals(repository.saveCustomer(customerModel), customerModel1);
    }

    @Test
    void saveProductToCustomerCallsRepositorySave() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);

        var product = new Product();
        customer.setName("product");
        customer.setCustomerId(1l);

        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));
        when(productRepository.findById(Long.parseLong(productId))).thenReturn(Optional.of(product));

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(customerRepository).findById(Long.parseLong(customerId));
        verify(productRepository).findById(Long.parseLong(productId));
        verify(customerProductRepository).save(any(CustomerProduct.class));
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenCustomerNotFound() throws NoSuchElementException{
        //given
        String customerId = "1";
        String productId = "1";

        //when

        //then
        assertThrows(NoSuchElementException.class, () -> repository.saveProductToCustomer(customerId,productId));
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenProductNotFound() throws NoSuchElementException{
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);


        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));

        //then
        assertThrows(NoSuchElementException.class, () -> repository.saveProductToCustomer(customerId,productId));
    }

    @Test
    void saveProductToCustomerMappedCustomer() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);

        var product = new Product();
        customer.setName("product");
        customer.setCustomerId(1l);

        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));
        when(productRepository.findById(Long.parseLong(productId))).thenReturn(Optional.of(product));

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(customerProductRepository).save(customerProductArgumentCaptor.capture());
        assertEquals(customerProductArgumentCaptor.getValue().getCustomer(), customer);
        assertEquals(customerProductArgumentCaptor.getValue().getProduct(), product);
    }

    @Test
    void saveProductToCustomerMapping() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);

        var product = new Product();
        customer.setName("product");
        customer.setCustomerId(1l);

        var customerModel = new CustomerModel();
        customer.setName("customer");
        customer.setCustomerId(1l);

        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));
        when(productRepository.findById(Long.parseLong(productId))).thenReturn(Optional.of(product));
        when(mapper.toModel(any(Customer.class))).thenReturn(customerModel);

        repository.saveProductToCustomer(customerId, productId);

        //then
        assertEquals(repository.saveProductToCustomer(customerId, productId), customerModel);
    }


    @Test
    void findByIdCallRepository() {
        String customerId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);


        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));

        repository.findById(customerId);

        //then
        verify(customerRepository).findById(Long.parseLong(customerId));
    }

    @Test
    void findByIdCallRepositoryThrowsExceptionWhenCustomerNotFound() {
        String customerId = "1";


        //when

        //then
        assertThrows(NoSuchElementException.class, () -> repository.findById(customerId));
    }

    @Test
    void findByIdCallMappedCustomer() {
        String customerId = "1";

        var customer = new Customer();
        customer.setName("customer");
        customer.setCustomerId(1l);

        var customerModel = new CustomerModel();
        customer.setName("customer");
        customer.setCustomerId(1l);


        //when
        when(customerRepository.findById(Long.parseLong(customerId))).thenReturn(Optional.of(customer));
        when(mapper.toModel(any(Customer.class))).thenReturn(customerModel);

        repository.findById(customerId);

        //then
        assertEquals(repository.findById(customerId), customerModel);
    }

    @Test
    void findAllProductsCallsProductRepository() {
        //given

        //when
        repository.findAllProducts();

        //then
        verify(productRepository).findAll();
    }

    @Test
    void findAllProductsCallsMapsEntitiesToModel() {
        //given
        var product = new Product();

        //when
        when(productRepository.findAll()).thenReturn(List.of(product));

        repository.findAllProducts();

        //then
        verify(mapper).toModel(productArgumentCaptor.capture());
        assertEquals(productArgumentCaptor.getValue(), product);
    }

    @Test
    void findAllProductsReturnsMappedAllProducts() {
        //given
        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        //when
        when(productRepository.findAll()).thenReturn(List.of(new Product()));
        when(mapper.toModel(any(Product.class))).thenReturn(productModel);

        repository.findAllProducts();

        //then
        assertEquals(productModel, repository.findAllProducts().get(0));
    }

    @Test
    void saveProductCallsMapsModelToEntities() {
        //given
        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        //when
        repository.saveProduct(productModel);

        //then
        verify(mapper).toEntity(productModel);
    }

    @Test
    void saveProductCallsRepositorySave() {
        //given
        var productModel = new ProductModel();
        productModel.setName("product");

        var product = new Product();
        product.setName("product");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId(1l);
        repository.saveProduct(productModel);

        //then
        verify(productRepository).save(product);
    }

    @Test
    void saveProductReturnsMappedCustomer() {
        //given
        var productModel = new ProductModel();
        productModel.setName("product");

        var productModel1 = new ProductModel();
        productModel1.setProductId("1");
        productModel1.setName("product");

        var product = new Product();
        product.setName("product");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId(1l);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        productModel.setProductId("1");
        when(mapper.toModel(any(Product.class))).thenReturn(productModel);

        repository.saveProduct(productModel);

        //then
        assertEquals(repository.saveProduct(productModel), productModel1);
    }

    @Test
    void findProductByIdCallRepository() {
        String productId = "1";

        var product = new Product();
        product.setName("product");
        product.setProductId(1l);


        //when
        when(productRepository.findById(Long.parseLong(productId))).thenReturn(Optional.of(product));

        repository.findProductById(productId);

        //then
        verify(productRepository).findById(Long.parseLong(productId));
    }

    @Test
    void findProductByIdCallRepositoryThrowsExceptionWhenCustomerNotFound() {
        String productId = "1";


        //when

        //then
        assertThrows(NoSuchElementException.class, () -> repository.findById(productId));
    }

    @Test
    void findProductByIdCallMappedCustomer() {
        String productId = "1";

        var product = new Product();
        product.setName("product");
        product.setProductId(1l);

        var productModel = new ProductModel();
        productModel.setName("product");
        productModel.setProductId("1");


        //when
        when(productRepository.findById(Long.parseLong(productId))).thenReturn(Optional.of(product));
        when(mapper.toModel(any(Product.class))).thenReturn(productModel);

        repository.findProductById(productId);

        //then
        assertEquals(repository.findProductById(productId), productModel);
    }
}