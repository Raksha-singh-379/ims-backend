package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.ClientDetails;
import com.techvg.inventory.management.domain.enumeration.ClientType;
import com.techvg.inventory.management.repository.ClientDetailsRepository;
import com.techvg.inventory.management.service.criteria.ClientDetailsCriteria;
import com.techvg.inventory.management.service.dto.ClientDetailsDTO;
import com.techvg.inventory.management.service.mapper.ClientDetailsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ClientDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientDetailsResourceIT {

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_GSTIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ClientType DEFAULT_CLIENT_TYPE = ClientType.SUPPLIER;
    private static final ClientType UPDATED_CLIENT_TYPE = ClientType.CONSUMER;

    private static final Boolean DEFAULT_ISACTIVATED = false;
    private static final Boolean UPDATED_ISACTIVATED = true;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final String DEFAULT_NAME_OF_BENEFICIARY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_BENEFICIARY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GST_CERTIFICATE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GST_CERTIFICATE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PAN_CARD_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PAN_CARD_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CANCELLED_CHEQUE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CANCELLED_CHEQUE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_UD_YOG_AADHAR_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UD_YOG_AADHAR_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private ClientDetailsMapper clientDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientDetailsMockMvc;

    private ClientDetails clientDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDetails createEntity(EntityManager em) {
        ClientDetails clientDetails = new ClientDetails()
            .clientName(DEFAULT_CLIENT_NAME)
            .mobileNo(DEFAULT_MOBILE_NO)
            .email(DEFAULT_EMAIL)
            .billingAddress(DEFAULT_BILLING_ADDRESS)
            .companyName(DEFAULT_COMPANY_NAME)
            .companyContactNo(DEFAULT_COMPANY_CONTACT_NO)
            .website(DEFAULT_WEBSITE)
            .gstinNumber(DEFAULT_GSTIN_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .clientType(DEFAULT_CLIENT_TYPE)
            .isactivated(DEFAULT_ISACTIVATED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .isApproved(DEFAULT_IS_APPROVED)
            .nameOfBeneficiary(DEFAULT_NAME_OF_BENEFICIARY)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .ifscCode(DEFAULT_IFSC_CODE)
            .gstCertificateImage(DEFAULT_GST_CERTIFICATE_IMAGE)
            .gstCertificateImageContentType(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)
            .panCardImage(DEFAULT_PAN_CARD_IMAGE)
            .panCardImageContentType(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE)
            .cancelledChequeImage(DEFAULT_CANCELLED_CHEQUE_IMAGE)
            .cancelledChequeImageContentType(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)
            .udYogAadharImage(DEFAULT_UD_YOG_AADHAR_IMAGE)
            .udYogAadharImageContentType(DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return clientDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDetails createUpdatedEntity(EntityManager em) {
        ClientDetails clientDetails = new ClientDetails()
            .clientName(UPDATED_CLIENT_NAME)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .companyName(UPDATED_COMPANY_NAME)
            .companyContactNo(UPDATED_COMPANY_CONTACT_NO)
            .website(UPDATED_WEBSITE)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .clientType(UPDATED_CLIENT_TYPE)
            .isactivated(UPDATED_ISACTIVATED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .isApproved(UPDATED_IS_APPROVED)
            .nameOfBeneficiary(UPDATED_NAME_OF_BENEFICIARY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .ifscCode(UPDATED_IFSC_CODE)
            .gstCertificateImage(UPDATED_GST_CERTIFICATE_IMAGE)
            .gstCertificateImageContentType(UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)
            .panCardImage(UPDATED_PAN_CARD_IMAGE)
            .panCardImageContentType(UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE)
            .cancelledChequeImage(UPDATED_CANCELLED_CHEQUE_IMAGE)
            .cancelledChequeImageContentType(UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)
            .udYogAadharImage(UPDATED_UD_YOG_AADHAR_IMAGE)
            .udYogAadharImageContentType(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return clientDetails;
    }

    @BeforeEach
    public void initTest() {
        clientDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createClientDetails() throws Exception {
        int databaseSizeBeforeCreate = clientDetailsRepository.findAll().size();
        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);
        restClientDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClientDetails.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testClientDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientDetails.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testClientDetails.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testClientDetails.getCompanyContactNo()).isEqualTo(DEFAULT_COMPANY_CONTACT_NO);
        assertThat(testClientDetails.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testClientDetails.getGstinNumber()).isEqualTo(DEFAULT_GSTIN_NUMBER);
        assertThat(testClientDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientDetails.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testClientDetails.getIsactivated()).isEqualTo(DEFAULT_ISACTIVATED);
        assertThat(testClientDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testClientDetails.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testClientDetails.getNameOfBeneficiary()).isEqualTo(DEFAULT_NAME_OF_BENEFICIARY);
        assertThat(testClientDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testClientDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testClientDetails.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testClientDetails.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testClientDetails.getGstCertificateImage()).isEqualTo(DEFAULT_GST_CERTIFICATE_IMAGE);
        assertThat(testClientDetails.getGstCertificateImageContentType()).isEqualTo(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getPanCardImage()).isEqualTo(DEFAULT_PAN_CARD_IMAGE);
        assertThat(testClientDetails.getPanCardImageContentType()).isEqualTo(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getCancelledChequeImage()).isEqualTo(DEFAULT_CANCELLED_CHEQUE_IMAGE);
        assertThat(testClientDetails.getCancelledChequeImageContentType()).isEqualTo(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getUdYogAadharImage()).isEqualTo(DEFAULT_UD_YOG_AADHAR_IMAGE);
        assertThat(testClientDetails.getUdYogAadharImageContentType()).isEqualTo(DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testClientDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createClientDetailsWithExistingId() throws Exception {
        // Create the ClientDetails with an existing ID
        clientDetails.setId(1L);
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        int databaseSizeBeforeCreate = clientDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClientNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setClientName(null);

        // Create the ClientDetails, which fails.
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        restClientDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGstinNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setGstinNumber(null);

        // Create the ClientDetails, which fails.
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        restClientDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setAccountNumber(null);

        // Create the ClientDetails, which fails.
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        restClientDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyContactNo").value(hasItem(DEFAULT_COMPANY_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].gstinNumber").value(hasItem(DEFAULT_GSTIN_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isactivated").value(hasItem(DEFAULT_ISACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].nameOfBeneficiary").value(hasItem(DEFAULT_NAME_OF_BENEFICIARY)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].gstCertificateImageContentType").value(hasItem(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].gstCertificateImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_GST_CERTIFICATE_IMAGE))))
            .andExpect(jsonPath("$.[*].panCardImageContentType").value(hasItem(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].panCardImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAN_CARD_IMAGE))))
            .andExpect(jsonPath("$.[*].cancelledChequeImageContentType").value(hasItem(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cancelledChequeImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_CANCELLED_CHEQUE_IMAGE))))
            .andExpect(jsonPath("$.[*].udYogAadharImageContentType").value(hasItem(DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].udYogAadharImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_UD_YOG_AADHAR_IMAGE))))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get the clientDetails
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, clientDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientDetails.getId().intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.billingAddress").value(DEFAULT_BILLING_ADDRESS))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyContactNo").value(DEFAULT_COMPANY_CONTACT_NO))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.gstinNumber").value(DEFAULT_GSTIN_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE.toString()))
            .andExpect(jsonPath("$.isactivated").value(DEFAULT_ISACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.nameOfBeneficiary").value(DEFAULT_NAME_OF_BENEFICIARY))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.gstCertificateImageContentType").value(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.gstCertificateImage").value(Base64Utils.encodeToString(DEFAULT_GST_CERTIFICATE_IMAGE)))
            .andExpect(jsonPath("$.panCardImageContentType").value(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.panCardImage").value(Base64Utils.encodeToString(DEFAULT_PAN_CARD_IMAGE)))
            .andExpect(jsonPath("$.cancelledChequeImageContentType").value(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.cancelledChequeImage").value(Base64Utils.encodeToString(DEFAULT_CANCELLED_CHEQUE_IMAGE)))
            .andExpect(jsonPath("$.udYogAadharImageContentType").value(DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.udYogAadharImage").value(Base64Utils.encodeToString(DEFAULT_UD_YOG_AADHAR_IMAGE)))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getClientDetailsByIdFiltering() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        Long id = clientDetails.getId();

        defaultClientDetailsShouldBeFound("id.equals=" + id);
        defaultClientDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultClientDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultClientDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName equals to DEFAULT_CLIENT_NAME
        defaultClientDetailsShouldBeFound("clientName.equals=" + DEFAULT_CLIENT_NAME);

        // Get all the clientDetailsList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientDetailsShouldNotBeFound("clientName.equals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName not equals to DEFAULT_CLIENT_NAME
        defaultClientDetailsShouldNotBeFound("clientName.notEquals=" + DEFAULT_CLIENT_NAME);

        // Get all the clientDetailsList where clientName not equals to UPDATED_CLIENT_NAME
        defaultClientDetailsShouldBeFound("clientName.notEquals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName in DEFAULT_CLIENT_NAME or UPDATED_CLIENT_NAME
        defaultClientDetailsShouldBeFound("clientName.in=" + DEFAULT_CLIENT_NAME + "," + UPDATED_CLIENT_NAME);

        // Get all the clientDetailsList where clientName equals to UPDATED_CLIENT_NAME
        defaultClientDetailsShouldNotBeFound("clientName.in=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName is not null
        defaultClientDetailsShouldBeFound("clientName.specified=true");

        // Get all the clientDetailsList where clientName is null
        defaultClientDetailsShouldNotBeFound("clientName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName contains DEFAULT_CLIENT_NAME
        defaultClientDetailsShouldBeFound("clientName.contains=" + DEFAULT_CLIENT_NAME);

        // Get all the clientDetailsList where clientName contains UPDATED_CLIENT_NAME
        defaultClientDetailsShouldNotBeFound("clientName.contains=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientName does not contain DEFAULT_CLIENT_NAME
        defaultClientDetailsShouldNotBeFound("clientName.doesNotContain=" + DEFAULT_CLIENT_NAME);

        // Get all the clientDetailsList where clientName does not contain UPDATED_CLIENT_NAME
        defaultClientDetailsShouldBeFound("clientName.doesNotContain=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultClientDetailsShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the clientDetailsList where mobileNo equals to UPDATED_MOBILE_NO
        defaultClientDetailsShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultClientDetailsShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the clientDetailsList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultClientDetailsShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultClientDetailsShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the clientDetailsList where mobileNo equals to UPDATED_MOBILE_NO
        defaultClientDetailsShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo is not null
        defaultClientDetailsShouldBeFound("mobileNo.specified=true");

        // Get all the clientDetailsList where mobileNo is null
        defaultClientDetailsShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo contains DEFAULT_MOBILE_NO
        defaultClientDetailsShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the clientDetailsList where mobileNo contains UPDATED_MOBILE_NO
        defaultClientDetailsShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultClientDetailsShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the clientDetailsList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultClientDetailsShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email equals to DEFAULT_EMAIL
        defaultClientDetailsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the clientDetailsList where email equals to UPDATED_EMAIL
        defaultClientDetailsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email not equals to DEFAULT_EMAIL
        defaultClientDetailsShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the clientDetailsList where email not equals to UPDATED_EMAIL
        defaultClientDetailsShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultClientDetailsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the clientDetailsList where email equals to UPDATED_EMAIL
        defaultClientDetailsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email is not null
        defaultClientDetailsShouldBeFound("email.specified=true");

        // Get all the clientDetailsList where email is null
        defaultClientDetailsShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email contains DEFAULT_EMAIL
        defaultClientDetailsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the clientDetailsList where email contains UPDATED_EMAIL
        defaultClientDetailsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllClientDetailsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where email does not contain DEFAULT_EMAIL
        defaultClientDetailsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the clientDetailsList where email does not contain UPDATED_EMAIL
        defaultClientDetailsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress equals to DEFAULT_BILLING_ADDRESS
        defaultClientDetailsShouldBeFound("billingAddress.equals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the clientDetailsList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldNotBeFound("billingAddress.equals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress not equals to DEFAULT_BILLING_ADDRESS
        defaultClientDetailsShouldNotBeFound("billingAddress.notEquals=" + DEFAULT_BILLING_ADDRESS);

        // Get all the clientDetailsList where billingAddress not equals to UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldBeFound("billingAddress.notEquals=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress in DEFAULT_BILLING_ADDRESS or UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldBeFound("billingAddress.in=" + DEFAULT_BILLING_ADDRESS + "," + UPDATED_BILLING_ADDRESS);

        // Get all the clientDetailsList where billingAddress equals to UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldNotBeFound("billingAddress.in=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress is not null
        defaultClientDetailsShouldBeFound("billingAddress.specified=true");

        // Get all the clientDetailsList where billingAddress is null
        defaultClientDetailsShouldNotBeFound("billingAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress contains DEFAULT_BILLING_ADDRESS
        defaultClientDetailsShouldBeFound("billingAddress.contains=" + DEFAULT_BILLING_ADDRESS);

        // Get all the clientDetailsList where billingAddress contains UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldNotBeFound("billingAddress.contains=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBillingAddressNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where billingAddress does not contain DEFAULT_BILLING_ADDRESS
        defaultClientDetailsShouldNotBeFound("billingAddress.doesNotContain=" + DEFAULT_BILLING_ADDRESS);

        // Get all the clientDetailsList where billingAddress does not contain UPDATED_BILLING_ADDRESS
        defaultClientDetailsShouldBeFound("billingAddress.doesNotContain=" + UPDATED_BILLING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName equals to DEFAULT_COMPANY_NAME
        defaultClientDetailsShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the clientDetailsList where companyName equals to UPDATED_COMPANY_NAME
        defaultClientDetailsShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName not equals to DEFAULT_COMPANY_NAME
        defaultClientDetailsShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);

        // Get all the clientDetailsList where companyName not equals to UPDATED_COMPANY_NAME
        defaultClientDetailsShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultClientDetailsShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the clientDetailsList where companyName equals to UPDATED_COMPANY_NAME
        defaultClientDetailsShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName is not null
        defaultClientDetailsShouldBeFound("companyName.specified=true");

        // Get all the clientDetailsList where companyName is null
        defaultClientDetailsShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName contains DEFAULT_COMPANY_NAME
        defaultClientDetailsShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the clientDetailsList where companyName contains UPDATED_COMPANY_NAME
        defaultClientDetailsShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultClientDetailsShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the clientDetailsList where companyName does not contain UPDATED_COMPANY_NAME
        defaultClientDetailsShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo equals to DEFAULT_COMPANY_CONTACT_NO
        defaultClientDetailsShouldBeFound("companyContactNo.equals=" + DEFAULT_COMPANY_CONTACT_NO);

        // Get all the clientDetailsList where companyContactNo equals to UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldNotBeFound("companyContactNo.equals=" + UPDATED_COMPANY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo not equals to DEFAULT_COMPANY_CONTACT_NO
        defaultClientDetailsShouldNotBeFound("companyContactNo.notEquals=" + DEFAULT_COMPANY_CONTACT_NO);

        // Get all the clientDetailsList where companyContactNo not equals to UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldBeFound("companyContactNo.notEquals=" + UPDATED_COMPANY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo in DEFAULT_COMPANY_CONTACT_NO or UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldBeFound("companyContactNo.in=" + DEFAULT_COMPANY_CONTACT_NO + "," + UPDATED_COMPANY_CONTACT_NO);

        // Get all the clientDetailsList where companyContactNo equals to UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldNotBeFound("companyContactNo.in=" + UPDATED_COMPANY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo is not null
        defaultClientDetailsShouldBeFound("companyContactNo.specified=true");

        // Get all the clientDetailsList where companyContactNo is null
        defaultClientDetailsShouldNotBeFound("companyContactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo contains DEFAULT_COMPANY_CONTACT_NO
        defaultClientDetailsShouldBeFound("companyContactNo.contains=" + DEFAULT_COMPANY_CONTACT_NO);

        // Get all the clientDetailsList where companyContactNo contains UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldNotBeFound("companyContactNo.contains=" + UPDATED_COMPANY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByCompanyContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where companyContactNo does not contain DEFAULT_COMPANY_CONTACT_NO
        defaultClientDetailsShouldNotBeFound("companyContactNo.doesNotContain=" + DEFAULT_COMPANY_CONTACT_NO);

        // Get all the clientDetailsList where companyContactNo does not contain UPDATED_COMPANY_CONTACT_NO
        defaultClientDetailsShouldBeFound("companyContactNo.doesNotContain=" + UPDATED_COMPANY_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website equals to DEFAULT_WEBSITE
        defaultClientDetailsShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the clientDetailsList where website equals to UPDATED_WEBSITE
        defaultClientDetailsShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website not equals to DEFAULT_WEBSITE
        defaultClientDetailsShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the clientDetailsList where website not equals to UPDATED_WEBSITE
        defaultClientDetailsShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultClientDetailsShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the clientDetailsList where website equals to UPDATED_WEBSITE
        defaultClientDetailsShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website is not null
        defaultClientDetailsShouldBeFound("website.specified=true");

        // Get all the clientDetailsList where website is null
        defaultClientDetailsShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website contains DEFAULT_WEBSITE
        defaultClientDetailsShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the clientDetailsList where website contains UPDATED_WEBSITE
        defaultClientDetailsShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where website does not contain DEFAULT_WEBSITE
        defaultClientDetailsShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the clientDetailsList where website does not contain UPDATED_WEBSITE
        defaultClientDetailsShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber equals to DEFAULT_GSTIN_NUMBER
        defaultClientDetailsShouldBeFound("gstinNumber.equals=" + DEFAULT_GSTIN_NUMBER);

        // Get all the clientDetailsList where gstinNumber equals to UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldNotBeFound("gstinNumber.equals=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber not equals to DEFAULT_GSTIN_NUMBER
        defaultClientDetailsShouldNotBeFound("gstinNumber.notEquals=" + DEFAULT_GSTIN_NUMBER);

        // Get all the clientDetailsList where gstinNumber not equals to UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldBeFound("gstinNumber.notEquals=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber in DEFAULT_GSTIN_NUMBER or UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldBeFound("gstinNumber.in=" + DEFAULT_GSTIN_NUMBER + "," + UPDATED_GSTIN_NUMBER);

        // Get all the clientDetailsList where gstinNumber equals to UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldNotBeFound("gstinNumber.in=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber is not null
        defaultClientDetailsShouldBeFound("gstinNumber.specified=true");

        // Get all the clientDetailsList where gstinNumber is null
        defaultClientDetailsShouldNotBeFound("gstinNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber contains DEFAULT_GSTIN_NUMBER
        defaultClientDetailsShouldBeFound("gstinNumber.contains=" + DEFAULT_GSTIN_NUMBER);

        // Get all the clientDetailsList where gstinNumber contains UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldNotBeFound("gstinNumber.contains=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByGstinNumberNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where gstinNumber does not contain DEFAULT_GSTIN_NUMBER
        defaultClientDetailsShouldNotBeFound("gstinNumber.doesNotContain=" + DEFAULT_GSTIN_NUMBER);

        // Get all the clientDetailsList where gstinNumber does not contain UPDATED_GSTIN_NUMBER
        defaultClientDetailsShouldBeFound("gstinNumber.doesNotContain=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description equals to DEFAULT_DESCRIPTION
        defaultClientDetailsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the clientDetailsList where description equals to UPDATED_DESCRIPTION
        defaultClientDetailsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description not equals to DEFAULT_DESCRIPTION
        defaultClientDetailsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the clientDetailsList where description not equals to UPDATED_DESCRIPTION
        defaultClientDetailsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultClientDetailsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the clientDetailsList where description equals to UPDATED_DESCRIPTION
        defaultClientDetailsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description is not null
        defaultClientDetailsShouldBeFound("description.specified=true");

        // Get all the clientDetailsList where description is null
        defaultClientDetailsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description contains DEFAULT_DESCRIPTION
        defaultClientDetailsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the clientDetailsList where description contains UPDATED_DESCRIPTION
        defaultClientDetailsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClientDetailsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where description does not contain DEFAULT_DESCRIPTION
        defaultClientDetailsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the clientDetailsList where description does not contain UPDATED_DESCRIPTION
        defaultClientDetailsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientType equals to DEFAULT_CLIENT_TYPE
        defaultClientDetailsShouldBeFound("clientType.equals=" + DEFAULT_CLIENT_TYPE);

        // Get all the clientDetailsList where clientType equals to UPDATED_CLIENT_TYPE
        defaultClientDetailsShouldNotBeFound("clientType.equals=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientType not equals to DEFAULT_CLIENT_TYPE
        defaultClientDetailsShouldNotBeFound("clientType.notEquals=" + DEFAULT_CLIENT_TYPE);

        // Get all the clientDetailsList where clientType not equals to UPDATED_CLIENT_TYPE
        defaultClientDetailsShouldBeFound("clientType.notEquals=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientTypeIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientType in DEFAULT_CLIENT_TYPE or UPDATED_CLIENT_TYPE
        defaultClientDetailsShouldBeFound("clientType.in=" + DEFAULT_CLIENT_TYPE + "," + UPDATED_CLIENT_TYPE);

        // Get all the clientDetailsList where clientType equals to UPDATED_CLIENT_TYPE
        defaultClientDetailsShouldNotBeFound("clientType.in=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByClientTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where clientType is not null
        defaultClientDetailsShouldBeFound("clientType.specified=true");

        // Get all the clientDetailsList where clientType is null
        defaultClientDetailsShouldNotBeFound("clientType.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsactivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isactivated equals to DEFAULT_ISACTIVATED
        defaultClientDetailsShouldBeFound("isactivated.equals=" + DEFAULT_ISACTIVATED);

        // Get all the clientDetailsList where isactivated equals to UPDATED_ISACTIVATED
        defaultClientDetailsShouldNotBeFound("isactivated.equals=" + UPDATED_ISACTIVATED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsactivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isactivated not equals to DEFAULT_ISACTIVATED
        defaultClientDetailsShouldNotBeFound("isactivated.notEquals=" + DEFAULT_ISACTIVATED);

        // Get all the clientDetailsList where isactivated not equals to UPDATED_ISACTIVATED
        defaultClientDetailsShouldBeFound("isactivated.notEquals=" + UPDATED_ISACTIVATED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsactivatedIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isactivated in DEFAULT_ISACTIVATED or UPDATED_ISACTIVATED
        defaultClientDetailsShouldBeFound("isactivated.in=" + DEFAULT_ISACTIVATED + "," + UPDATED_ISACTIVATED);

        // Get all the clientDetailsList where isactivated equals to UPDATED_ISACTIVATED
        defaultClientDetailsShouldNotBeFound("isactivated.in=" + UPDATED_ISACTIVATED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsactivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isactivated is not null
        defaultClientDetailsShouldBeFound("isactivated.specified=true");

        // Get all the clientDetailsList where isactivated is null
        defaultClientDetailsShouldNotBeFound("isactivated.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultClientDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the clientDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultClientDetailsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the clientDetailsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the clientDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 is not null
        defaultClientDetailsShouldBeFound("freeField1.specified=true");

        // Get all the clientDetailsList where freeField1 is null
        defaultClientDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultClientDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the clientDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllClientDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultClientDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the clientDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultClientDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isApproved equals to DEFAULT_IS_APPROVED
        defaultClientDetailsShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the clientDetailsList where isApproved equals to UPDATED_IS_APPROVED
        defaultClientDetailsShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultClientDetailsShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the clientDetailsList where isApproved not equals to UPDATED_IS_APPROVED
        defaultClientDetailsShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultClientDetailsShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the clientDetailsList where isApproved equals to UPDATED_IS_APPROVED
        defaultClientDetailsShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where isApproved is not null
        defaultClientDetailsShouldBeFound("isApproved.specified=true");

        // Get all the clientDetailsList where isApproved is null
        defaultClientDetailsShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary equals to DEFAULT_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.equals=" + DEFAULT_NAME_OF_BENEFICIARY);

        // Get all the clientDetailsList where nameOfBeneficiary equals to UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.equals=" + UPDATED_NAME_OF_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary not equals to DEFAULT_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.notEquals=" + DEFAULT_NAME_OF_BENEFICIARY);

        // Get all the clientDetailsList where nameOfBeneficiary not equals to UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.notEquals=" + UPDATED_NAME_OF_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary in DEFAULT_NAME_OF_BENEFICIARY or UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.in=" + DEFAULT_NAME_OF_BENEFICIARY + "," + UPDATED_NAME_OF_BENEFICIARY);

        // Get all the clientDetailsList where nameOfBeneficiary equals to UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.in=" + UPDATED_NAME_OF_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary is not null
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.specified=true");

        // Get all the clientDetailsList where nameOfBeneficiary is null
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary contains DEFAULT_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.contains=" + DEFAULT_NAME_OF_BENEFICIARY);

        // Get all the clientDetailsList where nameOfBeneficiary contains UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.contains=" + UPDATED_NAME_OF_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByNameOfBeneficiaryNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where nameOfBeneficiary does not contain DEFAULT_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldNotBeFound("nameOfBeneficiary.doesNotContain=" + DEFAULT_NAME_OF_BENEFICIARY);

        // Get all the clientDetailsList where nameOfBeneficiary does not contain UPDATED_NAME_OF_BENEFICIARY
        defaultClientDetailsShouldBeFound("nameOfBeneficiary.doesNotContain=" + UPDATED_NAME_OF_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultClientDetailsShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the clientDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultClientDetailsShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the clientDetailsList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the clientDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber is not null
        defaultClientDetailsShouldBeFound("accountNumber.specified=true");

        // Get all the clientDetailsList where accountNumber is null
        defaultClientDetailsShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultClientDetailsShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the clientDetailsList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultClientDetailsShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the clientDetailsList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultClientDetailsShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName equals to DEFAULT_BANK_NAME
        defaultClientDetailsShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the clientDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultClientDetailsShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName not equals to DEFAULT_BANK_NAME
        defaultClientDetailsShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the clientDetailsList where bankName not equals to UPDATED_BANK_NAME
        defaultClientDetailsShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultClientDetailsShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the clientDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultClientDetailsShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName is not null
        defaultClientDetailsShouldBeFound("bankName.specified=true");

        // Get all the clientDetailsList where bankName is null
        defaultClientDetailsShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName contains DEFAULT_BANK_NAME
        defaultClientDetailsShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the clientDetailsList where bankName contains UPDATED_BANK_NAME
        defaultClientDetailsShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where bankName does not contain DEFAULT_BANK_NAME
        defaultClientDetailsShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the clientDetailsList where bankName does not contain UPDATED_BANK_NAME
        defaultClientDetailsShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultClientDetailsShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the clientDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType not equals to DEFAULT_ACCOUNT_TYPE
        defaultClientDetailsShouldNotBeFound("accountType.notEquals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the clientDetailsList where accountType not equals to UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldBeFound("accountType.notEquals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the clientDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType is not null
        defaultClientDetailsShouldBeFound("accountType.specified=true");

        // Get all the clientDetailsList where accountType is null
        defaultClientDetailsShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultClientDetailsShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the clientDetailsList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultClientDetailsShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the clientDetailsList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultClientDetailsShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultClientDetailsShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the clientDetailsList where ifscCode equals to UPDATED_IFSC_CODE
        defaultClientDetailsShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode not equals to DEFAULT_IFSC_CODE
        defaultClientDetailsShouldNotBeFound("ifscCode.notEquals=" + DEFAULT_IFSC_CODE);

        // Get all the clientDetailsList where ifscCode not equals to UPDATED_IFSC_CODE
        defaultClientDetailsShouldBeFound("ifscCode.notEquals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultClientDetailsShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the clientDetailsList where ifscCode equals to UPDATED_IFSC_CODE
        defaultClientDetailsShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode is not null
        defaultClientDetailsShouldBeFound("ifscCode.specified=true");

        // Get all the clientDetailsList where ifscCode is null
        defaultClientDetailsShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode contains DEFAULT_IFSC_CODE
        defaultClientDetailsShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the clientDetailsList where ifscCode contains UPDATED_IFSC_CODE
        defaultClientDetailsShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultClientDetailsShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the clientDetailsList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultClientDetailsShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultClientDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the clientDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultClientDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the clientDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the clientDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified is not null
        defaultClientDetailsShouldBeFound("lastModified.specified=true");

        // Get all the clientDetailsList where lastModified is null
        defaultClientDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultClientDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the clientDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultClientDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the clientDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultClientDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultClientDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clientDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clientDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the clientDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy is not null
        defaultClientDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the clientDetailsList where lastModifiedBy is null
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultClientDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clientDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClientDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultClientDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clientDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultClientDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientDetailsShouldBeFound(String filter) throws Exception {
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyContactNo").value(hasItem(DEFAULT_COMPANY_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].gstinNumber").value(hasItem(DEFAULT_GSTIN_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isactivated").value(hasItem(DEFAULT_ISACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].nameOfBeneficiary").value(hasItem(DEFAULT_NAME_OF_BENEFICIARY)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].gstCertificateImageContentType").value(hasItem(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].gstCertificateImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_GST_CERTIFICATE_IMAGE))))
            .andExpect(jsonPath("$.[*].panCardImageContentType").value(hasItem(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].panCardImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAN_CARD_IMAGE))))
            .andExpect(jsonPath("$.[*].cancelledChequeImageContentType").value(hasItem(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cancelledChequeImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_CANCELLED_CHEQUE_IMAGE))))
            .andExpect(jsonPath("$.[*].udYogAadharImageContentType").value(hasItem(DEFAULT_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].udYogAadharImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_UD_YOG_AADHAR_IMAGE))))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientDetailsShouldNotBeFound(String filter) throws Exception {
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClientDetails() throws Exception {
        // Get the clientDetails
        restClientDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // Update the clientDetails
        ClientDetails updatedClientDetails = clientDetailsRepository.findById(clientDetails.getId()).get();
        // Disconnect from session so that the updates on updatedClientDetails are not directly saved in db
        em.detach(updatedClientDetails);
        updatedClientDetails
            .clientName(UPDATED_CLIENT_NAME)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .companyName(UPDATED_COMPANY_NAME)
            .companyContactNo(UPDATED_COMPANY_CONTACT_NO)
            .website(UPDATED_WEBSITE)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .clientType(UPDATED_CLIENT_TYPE)
            .isactivated(UPDATED_ISACTIVATED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .isApproved(UPDATED_IS_APPROVED)
            .nameOfBeneficiary(UPDATED_NAME_OF_BENEFICIARY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .ifscCode(UPDATED_IFSC_CODE)
            .gstCertificateImage(UPDATED_GST_CERTIFICATE_IMAGE)
            .gstCertificateImageContentType(UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)
            .panCardImage(UPDATED_PAN_CARD_IMAGE)
            .panCardImageContentType(UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE)
            .cancelledChequeImage(UPDATED_CANCELLED_CHEQUE_IMAGE)
            .cancelledChequeImageContentType(UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)
            .udYogAadharImage(UPDATED_UD_YOG_AADHAR_IMAGE)
            .udYogAadharImageContentType(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(updatedClientDetails);

        restClientDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClientDetails.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testClientDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientDetails.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testClientDetails.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testClientDetails.getCompanyContactNo()).isEqualTo(UPDATED_COMPANY_CONTACT_NO);
        assertThat(testClientDetails.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClientDetails.getGstinNumber()).isEqualTo(UPDATED_GSTIN_NUMBER);
        assertThat(testClientDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientDetails.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testClientDetails.getIsactivated()).isEqualTo(UPDATED_ISACTIVATED);
        assertThat(testClientDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testClientDetails.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testClientDetails.getNameOfBeneficiary()).isEqualTo(UPDATED_NAME_OF_BENEFICIARY);
        assertThat(testClientDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testClientDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testClientDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testClientDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testClientDetails.getGstCertificateImage()).isEqualTo(UPDATED_GST_CERTIFICATE_IMAGE);
        assertThat(testClientDetails.getGstCertificateImageContentType()).isEqualTo(UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getPanCardImage()).isEqualTo(UPDATED_PAN_CARD_IMAGE);
        assertThat(testClientDetails.getPanCardImageContentType()).isEqualTo(UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getCancelledChequeImage()).isEqualTo(UPDATED_CANCELLED_CHEQUE_IMAGE);
        assertThat(testClientDetails.getCancelledChequeImageContentType()).isEqualTo(UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getUdYogAadharImage()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE);
        assertThat(testClientDetails.getUdYogAadharImageContentType()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testClientDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientDetailsWithPatch() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // Update the clientDetails using partial update
        ClientDetails partialUpdatedClientDetails = new ClientDetails();
        partialUpdatedClientDetails.setId(clientDetails.getId());

        partialUpdatedClientDetails
            .companyName(UPDATED_COMPANY_NAME)
            .companyContactNo(UPDATED_COMPANY_CONTACT_NO)
            .website(UPDATED_WEBSITE)
            .description(UPDATED_DESCRIPTION)
            .clientType(UPDATED_CLIENT_TYPE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .ifscCode(UPDATED_IFSC_CODE)
            .udYogAadharImage(UPDATED_UD_YOG_AADHAR_IMAGE)
            .udYogAadharImageContentType(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE);

        restClientDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientDetails))
            )
            .andExpect(status().isOk());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClientDetails.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testClientDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientDetails.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testClientDetails.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testClientDetails.getCompanyContactNo()).isEqualTo(UPDATED_COMPANY_CONTACT_NO);
        assertThat(testClientDetails.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClientDetails.getGstinNumber()).isEqualTo(DEFAULT_GSTIN_NUMBER);
        assertThat(testClientDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientDetails.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testClientDetails.getIsactivated()).isEqualTo(DEFAULT_ISACTIVATED);
        assertThat(testClientDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testClientDetails.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testClientDetails.getNameOfBeneficiary()).isEqualTo(DEFAULT_NAME_OF_BENEFICIARY);
        assertThat(testClientDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testClientDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testClientDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testClientDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testClientDetails.getGstCertificateImage()).isEqualTo(DEFAULT_GST_CERTIFICATE_IMAGE);
        assertThat(testClientDetails.getGstCertificateImageContentType()).isEqualTo(DEFAULT_GST_CERTIFICATE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getPanCardImage()).isEqualTo(DEFAULT_PAN_CARD_IMAGE);
        assertThat(testClientDetails.getPanCardImageContentType()).isEqualTo(DEFAULT_PAN_CARD_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getCancelledChequeImage()).isEqualTo(DEFAULT_CANCELLED_CHEQUE_IMAGE);
        assertThat(testClientDetails.getCancelledChequeImageContentType()).isEqualTo(DEFAULT_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getUdYogAadharImage()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE);
        assertThat(testClientDetails.getUdYogAadharImageContentType()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testClientDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateClientDetailsWithPatch() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // Update the clientDetails using partial update
        ClientDetails partialUpdatedClientDetails = new ClientDetails();
        partialUpdatedClientDetails.setId(clientDetails.getId());

        partialUpdatedClientDetails
            .clientName(UPDATED_CLIENT_NAME)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .companyName(UPDATED_COMPANY_NAME)
            .companyContactNo(UPDATED_COMPANY_CONTACT_NO)
            .website(UPDATED_WEBSITE)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .clientType(UPDATED_CLIENT_TYPE)
            .isactivated(UPDATED_ISACTIVATED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .isApproved(UPDATED_IS_APPROVED)
            .nameOfBeneficiary(UPDATED_NAME_OF_BENEFICIARY)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .ifscCode(UPDATED_IFSC_CODE)
            .gstCertificateImage(UPDATED_GST_CERTIFICATE_IMAGE)
            .gstCertificateImageContentType(UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE)
            .panCardImage(UPDATED_PAN_CARD_IMAGE)
            .panCardImageContentType(UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE)
            .cancelledChequeImage(UPDATED_CANCELLED_CHEQUE_IMAGE)
            .cancelledChequeImageContentType(UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE)
            .udYogAadharImage(UPDATED_UD_YOG_AADHAR_IMAGE)
            .udYogAadharImageContentType(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restClientDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientDetails))
            )
            .andExpect(status().isOk());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClientDetails.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testClientDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientDetails.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testClientDetails.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testClientDetails.getCompanyContactNo()).isEqualTo(UPDATED_COMPANY_CONTACT_NO);
        assertThat(testClientDetails.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClientDetails.getGstinNumber()).isEqualTo(UPDATED_GSTIN_NUMBER);
        assertThat(testClientDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientDetails.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testClientDetails.getIsactivated()).isEqualTo(UPDATED_ISACTIVATED);
        assertThat(testClientDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testClientDetails.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testClientDetails.getNameOfBeneficiary()).isEqualTo(UPDATED_NAME_OF_BENEFICIARY);
        assertThat(testClientDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testClientDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testClientDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testClientDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testClientDetails.getGstCertificateImage()).isEqualTo(UPDATED_GST_CERTIFICATE_IMAGE);
        assertThat(testClientDetails.getGstCertificateImageContentType()).isEqualTo(UPDATED_GST_CERTIFICATE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getPanCardImage()).isEqualTo(UPDATED_PAN_CARD_IMAGE);
        assertThat(testClientDetails.getPanCardImageContentType()).isEqualTo(UPDATED_PAN_CARD_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getCancelledChequeImage()).isEqualTo(UPDATED_CANCELLED_CHEQUE_IMAGE);
        assertThat(testClientDetails.getCancelledChequeImageContentType()).isEqualTo(UPDATED_CANCELLED_CHEQUE_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getUdYogAadharImage()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE);
        assertThat(testClientDetails.getUdYogAadharImageContentType()).isEqualTo(UPDATED_UD_YOG_AADHAR_IMAGE_CONTENT_TYPE);
        assertThat(testClientDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testClientDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();
        clientDetails.setId(count.incrementAndGet());

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.toDto(clientDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        int databaseSizeBeforeDelete = clientDetailsRepository.findAll().size();

        // Delete the clientDetails
        restClientDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
