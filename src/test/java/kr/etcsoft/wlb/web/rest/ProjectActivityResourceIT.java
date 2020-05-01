package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.ProjectActivity;
import kr.etcsoft.wlb.repository.ProjectActivityRepository;
import kr.etcsoft.wlb.service.ProjectActivityService;
import kr.etcsoft.wlb.service.dto.ProjectActivityDTO;
import kr.etcsoft.wlb.service.mapper.ProjectActivityMapper;

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
 * Integration tests for the {@link ProjectActivityResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProjectActivityResourceIT {

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    @Autowired
    private ProjectActivityRepository projectActivityRepository;

    @Autowired
    private ProjectActivityMapper projectActivityMapper;

    @Autowired
    private ProjectActivityService projectActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectActivityMockMvc;

    private ProjectActivity projectActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectActivity createEntity(EntityManager em) {
        ProjectActivity projectActivity = new ProjectActivity()
            .activity(DEFAULT_ACTIVITY);
        return projectActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectActivity createUpdatedEntity(EntityManager em) {
        ProjectActivity projectActivity = new ProjectActivity()
            .activity(UPDATED_ACTIVITY);
        return projectActivity;
    }

    @BeforeEach
    public void initTest() {
        projectActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectActivity() throws Exception {
        int databaseSizeBeforeCreate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity
        ProjectActivityDTO projectActivityDTO = projectActivityMapper.toDto(projectActivity);
        restProjectActivityMockMvc.perform(post("/api/project-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectActivity testProjectActivity = projectActivityList.get(projectActivityList.size() - 1);
        assertThat(testProjectActivity.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
    }

    @Test
    @Transactional
    public void createProjectActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity with an existing ID
        projectActivity.setId(1L);
        ProjectActivityDTO projectActivityDTO = projectActivityMapper.toDto(projectActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectActivityMockMvc.perform(post("/api/project-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectActivityRepository.findAll().size();
        // set the field null
        projectActivity.setActivity(null);

        // Create the ProjectActivity, which fails.
        ProjectActivityDTO projectActivityDTO = projectActivityMapper.toDto(projectActivity);

        restProjectActivityMockMvc.perform(post("/api/project-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectActivityDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectActivities() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        // Get all the projectActivityList
        restProjectActivityMockMvc.perform(get("/api/project-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)));
    }
    
    @Test
    @Transactional
    public void getProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        // Get the projectActivity
        restProjectActivityMockMvc.perform(get("/api/project-activities/{id}", projectActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectActivity.getId().intValue()))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY));
    }

    @Test
    @Transactional
    public void getNonExistingProjectActivity() throws Exception {
        // Get the projectActivity
        restProjectActivityMockMvc.perform(get("/api/project-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        int databaseSizeBeforeUpdate = projectActivityRepository.findAll().size();

        // Update the projectActivity
        ProjectActivity updatedProjectActivity = projectActivityRepository.findById(projectActivity.getId()).get();
        // Disconnect from session so that the updates on updatedProjectActivity are not directly saved in db
        em.detach(updatedProjectActivity);
        updatedProjectActivity
            .activity(UPDATED_ACTIVITY);
        ProjectActivityDTO projectActivityDTO = projectActivityMapper.toDto(updatedProjectActivity);

        restProjectActivityMockMvc.perform(put("/api/project-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectActivityDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeUpdate);
        ProjectActivity testProjectActivity = projectActivityList.get(projectActivityList.size() - 1);
        assertThat(testProjectActivity.getActivity()).isEqualTo(UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectActivity() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity
        ProjectActivityDTO projectActivityDTO = projectActivityMapper.toDto(projectActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectActivityMockMvc.perform(put("/api/project-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        int databaseSizeBeforeDelete = projectActivityRepository.findAll().size();

        // Delete the projectActivity
        restProjectActivityMockMvc.perform(delete("/api/project-activities/{id}", projectActivity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
