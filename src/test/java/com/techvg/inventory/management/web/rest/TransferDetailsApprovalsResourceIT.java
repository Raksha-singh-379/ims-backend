package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.domain.Transfer;
import com.techvg.inventory.management.domain.TransferDetailsApprovals;
import com.techvg.inventory.management.repository.TransferDetailsApprovalsRepository;
import com.techvg.inventory.management.service.criteria.TransferDetailsApprovalsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsApprovalsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsApprovalsMapper;
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
 * Integration tests for the {@link TransferDetailsApprovalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferDetailsApprovalsResourceIT {

    private static final Instant DEFAULT_APPROVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_REQUESTED = 1D;
    private static final Double UPDATED_QTY_REQUESTED = 2D;
    private static final Double SMALLER_QTY_REQUESTED = 1D - 1D;

    private static final Double DEFAULT_QTY_APPROVED = 1D;
    private static final Double UPDATED_QTY_APPROVED = 2D;
    private static final Double SMALLER_QTY_APPROVED = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/transfer-details-approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferDetailsApprovalsRepository transferDetailsApprovalsRepository;

    @Autowired
    private TransferDetailsApprovalsMapper transferDetailsApprovalsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferDetailsApprovalsMockMvc;

    private TransferDetailsApprovals transferDetailsApprovals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDetailsApprovals createEntity(EntityManager em) {
        TransferDetailsApprovals transferDetailsApprovals = new TransferDetailsApprovals()
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .qtyRequested(DEFAULT_QTY_REQUESTED)
            .qtyApproved(DEFAULT_QTY_APPROVED)
            .comment(DEFAULT_COMMENT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return transferDetailsApprovals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDetailsApprovals createUpdatedEntity(EntityManager em) {
        TransferDetailsApprovals transferDetailsApprovals = new TransferDetailsApprovals()
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return transferDetailsApprovals;
    }

    @BeforeEach
    public void initTest() {
        transferDetailsApprovals = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeCreate = transferDetailsApprovalsRepository.findAll().size();
        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);
        restTransferDetailsApprovalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeCreate + 1);
        TransferDetailsApprovals testTransferDetailsApprovals = transferDetailsApprovalsList.get(transferDetailsApprovalsList.size() - 1);
        assertThat(testTransferDetailsApprovals.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testTransferDetailsApprovals.getQtyRequested()).isEqualTo(DEFAULT_QTY_REQUESTED);
        assertThat(testTransferDetailsApprovals.getQtyApproved()).isEqualTo(DEFAULT_QTY_APPROVED);
        assertThat(testTransferDetailsApprovals.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransferDetailsApprovals.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransferDetailsApprovals.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferDetailsApprovals.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTransferDetailsApprovals.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTransferDetailsApprovals.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createTransferDetailsApprovalsWithExistingId() throws Exception {
        // Create the TransferDetailsApprovals with an existing ID
        transferDetailsApprovals.setId(1L);
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        int databaseSizeBeforeCreate = transferDetailsApprovalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferDetailsApprovalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovals() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDetailsApprovals.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyRequested").value(hasItem(DEFAULT_QTY_REQUESTED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyApproved").value(hasItem(DEFAULT_QTY_APPROVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTransferDetailsApprovals() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get the transferDetailsApprovals
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL_ID, transferDetailsApprovals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferDetailsApprovals.getId().intValue()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.qtyRequested").value(DEFAULT_QTY_REQUESTED.doubleValue()))
            .andExpect(jsonPath("$.qtyApproved").value(DEFAULT_QTY_APPROVED.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getTransferDetailsApprovalsByIdFiltering() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        Long id = transferDetailsApprovals.getId();

        defaultTransferDetailsApprovalsShouldBeFound("id.equals=" + id);
        defaultTransferDetailsApprovalsShouldNotBeFound("id.notEquals=" + id);

        defaultTransferDetailsApprovalsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferDetailsApprovalsShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferDetailsApprovalsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferDetailsApprovalsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where approvalDate equals to DEFAULT_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldBeFound("approvalDate.equals=" + DEFAULT_APPROVAL_DATE);

        // Get all the transferDetailsApprovalsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldNotBeFound("approvalDate.equals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByApprovalDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where approvalDate not equals to DEFAULT_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldNotBeFound("approvalDate.notEquals=" + DEFAULT_APPROVAL_DATE);

        // Get all the transferDetailsApprovalsList where approvalDate not equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldBeFound("approvalDate.notEquals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where approvalDate in DEFAULT_APPROVAL_DATE or UPDATED_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldBeFound("approvalDate.in=" + DEFAULT_APPROVAL_DATE + "," + UPDATED_APPROVAL_DATE);

        // Get all the transferDetailsApprovalsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTransferDetailsApprovalsShouldNotBeFound("approvalDate.in=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where approvalDate is not null
        defaultTransferDetailsApprovalsShouldBeFound("approvalDate.specified=true");

        // Get all the transferDetailsApprovalsList where approvalDate is null
        defaultTransferDetailsApprovalsShouldNotBeFound("approvalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested equals to DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.equals=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested equals to UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.equals=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested not equals to DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.notEquals=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested not equals to UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.notEquals=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested in DEFAULT_QTY_REQUESTED or UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.in=" + DEFAULT_QTY_REQUESTED + "," + UPDATED_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested equals to UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.in=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested is not null
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.specified=true");

        // Get all the transferDetailsApprovalsList where qtyRequested is null
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested is greater than or equal to DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.greaterThanOrEqual=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested is greater than or equal to UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.greaterThanOrEqual=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested is less than or equal to DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.lessThanOrEqual=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested is less than or equal to SMALLER_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.lessThanOrEqual=" + SMALLER_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested is less than DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.lessThan=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested is less than UPDATED_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.lessThan=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyRequestedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyRequested is greater than DEFAULT_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyRequested.greaterThan=" + DEFAULT_QTY_REQUESTED);

        // Get all the transferDetailsApprovalsList where qtyRequested is greater than SMALLER_QTY_REQUESTED
        defaultTransferDetailsApprovalsShouldBeFound("qtyRequested.greaterThan=" + SMALLER_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved equals to DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.equals=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved equals to UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.equals=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved not equals to DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.notEquals=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved not equals to UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.notEquals=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved in DEFAULT_QTY_APPROVED or UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.in=" + DEFAULT_QTY_APPROVED + "," + UPDATED_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved equals to UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.in=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved is not null
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.specified=true");

        // Get all the transferDetailsApprovalsList where qtyApproved is null
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved is greater than or equal to DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.greaterThanOrEqual=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved is greater than or equal to UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.greaterThanOrEqual=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved is less than or equal to DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.lessThanOrEqual=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved is less than or equal to SMALLER_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.lessThanOrEqual=" + SMALLER_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved is less than DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.lessThan=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved is less than UPDATED_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.lessThan=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByQtyApprovedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where qtyApproved is greater than DEFAULT_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldNotBeFound("qtyApproved.greaterThan=" + DEFAULT_QTY_APPROVED);

        // Get all the transferDetailsApprovalsList where qtyApproved is greater than SMALLER_QTY_APPROVED
        defaultTransferDetailsApprovalsShouldBeFound("qtyApproved.greaterThan=" + SMALLER_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment equals to DEFAULT_COMMENT
        defaultTransferDetailsApprovalsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transferDetailsApprovalsList where comment equals to UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment not equals to DEFAULT_COMMENT
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transferDetailsApprovalsList where comment not equals to UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transferDetailsApprovalsList where comment equals to UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment is not null
        defaultTransferDetailsApprovalsShouldBeFound("comment.specified=true");

        // Get all the transferDetailsApprovalsList where comment is null
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment contains DEFAULT_COMMENT
        defaultTransferDetailsApprovalsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transferDetailsApprovalsList where comment contains UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where comment does not contain DEFAULT_COMMENT
        defaultTransferDetailsApprovalsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transferDetailsApprovalsList where comment does not contain UPDATED_COMMENT
        defaultTransferDetailsApprovalsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsApprovalsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsApprovalsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the transferDetailsApprovalsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 is not null
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.specified=true");

        // Get all the transferDetailsApprovalsList where freeField1 is null
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsApprovalsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferDetailsApprovalsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTransferDetailsApprovalsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsApprovalsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsApprovalsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transferDetailsApprovalsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified is not null
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.specified=true");

        // Get all the transferDetailsApprovalsList where lastModified is null
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsApprovalsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferDetailsApprovalsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultTransferDetailsApprovalsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsApprovalsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsApprovalsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transferDetailsApprovalsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy is not null
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transferDetailsApprovalsList where lastModifiedBy is null
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsApprovalsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferDetailsApprovalsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransferDetailsApprovalsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTransferDetailsApprovalsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the transferDetailsApprovalsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferDetailsApprovalsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultTransferDetailsApprovalsShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the transferDetailsApprovalsList where isDeleted not equals to UPDATED_IS_DELETED
        defaultTransferDetailsApprovalsShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTransferDetailsApprovalsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the transferDetailsApprovalsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTransferDetailsApprovalsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isDeleted is not null
        defaultTransferDetailsApprovalsShouldBeFound("isDeleted.specified=true");

        // Get all the transferDetailsApprovalsList where isDeleted is null
        defaultTransferDetailsApprovalsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferDetailsApprovalsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the transferDetailsApprovalsList where isActive not equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the transferDetailsApprovalsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTransferDetailsApprovalsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        // Get all the transferDetailsApprovalsList where isActive is not null
        defaultTransferDetailsApprovalsShouldBeFound("isActive.specified=true");

        // Get all the transferDetailsApprovalsList where isActive is null
        defaultTransferDetailsApprovalsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);
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
        transferDetailsApprovals.setSecurityUser(securityUser);
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);
        Long securityUserId = securityUser.getId();

        // Get all the transferDetailsApprovalsList where securityUser equals to securityUserId
        defaultTransferDetailsApprovalsShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the transferDetailsApprovalsList where securityUser equals to (securityUserId + 1)
        defaultTransferDetailsApprovalsShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDetailsApprovalsByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);
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
        transferDetailsApprovals.setTransfer(transfer);
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);
        Long transferId = transfer.getId();

        // Get all the transferDetailsApprovalsList where transfer equals to transferId
        defaultTransferDetailsApprovalsShouldBeFound("transferId.equals=" + transferId);

        // Get all the transferDetailsApprovalsList where transfer equals to (transferId + 1)
        defaultTransferDetailsApprovalsShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferDetailsApprovalsShouldBeFound(String filter) throws Exception {
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDetailsApprovals.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyRequested").value(hasItem(DEFAULT_QTY_REQUESTED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyApproved").value(hasItem(DEFAULT_QTY_APPROVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferDetailsApprovalsShouldNotBeFound(String filter) throws Exception {
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransferDetailsApprovals() throws Exception {
        // Get the transferDetailsApprovals
        restTransferDetailsApprovalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransferDetailsApprovals() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();

        // Update the transferDetailsApprovals
        TransferDetailsApprovals updatedTransferDetailsApprovals = transferDetailsApprovalsRepository
            .findById(transferDetailsApprovals.getId())
            .get();
        // Disconnect from session so that the updates on updatedTransferDetailsApprovals are not directly saved in db
        em.detach(updatedTransferDetailsApprovals);
        updatedTransferDetailsApprovals
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(updatedTransferDetailsApprovals);

        restTransferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDetailsApprovalsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetailsApprovals testTransferDetailsApprovals = transferDetailsApprovalsList.get(transferDetailsApprovalsList.size() - 1);
        assertThat(testTransferDetailsApprovals.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetailsApprovals.getQtyRequested()).isEqualTo(UPDATED_QTY_REQUESTED);
        assertThat(testTransferDetailsApprovals.getQtyApproved()).isEqualTo(UPDATED_QTY_APPROVED);
        assertThat(testTransferDetailsApprovals.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferDetailsApprovals.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferDetailsApprovals.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferDetailsApprovals.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferDetailsApprovals.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferDetailsApprovals.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDetailsApprovalsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferDetailsApprovalsWithPatch() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();

        // Update the transferDetailsApprovals using partial update
        TransferDetailsApprovals partialUpdatedTransferDetailsApprovals = new TransferDetailsApprovals();
        partialUpdatedTransferDetailsApprovals.setId(transferDetailsApprovals.getId());

        partialUpdatedTransferDetailsApprovals
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT);

        restTransferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDetailsApprovals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDetailsApprovals))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetailsApprovals testTransferDetailsApprovals = transferDetailsApprovalsList.get(transferDetailsApprovalsList.size() - 1);
        assertThat(testTransferDetailsApprovals.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetailsApprovals.getQtyRequested()).isEqualTo(UPDATED_QTY_REQUESTED);
        assertThat(testTransferDetailsApprovals.getQtyApproved()).isEqualTo(UPDATED_QTY_APPROVED);
        assertThat(testTransferDetailsApprovals.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferDetailsApprovals.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransferDetailsApprovals.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransferDetailsApprovals.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTransferDetailsApprovals.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTransferDetailsApprovals.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTransferDetailsApprovalsWithPatch() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();

        // Update the transferDetailsApprovals using partial update
        TransferDetailsApprovals partialUpdatedTransferDetailsApprovals = new TransferDetailsApprovals();
        partialUpdatedTransferDetailsApprovals.setId(transferDetailsApprovals.getId());

        partialUpdatedTransferDetailsApprovals
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTransferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDetailsApprovals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDetailsApprovals))
            )
            .andExpect(status().isOk());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TransferDetailsApprovals testTransferDetailsApprovals = transferDetailsApprovalsList.get(transferDetailsApprovalsList.size() - 1);
        assertThat(testTransferDetailsApprovals.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTransferDetailsApprovals.getQtyRequested()).isEqualTo(UPDATED_QTY_REQUESTED);
        assertThat(testTransferDetailsApprovals.getQtyApproved()).isEqualTo(UPDATED_QTY_APPROVED);
        assertThat(testTransferDetailsApprovals.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransferDetailsApprovals.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransferDetailsApprovals.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransferDetailsApprovals.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTransferDetailsApprovals.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTransferDetailsApprovals.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDetailsApprovalsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = transferDetailsApprovalsRepository.findAll().size();
        transferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TransferDetailsApprovals
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO = transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDetailsApprovalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDetailsApprovals in the database
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferDetailsApprovals() throws Exception {
        // Initialize the database
        transferDetailsApprovalsRepository.saveAndFlush(transferDetailsApprovals);

        int databaseSizeBeforeDelete = transferDetailsApprovalsRepository.findAll().size();

        // Delete the transferDetailsApprovals
        restTransferDetailsApprovalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferDetailsApprovals.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferDetailsApprovals> transferDetailsApprovalsList = transferDetailsApprovalsRepository.findAll();
        assertThat(transferDetailsApprovalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
