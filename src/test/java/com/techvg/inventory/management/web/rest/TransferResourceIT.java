package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.domain.Transfer;
import com.techvg.inventory.management.domain.TransferDetails;
import com.techvg.inventory.management.domain.TransferDetailsApprovals;
import com.techvg.inventory.management.domain.TransferRecieved;
import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.domain.enumeration.Status;
import com.techvg.inventory.management.repository.TransferRepository;
import com.techvg.inventory.management.service.criteria.TransferCriteria;
import com.techvg.inventory.management.service.dto.TransferDTO;
import com.techvg.inventory.management.service.mapper.TransferMapper;
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
 * Integration tests for the {@link TransferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferResourceIT {

    private static final Instant DEFAULT_TRANFER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANFER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.REQUESTED;
    private static final Status UPDATED_STATUS = Status.APPROVED;

    private static final String DEFAULT_SOURCE_WARE_HOUSE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_WARE_HOUSE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_WARE_HOUSE = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_WARE_HOUSE = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .tranferDate(DEFAULT_TRANFER_DATE)
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS)
            .sourceWareHouse(DEFAULT_SOURCE_WARE_HOUSE)
            .destinationWareHouse(DEFAULT_DESTINATION_WARE_HOUSE)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return transfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createUpdatedEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .sourceWareHouse(UPDATED_SOURCE_WARE_HOUSE)
            .destinationWareHouse(UPDATED_DESTINATION_WARE_HOUSE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return transfer;
    }

    @BeforeEach
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();
        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTranferDate()).isEqualTo(DEFAULT_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransfer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransfer.getSourceWareHouse()).isEqualTo(DEFAULT_SOURCE_WARE_HOUSE);
        assertThat(testTransfer.getDestinationWareHouse()).isEqualTo(DEFAULT_DESTINATION_WARE_HOUSE);
        assertThat(testTransfer.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransfer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTransferWithExistingId() throws Exception {
        // Create the Transfer with an existing ID
        transfer.setId(1L);
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tranferDate").value(hasItem(DEFAULT_TRANFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sourceWareHouse").value(hasItem(DEFAULT_SOURCE_WARE_HOUSE)))
            .andExpect(jsonPath("$.[*].destinationWareHouse").value(hasItem(DEFAULT_DESTINATION_WARE_HOUSE)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc
            .perform(get(ENTITY_API_URL_ID, transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().intValue()))
            .andExpect(jsonPath("$.tranferDate").value(DEFAULT_TRANFER_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.sourceWareHouse").value(DEFAULT_SOURCE_WARE_HOUSE))
            .andExpect(jsonPath("$.destinationWareHouse").value(DEFAULT_DESTINATION_WARE_HOUSE))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTransfersByIdFiltering() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        Long id = transfer.getId();

        defaultTransferShouldBeFound("id.equals=" + id);
        defaultTransferShouldNotBeFound("id.notEquals=" + id);

        defaultTransferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate equals to DEFAULT_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.equals=" + DEFAULT_TRANFER_DATE);

        // Get all the transferList where tranferDate equals to UPDATED_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.equals=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate not equals to DEFAULT_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.notEquals=" + DEFAULT_TRANFER_DATE);

        // Get all the transferList where tranferDate not equals to UPDATED_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.notEquals=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate in DEFAULT_TRANFER_DATE or UPDATED_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.in=" + DEFAULT_TRANFER_DATE + "," + UPDATED_TRANFER_DATE);

        // Get all the transferList where tranferDate equals to UPDATED_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.in=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate is not null
        defaultTransferShouldBeFound("tranferDate.specified=true");

        // Get all the transferList where tranferDate is null
        defaultTransferShouldNotBeFound("tranferDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment equals to DEFAULT_COMMENT
        defaultTransferShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transferList where comment equals to UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment not equals to DEFAULT_COMMENT
        defaultTransferShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transferList where comment not equals to UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transferList where comment equals to UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment is not null
        defaultTransferShouldBeFound("comment.specified=true");

        // Get all the transferList where comment is null
        defaultTransferShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByCommentContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment contains DEFAULT_COMMENT
        defaultTransferShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transferList where comment contains UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment does not contain DEFAULT_COMMENT
        defaultTransferShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transferList where comment does not contain UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status equals to DEFAULT_STATUS
        defaultTransferShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transferList where status equals to UPDATED_STATUS
        defaultTransferShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status not equals to DEFAULT_STATUS
        defaultTransferShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the transferList where status not equals to UPDATED_STATUS
        defaultTransferShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransferShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transferList where status equals to UPDATED_STATUS
        defaultTransferShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status is not null
        defaultTransferShouldBeFound("status.specified=true");

        // Get all the transferList where status is null
        defaultTransferShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse equals to DEFAULT_SOURCE_WARE_HOUSE
        defaultTransferShouldBeFound("sourceWareHouse.equals=" + DEFAULT_SOURCE_WARE_HOUSE);

        // Get all the transferList where sourceWareHouse equals to UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldNotBeFound("sourceWareHouse.equals=" + UPDATED_SOURCE_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse not equals to DEFAULT_SOURCE_WARE_HOUSE
        defaultTransferShouldNotBeFound("sourceWareHouse.notEquals=" + DEFAULT_SOURCE_WARE_HOUSE);

        // Get all the transferList where sourceWareHouse not equals to UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldBeFound("sourceWareHouse.notEquals=" + UPDATED_SOURCE_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse in DEFAULT_SOURCE_WARE_HOUSE or UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldBeFound("sourceWareHouse.in=" + DEFAULT_SOURCE_WARE_HOUSE + "," + UPDATED_SOURCE_WARE_HOUSE);

        // Get all the transferList where sourceWareHouse equals to UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldNotBeFound("sourceWareHouse.in=" + UPDATED_SOURCE_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse is not null
        defaultTransferShouldBeFound("sourceWareHouse.specified=true");

        // Get all the transferList where sourceWareHouse is null
        defaultTransferShouldNotBeFound("sourceWareHouse.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse contains DEFAULT_SOURCE_WARE_HOUSE
        defaultTransferShouldBeFound("sourceWareHouse.contains=" + DEFAULT_SOURCE_WARE_HOUSE);

        // Get all the transferList where sourceWareHouse contains UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldNotBeFound("sourceWareHouse.contains=" + UPDATED_SOURCE_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersBySourceWareHouseNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where sourceWareHouse does not contain DEFAULT_SOURCE_WARE_HOUSE
        defaultTransferShouldNotBeFound("sourceWareHouse.doesNotContain=" + DEFAULT_SOURCE_WARE_HOUSE);

        // Get all the transferList where sourceWareHouse does not contain UPDATED_SOURCE_WARE_HOUSE
        defaultTransferShouldBeFound("sourceWareHouse.doesNotContain=" + UPDATED_SOURCE_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse equals to DEFAULT_DESTINATION_WARE_HOUSE
        defaultTransferShouldBeFound("destinationWareHouse.equals=" + DEFAULT_DESTINATION_WARE_HOUSE);

        // Get all the transferList where destinationWareHouse equals to UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldNotBeFound("destinationWareHouse.equals=" + UPDATED_DESTINATION_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse not equals to DEFAULT_DESTINATION_WARE_HOUSE
        defaultTransferShouldNotBeFound("destinationWareHouse.notEquals=" + DEFAULT_DESTINATION_WARE_HOUSE);

        // Get all the transferList where destinationWareHouse not equals to UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldBeFound("destinationWareHouse.notEquals=" + UPDATED_DESTINATION_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse in DEFAULT_DESTINATION_WARE_HOUSE or UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldBeFound("destinationWareHouse.in=" + DEFAULT_DESTINATION_WARE_HOUSE + "," + UPDATED_DESTINATION_WARE_HOUSE);

        // Get all the transferList where destinationWareHouse equals to UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldNotBeFound("destinationWareHouse.in=" + UPDATED_DESTINATION_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse is not null
        defaultTransferShouldBeFound("destinationWareHouse.specified=true");

        // Get all the transferList where destinationWareHouse is null
        defaultTransferShouldNotBeFound("destinationWareHouse.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse contains DEFAULT_DESTINATION_WARE_HOUSE
        defaultTransferShouldBeFound("destinationWareHouse.contains=" + DEFAULT_DESTINATION_WARE_HOUSE);

        // Get all the transferList where destinationWareHouse contains UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldNotBeFound("destinationWareHouse.contains=" + UPDATED_DESTINATION_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByDestinationWareHouseNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where destinationWareHouse does not contain DEFAULT_DESTINATION_WARE_HOUSE
        defaultTransferShouldNotBeFound("destinationWareHouse.doesNotContain=" + DEFAULT_DESTINATION_WARE_HOUSE);

        // Get all the transferList where destinationWareHouse does not contain UPDATED_DESTINATION_WARE_HOUSE
        defaultTransferShouldBeFound("destinationWareHouse.doesNotContain=" + UPDATED_DESTINATION_WARE_HOUSE);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the transferList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 is not null
        defaultTransferShouldBeFound("freeField1.specified=true");

        // Get all the transferList where freeField1 is null
        defaultTransferShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transferList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified is not null
        defaultTransferShouldBeFound("lastModified.specified=true");

        // Get all the transferList where lastModified is null
        defaultTransferShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified contains UPDATED_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy is not null
        defaultTransferShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transferList where lastModifiedBy is null
        defaultTransferShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        TransferDetails transferDetails;
        if (TestUtil.findAll(em, TransferDetails.class).isEmpty()) {
            transferDetails = TransferDetailsResourceIT.createEntity(em);
            em.persist(transferDetails);
            em.flush();
        } else {
            transferDetails = TestUtil.findAll(em, TransferDetails.class).get(0);
        }
        em.persist(transferDetails);
        em.flush();
        transfer.addTransferDetails(transferDetails);
        transferRepository.saveAndFlush(transfer);
        Long transferDetailsId = transferDetails.getId();

        // Get all the transferList where transferDetails equals to transferDetailsId
        defaultTransferShouldBeFound("transferDetailsId.equals=" + transferDetailsId);

        // Get all the transferList where transferDetails equals to (transferDetailsId + 1)
        defaultTransferShouldNotBeFound("transferDetailsId.equals=" + (transferDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersByTransferRecievedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        TransferRecieved transferRecieved;
        if (TestUtil.findAll(em, TransferRecieved.class).isEmpty()) {
            transferRecieved = TransferRecievedResourceIT.createEntity(em);
            em.persist(transferRecieved);
            em.flush();
        } else {
            transferRecieved = TestUtil.findAll(em, TransferRecieved.class).get(0);
        }
        em.persist(transferRecieved);
        em.flush();
        transfer.addTransferRecieved(transferRecieved);
        transferRepository.saveAndFlush(transfer);
        Long transferRecievedId = transferRecieved.getId();

        // Get all the transferList where transferRecieved equals to transferRecievedId
        defaultTransferShouldBeFound("transferRecievedId.equals=" + transferRecievedId);

        // Get all the transferList where transferRecieved equals to (transferRecievedId + 1)
        defaultTransferShouldNotBeFound("transferRecievedId.equals=" + (transferRecievedId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersByTransferDetailsApprovalsIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        TransferDetailsApprovals transferDetailsApprovals;
        if (TestUtil.findAll(em, TransferDetailsApprovals.class).isEmpty()) {
            transferDetailsApprovals = TransferDetailsApprovalsResourceIT.createEntity(em);
            em.persist(transferDetailsApprovals);
            em.flush();
        } else {
            transferDetailsApprovals = TestUtil.findAll(em, TransferDetailsApprovals.class).get(0);
        }
        em.persist(transferDetailsApprovals);
        em.flush();
        transfer.addTransferDetailsApprovals(transferDetailsApprovals);
        transferRepository.saveAndFlush(transfer);
        Long transferDetailsApprovalsId = transferDetailsApprovals.getId();

        // Get all the transferList where transferDetailsApprovals equals to transferDetailsApprovalsId
        defaultTransferShouldBeFound("transferDetailsApprovalsId.equals=" + transferDetailsApprovalsId);

        // Get all the transferList where transferDetailsApprovals equals to (transferDetailsApprovalsId + 1)
        defaultTransferShouldNotBeFound("transferDetailsApprovalsId.equals=" + (transferDetailsApprovalsId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
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
        transfer.setSecurityUser(securityUser);
        transferRepository.saveAndFlush(transfer);
        Long securityUserId = securityUser.getId();

        // Get all the transferList where securityUser equals to securityUserId
        defaultTransferShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the transferList where securityUser equals to (securityUserId + 1)
        defaultTransferShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersByWareHouseIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
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
        transfer.setWareHouse(wareHouse);
        transferRepository.saveAndFlush(transfer);
        Long wareHouseId = wareHouse.getId();

        // Get all the transferList where wareHouse equals to wareHouseId
        defaultTransferShouldBeFound("wareHouseId.equals=" + wareHouseId);

        // Get all the transferList where wareHouse equals to (wareHouseId + 1)
        defaultTransferShouldNotBeFound("wareHouseId.equals=" + (wareHouseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferShouldBeFound(String filter) throws Exception {
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tranferDate").value(hasItem(DEFAULT_TRANFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sourceWareHouse").value(hasItem(DEFAULT_SOURCE_WARE_HOUSE)))
            .andExpect(jsonPath("$.[*].destinationWareHouse").value(hasItem(DEFAULT_DESTINATION_WARE_HOUSE)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferShouldNotBeFound(String filter) throws Exception {
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findById(transfer.getId()).get();
        // Disconnect from session so that the updates on updatedTransfer are not directly saved in db
        em.detach(updatedTransfer);
        updatedTransfer
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .sourceWareHouse(UPDATED_SOURCE_WARE_HOUSE)
            .destinationWareHouse(UPDATED_DESTINATION_WARE_HOUSE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TransferDTO transferDTO = transferMapper.toDto(updatedTransfer);

        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTranferDate()).isEqualTo(UPDATED_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getSourceWareHouse()).isEqualTo(UPDATED_SOURCE_WARE_HOUSE);
        assertThat(testTransfer.getDestinationWareHouse()).isEqualTo(UPDATED_DESTINATION_WARE_HOUSE);
        assertThat(testTransfer.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransfer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer
            .tranferDate(UPDATED_TRANFER_DATE)
            .status(UPDATED_STATUS)
            .sourceWareHouse(UPDATED_SOURCE_WARE_HOUSE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTranferDate()).isEqualTo(UPDATED_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getSourceWareHouse()).isEqualTo(UPDATED_SOURCE_WARE_HOUSE);
        assertThat(testTransfer.getDestinationWareHouse()).isEqualTo(DEFAULT_DESTINATION_WARE_HOUSE);
        assertThat(testTransfer.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransfer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .sourceWareHouse(UPDATED_SOURCE_WARE_HOUSE)
            .destinationWareHouse(UPDATED_DESTINATION_WARE_HOUSE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTranferDate()).isEqualTo(UPDATED_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getSourceWareHouse()).isEqualTo(UPDATED_SOURCE_WARE_HOUSE);
        assertThat(testTransfer.getDestinationWareHouse()).isEqualTo(UPDATED_DESTINATION_WARE_HOUSE);
        assertThat(testTransfer.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransfer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Delete the transfer
        restTransferMockMvc
            .perform(delete(ENTITY_API_URL_ID, transfer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
