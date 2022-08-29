package com.example.petproject2.unit.repository;

import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.MongoEntity.MongoCustomer;
import com.example.petproject2.persistance.entity.MongoEntity.MongoProduct;
import com.example.petproject2.persistance.mappers.MongoMapper;
import com.example.petproject2.persistance.repository.MongoRepository;
import com.example.petproject2.persistance.repository.mongorepository.CustomerMongoRepository;
import com.example.petproject2.persistance.repository.mongorepository.ProductMongoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MongoRepositoryTest {

    @InjectMocks
    private MongoRepository repository;

    @Mock
    private CustomerMongoRepository customerRepository;
    @Mock
    private ProductMongoRepository productRepository;
    @Mock
    private MongoMapper mapper;

    @Captor
    private ArgumentCaptor<MongoCustomer> customerArgumentCaptor;

    @Captor
    private ArgumentCaptor<MongoProduct> productArgumentCaptor;

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
        var customer = new MongoCustomer();

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
        when(customerRepository.findAll()).thenReturn(List.of(new MongoCustomer()));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);

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

        var customer = new MongoCustomer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId("1");
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

        var customer = new MongoCustomer();
        customer.setName("customer");

        //when
        when(mapper.toEntity(any(CustomerModel.class))).thenReturn(customer);
        customer.setCustomerId("1");
        when(customerRepository.save(any(MongoCustomer.class))).thenReturn(customer);
        customerModel.setCustomerId("1");
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);

        //then
        assertEquals(repository.saveCustomer(customerModel), customerModel1);
    }

    @Test
    void saveProductToCustomerCallsRepositorySave() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new MongoProduct();
        customer.setName("product");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        var productModel = new ProductModel();
        productModel.setName("customer");
        productModel.setProductId("1");


        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(customerRepository).findById(customerId);
        verify(productRepository).findById(productId);
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenCustomerNotFound() throws NoSuchElementException {
        //given
        String customerId = "1";
        String productId = "1";

        //when

        //then
        assertThrows(NoSuchElementException.class, () -> repository.saveProductToCustomer(customerId, productId));
    }

    @Test()
    void saveProductToCustomerThrowsExceptionWhenProductNotFound() throws NoSuchElementException {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");


        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        //then
        assertThrows(NoSuchElementException.class, () -> repository.saveProductToCustomer(customerId, productId));
    }

    @Test
    void saveProductToCustomerMappedCustomer() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new MongoProduct();
        product.setName("product");
        product.setProductId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        var productModel = new ProductModel();
        productModel.setName("customer");
        productModel.setProductId("1");

        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

        repository.saveProductToCustomer(customerId, productId);

        //then
        verify(customerRepository).save(customerArgumentCaptor.capture());
        assertEquals(customerModel.getProducts().get(0), productModel);
        assertEquals(productModel.getCustomers().get(0), customerModel);
    }

    @Test
    void saveProductToCustomerMapping() {
        //given
        String customerId = "1";
        String productId = "1";

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new MongoProduct();
        customer.setName("product");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customer.setName("customer");
        customer.setCustomerId("1");

        var productModel = new ProductModel();
        customer.setName("customer");
        customer.setCustomerId("1");

        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

        repository.saveProductToCustomer(customerId, productId);

        //then
        assertEquals(repository.saveProductToCustomer(customerId, productId), customerModel);
    }

    @Test
    void findByIdCallRepository() {
        String customerId = "1";

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");


        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);

        repository.findById(customerId);

        //then
        verify(customerRepository).findById(customerId);
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

        var customer = new MongoCustomer();
        customer.setName("customer");
        customer.setCustomerId("1");

        var product = new MongoProduct();
        customer.setName("product");
        customer.setCustomerId("1");
        customer.setMongoProducts(List.of(product));

        var customerModel = new CustomerModel();
        customerModel.setName("customer");
        customerModel.setCustomerId("1");

        var productModel = new ProductModel();
        customerModel.setName("product");
        customerModel.setCustomerId("1");
        customerModel.setProducts(List.of(productModel));


        //when
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.toModel(any(MongoCustomer.class))).thenReturn(customerModel);

        var findedCustomer = repository.findById(customerId);

        //then
        assertEquals(findedCustomer, customerModel);
        assertEquals(findedCustomer.getProducts().get(0), productModel);
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
        var product = new MongoProduct();

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
        when(productRepository.findAll()).thenReturn(List.of(new MongoProduct()));
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

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

        var product = new MongoProduct();
        product.setName("product");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId("1");
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

        var product = new MongoProduct();
        product.setName("product");

        //when
        when(mapper.toEntity(any(ProductModel.class))).thenReturn(product);
        product.setProductId("1");
        when(productRepository.save(any(MongoProduct.class))).thenReturn(product);
        productModel.setProductId("1");
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

        repository.saveProduct(productModel);

        //then
        assertEquals(repository.saveProduct(productModel), productModel1);
    }

    @Test
    void findProductByIdCallRepository() {
        String productId = "1";

        var product = new MongoProduct();
        product.setName("product");
        product.setProductId("1");


        //when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        repository.findProductById(productId);

        //then
        verify(productRepository).findById(productId);
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

        var product = new MongoProduct();
        product.setName("product");
        product.setProductId("1");

        var productModel = new ProductModel();
        productModel.setName("product");
        productModel.setProductId("1");


        //when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.toModel(any(MongoProduct.class))).thenReturn(productModel);

        repository.findProductById(productId);

        //then
        assertEquals(repository.findProductById(productId), productModel);
    }
}