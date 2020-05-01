package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.LabelGroup;
import kr.etcsoft.wlb.repository.LabelGroupRepository;
import kr.etcsoft.wlb.service.LabelGroupService;
import kr.etcsoft.wlb.service.dto.LabelGroupDTO;
import kr.etcsoft.wlb.service.mapper.LabelGroupMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LabelGroupResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LabelGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LabelGroupRepository labelGroupRepository;

    @Autowired
    private LabelGroupMapper labelGroupMapper;

    @Autowired
    private LabelGroupService labelGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLabelGroupMockMvc;

    private LabelGroup labelGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LabelGroup createEntity(EntityManager em) {
        LabelGroup labelGroup = new LabelGroup()
            .name(DEFAULT_NAME);
        return labelGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LabelGroup createUpdatedEntity(EntityManager em) {
        LabelGroup labelGroup = new LabelGroup()
            .name(UPDATED_NAME);
        return labelGroup;
    }

    @BeforeEach
    public void initTest() {
        labelGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createLabelGroup() throws Exception {
        int databaseSizeBeforeCreate = labelGroupRepository.findAll().size();

        // Create the LabelGroup
        LabelGroupDTO labelGroupDTO = labelGroupMapper.toDto(labelGroup);
        restLabelGroupMockMvc.perform(post("/api/label-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(labelGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the LabelGroup in the database
        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeCreate + 1);
        LabelGroup testLabelGroup = labelGroupList.get(labelGroupList.size() - 1);
        assertThat(testLabelGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLabelGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = labelGroupRepository.findAll().size();

        // Create the LabelGroup with an existing ID
        labelGroup.setId(1L);
        LabelGroupDTO labelGroupDTO = labelGroupMapper.toDto(labelGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLabelGroupMockMvc.perform(post("/api/label-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(labelGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LabelGroup in the database
        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = labelGroupRepository.findAll().size();
        // set the field null
        labelGroup.setName(null);

        // Create the LabelGroup, which fails.
        LabelGroupDTO labelGroupDTO = labelGroupMapper.toDto(labelGroup);

        restLabelGroupMockMvc.perform(post("/api/label-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(labelGroupDTO)))
            .andExpect(status().isBadRequest());

        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLabelGroups() throws Exception {
        // Initialize the database
        labelGroupRepository.saveAndFlush(labelGroup);

        // Get all the labelGroupList
        restLabelGroupMockMvc.perform(get("/api/label-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(labelGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getLabelGroup() throws Exception {
        // Initialize the database
        labelGroupRepository.saveAndFlush(labelGroup);

        // Get the labelGroup
        restLabelGroupMockMvc.perform(get("/api/label-groups/{id}", labelGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(labelGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLabelGroup() throws Exception {
        // Get the labelGroup
        restLabelGroupMockMvc.perform(get("/api/label-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLabelGroup() throws Exception {
        // Initialize the database
        labelGroupRepository.saveAndFlush(labelGroup);

        int databaseSizeBeforeUpdate = labelGroupRepository.findAll().size();

        // Update the labelGroup
        LabelGroup updatedLabelGroup = labelGroupRepository.findById(labelGroup.getId()).get();
        // Disconnect from session so that the updates on updatedLabelGroup are not directly saved in db
        em.detach(updatedLabelGroup);
        updatedLabelGroup
            .name(UPDATED_NAME);
        LabelGroupDTO labelGroupDTO = labelGroupMapper.toDto(updatedLabelGroup);

        restLabelGroupMockMvc.perform(put("/api/label-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(labelGroupDTO)))
            .andExpect(status().isOk());

        // Validate the LabelGroup in the database
        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeUpdate);
        LabelGroup testLabelGroup = labelGroupList.get(labelGroupList.size() - 1);
        assertThat(testLabelGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLabelGroup() throws Exception {
        int databaseSizeBeforeUpdate = labelGroupRepository.findAll().size();

        // Create the LabelGroup
        LabelGroupDTO labelGroupDTO = labelGroupMapper.toDto(labelGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLabelGroupMockMvc.perform(put("/api/label-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(labelGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LabelGroup in the database
        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLabelGroup() throws Exception {
        // Initialize the database
        labelGroupRepository.saveAndFlush(labelGroup);

        int databaseSizeBeforeDelete = labelGroupRepository.findAll().size();

        // Delete the labelGroup
        restLabelGroupMockMvc.perform(delete("/api/label-groups/{id}", labelGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LabelGroup> labelGroupList = labelGroupRepository.findAll();
        assertThat(labelGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
