package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.ConsumptionDetails;
import com.techvg.inventory.management.domain.ProductInventory;
import com.techvg.inventory.management.domain.Project;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.repository.ConsumptionDetailsRepository;
import com.techvg.inventory.management.service.criteria.ConsumptionDetailsCriteria;
import com.techvg.inventory.management.service.dto.ConsumptionDetailsDTO;
import com.techvg.inventory.management.service.mapper.ConsumptionDetailsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ConsumptionDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsumptionDetailsResourceIT {

    private static final Instant DEFAULT_COMSUMPTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMSUMPTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_CONSUMED = 1D;
    private static final Double UPDATED_QTY_CONSUMED = 2D;
    private static final Double SMALLER_QTY_CONSUMED = 1D - 1D;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/consumption-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsumptionDetailsRepository consumptionDetailsRepository;

    @Autowired
    private ConsumptionDetailsMapper consumptionDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsumptionDetailsMockMvc;

    private ConsumptionDetails consumptionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionDetails createEntity(EntityManager em) {
        ConsumptionDetails consumptionDetails = new ConsumptionDetails()
            .comsumptionDate(DEFAULT_COMSUMPTION_DATE)
            .qtyConsumed(DEFAULT_QTY_CONSUMED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return consumptionDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionDetails createUpdatedEntity(EntityManager em) {
        ConsumptionDetails consumptionDetails = new ConsumptionDetails()
            .comsumptionDate(UPDATED_COMSUMPTION_DATE)
            .qtyConsumed(UPDATED_QTY_CONSUMED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return consumptionDetails;
    }

    @BeforeEach
    public void initTest() {
        consumptionDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createConsumptionDetails() throws Exception {
        int databaseSizeBeforeCreate = consumptionDetailsRepository.findAll().size();
        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);
        restConsumptionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionDetails testConsumptionDetails = consumptionDetailsList.get(consumptionDetailsList.size() - 1);
        assertThat(testConsumptionDetails.getComsumptionDate()).isEqualTo(DEFAULT_COMSUMPTION_DATE);
        assertThat(testConsumptionDetails.getQtyConsumed()).isEqualTo(DEFAULT_QTY_CONSUMED);
        assertThat(testConsumptionDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testConsumptionDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testConsumptionDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testConsumptionDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createConsumptionDetailsWithExistingId() throws Exception {
        // Create the ConsumptionDetails with an existing ID
        consumptionDetails.setId(1L);
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        int databaseSizeBeforeCreate = consumptionDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConsumptionDetails() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].comsumptionDate").value(hasItem(DEFAULT_COMSUMPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyConsumed").value(hasItem(DEFAULT_QTY_CONSUMED.doubleValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getConsumptionDetails() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get the consumptionDetails
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, consumptionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionDetails.getId().intValue()))
            .andExpect(jsonPath("$.comsumptionDate").value(DEFAULT_COMSUMPTION_DATE.toString()))
            .andExpect(jsonPath("$.qtyConsumed").value(DEFAULT_QTY_CONSUMED.doubleValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getConsumptionDetailsByIdFiltering() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        Long id = consumptionDetails.getId();

        defaultConsumptionDetailsShouldBeFound("id.equals=" + id);
        defaultConsumptionDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultConsumptionDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsumptionDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultConsumptionDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsumptionDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByComsumptionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where comsumptionDate equals to DEFAULT_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldBeFound("comsumptionDate.equals=" + DEFAULT_COMSUMPTION_DATE);

        // Get all the consumptionDetailsList where comsumptionDate equals to UPDATED_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldNotBeFound("comsumptionDate.equals=" + UPDATED_COMSUMPTION_DATE);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByComsumptionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where comsumptionDate not equals to DEFAULT_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldNotBeFound("comsumptionDate.notEquals=" + DEFAULT_COMSUMPTION_DATE);

        // Get all the consumptionDetailsList where comsumptionDate not equals to UPDATED_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldBeFound("comsumptionDate.notEquals=" + UPDATED_COMSUMPTION_DATE);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByComsumptionDateIsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where comsumptionDate in DEFAULT_COMSUMPTION_DATE or UPDATED_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldBeFound("comsumptionDate.in=" + DEFAULT_COMSUMPTION_DATE + "," + UPDATED_COMSUMPTION_DATE);

        // Get all the consumptionDetailsList where comsumptionDate equals to UPDATED_COMSUMPTION_DATE
        defaultConsumptionDetailsShouldNotBeFound("comsumptionDate.in=" + UPDATED_COMSUMPTION_DATE);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByComsumptionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where comsumptionDate is not null
        defaultConsumptionDetailsShouldBeFound("comsumptionDate.specified=true");

        // Get all the consumptionDetailsList where comsumptionDate is null
        defaultConsumptionDetailsShouldNotBeFound("comsumptionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed equals to DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.equals=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed equals to UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.equals=" + UPDATED_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed not equals to DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.notEquals=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed not equals to UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.notEquals=" + UPDATED_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed in DEFAULT_QTY_CONSUMED or UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.in=" + DEFAULT_QTY_CONSUMED + "," + UPDATED_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed equals to UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.in=" + UPDATED_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed is not null
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.specified=true");

        // Get all the consumptionDetailsList where qtyConsumed is null
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed is greater than or equal to DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.greaterThanOrEqual=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed is greater than or equal to UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.greaterThanOrEqual=" + UPDATED_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed is less than or equal to DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.lessThanOrEqual=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed is less than or equal to SMALLER_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.lessThanOrEqual=" + SMALLER_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsLessThanSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed is less than DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.lessThan=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed is less than UPDATED_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.lessThan=" + UPDATED_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByQtyConsumedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where qtyConsumed is greater than DEFAULT_QTY_CONSUMED
        defaultConsumptionDetailsShouldNotBeFound("qtyConsumed.greaterThan=" + DEFAULT_QTY_CONSUMED);

        // Get all the consumptionDetailsList where qtyConsumed is greater than SMALLER_QTY_CONSUMED
        defaultConsumptionDetailsShouldBeFound("qtyConsumed.greaterThan=" + SMALLER_QTY_CONSUMED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultConsumptionDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the consumptionDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultConsumptionDetailsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the consumptionDetailsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the consumptionDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 is not null
        defaultConsumptionDetailsShouldBeFound("freeField1.specified=true");

        // Get all the consumptionDetailsList where freeField1 is null
        defaultConsumptionDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultConsumptionDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the consumptionDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultConsumptionDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the consumptionDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultConsumptionDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultConsumptionDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the consumptionDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultConsumptionDetailsShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the consumptionDetailsList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the consumptionDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 is not null
        defaultConsumptionDetailsShouldBeFound("freeField2.specified=true");

        // Get all the consumptionDetailsList where freeField2 is null
        defaultConsumptionDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultConsumptionDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the consumptionDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultConsumptionDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the consumptionDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultConsumptionDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultConsumptionDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the consumptionDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultConsumptionDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the consumptionDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the consumptionDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified is not null
        defaultConsumptionDetailsShouldBeFound("lastModified.specified=true");

        // Get all the consumptionDetailsList where lastModified is null
        defaultConsumptionDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultConsumptionDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the consumptionDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultConsumptionDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the consumptionDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultConsumptionDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the consumptionDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the consumptionDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the consumptionDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy is not null
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the consumptionDetailsList where lastModifiedBy is null
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the consumptionDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        // Get all the consumptionDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the consumptionDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultConsumptionDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
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
        consumptionDetails.setSecurityUser(securityUser);
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
        Long securityUserId = securityUser.getId();

        // Get all the consumptionDetailsList where securityUser equals to securityUserId
        defaultConsumptionDetailsShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the consumptionDetailsList where securityUser equals to (securityUserId + 1)
        defaultConsumptionDetailsShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        em.persist(project);
        em.flush();
        consumptionDetails.setProject(project);
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
        Long projectId = project.getId();

        // Get all the consumptionDetailsList where project equals to projectId
        defaultConsumptionDetailsShouldBeFound("projectId.equals=" + projectId);

        // Get all the consumptionDetailsList where project equals to (projectId + 1)
        defaultConsumptionDetailsShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    @Test
    @Transactional
    void getAllConsumptionDetailsByProductInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
        ProductInventory productInventory;
        if (TestUtil.findAll(em, ProductInventory.class).isEmpty()) {
            productInventory = ProductInventoryResourceIT.createEntity(em);
            em.persist(productInventory);
            em.flush();
        } else {
            productInventory = TestUtil.findAll(em, ProductInventory.class).get(0);
        }
        em.persist(productInventory);
        em.flush();
        consumptionDetails.setProductInventory(productInventory);
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);
        Long productInventoryId = productInventory.getId();

        // Get all the consumptionDetailsList where productInventory equals to productInventoryId
        defaultConsumptionDetailsShouldBeFound("productInventoryId.equals=" + productInventoryId);

        // Get all the consumptionDetailsList where productInventory equals to (productInventoryId + 1)
        defaultConsumptionDetailsShouldNotBeFound("productInventoryId.equals=" + (productInventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsumptionDetailsShouldBeFound(String filter) throws Exception {
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].comsumptionDate").value(hasItem(DEFAULT_COMSUMPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyConsumed").value(hasItem(DEFAULT_QTY_CONSUMED.doubleValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsumptionDetailsShouldNotBeFound(String filter) throws Exception {
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsumptionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConsumptionDetails() throws Exception {
        // Get the consumptionDetails
        restConsumptionDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConsumptionDetails() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();

        // Update the consumptionDetails
        ConsumptionDetails updatedConsumptionDetails = consumptionDetailsRepository.findById(consumptionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionDetails are not directly saved in db
        em.detach(updatedConsumptionDetails);
        updatedConsumptionDetails
            .comsumptionDate(UPDATED_COMSUMPTION_DATE)
            .qtyConsumed(UPDATED_QTY_CONSUMED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(updatedConsumptionDetails);

        restConsumptionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumptionDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionDetails testConsumptionDetails = consumptionDetailsList.get(consumptionDetailsList.size() - 1);
        assertThat(testConsumptionDetails.getComsumptionDate()).isEqualTo(UPDATED_COMSUMPTION_DATE);
        assertThat(testConsumptionDetails.getQtyConsumed()).isEqualTo(UPDATED_QTY_CONSUMED);
        assertThat(testConsumptionDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testConsumptionDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testConsumptionDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testConsumptionDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumptionDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsumptionDetailsWithPatch() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();

        // Update the consumptionDetails using partial update
        ConsumptionDetails partialUpdatedConsumptionDetails = new ConsumptionDetails();
        partialUpdatedConsumptionDetails.setId(consumptionDetails.getId());

        partialUpdatedConsumptionDetails.comsumptionDate(UPDATED_COMSUMPTION_DATE).qtyConsumed(UPDATED_QTY_CONSUMED);

        restConsumptionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumptionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumptionDetails))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionDetails testConsumptionDetails = consumptionDetailsList.get(consumptionDetailsList.size() - 1);
        assertThat(testConsumptionDetails.getComsumptionDate()).isEqualTo(UPDATED_COMSUMPTION_DATE);
        assertThat(testConsumptionDetails.getQtyConsumed()).isEqualTo(UPDATED_QTY_CONSUMED);
        assertThat(testConsumptionDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testConsumptionDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testConsumptionDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testConsumptionDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateConsumptionDetailsWithPatch() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();

        // Update the consumptionDetails using partial update
        ConsumptionDetails partialUpdatedConsumptionDetails = new ConsumptionDetails();
        partialUpdatedConsumptionDetails.setId(consumptionDetails.getId());

        partialUpdatedConsumptionDetails
            .comsumptionDate(UPDATED_COMSUMPTION_DATE)
            .qtyConsumed(UPDATED_QTY_CONSUMED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restConsumptionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumptionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsumptionDetails))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionDetails testConsumptionDetails = consumptionDetailsList.get(consumptionDetailsList.size() - 1);
        assertThat(testConsumptionDetails.getComsumptionDate()).isEqualTo(UPDATED_COMSUMPTION_DATE);
        assertThat(testConsumptionDetails.getQtyConsumed()).isEqualTo(UPDATED_QTY_CONSUMED);
        assertThat(testConsumptionDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testConsumptionDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testConsumptionDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testConsumptionDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consumptionDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsumptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDetailsRepository.findAll().size();
        consumptionDetails.setId(count.incrementAndGet());

        // Create the ConsumptionDetails
        ConsumptionDetailsDTO consumptionDetailsDTO = consumptionDetailsMapper.toDto(consumptionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consumptionDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsumptionDetails in the database
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsumptionDetails() throws Exception {
        // Initialize the database
        consumptionDetailsRepository.saveAndFlush(consumptionDetails);

        int databaseSizeBeforeDelete = consumptionDetailsRepository.findAll().size();

        // Delete the consumptionDetails
        restConsumptionDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, consumptionDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionDetails> consumptionDetailsList = consumptionDetailsRepository.findAll();
        assertThat(consumptionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
