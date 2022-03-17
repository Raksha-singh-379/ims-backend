package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.Product;
import com.techvg.inventory.management.domain.PurchaseQuotation;
import com.techvg.inventory.management.domain.PurchaseQuotationDetails;
import com.techvg.inventory.management.repository.PurchaseQuotationDetailsRepository;
import com.techvg.inventory.management.service.criteria.PurchaseQuotationDetailsCriteria;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDetailsDTO;
import com.techvg.inventory.management.service.mapper.PurchaseQuotationDetailsMapper;
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
 * Integration tests for the {@link PurchaseQuotationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchaseQuotationDetailsResourceIT {

    private static final Double DEFAULT_QTYORDERED = 1D;
    private static final Double UPDATED_QTYORDERED = 2D;
    private static final Double SMALLER_QTYORDERED = 1D - 1D;

    private static final Integer DEFAULT_GST_TAX_PERCENTAGE = 1;
    private static final Integer UPDATED_GST_TAX_PERCENTAGE = 2;
    private static final Integer SMALLER_GST_TAX_PERCENTAGE = 1 - 1;

    private static final Double DEFAULT_PRICE_PER_UNIT = 1D;
    private static final Double UPDATED_PRICE_PER_UNIT = 2D;
    private static final Double SMALLER_PRICE_PER_UNIT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;
    private static final Double SMALLER_TOTAL_PRICE = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/purchase-quotation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchaseQuotationDetailsRepository purchaseQuotationDetailsRepository;

    @Autowired
    private PurchaseQuotationDetailsMapper purchaseQuotationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseQuotationDetailsMockMvc;

    private PurchaseQuotationDetails purchaseQuotationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseQuotationDetails createEntity(EntityManager em) {
        PurchaseQuotationDetails purchaseQuotationDetails = new PurchaseQuotationDetails()
            .qtyordered(DEFAULT_QTYORDERED)
            .gstTaxPercentage(DEFAULT_GST_TAX_PERCENTAGE)
            .pricePerUnit(DEFAULT_PRICE_PER_UNIT)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2);
        return purchaseQuotationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseQuotationDetails createUpdatedEntity(EntityManager em) {
        PurchaseQuotationDetails purchaseQuotationDetails = new PurchaseQuotationDetails()
            .qtyordered(UPDATED_QTYORDERED)
            .gstTaxPercentage(UPDATED_GST_TAX_PERCENTAGE)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);
        return purchaseQuotationDetails;
    }

    @BeforeEach
    public void initTest() {
        purchaseQuotationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeCreate = purchaseQuotationDetailsRepository.findAll().size();
        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);
        restPurchaseQuotationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseQuotationDetails testPurchaseQuotationDetails = purchaseQuotationDetailsList.get(purchaseQuotationDetailsList.size() - 1);
        assertThat(testPurchaseQuotationDetails.getQtyordered()).isEqualTo(DEFAULT_QTYORDERED);
        assertThat(testPurchaseQuotationDetails.getGstTaxPercentage()).isEqualTo(DEFAULT_GST_TAX_PERCENTAGE);
        assertThat(testPurchaseQuotationDetails.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testPurchaseQuotationDetails.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testPurchaseQuotationDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPurchaseQuotationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPurchaseQuotationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchaseQuotationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPurchaseQuotationDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void createPurchaseQuotationDetailsWithExistingId() throws Exception {
        // Create the PurchaseQuotationDetails with an existing ID
        purchaseQuotationDetails.setId(1L);
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        int databaseSizeBeforeCreate = purchaseQuotationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseQuotationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetails() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseQuotationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtyordered").value(hasItem(DEFAULT_QTYORDERED.doubleValue())))
            .andExpect(jsonPath("$.[*].gstTaxPercentage").value(hasItem(DEFAULT_GST_TAX_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)));
    }

    @Test
    @Transactional
    void getPurchaseQuotationDetails() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get the purchaseQuotationDetails
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, purchaseQuotationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseQuotationDetails.getId().intValue()))
            .andExpect(jsonPath("$.qtyordered").value(DEFAULT_QTYORDERED.doubleValue()))
            .andExpect(jsonPath("$.gstTaxPercentage").value(DEFAULT_GST_TAX_PERCENTAGE))
            .andExpect(jsonPath("$.pricePerUnit").value(DEFAULT_PRICE_PER_UNIT.doubleValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2));
    }

    @Test
    @Transactional
    void getPurchaseQuotationDetailsByIdFiltering() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        Long id = purchaseQuotationDetails.getId();

        defaultPurchaseQuotationDetailsShouldBeFound("id.equals=" + id);
        defaultPurchaseQuotationDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultPurchaseQuotationDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPurchaseQuotationDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultPurchaseQuotationDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPurchaseQuotationDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered equals to DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.equals=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered equals to UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.equals=" + UPDATED_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered not equals to DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.notEquals=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered not equals to UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.notEquals=" + UPDATED_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered in DEFAULT_QTYORDERED or UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.in=" + DEFAULT_QTYORDERED + "," + UPDATED_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered equals to UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.in=" + UPDATED_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered is not null
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.specified=true");

        // Get all the purchaseQuotationDetailsList where qtyordered is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered is greater than or equal to DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.greaterThanOrEqual=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered is greater than or equal to UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.greaterThanOrEqual=" + UPDATED_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered is less than or equal to DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.lessThanOrEqual=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered is less than or equal to SMALLER_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.lessThanOrEqual=" + SMALLER_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered is less than DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.lessThan=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered is less than UPDATED_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.lessThan=" + UPDATED_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByQtyorderedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where qtyordered is greater than DEFAULT_QTYORDERED
        defaultPurchaseQuotationDetailsShouldNotBeFound("qtyordered.greaterThan=" + DEFAULT_QTYORDERED);

        // Get all the purchaseQuotationDetailsList where qtyordered is greater than SMALLER_QTYORDERED
        defaultPurchaseQuotationDetailsShouldBeFound("qtyordered.greaterThan=" + SMALLER_QTYORDERED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage equals to DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.equals=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage equals to UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.equals=" + UPDATED_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage not equals to DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.notEquals=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage not equals to UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.notEquals=" + UPDATED_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage in DEFAULT_GST_TAX_PERCENTAGE or UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound(
            "gstTaxPercentage.in=" + DEFAULT_GST_TAX_PERCENTAGE + "," + UPDATED_GST_TAX_PERCENTAGE
        );

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage equals to UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.in=" + UPDATED_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is not null
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.specified=true");

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is greater than or equal to DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.greaterThanOrEqual=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is greater than or equal to UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.greaterThanOrEqual=" + UPDATED_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is less than or equal to DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.lessThanOrEqual=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is less than or equal to SMALLER_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.lessThanOrEqual=" + SMALLER_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is less than DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.lessThan=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is less than UPDATED_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.lessThan=" + UPDATED_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByGstTaxPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is greater than DEFAULT_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldNotBeFound("gstTaxPercentage.greaterThan=" + DEFAULT_GST_TAX_PERCENTAGE);

        // Get all the purchaseQuotationDetailsList where gstTaxPercentage is greater than SMALLER_GST_TAX_PERCENTAGE
        defaultPurchaseQuotationDetailsShouldBeFound("gstTaxPercentage.greaterThan=" + SMALLER_GST_TAX_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit equals to DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.equals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.equals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit not equals to DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.notEquals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit not equals to UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.notEquals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit in DEFAULT_PRICE_PER_UNIT or UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.in=" + DEFAULT_PRICE_PER_UNIT + "," + UPDATED_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.in=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is not null
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.specified=true");

        // Get all the purchaseQuotationDetailsList where pricePerUnit is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is greater than or equal to DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.greaterThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is greater than or equal to UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.greaterThanOrEqual=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is less than or equal to DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.lessThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is less than or equal to SMALLER_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.lessThanOrEqual=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is less than DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.lessThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is less than UPDATED_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.lessThan=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPricePerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is greater than DEFAULT_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldNotBeFound("pricePerUnit.greaterThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the purchaseQuotationDetailsList where pricePerUnit is greater than SMALLER_PRICE_PER_UNIT
        defaultPurchaseQuotationDetailsShouldBeFound("pricePerUnit.greaterThan=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice is not null
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.specified=true");

        // Get all the purchaseQuotationDetailsList where totalPrice is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the purchaseQuotationDetailsList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultPurchaseQuotationDetailsShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount equals to DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount equals to UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount not equals to DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount not equals to UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount equals to UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount is not null
        defaultPurchaseQuotationDetailsShouldBeFound("discount.specified=true");

        // Get all the purchaseQuotationDetailsList where discount is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount is less than or equal to SMALLER_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount is less than DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount is less than UPDATED_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where discount is greater than DEFAULT_DISCOUNT
        defaultPurchaseQuotationDetailsShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the purchaseQuotationDetailsList where discount is greater than SMALLER_DISCOUNT
        defaultPurchaseQuotationDetailsShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseQuotationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseQuotationDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the purchaseQuotationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified is not null
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.specified=true");

        // Get all the purchaseQuotationDetailsList where lastModified is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseQuotationDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseQuotationDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPurchaseQuotationDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy is not null
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the purchaseQuotationDetailsList where lastModifiedBy is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseQuotationDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPurchaseQuotationDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseQuotationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseQuotationDetailsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the purchaseQuotationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 is not null
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.specified=true");

        // Get all the purchaseQuotationDetailsList where freeField1 is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseQuotationDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseQuotationDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultPurchaseQuotationDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseQuotationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseQuotationDetailsList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the purchaseQuotationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 is not null
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.specified=true");

        // Get all the purchaseQuotationDetailsList where freeField2 is null
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseQuotationDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        // Get all the purchaseQuotationDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseQuotationDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultPurchaseQuotationDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        purchaseQuotationDetails.addProduct(product);
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);
        Long productId = product.getId();

        // Get all the purchaseQuotationDetailsList where product equals to productId
        defaultPurchaseQuotationDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the purchaseQuotationDetailsList where product equals to (productId + 1)
        defaultPurchaseQuotationDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseQuotationDetailsByPurchaseQuotationIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);
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
        purchaseQuotationDetails.setPurchaseQuotation(purchaseQuotation);
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);
        Long purchaseQuotationId = purchaseQuotation.getId();

        // Get all the purchaseQuotationDetailsList where purchaseQuotation equals to purchaseQuotationId
        defaultPurchaseQuotationDetailsShouldBeFound("purchaseQuotationId.equals=" + purchaseQuotationId);

        // Get all the purchaseQuotationDetailsList where purchaseQuotation equals to (purchaseQuotationId + 1)
        defaultPurchaseQuotationDetailsShouldNotBeFound("purchaseQuotationId.equals=" + (purchaseQuotationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurchaseQuotationDetailsShouldBeFound(String filter) throws Exception {
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseQuotationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtyordered").value(hasItem(DEFAULT_QTYORDERED.doubleValue())))
            .andExpect(jsonPath("$.[*].gstTaxPercentage").value(hasItem(DEFAULT_GST_TAX_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)));

        // Check, that the count call also returns 1
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurchaseQuotationDetailsShouldNotBeFound(String filter) throws Exception {
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseQuotationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPurchaseQuotationDetails() throws Exception {
        // Get the purchaseQuotationDetails
        restPurchaseQuotationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPurchaseQuotationDetails() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();

        // Update the purchaseQuotationDetails
        PurchaseQuotationDetails updatedPurchaseQuotationDetails = purchaseQuotationDetailsRepository
            .findById(purchaseQuotationDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedPurchaseQuotationDetails are not directly saved in db
        em.detach(updatedPurchaseQuotationDetails);
        updatedPurchaseQuotationDetails
            .qtyordered(UPDATED_QTYORDERED)
            .gstTaxPercentage(UPDATED_GST_TAX_PERCENTAGE)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(updatedPurchaseQuotationDetails);

        restPurchaseQuotationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseQuotationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
        PurchaseQuotationDetails testPurchaseQuotationDetails = purchaseQuotationDetailsList.get(purchaseQuotationDetailsList.size() - 1);
        assertThat(testPurchaseQuotationDetails.getQtyordered()).isEqualTo(UPDATED_QTYORDERED);
        assertThat(testPurchaseQuotationDetails.getGstTaxPercentage()).isEqualTo(UPDATED_GST_TAX_PERCENTAGE);
        assertThat(testPurchaseQuotationDetails.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testPurchaseQuotationDetails.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testPurchaseQuotationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPurchaseQuotationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPurchaseQuotationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchaseQuotationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPurchaseQuotationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void putNonExistingPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseQuotationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchaseQuotationDetailsWithPatch() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();

        // Update the purchaseQuotationDetails using partial update
        PurchaseQuotationDetails partialUpdatedPurchaseQuotationDetails = new PurchaseQuotationDetails();
        partialUpdatedPurchaseQuotationDetails.setId(purchaseQuotationDetails.getId());

        partialUpdatedPurchaseQuotationDetails
            .qtyordered(UPDATED_QTYORDERED)
            .gstTaxPercentage(UPDATED_GST_TAX_PERCENTAGE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT);

        restPurchaseQuotationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseQuotationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseQuotationDetails))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
        PurchaseQuotationDetails testPurchaseQuotationDetails = purchaseQuotationDetailsList.get(purchaseQuotationDetailsList.size() - 1);
        assertThat(testPurchaseQuotationDetails.getQtyordered()).isEqualTo(UPDATED_QTYORDERED);
        assertThat(testPurchaseQuotationDetails.getGstTaxPercentage()).isEqualTo(UPDATED_GST_TAX_PERCENTAGE);
        assertThat(testPurchaseQuotationDetails.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testPurchaseQuotationDetails.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testPurchaseQuotationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPurchaseQuotationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPurchaseQuotationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchaseQuotationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPurchaseQuotationDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void fullUpdatePurchaseQuotationDetailsWithPatch() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();

        // Update the purchaseQuotationDetails using partial update
        PurchaseQuotationDetails partialUpdatedPurchaseQuotationDetails = new PurchaseQuotationDetails();
        partialUpdatedPurchaseQuotationDetails.setId(purchaseQuotationDetails.getId());

        partialUpdatedPurchaseQuotationDetails
            .qtyordered(UPDATED_QTYORDERED)
            .gstTaxPercentage(UPDATED_GST_TAX_PERCENTAGE)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);

        restPurchaseQuotationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseQuotationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseQuotationDetails))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
        PurchaseQuotationDetails testPurchaseQuotationDetails = purchaseQuotationDetailsList.get(purchaseQuotationDetailsList.size() - 1);
        assertThat(testPurchaseQuotationDetails.getQtyordered()).isEqualTo(UPDATED_QTYORDERED);
        assertThat(testPurchaseQuotationDetails.getGstTaxPercentage()).isEqualTo(UPDATED_GST_TAX_PERCENTAGE);
        assertThat(testPurchaseQuotationDetails.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testPurchaseQuotationDetails.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testPurchaseQuotationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPurchaseQuotationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPurchaseQuotationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchaseQuotationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPurchaseQuotationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void patchNonExistingPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchaseQuotationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchaseQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseQuotationDetailsRepository.findAll().size();
        purchaseQuotationDetails.setId(count.incrementAndGet());

        // Create the PurchaseQuotationDetails
        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseQuotationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseQuotationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseQuotationDetails in the database
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchaseQuotationDetails() throws Exception {
        // Initialize the database
        purchaseQuotationDetailsRepository.saveAndFlush(purchaseQuotationDetails);

        int databaseSizeBeforeDelete = purchaseQuotationDetailsRepository.findAll().size();

        // Delete the purchaseQuotationDetails
        restPurchaseQuotationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchaseQuotationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseQuotationDetails> purchaseQuotationDetailsList = purchaseQuotationDetailsRepository.findAll();
        assertThat(purchaseQuotationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
