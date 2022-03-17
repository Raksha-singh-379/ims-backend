package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.ProductTransaction;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.domain.enumeration.Status;
import com.techvg.inventory.management.domain.enumeration.TransactionType;
import com.techvg.inventory.management.repository.ProductTransactionRepository;
import com.techvg.inventory.management.service.criteria.ProductTransactionCriteria;
import com.techvg.inventory.management.service.dto.ProductTransactionDTO;
import com.techvg.inventory.management.service.mapper.ProductTransactionMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductTransactionResourceIT {

    private static final Long DEFAULT_REFRENCE_ID = 1L;
    private static final Long UPDATED_REFRENCE_ID = 2L;
    private static final Long SMALLER_REFRENCE_ID = 1L - 1L;

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.OUTWARDS_CONSUMPTION;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.INWARD_STOCKS;

    private static final Status DEFAULT_TRANSACTION_STATUS = Status.REQUESTED;
    private static final Status UPDATED_TRANSACTION_STATUS = Status.APPROVED;

    private static final String DEFAULT_TRANSACTION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTransactionRepository productTransactionRepository;

    @Autowired
    private ProductTransactionMapper productTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTransactionMockMvc;

    private ProductTransaction productTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransaction createEntity(EntityManager em) {
        ProductTransaction productTransaction = new ProductTransaction()
            .refrenceId(DEFAULT_REFRENCE_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .transactionStatus(DEFAULT_TRANSACTION_STATUS)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .description(DEFAULT_DESCRIPTION)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return productTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransaction createUpdatedEntity(EntityManager em) {
        ProductTransaction productTransaction = new ProductTransaction()
            .refrenceId(UPDATED_REFRENCE_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return productTransaction;
    }

    @BeforeEach
    public void initTest() {
        productTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTransaction() throws Exception {
        int databaseSizeBeforeCreate = productTransactionRepository.findAll().size();
        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);
        restProductTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getRefrenceId()).isEqualTo(DEFAULT_REFRENCE_ID);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getTransactionStatus()).isEqualTo(DEFAULT_TRANSACTION_STATUS);
        assertThat(testProductTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductTransaction.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProductTransaction.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProductTransaction.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductTransaction.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductTransactionWithExistingId() throws Exception {
        // Create the ProductTransaction with an existing ID
        productTransaction.setId(1L);
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        int databaseSizeBeforeCreate = productTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTransactions() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].refrenceId").value(hasItem(DEFAULT_REFRENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionStatus").value(hasItem(DEFAULT_TRANSACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get the productTransaction
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, productTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTransaction.getId().intValue()))
            .andExpect(jsonPath("$.refrenceId").value(DEFAULT_REFRENCE_ID.intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.transactionStatus").value(DEFAULT_TRANSACTION_STATUS.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getProductTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        Long id = productTransaction.getId();

        defaultProductTransactionShouldBeFound("id.equals=" + id);
        defaultProductTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultProductTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTransactionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId equals to DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.equals=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId equals to UPDATED_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.equals=" + UPDATED_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId not equals to DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.notEquals=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId not equals to UPDATED_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.notEquals=" + UPDATED_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId in DEFAULT_REFRENCE_ID or UPDATED_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.in=" + DEFAULT_REFRENCE_ID + "," + UPDATED_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId equals to UPDATED_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.in=" + UPDATED_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId is not null
        defaultProductTransactionShouldBeFound("refrenceId.specified=true");

        // Get all the productTransactionList where refrenceId is null
        defaultProductTransactionShouldNotBeFound("refrenceId.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId is greater than or equal to DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.greaterThanOrEqual=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId is greater than or equal to UPDATED_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.greaterThanOrEqual=" + UPDATED_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId is less than or equal to DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.lessThanOrEqual=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId is less than or equal to SMALLER_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.lessThanOrEqual=" + SMALLER_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId is less than DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.lessThan=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId is less than UPDATED_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.lessThan=" + UPDATED_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByRefrenceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where refrenceId is greater than DEFAULT_REFRENCE_ID
        defaultProductTransactionShouldNotBeFound("refrenceId.greaterThan=" + DEFAULT_REFRENCE_ID);

        // Get all the productTransactionList where refrenceId is greater than SMALLER_REFRENCE_ID
        defaultProductTransactionShouldBeFound("refrenceId.greaterThan=" + SMALLER_REFRENCE_ID);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultProductTransactionShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the productTransactionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultProductTransactionShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultProductTransactionShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the productTransactionList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultProductTransactionShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultProductTransactionShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the productTransactionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultProductTransactionShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionType is not null
        defaultProductTransactionShouldBeFound("transactionType.specified=true");

        // Get all the productTransactionList where transactionType is null
        defaultProductTransactionShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionStatus equals to DEFAULT_TRANSACTION_STATUS
        defaultProductTransactionShouldBeFound("transactionStatus.equals=" + DEFAULT_TRANSACTION_STATUS);

        // Get all the productTransactionList where transactionStatus equals to UPDATED_TRANSACTION_STATUS
        defaultProductTransactionShouldNotBeFound("transactionStatus.equals=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionStatus not equals to DEFAULT_TRANSACTION_STATUS
        defaultProductTransactionShouldNotBeFound("transactionStatus.notEquals=" + DEFAULT_TRANSACTION_STATUS);

        // Get all the productTransactionList where transactionStatus not equals to UPDATED_TRANSACTION_STATUS
        defaultProductTransactionShouldBeFound("transactionStatus.notEquals=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionStatus in DEFAULT_TRANSACTION_STATUS or UPDATED_TRANSACTION_STATUS
        defaultProductTransactionShouldBeFound("transactionStatus.in=" + DEFAULT_TRANSACTION_STATUS + "," + UPDATED_TRANSACTION_STATUS);

        // Get all the productTransactionList where transactionStatus equals to UPDATED_TRANSACTION_STATUS
        defaultProductTransactionShouldNotBeFound("transactionStatus.in=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionStatus is not null
        defaultProductTransactionShouldBeFound("transactionStatus.specified=true");

        // Get all the productTransactionList where transactionStatus is null
        defaultProductTransactionShouldNotBeFound("transactionStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultProductTransactionShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the productTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultProductTransactionShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the productTransactionList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the productTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate is not null
        defaultProductTransactionShouldBeFound("transactionDate.specified=true");

        // Get all the productTransactionList where transactionDate is null
        defaultProductTransactionShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate contains DEFAULT_TRANSACTION_DATE
        defaultProductTransactionShouldBeFound("transactionDate.contains=" + DEFAULT_TRANSACTION_DATE);

        // Get all the productTransactionList where transactionDate contains UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldNotBeFound("transactionDate.contains=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByTransactionDateNotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where transactionDate does not contain DEFAULT_TRANSACTION_DATE
        defaultProductTransactionShouldNotBeFound("transactionDate.doesNotContain=" + DEFAULT_TRANSACTION_DATE);

        // Get all the productTransactionList where transactionDate does not contain UPDATED_TRANSACTION_DATE
        defaultProductTransactionShouldBeFound("transactionDate.doesNotContain=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description equals to DEFAULT_DESCRIPTION
        defaultProductTransactionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productTransactionList where description equals to UPDATED_DESCRIPTION
        defaultProductTransactionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description not equals to DEFAULT_DESCRIPTION
        defaultProductTransactionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the productTransactionList where description not equals to UPDATED_DESCRIPTION
        defaultProductTransactionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductTransactionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productTransactionList where description equals to UPDATED_DESCRIPTION
        defaultProductTransactionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description is not null
        defaultProductTransactionShouldBeFound("description.specified=true");

        // Get all the productTransactionList where description is null
        defaultProductTransactionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description contains DEFAULT_DESCRIPTION
        defaultProductTransactionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the productTransactionList where description contains UPDATED_DESCRIPTION
        defaultProductTransactionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where description does not contain DEFAULT_DESCRIPTION
        defaultProductTransactionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the productTransactionList where description does not contain UPDATED_DESCRIPTION
        defaultProductTransactionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultProductTransactionShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productTransactionList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultProductTransactionShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productTransactionList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the productTransactionList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 is not null
        defaultProductTransactionShouldBeFound("freeField1.specified=true");

        // Get all the productTransactionList where freeField1 is null
        defaultProductTransactionShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultProductTransactionShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the productTransactionList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultProductTransactionShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the productTransactionList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultProductTransactionShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultProductTransactionShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productTransactionList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultProductTransactionShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productTransactionList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the productTransactionList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 is not null
        defaultProductTransactionShouldBeFound("freeField2.specified=true");

        // Get all the productTransactionList where freeField2 is null
        defaultProductTransactionShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultProductTransactionShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the productTransactionList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultProductTransactionShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the productTransactionList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultProductTransactionShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductTransactionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTransactionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultProductTransactionShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTransactionList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productTransactionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified is not null
        defaultProductTransactionShouldBeFound("lastModified.specified=true");

        // Get all the productTransactionList where lastModified is null
        defaultProductTransactionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultProductTransactionShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTransactionList where lastModified contains UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultProductTransactionShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTransactionList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultProductTransactionShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTransactionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTransactionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTransactionList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productTransactionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy is not null
        defaultProductTransactionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productTransactionList where lastModifiedBy is null
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductTransactionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTransactionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTransactionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductTransactionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTransactionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductTransactionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTransactionsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            securityUser = SecurityUserResourceIT.createEntity(em);
            em.persist(securityUser);
            em.flush();
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        productTransaction.setSecurityUser(securityUser);
        productTransactionRepository.saveAndFlush(productTransaction);
        Long securityUserId = securityUser.getId();

        // Get all the productTransactionList where securityUser equals to securityUserId
        defaultProductTransactionShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the productTransactionList where securityUser equals to (securityUserId + 1)
        defaultProductTransactionShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllProductTransactionsByWareHouseIsEqualToSomething() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);
        WareHouse wareHouse;
        if (TestUtil.findAll(em, WareHouse.class).isEmpty()) {
            wareHouse = WareHouseResourceIT.createEntity(em);
            em.persist(wareHouse);
            em.flush();
        } else {
            wareHouse = TestUtil.findAll(em, WareHouse.class).get(0);
        }
        em.persist(wareHouse);
        em.flush();
        productTransaction.setWareHouse(wareHouse);
        productTransactionRepository.saveAndFlush(productTransaction);
        Long wareHouseId = wareHouse.getId();

        // Get all the productTransactionList where wareHouse equals to wareHouseId
        defaultProductTransactionShouldBeFound("wareHouseId.equals=" + wareHouseId);

        // Get all the productTransactionList where wareHouse equals to (wareHouseId + 1)
        defaultProductTransactionShouldNotBeFound("wareHouseId.equals=" + (wareHouseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTransactionShouldBeFound(String filter) throws Exception {
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].refrenceId").value(hasItem(DEFAULT_REFRENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionStatus").value(hasItem(DEFAULT_TRANSACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTransactionShouldNotBeFound(String filter) throws Exception {
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductTransaction() throws Exception {
        // Get the productTransaction
        restProductTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction
        ProductTransaction updatedProductTransaction = productTransactionRepository.findById(productTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedProductTransaction are not directly saved in db
        em.detach(updatedProductTransaction);
        updatedProductTransaction
            .refrenceId(UPDATED_REFRENCE_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(updatedProductTransaction);

        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getRefrenceId()).isEqualTo(UPDATED_REFRENCE_ID);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
        assertThat(testProductTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductTransaction.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductTransaction.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductTransaction.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTransactionWithPatch() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction using partial update
        ProductTransaction partialUpdatedProductTransaction = new ProductTransaction();
        partialUpdatedProductTransaction.setId(productTransaction.getId());

        partialUpdatedProductTransaction
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getRefrenceId()).isEqualTo(DEFAULT_REFRENCE_ID);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
        assertThat(testProductTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductTransaction.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProductTransaction.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProductTransaction.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductTransactionWithPatch() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction using partial update
        ProductTransaction partialUpdatedProductTransaction = new ProductTransaction();
        partialUpdatedProductTransaction.setId(productTransaction.getId());

        partialUpdatedProductTransaction
            .refrenceId(UPDATED_REFRENCE_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .description(UPDATED_DESCRIPTION)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getRefrenceId()).isEqualTo(UPDATED_REFRENCE_ID);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
        assertThat(testProductTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductTransaction.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductTransaction.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductTransaction.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeDelete = productTransactionRepository.findAll().size();

        // Delete the productTransaction
        restProductTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
