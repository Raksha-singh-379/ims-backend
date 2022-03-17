package com.techvg.inventory.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.inventory.management.IntegrationTest;
import com.techvg.inventory.management.domain.SecurityUser;
import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.repository.WareHouseRepository;
import com.techvg.inventory.management.service.criteria.WareHouseCriteria;
import com.techvg.inventory.management.service.dto.WareHouseDTO;
import com.techvg.inventory.management.service.mapper.WareHouseMapper;
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
 * Integration tests for the {@link WareHouseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WareHouseResourceIT {

    private static final String DEFAULT_WH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;
    private static final Integer SMALLER_PINCODE = 1 - 1;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_G_ST_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_G_ST_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Long DEFAULT_WARE_HOUSE_ID = 1L;
    private static final Long UPDATED_WARE_HOUSE_ID = 2L;
    private static final Long SMALLER_WARE_HOUSE_ID = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ware-houses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWareHouseMockMvc;

    private WareHouse wareHouse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WareHouse createEntity(EntityManager em) {
        WareHouse wareHouse = new WareHouse()
            .whName(DEFAULT_WH_NAME)
            .address(DEFAULT_ADDRESS)
            .pincode(DEFAULT_PINCODE)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .gSTDetails(DEFAULT_G_ST_DETAILS)
            .managerName(DEFAULT_MANAGER_NAME)
            .managerEmail(DEFAULT_MANAGER_EMAIL)
            .managerContact(DEFAULT_MANAGER_CONTACT)
            .contact(DEFAULT_CONTACT)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE)
            .wareHouseId(DEFAULT_WARE_HOUSE_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return wareHouse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WareHouse createUpdatedEntity(EntityManager em) {
        WareHouse wareHouse = new WareHouse()
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .wareHouseId(UPDATED_WARE_HOUSE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return wareHouse;
    }

    @BeforeEach
    public void initTest() {
        wareHouse = createEntity(em);
    }

    @Test
    @Transactional
    void createWareHouse() throws Exception {
        int databaseSizeBeforeCreate = wareHouseRepository.findAll().size();
        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);
        restWareHouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wareHouseDTO)))
            .andExpect(status().isCreated());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeCreate + 1);
        WareHouse testWareHouse = wareHouseList.get(wareHouseList.size() - 1);
        assertThat(testWareHouse.getWhName()).isEqualTo(DEFAULT_WH_NAME);
        assertThat(testWareHouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWareHouse.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testWareHouse.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWareHouse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWareHouse.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWareHouse.getgSTDetails()).isEqualTo(DEFAULT_G_ST_DETAILS);
        assertThat(testWareHouse.getManagerName()).isEqualTo(DEFAULT_MANAGER_NAME);
        assertThat(testWareHouse.getManagerEmail()).isEqualTo(DEFAULT_MANAGER_EMAIL);
        assertThat(testWareHouse.getManagerContact()).isEqualTo(DEFAULT_MANAGER_CONTACT);
        assertThat(testWareHouse.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testWareHouse.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWareHouse.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWareHouse.getWareHouseId()).isEqualTo(DEFAULT_WARE_HOUSE_ID);
        assertThat(testWareHouse.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWareHouse.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createWareHouseWithExistingId() throws Exception {
        // Create the WareHouse with an existing ID
        wareHouse.setId(1L);
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        int databaseSizeBeforeCreate = wareHouseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWareHouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wareHouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWareHouses() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wareHouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].whName").value(hasItem(DEFAULT_WH_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].gSTDetails").value(hasItem(DEFAULT_G_ST_DETAILS)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerEmail").value(hasItem(DEFAULT_MANAGER_EMAIL)))
            .andExpect(jsonPath("$.[*].managerContact").value(hasItem(DEFAULT_MANAGER_CONTACT)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].wareHouseId").value(hasItem(DEFAULT_WARE_HOUSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getWareHouse() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get the wareHouse
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL_ID, wareHouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wareHouse.getId().intValue()))
            .andExpect(jsonPath("$.whName").value(DEFAULT_WH_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.gSTDetails").value(DEFAULT_G_ST_DETAILS))
            .andExpect(jsonPath("$.managerName").value(DEFAULT_MANAGER_NAME))
            .andExpect(jsonPath("$.managerEmail").value(DEFAULT_MANAGER_EMAIL))
            .andExpect(jsonPath("$.managerContact").value(DEFAULT_MANAGER_CONTACT))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.wareHouseId").value(DEFAULT_WARE_HOUSE_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getWareHousesByIdFiltering() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        Long id = wareHouse.getId();

        defaultWareHouseShouldBeFound("id.equals=" + id);
        defaultWareHouseShouldNotBeFound("id.notEquals=" + id);

        defaultWareHouseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWareHouseShouldNotBeFound("id.greaterThan=" + id);

        defaultWareHouseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWareHouseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName equals to DEFAULT_WH_NAME
        defaultWareHouseShouldBeFound("whName.equals=" + DEFAULT_WH_NAME);

        // Get all the wareHouseList where whName equals to UPDATED_WH_NAME
        defaultWareHouseShouldNotBeFound("whName.equals=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName not equals to DEFAULT_WH_NAME
        defaultWareHouseShouldNotBeFound("whName.notEquals=" + DEFAULT_WH_NAME);

        // Get all the wareHouseList where whName not equals to UPDATED_WH_NAME
        defaultWareHouseShouldBeFound("whName.notEquals=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName in DEFAULT_WH_NAME or UPDATED_WH_NAME
        defaultWareHouseShouldBeFound("whName.in=" + DEFAULT_WH_NAME + "," + UPDATED_WH_NAME);

        // Get all the wareHouseList where whName equals to UPDATED_WH_NAME
        defaultWareHouseShouldNotBeFound("whName.in=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName is not null
        defaultWareHouseShouldBeFound("whName.specified=true");

        // Get all the wareHouseList where whName is null
        defaultWareHouseShouldNotBeFound("whName.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName contains DEFAULT_WH_NAME
        defaultWareHouseShouldBeFound("whName.contains=" + DEFAULT_WH_NAME);

        // Get all the wareHouseList where whName contains UPDATED_WH_NAME
        defaultWareHouseShouldNotBeFound("whName.contains=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByWhNameNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where whName does not contain DEFAULT_WH_NAME
        defaultWareHouseShouldNotBeFound("whName.doesNotContain=" + DEFAULT_WH_NAME);

        // Get all the wareHouseList where whName does not contain UPDATED_WH_NAME
        defaultWareHouseShouldBeFound("whName.doesNotContain=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address equals to DEFAULT_ADDRESS
        defaultWareHouseShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the wareHouseList where address equals to UPDATED_ADDRESS
        defaultWareHouseShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address not equals to DEFAULT_ADDRESS
        defaultWareHouseShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the wareHouseList where address not equals to UPDATED_ADDRESS
        defaultWareHouseShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultWareHouseShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the wareHouseList where address equals to UPDATED_ADDRESS
        defaultWareHouseShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address is not null
        defaultWareHouseShouldBeFound("address.specified=true");

        // Get all the wareHouseList where address is null
        defaultWareHouseShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address contains DEFAULT_ADDRESS
        defaultWareHouseShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the wareHouseList where address contains UPDATED_ADDRESS
        defaultWareHouseShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWareHousesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where address does not contain DEFAULT_ADDRESS
        defaultWareHouseShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the wareHouseList where address does not contain UPDATED_ADDRESS
        defaultWareHouseShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode equals to DEFAULT_PINCODE
        defaultWareHouseShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode equals to UPDATED_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode not equals to DEFAULT_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode not equals to UPDATED_PINCODE
        defaultWareHouseShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultWareHouseShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the wareHouseList where pincode equals to UPDATED_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode is not null
        defaultWareHouseShouldBeFound("pincode.specified=true");

        // Get all the wareHouseList where pincode is null
        defaultWareHouseShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode is greater than or equal to DEFAULT_PINCODE
        defaultWareHouseShouldBeFound("pincode.greaterThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode is greater than or equal to UPDATED_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.greaterThanOrEqual=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode is less than or equal to DEFAULT_PINCODE
        defaultWareHouseShouldBeFound("pincode.lessThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode is less than or equal to SMALLER_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.lessThanOrEqual=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsLessThanSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode is less than DEFAULT_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.lessThan=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode is less than UPDATED_PINCODE
        defaultWareHouseShouldBeFound("pincode.lessThan=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByPincodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where pincode is greater than DEFAULT_PINCODE
        defaultWareHouseShouldNotBeFound("pincode.greaterThan=" + DEFAULT_PINCODE);

        // Get all the wareHouseList where pincode is greater than SMALLER_PINCODE
        defaultWareHouseShouldBeFound("pincode.greaterThan=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllWareHousesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city equals to DEFAULT_CITY
        defaultWareHouseShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the wareHouseList where city equals to UPDATED_CITY
        defaultWareHouseShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city not equals to DEFAULT_CITY
        defaultWareHouseShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the wareHouseList where city not equals to UPDATED_CITY
        defaultWareHouseShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city in DEFAULT_CITY or UPDATED_CITY
        defaultWareHouseShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the wareHouseList where city equals to UPDATED_CITY
        defaultWareHouseShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city is not null
        defaultWareHouseShouldBeFound("city.specified=true");

        // Get all the wareHouseList where city is null
        defaultWareHouseShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByCityContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city contains DEFAULT_CITY
        defaultWareHouseShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the wareHouseList where city contains UPDATED_CITY
        defaultWareHouseShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where city does not contain DEFAULT_CITY
        defaultWareHouseShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the wareHouseList where city does not contain UPDATED_CITY
        defaultWareHouseShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWareHousesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state equals to DEFAULT_STATE
        defaultWareHouseShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the wareHouseList where state equals to UPDATED_STATE
        defaultWareHouseShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWareHousesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state not equals to DEFAULT_STATE
        defaultWareHouseShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the wareHouseList where state not equals to UPDATED_STATE
        defaultWareHouseShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWareHousesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state in DEFAULT_STATE or UPDATED_STATE
        defaultWareHouseShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the wareHouseList where state equals to UPDATED_STATE
        defaultWareHouseShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWareHousesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state is not null
        defaultWareHouseShouldBeFound("state.specified=true");

        // Get all the wareHouseList where state is null
        defaultWareHouseShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByStateContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state contains DEFAULT_STATE
        defaultWareHouseShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the wareHouseList where state contains UPDATED_STATE
        defaultWareHouseShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWareHousesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where state does not contain DEFAULT_STATE
        defaultWareHouseShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the wareHouseList where state does not contain UPDATED_STATE
        defaultWareHouseShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country equals to DEFAULT_COUNTRY
        defaultWareHouseShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the wareHouseList where country equals to UPDATED_COUNTRY
        defaultWareHouseShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country not equals to DEFAULT_COUNTRY
        defaultWareHouseShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the wareHouseList where country not equals to UPDATED_COUNTRY
        defaultWareHouseShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultWareHouseShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the wareHouseList where country equals to UPDATED_COUNTRY
        defaultWareHouseShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country is not null
        defaultWareHouseShouldBeFound("country.specified=true");

        // Get all the wareHouseList where country is null
        defaultWareHouseShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country contains DEFAULT_COUNTRY
        defaultWareHouseShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the wareHouseList where country contains UPDATED_COUNTRY
        defaultWareHouseShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWareHousesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where country does not contain DEFAULT_COUNTRY
        defaultWareHouseShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the wareHouseList where country does not contain UPDATED_COUNTRY
        defaultWareHouseShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails equals to DEFAULT_G_ST_DETAILS
        defaultWareHouseShouldBeFound("gSTDetails.equals=" + DEFAULT_G_ST_DETAILS);

        // Get all the wareHouseList where gSTDetails equals to UPDATED_G_ST_DETAILS
        defaultWareHouseShouldNotBeFound("gSTDetails.equals=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails not equals to DEFAULT_G_ST_DETAILS
        defaultWareHouseShouldNotBeFound("gSTDetails.notEquals=" + DEFAULT_G_ST_DETAILS);

        // Get all the wareHouseList where gSTDetails not equals to UPDATED_G_ST_DETAILS
        defaultWareHouseShouldBeFound("gSTDetails.notEquals=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails in DEFAULT_G_ST_DETAILS or UPDATED_G_ST_DETAILS
        defaultWareHouseShouldBeFound("gSTDetails.in=" + DEFAULT_G_ST_DETAILS + "," + UPDATED_G_ST_DETAILS);

        // Get all the wareHouseList where gSTDetails equals to UPDATED_G_ST_DETAILS
        defaultWareHouseShouldNotBeFound("gSTDetails.in=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails is not null
        defaultWareHouseShouldBeFound("gSTDetails.specified=true");

        // Get all the wareHouseList where gSTDetails is null
        defaultWareHouseShouldNotBeFound("gSTDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails contains DEFAULT_G_ST_DETAILS
        defaultWareHouseShouldBeFound("gSTDetails.contains=" + DEFAULT_G_ST_DETAILS);

        // Get all the wareHouseList where gSTDetails contains UPDATED_G_ST_DETAILS
        defaultWareHouseShouldNotBeFound("gSTDetails.contains=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWareHousesBygSTDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where gSTDetails does not contain DEFAULT_G_ST_DETAILS
        defaultWareHouseShouldNotBeFound("gSTDetails.doesNotContain=" + DEFAULT_G_ST_DETAILS);

        // Get all the wareHouseList where gSTDetails does not contain UPDATED_G_ST_DETAILS
        defaultWareHouseShouldBeFound("gSTDetails.doesNotContain=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName equals to DEFAULT_MANAGER_NAME
        defaultWareHouseShouldBeFound("managerName.equals=" + DEFAULT_MANAGER_NAME);

        // Get all the wareHouseList where managerName equals to UPDATED_MANAGER_NAME
        defaultWareHouseShouldNotBeFound("managerName.equals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName not equals to DEFAULT_MANAGER_NAME
        defaultWareHouseShouldNotBeFound("managerName.notEquals=" + DEFAULT_MANAGER_NAME);

        // Get all the wareHouseList where managerName not equals to UPDATED_MANAGER_NAME
        defaultWareHouseShouldBeFound("managerName.notEquals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName in DEFAULT_MANAGER_NAME or UPDATED_MANAGER_NAME
        defaultWareHouseShouldBeFound("managerName.in=" + DEFAULT_MANAGER_NAME + "," + UPDATED_MANAGER_NAME);

        // Get all the wareHouseList where managerName equals to UPDATED_MANAGER_NAME
        defaultWareHouseShouldNotBeFound("managerName.in=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName is not null
        defaultWareHouseShouldBeFound("managerName.specified=true");

        // Get all the wareHouseList where managerName is null
        defaultWareHouseShouldNotBeFound("managerName.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName contains DEFAULT_MANAGER_NAME
        defaultWareHouseShouldBeFound("managerName.contains=" + DEFAULT_MANAGER_NAME);

        // Get all the wareHouseList where managerName contains UPDATED_MANAGER_NAME
        defaultWareHouseShouldNotBeFound("managerName.contains=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerNameNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerName does not contain DEFAULT_MANAGER_NAME
        defaultWareHouseShouldNotBeFound("managerName.doesNotContain=" + DEFAULT_MANAGER_NAME);

        // Get all the wareHouseList where managerName does not contain UPDATED_MANAGER_NAME
        defaultWareHouseShouldBeFound("managerName.doesNotContain=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail equals to DEFAULT_MANAGER_EMAIL
        defaultWareHouseShouldBeFound("managerEmail.equals=" + DEFAULT_MANAGER_EMAIL);

        // Get all the wareHouseList where managerEmail equals to UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldNotBeFound("managerEmail.equals=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail not equals to DEFAULT_MANAGER_EMAIL
        defaultWareHouseShouldNotBeFound("managerEmail.notEquals=" + DEFAULT_MANAGER_EMAIL);

        // Get all the wareHouseList where managerEmail not equals to UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldBeFound("managerEmail.notEquals=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail in DEFAULT_MANAGER_EMAIL or UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldBeFound("managerEmail.in=" + DEFAULT_MANAGER_EMAIL + "," + UPDATED_MANAGER_EMAIL);

        // Get all the wareHouseList where managerEmail equals to UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldNotBeFound("managerEmail.in=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail is not null
        defaultWareHouseShouldBeFound("managerEmail.specified=true");

        // Get all the wareHouseList where managerEmail is null
        defaultWareHouseShouldNotBeFound("managerEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail contains DEFAULT_MANAGER_EMAIL
        defaultWareHouseShouldBeFound("managerEmail.contains=" + DEFAULT_MANAGER_EMAIL);

        // Get all the wareHouseList where managerEmail contains UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldNotBeFound("managerEmail.contains=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerEmailNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerEmail does not contain DEFAULT_MANAGER_EMAIL
        defaultWareHouseShouldNotBeFound("managerEmail.doesNotContain=" + DEFAULT_MANAGER_EMAIL);

        // Get all the wareHouseList where managerEmail does not contain UPDATED_MANAGER_EMAIL
        defaultWareHouseShouldBeFound("managerEmail.doesNotContain=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact equals to DEFAULT_MANAGER_CONTACT
        defaultWareHouseShouldBeFound("managerContact.equals=" + DEFAULT_MANAGER_CONTACT);

        // Get all the wareHouseList where managerContact equals to UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldNotBeFound("managerContact.equals=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact not equals to DEFAULT_MANAGER_CONTACT
        defaultWareHouseShouldNotBeFound("managerContact.notEquals=" + DEFAULT_MANAGER_CONTACT);

        // Get all the wareHouseList where managerContact not equals to UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldBeFound("managerContact.notEquals=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact in DEFAULT_MANAGER_CONTACT or UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldBeFound("managerContact.in=" + DEFAULT_MANAGER_CONTACT + "," + UPDATED_MANAGER_CONTACT);

        // Get all the wareHouseList where managerContact equals to UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldNotBeFound("managerContact.in=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact is not null
        defaultWareHouseShouldBeFound("managerContact.specified=true");

        // Get all the wareHouseList where managerContact is null
        defaultWareHouseShouldNotBeFound("managerContact.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact contains DEFAULT_MANAGER_CONTACT
        defaultWareHouseShouldBeFound("managerContact.contains=" + DEFAULT_MANAGER_CONTACT);

        // Get all the wareHouseList where managerContact contains UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldNotBeFound("managerContact.contains=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByManagerContactNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where managerContact does not contain DEFAULT_MANAGER_CONTACT
        defaultWareHouseShouldNotBeFound("managerContact.doesNotContain=" + DEFAULT_MANAGER_CONTACT);

        // Get all the wareHouseList where managerContact does not contain UPDATED_MANAGER_CONTACT
        defaultWareHouseShouldBeFound("managerContact.doesNotContain=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact equals to DEFAULT_CONTACT
        defaultWareHouseShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the wareHouseList where contact equals to UPDATED_CONTACT
        defaultWareHouseShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact not equals to DEFAULT_CONTACT
        defaultWareHouseShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the wareHouseList where contact not equals to UPDATED_CONTACT
        defaultWareHouseShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultWareHouseShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the wareHouseList where contact equals to UPDATED_CONTACT
        defaultWareHouseShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact is not null
        defaultWareHouseShouldBeFound("contact.specified=true");

        // Get all the wareHouseList where contact is null
        defaultWareHouseShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByContactContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact contains DEFAULT_CONTACT
        defaultWareHouseShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the wareHouseList where contact contains UPDATED_CONTACT
        defaultWareHouseShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByContactNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where contact does not contain DEFAULT_CONTACT
        defaultWareHouseShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the wareHouseList where contact does not contain UPDATED_CONTACT
        defaultWareHouseShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isDeleted equals to DEFAULT_IS_DELETED
        defaultWareHouseShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the wareHouseList where isDeleted equals to UPDATED_IS_DELETED
        defaultWareHouseShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultWareHouseShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the wareHouseList where isDeleted not equals to UPDATED_IS_DELETED
        defaultWareHouseShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultWareHouseShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the wareHouseList where isDeleted equals to UPDATED_IS_DELETED
        defaultWareHouseShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isDeleted is not null
        defaultWareHouseShouldBeFound("isDeleted.specified=true");

        // Get all the wareHouseList where isDeleted is null
        defaultWareHouseShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isActive equals to DEFAULT_IS_ACTIVE
        defaultWareHouseShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the wareHouseList where isActive equals to UPDATED_IS_ACTIVE
        defaultWareHouseShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultWareHouseShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the wareHouseList where isActive not equals to UPDATED_IS_ACTIVE
        defaultWareHouseShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultWareHouseShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the wareHouseList where isActive equals to UPDATED_IS_ACTIVE
        defaultWareHouseShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWareHousesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where isActive is not null
        defaultWareHouseShouldBeFound("isActive.specified=true");

        // Get all the wareHouseList where isActive is null
        defaultWareHouseShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId equals to DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.equals=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId equals to UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.equals=" + UPDATED_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId not equals to DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.notEquals=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId not equals to UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.notEquals=" + UPDATED_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId in DEFAULT_WARE_HOUSE_ID or UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.in=" + DEFAULT_WARE_HOUSE_ID + "," + UPDATED_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId equals to UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.in=" + UPDATED_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId is not null
        defaultWareHouseShouldBeFound("wareHouseId.specified=true");

        // Get all the wareHouseList where wareHouseId is null
        defaultWareHouseShouldNotBeFound("wareHouseId.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId is greater than or equal to DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.greaterThanOrEqual=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId is greater than or equal to UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.greaterThanOrEqual=" + UPDATED_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId is less than or equal to DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.lessThanOrEqual=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId is less than or equal to SMALLER_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.lessThanOrEqual=" + SMALLER_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsLessThanSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId is less than DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.lessThan=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId is less than UPDATED_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.lessThan=" + UPDATED_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByWareHouseIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where wareHouseId is greater than DEFAULT_WARE_HOUSE_ID
        defaultWareHouseShouldNotBeFound("wareHouseId.greaterThan=" + DEFAULT_WARE_HOUSE_ID);

        // Get all the wareHouseList where wareHouseId is greater than SMALLER_WARE_HOUSE_ID
        defaultWareHouseShouldBeFound("wareHouseId.greaterThan=" + SMALLER_WARE_HOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWareHouseShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the wareHouseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWareHouseShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultWareHouseShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the wareHouseList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultWareHouseShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWareHouseShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the wareHouseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWareHouseShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified is not null
        defaultWareHouseShouldBeFound("lastModified.specified=true");

        // Get all the wareHouseList where lastModified is null
        defaultWareHouseShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultWareHouseShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the wareHouseList where lastModified contains UPDATED_LAST_MODIFIED
        defaultWareHouseShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultWareHouseShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the wareHouseList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultWareHouseShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWareHouseShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the wareHouseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultWareHouseShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the wareHouseList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the wareHouseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy is not null
        defaultWareHouseShouldBeFound("lastModifiedBy.specified=true");

        // Get all the wareHouseList where lastModifiedBy is null
        defaultWareHouseShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWareHouseShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the wareHouseList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWareHousesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        // Get all the wareHouseList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWareHouseShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the wareHouseList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWareHouseShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWareHousesBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);
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
        wareHouse.addSecurityUser(securityUser);
        wareHouseRepository.saveAndFlush(wareHouse);
        Long securityUserId = securityUser.getId();

        // Get all the wareHouseList where securityUser equals to securityUserId
        defaultWareHouseShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the wareHouseList where securityUser equals to (securityUserId + 1)
        defaultWareHouseShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWareHouseShouldBeFound(String filter) throws Exception {
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wareHouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].whName").value(hasItem(DEFAULT_WH_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].gSTDetails").value(hasItem(DEFAULT_G_ST_DETAILS)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerEmail").value(hasItem(DEFAULT_MANAGER_EMAIL)))
            .andExpect(jsonPath("$.[*].managerContact").value(hasItem(DEFAULT_MANAGER_CONTACT)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].wareHouseId").value(hasItem(DEFAULT_WARE_HOUSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWareHouseShouldNotBeFound(String filter) throws Exception {
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWareHouseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWareHouse() throws Exception {
        // Get the wareHouse
        restWareHouseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWareHouse() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();

        // Update the wareHouse
        WareHouse updatedWareHouse = wareHouseRepository.findById(wareHouse.getId()).get();
        // Disconnect from session so that the updates on updatedWareHouse are not directly saved in db
        em.detach(updatedWareHouse);
        updatedWareHouse
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .wareHouseId(UPDATED_WARE_HOUSE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(updatedWareHouse);

        restWareHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wareHouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isOk());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
        WareHouse testWareHouse = wareHouseList.get(wareHouseList.size() - 1);
        assertThat(testWareHouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWareHouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWareHouse.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testWareHouse.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWareHouse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWareHouse.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWareHouse.getgSTDetails()).isEqualTo(UPDATED_G_ST_DETAILS);
        assertThat(testWareHouse.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testWareHouse.getManagerEmail()).isEqualTo(UPDATED_MANAGER_EMAIL);
        assertThat(testWareHouse.getManagerContact()).isEqualTo(UPDATED_MANAGER_CONTACT);
        assertThat(testWareHouse.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testWareHouse.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWareHouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWareHouse.getWareHouseId()).isEqualTo(UPDATED_WARE_HOUSE_ID);
        assertThat(testWareHouse.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWareHouse.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wareHouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wareHouseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWareHouseWithPatch() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();

        // Update the wareHouse using partial update
        WareHouse partialUpdatedWareHouse = new WareHouse();
        partialUpdatedWareHouse.setId(wareHouse.getId());

        partialUpdatedWareHouse
            .whName(UPDATED_WH_NAME)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .isActive(UPDATED_IS_ACTIVE)
            .wareHouseId(UPDATED_WARE_HOUSE_ID)
            .lastModified(UPDATED_LAST_MODIFIED);

        restWareHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWareHouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWareHouse))
            )
            .andExpect(status().isOk());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
        WareHouse testWareHouse = wareHouseList.get(wareHouseList.size() - 1);
        assertThat(testWareHouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWareHouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWareHouse.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testWareHouse.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWareHouse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWareHouse.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWareHouse.getgSTDetails()).isEqualTo(UPDATED_G_ST_DETAILS);
        assertThat(testWareHouse.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testWareHouse.getManagerEmail()).isEqualTo(DEFAULT_MANAGER_EMAIL);
        assertThat(testWareHouse.getManagerContact()).isEqualTo(UPDATED_MANAGER_CONTACT);
        assertThat(testWareHouse.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testWareHouse.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWareHouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWareHouse.getWareHouseId()).isEqualTo(UPDATED_WARE_HOUSE_ID);
        assertThat(testWareHouse.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWareHouse.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWareHouseWithPatch() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();

        // Update the wareHouse using partial update
        WareHouse partialUpdatedWareHouse = new WareHouse();
        partialUpdatedWareHouse.setId(wareHouse.getId());

        partialUpdatedWareHouse
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .wareHouseId(UPDATED_WARE_HOUSE_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restWareHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWareHouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWareHouse))
            )
            .andExpect(status().isOk());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
        WareHouse testWareHouse = wareHouseList.get(wareHouseList.size() - 1);
        assertThat(testWareHouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWareHouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWareHouse.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testWareHouse.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWareHouse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWareHouse.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWareHouse.getgSTDetails()).isEqualTo(UPDATED_G_ST_DETAILS);
        assertThat(testWareHouse.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testWareHouse.getManagerEmail()).isEqualTo(UPDATED_MANAGER_EMAIL);
        assertThat(testWareHouse.getManagerContact()).isEqualTo(UPDATED_MANAGER_CONTACT);
        assertThat(testWareHouse.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testWareHouse.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWareHouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWareHouse.getWareHouseId()).isEqualTo(UPDATED_WARE_HOUSE_ID);
        assertThat(testWareHouse.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWareHouse.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wareHouseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWareHouse() throws Exception {
        int databaseSizeBeforeUpdate = wareHouseRepository.findAll().size();
        wareHouse.setId(count.incrementAndGet());

        // Create the WareHouse
        WareHouseDTO wareHouseDTO = wareHouseMapper.toDto(wareHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWareHouseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wareHouseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WareHouse in the database
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWareHouse() throws Exception {
        // Initialize the database
        wareHouseRepository.saveAndFlush(wareHouse);

        int databaseSizeBeforeDelete = wareHouseRepository.findAll().size();

        // Delete the wareHouse
        restWareHouseMockMvc
            .perform(delete(ENTITY_API_URL_ID, wareHouse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WareHouse> wareHouseList = wareHouseRepository.findAll();
        assertThat(wareHouseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
