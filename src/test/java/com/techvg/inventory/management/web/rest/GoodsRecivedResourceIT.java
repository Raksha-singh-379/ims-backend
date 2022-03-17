package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.GoodsRecived;
import com.techvg.inventory.management.domain.PurchaseQuotation;
import com.techvg.inventory.management.repository.GoodsRecivedRepository;
import com.techvg.inventory.management.service.criteria.GoodsRecivedCriteria;
import com.techvg.inventory.management.service.dto.GoodsRecivedDTO;
import com.techvg.inventory.management.service.mapper.GoodsRecivedMapper;
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
 * Integration tests for the {@link GoodsRecivedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoodsRecivedResourceIT {

    private static final Instant DEFAULT_GR_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GR_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_ORDERED = 1D;
    private static final Double UPDATED_QTY_ORDERED = 2D;
    private static final Double SMALLER_QTY_ORDERED = 1D - 1D;

    private static final Double DEFAULT_QTY_RECIEVED = 1D;
    private static final Double UPDATED_QTY_RECIEVED = 2D;
    private static final Double SMALLER_QTY_RECIEVED = 1D - 1D;

    private static final Instant DEFAULT_MANUFACTURING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MANUFACTURING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LOT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/goods-reciveds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoodsRecivedRepository goodsRecivedRepository;

    @Autowired
    private GoodsRecivedMapper goodsRecivedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoodsRecivedMockMvc;

    private GoodsRecived goodsRecived;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodsRecived createEntity(EntityManager em) {
        GoodsRecived goodsRecived = new GoodsRecived()
            .grDate(DEFAULT_GR_DATE)
            .qtyOrdered(DEFAULT_QTY_ORDERED)
            .qtyRecieved(DEFAULT_QTY_RECIEVED)
            .manufacturingDate(DEFAULT_MANUFACTURING_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .lotNo(DEFAULT_LOT_NO)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return goodsRecived;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodsRecived createUpdatedEntity(EntityManager em) {
        GoodsRecived goodsRecived = new GoodsRecived()
            .grDate(UPDATED_GR_DATE)
            .qtyOrdered(UPDATED_QTY_ORDERED)
            .qtyRecieved(UPDATED_QTY_RECIEVED)
            .manufacturingDate(UPDATED_MANUFACTURING_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .lotNo(UPDATED_LOT_NO)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return goodsRecived;
    }

    @BeforeEach
    public void initTest() {
        goodsRecived = createEntity(em);
    }

    @Test
    @Transactional
    void createGoodsRecived() throws Exception {
        int databaseSizeBeforeCreate = goodsRecivedRepository.findAll().size();
        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);
        restGoodsRecivedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeCreate + 1);
        GoodsRecived testGoodsRecived = goodsRecivedList.get(goodsRecivedList.size() - 1);
        assertThat(testGoodsRecived.getGrDate()).isEqualTo(DEFAULT_GR_DATE);
        assertThat(testGoodsRecived.getQtyOrdered()).isEqualTo(DEFAULT_QTY_ORDERED);
        assertThat(testGoodsRecived.getQtyRecieved()).isEqualTo(DEFAULT_QTY_RECIEVED);
        assertThat(testGoodsRecived.getManufacturingDate()).isEqualTo(DEFAULT_MANUFACTURING_DATE);
        assertThat(testGoodsRecived.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testGoodsRecived.getLotNo()).isEqualTo(DEFAULT_LOT_NO);
        assertThat(testGoodsRecived.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testGoodsRecived.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testGoodsRecived.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createGoodsRecivedWithExistingId() throws Exception {
        // Create the GoodsRecived with an existing ID
        goodsRecived.setId(1L);
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        int databaseSizeBeforeCreate = goodsRecivedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsRecivedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGoodsReciveds() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodsRecived.getId().intValue())))
            .andExpect(jsonPath("$.[*].grDate").value(hasItem(DEFAULT_GR_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyOrdered").value(hasItem(DEFAULT_QTY_ORDERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyRecieved").value(hasItem(DEFAULT_QTY_RECIEVED.doubleValue())))
            .andExpect(jsonPath("$.[*].manufacturingDate").value(hasItem(DEFAULT_MANUFACTURING_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getGoodsRecived() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get the goodsRecived
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL_ID, goodsRecived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goodsRecived.getId().intValue()))
            .andExpect(jsonPath("$.grDate").value(DEFAULT_GR_DATE.toString()))
            .andExpect(jsonPath("$.qtyOrdered").value(DEFAULT_QTY_ORDERED.doubleValue()))
            .andExpect(jsonPath("$.qtyRecieved").value(DEFAULT_QTY_RECIEVED.doubleValue()))
            .andExpect(jsonPath("$.manufacturingDate").value(DEFAULT_MANUFACTURING_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.lotNo").value(DEFAULT_LOT_NO))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getGoodsRecivedsByIdFiltering() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        Long id = goodsRecived.getId();

        defaultGoodsRecivedShouldBeFound("id.equals=" + id);
        defaultGoodsRecivedShouldNotBeFound("id.notEquals=" + id);

        defaultGoodsRecivedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGoodsRecivedShouldNotBeFound("id.greaterThan=" + id);

        defaultGoodsRecivedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGoodsRecivedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByGrDateIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where grDate equals to DEFAULT_GR_DATE
        defaultGoodsRecivedShouldBeFound("grDate.equals=" + DEFAULT_GR_DATE);

        // Get all the goodsRecivedList where grDate equals to UPDATED_GR_DATE
        defaultGoodsRecivedShouldNotBeFound("grDate.equals=" + UPDATED_GR_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByGrDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where grDate not equals to DEFAULT_GR_DATE
        defaultGoodsRecivedShouldNotBeFound("grDate.notEquals=" + DEFAULT_GR_DATE);

        // Get all the goodsRecivedList where grDate not equals to UPDATED_GR_DATE
        defaultGoodsRecivedShouldBeFound("grDate.notEquals=" + UPDATED_GR_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByGrDateIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where grDate in DEFAULT_GR_DATE or UPDATED_GR_DATE
        defaultGoodsRecivedShouldBeFound("grDate.in=" + DEFAULT_GR_DATE + "," + UPDATED_GR_DATE);

        // Get all the goodsRecivedList where grDate equals to UPDATED_GR_DATE
        defaultGoodsRecivedShouldNotBeFound("grDate.in=" + UPDATED_GR_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByGrDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where grDate is not null
        defaultGoodsRecivedShouldBeFound("grDate.specified=true");

        // Get all the goodsRecivedList where grDate is null
        defaultGoodsRecivedShouldNotBeFound("grDate.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered equals to DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.equals=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered equals to UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.equals=" + UPDATED_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered not equals to DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.notEquals=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered not equals to UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.notEquals=" + UPDATED_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered in DEFAULT_QTY_ORDERED or UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.in=" + DEFAULT_QTY_ORDERED + "," + UPDATED_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered equals to UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.in=" + UPDATED_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered is not null
        defaultGoodsRecivedShouldBeFound("qtyOrdered.specified=true");

        // Get all the goodsRecivedList where qtyOrdered is null
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered is greater than or equal to DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.greaterThanOrEqual=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered is greater than or equal to UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.greaterThanOrEqual=" + UPDATED_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered is less than or equal to DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.lessThanOrEqual=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered is less than or equal to SMALLER_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.lessThanOrEqual=" + SMALLER_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsLessThanSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered is less than DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.lessThan=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered is less than UPDATED_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.lessThan=" + UPDATED_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyOrderedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyOrdered is greater than DEFAULT_QTY_ORDERED
        defaultGoodsRecivedShouldNotBeFound("qtyOrdered.greaterThan=" + DEFAULT_QTY_ORDERED);

        // Get all the goodsRecivedList where qtyOrdered is greater than SMALLER_QTY_ORDERED
        defaultGoodsRecivedShouldBeFound("qtyOrdered.greaterThan=" + SMALLER_QTY_ORDERED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved equals to DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.equals=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved equals to UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.equals=" + UPDATED_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved not equals to DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.notEquals=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved not equals to UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.notEquals=" + UPDATED_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved in DEFAULT_QTY_RECIEVED or UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.in=" + DEFAULT_QTY_RECIEVED + "," + UPDATED_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved equals to UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.in=" + UPDATED_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved is not null
        defaultGoodsRecivedShouldBeFound("qtyRecieved.specified=true");

        // Get all the goodsRecivedList where qtyRecieved is null
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved is greater than or equal to DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.greaterThanOrEqual=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved is greater than or equal to UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.greaterThanOrEqual=" + UPDATED_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved is less than or equal to DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.lessThanOrEqual=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved is less than or equal to SMALLER_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.lessThanOrEqual=" + SMALLER_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsLessThanSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved is less than DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.lessThan=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved is less than UPDATED_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.lessThan=" + UPDATED_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByQtyRecievedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where qtyRecieved is greater than DEFAULT_QTY_RECIEVED
        defaultGoodsRecivedShouldNotBeFound("qtyRecieved.greaterThan=" + DEFAULT_QTY_RECIEVED);

        // Get all the goodsRecivedList where qtyRecieved is greater than SMALLER_QTY_RECIEVED
        defaultGoodsRecivedShouldBeFound("qtyRecieved.greaterThan=" + SMALLER_QTY_RECIEVED);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByManufacturingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where manufacturingDate equals to DEFAULT_MANUFACTURING_DATE
        defaultGoodsRecivedShouldBeFound("manufacturingDate.equals=" + DEFAULT_MANUFACTURING_DATE);

        // Get all the goodsRecivedList where manufacturingDate equals to UPDATED_MANUFACTURING_DATE
        defaultGoodsRecivedShouldNotBeFound("manufacturingDate.equals=" + UPDATED_MANUFACTURING_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByManufacturingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where manufacturingDate not equals to DEFAULT_MANUFACTURING_DATE
        defaultGoodsRecivedShouldNotBeFound("manufacturingDate.notEquals=" + DEFAULT_MANUFACTURING_DATE);

        // Get all the goodsRecivedList where manufacturingDate not equals to UPDATED_MANUFACTURING_DATE
        defaultGoodsRecivedShouldBeFound("manufacturingDate.notEquals=" + UPDATED_MANUFACTURING_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByManufacturingDateIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where manufacturingDate in DEFAULT_MANUFACTURING_DATE or UPDATED_MANUFACTURING_DATE
        defaultGoodsRecivedShouldBeFound("manufacturingDate.in=" + DEFAULT_MANUFACTURING_DATE + "," + UPDATED_MANUFACTURING_DATE);

        // Get all the goodsRecivedList where manufacturingDate equals to UPDATED_MANUFACTURING_DATE
        defaultGoodsRecivedShouldNotBeFound("manufacturingDate.in=" + UPDATED_MANUFACTURING_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByManufacturingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where manufacturingDate is not null
        defaultGoodsRecivedShouldBeFound("manufacturingDate.specified=true");

        // Get all the goodsRecivedList where manufacturingDate is null
        defaultGoodsRecivedShouldNotBeFound("manufacturingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultGoodsRecivedShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the goodsRecivedList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultGoodsRecivedShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultGoodsRecivedShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the goodsRecivedList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultGoodsRecivedShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultGoodsRecivedShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the goodsRecivedList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultGoodsRecivedShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where expiryDate is not null
        defaultGoodsRecivedShouldBeFound("expiryDate.specified=true");

        // Get all the goodsRecivedList where expiryDate is null
        defaultGoodsRecivedShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo equals to DEFAULT_LOT_NO
        defaultGoodsRecivedShouldBeFound("lotNo.equals=" + DEFAULT_LOT_NO);

        // Get all the goodsRecivedList where lotNo equals to UPDATED_LOT_NO
        defaultGoodsRecivedShouldNotBeFound("lotNo.equals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo not equals to DEFAULT_LOT_NO
        defaultGoodsRecivedShouldNotBeFound("lotNo.notEquals=" + DEFAULT_LOT_NO);

        // Get all the goodsRecivedList where lotNo not equals to UPDATED_LOT_NO
        defaultGoodsRecivedShouldBeFound("lotNo.notEquals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoIsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo in DEFAULT_LOT_NO or UPDATED_LOT_NO
        defaultGoodsRecivedShouldBeFound("lotNo.in=" + DEFAULT_LOT_NO + "," + UPDATED_LOT_NO);

        // Get all the goodsRecivedList where lotNo equals to UPDATED_LOT_NO
        defaultGoodsRecivedShouldNotBeFound("lotNo.in=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo is not null
        defaultGoodsRecivedShouldBeFound("lotNo.specified=true");

        // Get all the goodsRecivedList where lotNo is null
        defaultGoodsRecivedShouldNotBeFound("lotNo.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo contains DEFAULT_LOT_NO
        defaultGoodsRecivedShouldBeFound("lotNo.contains=" + DEFAULT_LOT_NO);

        // Get all the goodsRecivedList where lotNo contains UPDATED_LOT_NO
        defaultGoodsRecivedShouldNotBeFound("lotNo.contains=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByLotNoNotContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where lotNo does not contain DEFAULT_LOT_NO
        defaultGoodsRecivedShouldNotBeFound("lotNo.doesNotContain=" + DEFAULT_LOT_NO);

        // Get all the goodsRecivedList where lotNo does not contain UPDATED_LOT_NO
        defaultGoodsRecivedShouldBeFound("lotNo.doesNotContain=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultGoodsRecivedShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the goodsRecivedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultGoodsRecivedShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the goodsRecivedList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the goodsRecivedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 is not null
        defaultGoodsRecivedShouldBeFound("freeField1.specified=true");

        // Get all the goodsRecivedList where freeField1 is null
        defaultGoodsRecivedShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultGoodsRecivedShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the goodsRecivedList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultGoodsRecivedShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the goodsRecivedList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultGoodsRecivedShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultGoodsRecivedShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the goodsRecivedList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultGoodsRecivedShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the goodsRecivedList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the goodsRecivedList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 is not null
        defaultGoodsRecivedShouldBeFound("freeField2.specified=true");

        // Get all the goodsRecivedList where freeField2 is null
        defaultGoodsRecivedShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultGoodsRecivedShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the goodsRecivedList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultGoodsRecivedShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the goodsRecivedList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultGoodsRecivedShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultGoodsRecivedShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the goodsRecivedList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultGoodsRecivedShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the goodsRecivedList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the goodsRecivedList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 is not null
        defaultGoodsRecivedShouldBeFound("freeField3.specified=true");

        // Get all the goodsRecivedList where freeField3 is null
        defaultGoodsRecivedShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultGoodsRecivedShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the goodsRecivedList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        // Get all the goodsRecivedList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultGoodsRecivedShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the goodsRecivedList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultGoodsRecivedShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGoodsRecivedsByPurchaseQuotationIsEqualToSomething() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);
        PurchaseQuotation purchaseQuotation;
        if (TestUtil.findAll(em, PurchaseQuotation.class).isEmpty()) {
            purchaseQuotation = PurchaseQuotationResourceIT.createEntity(em);
            em.persist(purchaseQuotation);
            em.flush();
        } else {
            purchaseQuotation = TestUtil.findAll(em, PurchaseQuotation.class).get(0);
        }
        em.persist(purchaseQuotation);
        em.flush();
        goodsRecived.setPurchaseQuotation(purchaseQuotation);
        goodsRecivedRepository.saveAndFlush(goodsRecived);
        Long purchaseQuotationId = purchaseQuotation.getId();

        // Get all the goodsRecivedList where purchaseQuotation equals to purchaseQuotationId
        defaultGoodsRecivedShouldBeFound("purchaseQuotationId.equals=" + purchaseQuotationId);

        // Get all the goodsRecivedList where purchaseQuotation equals to (purchaseQuotationId + 1)
        defaultGoodsRecivedShouldNotBeFound("purchaseQuotationId.equals=" + (purchaseQuotationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGoodsRecivedShouldBeFound(String filter) throws Exception {
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodsRecived.getId().intValue())))
            .andExpect(jsonPath("$.[*].grDate").value(hasItem(DEFAULT_GR_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyOrdered").value(hasItem(DEFAULT_QTY_ORDERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyRecieved").value(hasItem(DEFAULT_QTY_RECIEVED.doubleValue())))
            .andExpect(jsonPath("$.[*].manufacturingDate").value(hasItem(DEFAULT_MANUFACTURING_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGoodsRecivedShouldNotBeFound(String filter) throws Exception {
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoodsRecivedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGoodsRecived() throws Exception {
        // Get the goodsRecived
        restGoodsRecivedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGoodsRecived() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();

        // Update the goodsRecived
        GoodsRecived updatedGoodsRecived = goodsRecivedRepository.findById(goodsRecived.getId()).get();
        // Disconnect from session so that the updates on updatedGoodsRecived are not directly saved in db
        em.detach(updatedGoodsRecived);
        updatedGoodsRecived
            .grDate(UPDATED_GR_DATE)
            .qtyOrdered(UPDATED_QTY_ORDERED)
            .qtyRecieved(UPDATED_QTY_RECIEVED)
            .manufacturingDate(UPDATED_MANUFACTURING_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .lotNo(UPDATED_LOT_NO)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(updatedGoodsRecived);

        restGoodsRecivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goodsRecivedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
        GoodsRecived testGoodsRecived = goodsRecivedList.get(goodsRecivedList.size() - 1);
        assertThat(testGoodsRecived.getGrDate()).isEqualTo(UPDATED_GR_DATE);
        assertThat(testGoodsRecived.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testGoodsRecived.getQtyRecieved()).isEqualTo(UPDATED_QTY_RECIEVED);
        assertThat(testGoodsRecived.getManufacturingDate()).isEqualTo(UPDATED_MANUFACTURING_DATE);
        assertThat(testGoodsRecived.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testGoodsRecived.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testGoodsRecived.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGoodsRecived.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGoodsRecived.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goodsRecivedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoodsRecivedWithPatch() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();

        // Update the goodsRecived using partial update
        GoodsRecived partialUpdatedGoodsRecived = new GoodsRecived();
        partialUpdatedGoodsRecived.setId(goodsRecived.getId());

        partialUpdatedGoodsRecived
            .qtyOrdered(UPDATED_QTY_ORDERED)
            .qtyRecieved(UPDATED_QTY_RECIEVED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restGoodsRecivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoodsRecived.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoodsRecived))
            )
            .andExpect(status().isOk());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
        GoodsRecived testGoodsRecived = goodsRecivedList.get(goodsRecivedList.size() - 1);
        assertThat(testGoodsRecived.getGrDate()).isEqualTo(DEFAULT_GR_DATE);
        assertThat(testGoodsRecived.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testGoodsRecived.getQtyRecieved()).isEqualTo(UPDATED_QTY_RECIEVED);
        assertThat(testGoodsRecived.getManufacturingDate()).isEqualTo(DEFAULT_MANUFACTURING_DATE);
        assertThat(testGoodsRecived.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testGoodsRecived.getLotNo()).isEqualTo(DEFAULT_LOT_NO);
        assertThat(testGoodsRecived.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGoodsRecived.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGoodsRecived.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateGoodsRecivedWithPatch() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();

        // Update the goodsRecived using partial update
        GoodsRecived partialUpdatedGoodsRecived = new GoodsRecived();
        partialUpdatedGoodsRecived.setId(goodsRecived.getId());

        partialUpdatedGoodsRecived
            .grDate(UPDATED_GR_DATE)
            .qtyOrdered(UPDATED_QTY_ORDERED)
            .qtyRecieved(UPDATED_QTY_RECIEVED)
            .manufacturingDate(UPDATED_MANUFACTURING_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .lotNo(UPDATED_LOT_NO)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restGoodsRecivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoodsRecived.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoodsRecived))
            )
            .andExpect(status().isOk());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
        GoodsRecived testGoodsRecived = goodsRecivedList.get(goodsRecivedList.size() - 1);
        assertThat(testGoodsRecived.getGrDate()).isEqualTo(UPDATED_GR_DATE);
        assertThat(testGoodsRecived.getQtyOrdered()).isEqualTo(UPDATED_QTY_ORDERED);
        assertThat(testGoodsRecived.getQtyRecieved()).isEqualTo(UPDATED_QTY_RECIEVED);
        assertThat(testGoodsRecived.getManufacturingDate()).isEqualTo(UPDATED_MANUFACTURING_DATE);
        assertThat(testGoodsRecived.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testGoodsRecived.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testGoodsRecived.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGoodsRecived.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGoodsRecived.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goodsRecivedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoodsRecived() throws Exception {
        int databaseSizeBeforeUpdate = goodsRecivedRepository.findAll().size();
        goodsRecived.setId(count.incrementAndGet());

        // Create the GoodsRecived
        GoodsRecivedDTO goodsRecivedDTO = goodsRecivedMapper.toDto(goodsRecived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoodsRecivedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goodsRecivedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoodsRecived in the database
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoodsRecived() throws Exception {
        // Initialize the database
        goodsRecivedRepository.saveAndFlush(goodsRecived);

        int databaseSizeBeforeDelete = goodsRecivedRepository.findAll().size();

        // Delete the goodsRecived
        restGoodsRecivedMockMvc
            .perform(delete(ENTITY_API_URL_ID, goodsRecived.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoodsRecived> goodsRecivedList = goodsRecivedRepository.findAll();
        assertThat(goodsRecivedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
