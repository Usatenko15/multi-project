package com.example.petproject2.integration;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.petproject2.domain.model.CustomerModel;
import com.example.petproject2.domain.model.ProductModel;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoCustomer;
import com.example.petproject2.persistance.entity.DynamoEntity.DynamoProduct;
import com.example.petproject2.persistance.repository.DynamoRepository;
import com.example.petproject2.persistance.repository.PostgresRepository;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
class DynamoRepositoryTest {

    @MockBean
    @Qualifier("dynamoDBMapper")
    private DynamoDBMapper dynamoDBMapper;

    @MockBean
    PostgresRepository postgresRepository;

    @Autowired
    DynamoRepository repository;

    @Test
    void findAllCustomers() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        product.setCustomers(List.of(customer.getCustomerId()));
        customer.setProducts(List.of(product.getProductId()));

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        productModel.setCustomers(List.of(customerModel));
        customerModel.setProducts(List.of(productModel));

        List<DynamoCustomer> list = new ArrayList<>();
        list.add(customer);

        //when
        when(dynamoDBMapper.scan(eq(DynamoCustomer.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(dynamoDBMapper.load(DynamoCustomer.class, customer.getCustomerId())).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, product.getProductId())).thenReturn(product);
         var expectedCustomerModel = repository.findAllCustomers().get(0);

        //then
        assertEquals(customerModel, expectedCustomerModel);
        assertEquals(customerModel.getProducts(), expectedCustomerModel.getProducts());
        assertEquals(customerModel.getProducts().get(0).getCustomers(),
                expectedCustomerModel.getProducts().get(0).getCustomers());
    }

    @Test
    void findAllProducts() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        product.setCustomers(List.of(customer.getCustomerId()));
        customer.setProducts(List.of(product.getProductId()));

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        productModel.setCustomers(List.of(customerModel));
        customerModel.setProducts(List.of(productModel));

        List<DynamoProduct> list = new ArrayList<>();
        list.add(product);

        //when
        when(dynamoDBMapper.scan(eq(DynamoProduct.class), any(DynamoDBScanExpression.class))).
                thenReturn(mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list))));
        when(dynamoDBMapper.load(DynamoCustomer.class, customer.getCustomerId())).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, product.getProductId())).thenReturn(product);

        var expectedProductModel = repository.findAllProducts();

        //then
        assertEquals(productModel, expectedProductModel.get(0));
        assertEquals(productModel.getCustomers(), expectedProductModel.get(0).getCustomers());
        assertEquals(productModel.getCustomers().get(0).getProducts(),
                expectedProductModel.get(0).getCustomers().get(0).getProducts());
    }

    @Test
    void findById() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        product.setCustomers(List.of(customer.getCustomerId()));
        customer.setProducts(List.of(product.getProductId()));

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        productModel.setCustomers(List.of(customerModel));
        customerModel.setProducts(List.of(productModel));

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customer.getCustomerId())).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, product.getProductId())).thenReturn(product);

        var expectedCustomerModel = repository.findById(customer.getCustomerId());

        //then
        assertEquals(customerModel, expectedCustomerModel);
        assertEquals(customerModel.getProducts(), expectedCustomerModel.getProducts());
        assertEquals(customerModel.getProducts().get(0).getCustomers(),
                expectedCustomerModel.getProducts().get(0).getCustomers());
    }

    @Test
    void findProductById() {
        //given
        var customer = new DynamoCustomer();
        customer.setCustomerId("1");
        customer.setName("customer");

        var product = new DynamoProduct();
        product.setProductId("1");
        product.setName("product");

        product.setCustomers(List.of(customer.getCustomerId()));
        customer.setProducts(List.of(product.getProductId()));

        var customerModel = new CustomerModel();
        customerModel.setCustomerId("1");
        customerModel.setName("customer");

        var productModel = new ProductModel();
        productModel.setProductId("1");
        productModel.setName("product");

        productModel.setCustomers(List.of(customerModel));
        customerModel.setProducts(List.of(productModel));

        //when
        when(dynamoDBMapper.load(DynamoCustomer.class, customer.getCustomerId())).thenReturn(customer);
        when(dynamoDBMapper.load(DynamoProduct.class, product.getProductId())).thenReturn(product);

        var expectedProductModel = repository.findProductById(product.getProductId());

        //then
        assertEquals(productModel, expectedProductModel);
        assertEquals(productModel.getCustomers(), expectedProductModel.getCustomers());
        assertEquals(productModel.getCustomers().get(0).getProducts(),
                expectedProductModel.getCustomers().get(0).getProducts());
    }
}
