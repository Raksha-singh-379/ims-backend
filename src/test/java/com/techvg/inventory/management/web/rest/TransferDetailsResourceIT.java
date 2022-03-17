package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.Product;
import com.techvg.inventory.management.domain.Transfer;
import com.techvg.inventory.management.domain.TransferDetails;
import com.techvg.inventory.management.repository.TransferDetailsRepository;
import com.techvg.inventory.management.service.criteria.TransferDetailsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsMapper;
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
 * Integration tests for the {@link TransferDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferDetailsResourceIT {

    private static final Instant DEFAULT_APPROVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;
    private static final Double SMALLER_QTY = 1D - 1D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/transfer-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferDetailsRepository transferDetailsRepository;

    @Autowired
    private TransferDetailsMapper transferDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferDetailsMockMvc;

    private TransferDetails transferDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDetails createEntity(EntityManager em) {
        TransferDetails transferDetails = new TransferDetails()
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .qty(DEFAULT_QTY)
            .comment(DEFAULT_COMMENT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return transferDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDetails createUpdatedEntity(EntityManager em) {
        TransferDetails transferDetails = new TransferDetails()
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qty(UPDATED_QTY)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return transferDetails;
    }

    @BeforeEach
    public void initTest() {
        transferDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferDetails() throws Exception {
        int databaseSizeBeforeCreate = transferDetailsRepository.findAll().size();
        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);
        restTransferDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TransferDetails testTransferDetails = transferDetailsList.get(transferDetailsList.size() - 1);
        assertThat(testTransferDetails.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testTransferDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTransferDetails.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransferDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransferDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTransferDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTransferDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTransferDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createTransferDetailsWithExistingId() throws Exception {
        // Create the TransferDetails with an existing ID
        transferDetails.setId(1L);
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        int databaseSizeBeforeCreate = transferDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferDetails() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTransferDetails() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get the transferDetails
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, transferDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferDetails.getId().intValue()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getTransferDetailsByIdFiltering() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        Long id = transferDetails.getId();

        defaultTransferDetailsShouldBeFound("id.equals=" + id);
        defaultTransferDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultTransferDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where approvalDate equals to DEFAULT_APPROVAL_DATE
        defaultTransferDetailsShouldBeFound("approvalDate.equals=" + DEFAULT_APPROVAL_DATE);

        // Get all the transferDetailsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsShouldNotBeFound("approvalDate.equals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByApprovalDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where approvalDate not equals to DEFAULT_APPROVAL_DATE
        defaultTransferDetailsShouldNotBeFound("approvalDate.notEquals=" + DEFAULT_APPROVAL_DATE);

        // Get all the transferDetailsList where approvalDate not equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsShouldBeFound("approvalDate.notEquals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where approvalDate in DEFAULT_APPROVAL_DATE or UPDATED_APPROVAL_DATE
        defaultTransferDetailsShouldBeFound("approvalDate.in=" + DEFAULT_APPROVAL_DATE + "," + UPDATED_APPROVAL_DATE);

        // Get all the transferDetailsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsShouldNotBeFound("approvalDate.in=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where approvalDate is not null
        defaultTransferDetailsShouldBeFound("approvalDate.specified=true");

        // Get all the transferDetailsList where approvalDate is null
        defaultTransferDetailsShouldNotBeFound("approvalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty equals to DEFAULT_QTY
        defaultTransferDetailsShouldBeFound("qty.equals=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty equals to UPDATED_QTY
        defaultTransferDetailsShouldNotBeFound("qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty not equals to DEFAULT_QTY
        defaultTransferDetailsShouldNotBeFound("qty.notEquals=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty not equals to UPDATED_QTY
        defaultTransferDetailsShouldBeFound("qty.notEquals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty in DEFAULT_QTY or UPDATED_QTY
        defaultTransferDetailsShouldBeFound("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY);

        // Get all the transferDetailsList where qty equals to UPDATED_QTY
        defaultTransferDetailsShouldNotBeFound("qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty is not null
        defaultTransferDetailsShouldBeFound("qty.specified=true");

        // Get all the transferDetailsList where qty is null
        defaultTransferDetailsShouldNotBeFound("qty.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty is greater than or equal to DEFAULT_QTY
        defaultTransferDetailsShouldBeFound("qty.greaterThanOrEqual=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty is greater than or equal to UPDATED_QTY
        defaultTransferDetailsShouldNotBeFound("qty.greaterThanOrEqual=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty is less than or equal to DEFAULT_QTY
        defaultTransferDetailsShouldBeFound("qty.lessThanOrEqual=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty is less than or equal to SMALLER_QTY
        defaultTransferDetailsShouldNotBeFound("qty.lessThanOrEqual=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty is less than DEFAULT_QTY
        defaultTransferDetailsShouldNotBeFound("qty.lessThan=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty is less than UPDATED_QTY
        defaultTransferDetailsShouldBeFound("qty.lessThan=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where qty is greater than DEFAULT_QTY
        defaultTransferDetailsShouldNotBeFound("qty.greaterThan=" + DEFAULT_QTY);

        // Get all the transferDetailsList where qty is greater than SMALLER_QTY
        defaultTransferDetailsShouldBeFound("qty.greaterThan=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment equals to DEFAULT_COMMENT
        defaultTransferDetailsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transferDetailsList where comment equals to UPDATED_COMMENT
        defaultTransferDetailsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment not equals to DEFAULT_COMMENT
        defaultTransferDetailsShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transferDetailsList where comment not equals to UPDATED_COMMENT
        defaultTransferDetailsShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransferDetailsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transferDetailsList where comment equals to UPDATED_COMMENT
        defaultTransferDetailsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment is not null
        defaultTransferDetailsShouldBeFound("comment.specified=true");

        // Get all the transferDetailsList where comment is null
        defaultTransferDetailsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment contains DEFAULT_COMMENT
        defaultTransferDetailsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transferDetailsList where comment contains UPDATED_COMMENT
        defaultTransferDetailsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where comment does not contain DEFAULT_COMMENT
        defaultTransferDetailsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transferDetailsList where comment does not contain UPDATED_COMMENT
        defaultTransferDetailsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTransferDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTransferDetailsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the transferDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 is not null
        defaultTransferDetailsShouldBeFound("freeField1.specified=true");

        // Get all the transferDetailsList where freeField1 is null
        defaultTransferDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTransferDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTransferDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTransferDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultTransferDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultTransferDetailsShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferDetailsList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the transferDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 is not null
        defaultTransferDetailsShouldBeFound("freeField2.specified=true");

        // Get all the transferDetailsList where freeField2 is null
        defaultTransferDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultTransferDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultTransferDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultTransferDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransferDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransferDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transferDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified is not null
        defaultTransferDetailsShouldBeFound("lastModified.specified=true");

        // Get all the transferDetailsList where lastModified is null
        defaultTransferDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultTransferDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultTransferDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultTransferDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transferDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy is not null
        defaultTransferDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transferDetailsList where lastModifiedBy is null
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTransferDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the transferDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultTransferDetailsShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the transferDetailsList where isDeleted not equals to UPDATED_IS_DELETED
        defaultTransferDetailsShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTransferDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the transferDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isDeleted is not null
        defaultTransferDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the transferDetailsList where isDeleted is null
        defaultTransferDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTransferDetailsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultTransferDetailsShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferDetailsList where isActive not equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTransferDetailsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the transferDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        // Get all the transferDetailsList where isActive is not null
        defaultTransferDetailsShouldBeFound("isActive.specified=true");

        // Get all the transferDetailsList where isActive is null
        defaultTransferDetailsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);
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
        transferDetails.setProduct(product);
        transferDetailsRepository.saveAndFlush(transferDetails);
        Long productId = product.getId();

        // Get all the transferDetailsList where product equals to productId
        defaultTransferDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the transferDetailsList where product equals to (productId + 1)
        defaultTransferDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDetailsByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);
        Transfer transfer;
        if (TestUtil.findAll(em, Transfer.class).isEmpty()) {
            transfer = TransferResourceIT.createEntity(em);
            em.persist(transfer);
            em.flush();
        } else {
            transfer = TestUtil.findAll(em, Transfer.class).get(0);
        }
        em.persist(transfer);
        em.flush();
        transferDetails.setTransfer(transfer);
        transferDetailsRepository.saveAndFlush(transferDetails);
        Long transferId = transfer.getId();

        // Get all the transferDetailsList where transfer equals to transferId
        defaultTransferDetailsShouldBeFound("transferId.equals=" + transferId);

        // Get all the transferDetailsList where transfer equals to (transferId + 1)
        defaultTransferDetailsShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferDetailsShouldBeFound(String filter) throws Exception {
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferDetailsShouldNotBeFound(String filter) throws Exception {
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferDetails() throws Exception {
        // Get the transferDetails
        restTransferDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransferDetails() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();

        // Update the transferDetails
        TransferDetails updatedTransferDetails = transferDetailsRepository.findById(transferDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTransferDetails are not directly saved in db
        em.detach(updatedTransferDetails);
        updatedTransferDetails
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qty(UPDATED_QTY)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(updatedTransferDetails);

        restTransferDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetails testTransferDetails = transferDetailsList.get(transferDetailsList.size() - 1);
        assertThat(testTransferDetails.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTransferDetails.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTransferDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferDetailsWithPatch() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();

        // Update the transferDetails using partial update
        TransferDetails partialUpdatedTransferDetails = new TransferDetails();
        partialUpdatedTransferDetails.setId(transferDetails.getId());

        partialUpdatedTransferDetails
            .approvalDate(UPDATED_APPROVAL_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTransferDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDetails))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetails testTransferDetails = transferDetailsList.get(transferDetailsList.size() - 1);
        assertThat(testTransferDetails.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTransferDetails.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransferDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTransferDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTransferDetailsWithPatch() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();

        // Update the transferDetails using partial update
        TransferDetails partialUpdatedTransferDetails = new TransferDetails();
        partialUpdatedTransferDetails.setId(transferDetails.getId());

        partialUpdatedTransferDetails
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qty(UPDATED_QTY)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTransferDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDetails))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetails testTransferDetails = transferDetailsList.get(transferDetailsList.size() - 1);
        assertThat(testTransferDetails.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTransferDetails.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTransferDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferDetails() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsRepository.findAll().size();
        transferDetails.setId(count.incrementAndGet());

        // Create the TransferDetails
        TransferDetailsDTO transferDetailsDTO = transferDetailsMapper.toDto(transferDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDetails in the database
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferDetails() throws Exception {
        // Initialize the database
        transferDetailsRepository.saveAndFlush(transferDetails);

        int databaseSizeBeforeDelete = transferDetailsRepository.findAll().size();

        // Delete the transferDetails
        restTransferDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferDetails> transferDetailsList = transferDetailsRepository.findAll();
        assertThat(transferDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
