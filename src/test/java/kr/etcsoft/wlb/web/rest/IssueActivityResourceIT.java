package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.IssueActivity;
import kr.etcsoft.wlb.repository.IssueActivityRepository;
import kr.etcsoft.wlb.service.IssueActivityService;
import kr.etcsoft.wlb.service.dto.IssueActivityDTO;
import kr.etcsoft.wlb.service.mapper.IssueActivityMapper;

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
 * Integration tests for the {@link IssueActivityResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueActivityResourceIT {

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    @Autowired
    private IssueActivityRepository issueActivityRepository;

    @Autowired
    private IssueActivityMapper issueActivityMapper;

    @Autowired
    private IssueActivityService issueActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueActivityMockMvc;

    private IssueActivity issueActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueActivity createEntity(EntityManager em) {
        IssueActivity issueActivity = new IssueActivity()
            .activity(DEFAULT_ACTIVITY);
        return issueActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueActivity createUpdatedEntity(EntityManager em) {
        IssueActivity issueActivity = new IssueActivity()
            .activity(UPDATED_ACTIVITY);
        return issueActivity;
    }

    @BeforeEach
    public void initTest() {
        issueActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueActivity() throws Exception {
        int databaseSizeBeforeCreate = issueActivityRepository.findAll().size();

        // Create the IssueActivity
        IssueActivityDTO issueActivityDTO = issueActivityMapper.toDto(issueActivity);
        restIssueActivityMockMvc.perform(post("/api/issue-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueActivity in the database
        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeCreate + 1);
        IssueActivity testIssueActivity = issueActivityList.get(issueActivityList.size() - 1);
        assertThat(testIssueActivity.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
    }

    @Test
    @Transactional
    public void createIssueActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueActivityRepository.findAll().size();

        // Create the IssueActivity with an existing ID
        issueActivity.setId(1L);
        IssueActivityDTO issueActivityDTO = issueActivityMapper.toDto(issueActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueActivityMockMvc.perform(post("/api/issue-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueActivity in the database
        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueActivityRepository.findAll().size();
        // set the field null
        issueActivity.setActivity(null);

        // Create the IssueActivity, which fails.
        IssueActivityDTO issueActivityDTO = issueActivityMapper.toDto(issueActivity);

        restIssueActivityMockMvc.perform(post("/api/issue-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueActivityDTO)))
            .andExpect(status().isBadRequest());

        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIssueActivities() throws Exception {
        // Initialize the database
        issueActivityRepository.saveAndFlush(issueActivity);

        // Get all the issueActivityList
        restIssueActivityMockMvc.perform(get("/api/issue-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)));
    }
    
    @Test
    @Transactional
    public void getIssueActivity() throws Exception {
        // Initialize the database
        issueActivityRepository.saveAndFlush(issueActivity);

        // Get the issueActivity
        restIssueActivityMockMvc.perform(get("/api/issue-activities/{id}", issueActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueActivity.getId().intValue()))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY));
    }

    @Test
    @Transactional
    public void getNonExistingIssueActivity() throws Exception {
        // Get the issueActivity
        restIssueActivityMockMvc.perform(get("/api/issue-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueActivity() throws Exception {
        // Initialize the database
        issueActivityRepository.saveAndFlush(issueActivity);

        int databaseSizeBeforeUpdate = issueActivityRepository.findAll().size();

        // Update the issueActivity
        IssueActivity updatedIssueActivity = issueActivityRepository.findById(issueActivity.getId()).get();
        // Disconnect from session so that the updates on updatedIssueActivity are not directly saved in db
        em.detach(updatedIssueActivity);
        updatedIssueActivity
            .activity(UPDATED_ACTIVITY);
        IssueActivityDTO issueActivityDTO = issueActivityMapper.toDto(updatedIssueActivity);

        restIssueActivityMockMvc.perform(put("/api/issue-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueActivityDTO)))
            .andExpect(status().isOk());

        // Validate the IssueActivity in the database
        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeUpdate);
        IssueActivity testIssueActivity = issueActivityList.get(issueActivityList.size() - 1);
        assertThat(testIssueActivity.getActivity()).isEqualTo(UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueActivity() throws Exception {
        int databaseSizeBeforeUpdate = issueActivityRepository.findAll().size();

        // Create the IssueActivity
        IssueActivityDTO issueActivityDTO = issueActivityMapper.toDto(issueActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueActivityMockMvc.perform(put("/api/issue-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueActivity in the database
        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueActivity() throws Exception {
        // Initialize the database
        issueActivityRepository.saveAndFlush(issueActivity);

        int databaseSizeBeforeDelete = issueActivityRepository.findAll().size();

        // Delete the issueActivity
        restIssueActivityMockMvc.perform(delete("/api/issue-activities/{id}", issueActivity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueActivity> issueActivityList = issueActivityRepository.findAll();
        assertThat(issueActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
