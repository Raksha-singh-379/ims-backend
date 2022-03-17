package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.domain.Transfer;
import com.techvg.inventory.management.domain.TransferRecieved;
import com.techvg.inventory.management.repository.TransferRecievedRepository;
import com.techvg.inventory.management.service.criteria.TransferRecievedCriteria;
import com.techvg.inventory.management.service.dto.TransferRecievedDTO;
import com.techvg.inventory.management.service.mapper.TransferRecievedMapper;
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
 * Integration tests for the {@link TransferRecievedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferRecievedResourceIT {

    private static final Instant DEFAULT_TRANSFER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSFER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_TRANSFERED = 1D;
    private static final Double UPDATED_QTY_TRANSFERED = 2D;
    private static final Double SMALLER_QTY_TRANSFERED = 1D - 1D;

    private static final Double DEFAULT_QTY_RECEIVED = 1D;
    private static final Double UPDATED_QTY_RECEIVED = 2D;
    private static final Double SMALLER_QTY_RECEIVED = 1D - 1D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/transfer-recieveds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferRecievedRepository transferRecievedRepository;

    @Autowired
    private TransferRecievedMapper transferRecievedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferRecievedMockMvc;

    private TransferRecieved transferRecieved;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferRecieved createEntity(EntityManager em) {
        TransferRecieved transferRecieved = new TransferRecieved()
            .transferDate(DEFAULT_TRANSFER_DATE)
            .qtyTransfered(DEFAULT_QTY_TRANSFERED)
            .qtyReceived(DEFAULT_QTY_RECEIVED)
            .comment(DEFAULT_COMMENT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return transferRecieved;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferRecieved createUpdatedEntity(EntityManager em) {
        TransferRecieved transferRecieved = new TransferRecieved()
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return transferRecieved;
    }

    @BeforeEach
    public void initTest() {
        transferRecieved = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferRecieved() throws Exception {
        int databaseSizeBeforeCreate = transferRecievedRepository.findAll().size();
        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);
        restTransferRecievedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeCreate + 1);
        TransferRecieved testTransferRecieved = transferRecievedList.get(transferRecievedList.size() - 1);
        assertThat(testTransferRecieved.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testTransferRecieved.getQtyTransfered()).isEqualTo(DEFAULT_QTY_TRANSFERED);
        assertThat(testTransferRecieved.getQtyReceived()).isEqualTo(DEFAULT_QTY_RECEIVED);
        assertThat(testTransferRecieved.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransferRecieved.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransferRecieved.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferRecieved.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTransferRecieved.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTransferRecieved.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createTransferRecievedWithExistingId() throws Exception {
        // Create the TransferRecieved with an existing ID
        transferRecieved.setId(1L);
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        int databaseSizeBeforeCreate = transferRecievedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferRecievedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferRecieveds() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferRecieved.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyTransfered").value(hasItem(DEFAULT_QTY_TRANSFERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyReceived").value(hasItem(DEFAULT_QTY_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTransferRecieved() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get the transferRecieved
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL_ID, transferRecieved.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferRecieved.getId().intValue()))
            .andExpect(jsonPath("$.transferDate").value(DEFAULT_TRANSFER_DATE.toString()))
            .andExpect(jsonPath("$.qtyTransfered").value(DEFAULT_QTY_TRANSFERED.doubleValue()))
            .andExpect(jsonPath("$.qtyReceived").value(DEFAULT_QTY_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getTransferRecievedsByIdFiltering() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        Long id = transferRecieved.getId();

        defaultTransferRecievedShouldBeFound("id.equals=" + id);
        defaultTransferRecievedShouldNotBeFound("id.notEquals=" + id);

        defaultTransferRecievedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferRecievedShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferRecievedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferRecievedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByTransferDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where transferDate equals to DEFAULT_TRANSFER_DATE
        defaultTransferRecievedShouldBeFound("transferDate.equals=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferRecievedList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTransferRecievedShouldNotBeFound("transferDate.equals=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByTransferDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where transferDate not equals to DEFAULT_TRANSFER_DATE
        defaultTransferRecievedShouldNotBeFound("transferDate.notEquals=" + DEFAULT_TRANSFER_DATE);

        // Get all the transferRecievedList where transferDate not equals to UPDATED_TRANSFER_DATE
        defaultTransferRecievedShouldBeFound("transferDate.notEquals=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByTransferDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where transferDate in DEFAULT_TRANSFER_DATE or UPDATED_TRANSFER_DATE
        defaultTransferRecievedShouldBeFound("transferDate.in=" + DEFAULT_TRANSFER_DATE + "," + UPDATED_TRANSFER_DATE);

        // Get all the transferRecievedList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTransferRecievedShouldNotBeFound("transferDate.in=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByTransferDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where transferDate is not null
        defaultTransferRecievedShouldBeFound("transferDate.specified=true");

        // Get all the transferRecievedList where transferDate is null
        defaultTransferRecievedShouldNotBeFound("transferDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered equals to DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.equals=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered equals to UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.equals=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered not equals to DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.notEquals=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered not equals to UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.notEquals=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered in DEFAULT_QTY_TRANSFERED or UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.in=" + DEFAULT_QTY_TRANSFERED + "," + UPDATED_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered equals to UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.in=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered is not null
        defaultTransferRecievedShouldBeFound("qtyTransfered.specified=true");

        // Get all the transferRecievedList where qtyTransfered is null
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered is greater than or equal to DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.greaterThanOrEqual=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered is greater than or equal to UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.greaterThanOrEqual=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered is less than or equal to DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.lessThanOrEqual=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered is less than or equal to SMALLER_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.lessThanOrEqual=" + SMALLER_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsLessThanSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered is less than DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.lessThan=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered is less than UPDATED_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.lessThan=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyTransferedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyTransfered is greater than DEFAULT_QTY_TRANSFERED
        defaultTransferRecievedShouldNotBeFound("qtyTransfered.greaterThan=" + DEFAULT_QTY_TRANSFERED);

        // Get all the transferRecievedList where qtyTransfered is greater than SMALLER_QTY_TRANSFERED
        defaultTransferRecievedShouldBeFound("qtyTransfered.greaterThan=" + SMALLER_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived equals to DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.equals=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived equals to UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.equals=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived not equals to DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.notEquals=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived not equals to UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.notEquals=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived in DEFAULT_QTY_RECEIVED or UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.in=" + DEFAULT_QTY_RECEIVED + "," + UPDATED_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived equals to UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.in=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived is not null
        defaultTransferRecievedShouldBeFound("qtyReceived.specified=true");

        // Get all the transferRecievedList where qtyReceived is null
        defaultTransferRecievedShouldNotBeFound("qtyReceived.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived is greater than or equal to DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.greaterThanOrEqual=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived is greater than or equal to UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.greaterThanOrEqual=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived is less than or equal to DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.lessThanOrEqual=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived is less than or equal to SMALLER_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.lessThanOrEqual=" + SMALLER_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsLessThanSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived is less than DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.lessThan=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived is less than UPDATED_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.lessThan=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByQtyReceivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where qtyReceived is greater than DEFAULT_QTY_RECEIVED
        defaultTransferRecievedShouldNotBeFound("qtyReceived.greaterThan=" + DEFAULT_QTY_RECEIVED);

        // Get all the transferRecievedList where qtyReceived is greater than SMALLER_QTY_RECEIVED
        defaultTransferRecievedShouldBeFound("qtyReceived.greaterThan=" + SMALLER_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment equals to DEFAULT_COMMENT
        defaultTransferRecievedShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transferRecievedList where comment equals to UPDATED_COMMENT
        defaultTransferRecievedShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment not equals to DEFAULT_COMMENT
        defaultTransferRecievedShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transferRecievedList where comment not equals to UPDATED_COMMENT
        defaultTransferRecievedShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransferRecievedShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transferRecievedList where comment equals to UPDATED_COMMENT
        defaultTransferRecievedShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment is not null
        defaultTransferRecievedShouldBeFound("comment.specified=true");

        // Get all the transferRecievedList where comment is null
        defaultTransferRecievedShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment contains DEFAULT_COMMENT
        defaultTransferRecievedShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transferRecievedList where comment contains UPDATED_COMMENT
        defaultTransferRecievedShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where comment does not contain DEFAULT_COMMENT
        defaultTransferRecievedShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transferRecievedList where comment does not contain UPDATED_COMMENT
        defaultTransferRecievedShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTransferRecievedShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferRecievedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTransferRecievedShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferRecievedList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the transferRecievedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 is not null
        defaultTransferRecievedShouldBeFound("freeField1.specified=true");

        // Get all the transferRecievedList where freeField1 is null
        defaultTransferRecievedShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTransferRecievedShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferRecievedList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTransferRecievedShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferRecievedList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTransferRecievedShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransferRecievedShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferRecievedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransferRecievedShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferRecievedList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transferRecievedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified is not null
        defaultTransferRecievedShouldBeFound("lastModified.specified=true");

        // Get all the transferRecievedList where lastModified is null
        defaultTransferRecievedShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultTransferRecievedShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferRecievedList where lastModified contains UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultTransferRecievedShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferRecievedList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultTransferRecievedShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferRecievedShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferRecievedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferRecievedList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transferRecievedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy is not null
        defaultTransferRecievedShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transferRecievedList where lastModifiedBy is null
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransferRecievedShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferRecievedList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransferRecievedShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferRecievedList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransferRecievedShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTransferRecievedShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the transferRecievedList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferRecievedShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultTransferRecievedShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the transferRecievedList where isDeleted not equals to UPDATED_IS_DELETED
        defaultTransferRecievedShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTransferRecievedShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the transferRecievedList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferRecievedShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isDeleted is not null
        defaultTransferRecievedShouldBeFound("isDeleted.specified=true");

        // Get all the transferRecievedList where isDeleted is null
        defaultTransferRecievedShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTransferRecievedShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferRecievedList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferRecievedShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultTransferRecievedShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferRecievedList where isActive not equals to UPDATED_IS_ACTIVE
        defaultTransferRecievedShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTransferRecievedShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the transferRecievedList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferRecievedShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        // Get all the transferRecievedList where isActive is not null
        defaultTransferRecievedShouldBeFound("isActive.specified=true");

        // Get all the transferRecievedList where isActive is null
        defaultTransferRecievedShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferRecievedsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);
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
        transferRecieved.setSecurityUser(securityUser);
        transferRecievedRepository.saveAndFlush(transferRecieved);
        Long securityUserId = securityUser.getId();

        // Get all the transferRecievedList where securityUser equals to securityUserId
        defaultTransferRecievedShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the transferRecievedList where securityUser equals to (securityUserId + 1)
        defaultTransferRecievedShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllTransferRecievedsByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);
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
        transferRecieved.setTransfer(transfer);
        transferRecievedRepository.saveAndFlush(transferRecieved);
        Long transferId = transfer.getId();

        // Get all the transferRecievedList where transfer equals to transferId
        defaultTransferRecievedShouldBeFound("transferId.equals=" + transferId);

        // Get all the transferRecievedList where transfer equals to (transferId + 1)
        defaultTransferRecievedShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferRecievedShouldBeFound(String filter) throws Exception {
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferRecieved.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyTransfered").value(hasItem(DEFAULT_QTY_TRANSFERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyReceived").value(hasItem(DEFAULT_QTY_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferRecievedShouldNotBeFound(String filter) throws Exception {
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferRecieved() throws Exception {
        // Get the transferRecieved
        restTransferRecievedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransferRecieved() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();

        // Update the transferRecieved
        TransferRecieved updatedTransferRecieved = transferRecievedRepository.findById(transferRecieved.getId()).get();
        // Disconnect from session so that the updates on updatedTransferRecieved are not directly saved in db
        em.detach(updatedTransferRecieved);
        updatedTransferRecieved
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(updatedTransferRecieved);

        restTransferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferRecievedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TransferRecieved testTransferRecieved = transferRecievedList.get(transferRecievedList.size() - 1);
        assertThat(testTransferRecieved.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTransferRecieved.getQtyTransfered()).isEqualTo(UPDATED_QTY_TRANSFERED);
        assertThat(testTransferRecieved.getQtyReceived()).isEqualTo(UPDATED_QTY_RECEIVED);
        assertThat(testTransferRecieved.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferRecieved.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferRecieved.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferRecieved.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferRecievedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferRecievedWithPatch() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();

        // Update the transferRecieved using partial update
        TransferRecieved partialUpdatedTransferRecieved = new TransferRecieved();
        partialUpdatedTransferRecieved.setId(transferRecieved.getId());

        partialUpdatedTransferRecieved.comment(UPDATED_COMMENT).lastModifiedBy(UPDATED_LAST_MODIFIED_BY).isActive(UPDATED_IS_ACTIVE);

        restTransferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferRecieved.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferRecieved))
            )
            .andExpect(status().isOk());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TransferRecieved testTransferRecieved = transferRecievedList.get(transferRecievedList.size() - 1);
        assertThat(testTransferRecieved.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testTransferRecieved.getQtyTransfered()).isEqualTo(DEFAULT_QTY_TRANSFERED);
        assertThat(testTransferRecieved.getQtyReceived()).isEqualTo(DEFAULT_QTY_RECEIVED);
        assertThat(testTransferRecieved.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferRecieved.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransferRecieved.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferRecieved.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTransferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTransferRecievedWithPatch() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();

        // Update the transferRecieved using partial update
        TransferRecieved partialUpdatedTransferRecieved = new TransferRecieved();
        partialUpdatedTransferRecieved.setId(transferRecieved.getId());

        partialUpdatedTransferRecieved
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTransferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferRecieved.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferRecieved))
            )
            .andExpect(status().isOk());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TransferRecieved testTransferRecieved = transferRecievedList.get(transferRecievedList.size() - 1);
        assertThat(testTransferRecieved.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTransferRecieved.getQtyTransfered()).isEqualTo(UPDATED_QTY_TRANSFERED);
        assertThat(testTransferRecieved.getQtyReceived()).isEqualTo(UPDATED_QTY_RECEIVED);
        assertThat(testTransferRecieved.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferRecieved.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferRecieved.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferRecieved.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferRecievedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = transferRecievedRepository.findAll().size();
        transferRecieved.setId(count.incrementAndGet());

        // Create the TransferRecieved
        TransferRecievedDTO transferRecievedDTO = transferRecievedMapper.toDto(transferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferRecievedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferRecieved in the database
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferRecieved() throws Exception {
        // Initialize the database
        transferRecievedRepository.saveAndFlush(transferRecieved);

        int databaseSizeBeforeDelete = transferRecievedRepository.findAll().size();

        // Delete the transferRecieved
        restTransferRecievedMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferRecieved.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferRecieved> transferRecievedList = transferRecievedRepository.findAll();
        assertThat(transferRecievedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
