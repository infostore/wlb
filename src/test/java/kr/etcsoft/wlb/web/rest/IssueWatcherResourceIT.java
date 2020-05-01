package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.IssueWatcher;
import kr.etcsoft.wlb.repository.IssueWatcherRepository;
import kr.etcsoft.wlb.service.IssueWatcherService;
import kr.etcsoft.wlb.service.dto.IssueWatcherDTO;
import kr.etcsoft.wlb.service.mapper.IssueWatcherMapper;

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
 * Integration tests for the {@link IssueWatcherResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueWatcherResourceIT {

    @Autowired
    private IssueWatcherRepository issueWatcherRepository;

    @Autowired
    private IssueWatcherMapper issueWatcherMapper;

    @Autowired
    private IssueWatcherService issueWatcherService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueWatcherMockMvc;

    private IssueWatcher issueWatcher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueWatcher createEntity(EntityManager em) {
        IssueWatcher issueWatcher = new IssueWatcher();
        return issueWatcher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueWatcher createUpdatedEntity(EntityManager em) {
        IssueWatcher issueWatcher = new IssueWatcher();
        return issueWatcher;
    }

    @BeforeEach
    public void initTest() {
        issueWatcher = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueWatcher() throws Exception {
        int databaseSizeBeforeCreate = issueWatcherRepository.findAll().size();

        // Create the IssueWatcher
        IssueWatcherDTO issueWatcherDTO = issueWatcherMapper.toDto(issueWatcher);
        restIssueWatcherMockMvc.perform(post("/api/issue-watchers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueWatcherDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueWatcher in the database
        List<IssueWatcher> issueWatcherList = issueWatcherRepository.findAll();
        assertThat(issueWatcherList).hasSize(databaseSizeBeforeCreate + 1);
        IssueWatcher testIssueWatcher = issueWatcherList.get(issueWatcherList.size() - 1);
    }

    @Test
    @Transactional
    public void createIssueWatcherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueWatcherRepository.findAll().size();

        // Create the IssueWatcher with an existing ID
        issueWatcher.setId(1L);
        IssueWatcherDTO issueWatcherDTO = issueWatcherMapper.toDto(issueWatcher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueWatcherMockMvc.perform(post("/api/issue-watchers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueWatcherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueWatcher in the database
        List<IssueWatcher> issueWatcherList = issueWatcherRepository.findAll();
        assertThat(issueWatcherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIssueWatchers() throws Exception {
        // Initialize the database
        issueWatcherRepository.saveAndFlush(issueWatcher);

        // Get all the issueWatcherList
        restIssueWatcherMockMvc.perform(get("/api/issue-watchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueWatcher.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getIssueWatcher() throws Exception {
        // Initialize the database
        issueWatcherRepository.saveAndFlush(issueWatcher);

        // Get the issueWatcher
        restIssueWatcherMockMvc.perform(get("/api/issue-watchers/{id}", issueWatcher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueWatcher.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssueWatcher() throws Exception {
        // Get the issueWatcher
        restIssueWatcherMockMvc.perform(get("/api/issue-watchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueWatcher() throws Exception {
        // Initialize the database
        issueWatcherRepository.saveAndFlush(issueWatcher);

        int databaseSizeBeforeUpdate = issueWatcherRepository.findAll().size();

        // Update the issueWatcher
        IssueWatcher updatedIssueWatcher = issueWatcherRepository.findById(issueWatcher.getId()).get();
        // Disconnect from session so that the updates on updatedIssueWatcher are not directly saved in db
        em.detach(updatedIssueWatcher);
        IssueWatcherDTO issueWatcherDTO = issueWatcherMapper.toDto(updatedIssueWatcher);

        restIssueWatcherMockMvc.perform(put("/api/issue-watchers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueWatcherDTO)))
            .andExpect(status().isOk());

        // Validate the IssueWatcher in the database
        List<IssueWatcher> issueWatcherList = issueWatcherRepository.findAll();
        assertThat(issueWatcherList).hasSize(databaseSizeBeforeUpdate);
        IssueWatcher testIssueWatcher = issueWatcherList.get(issueWatcherList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueWatcher() throws Exception {
        int databaseSizeBeforeUpdate = issueWatcherRepository.findAll().size();

        // Create the IssueWatcher
        IssueWatcherDTO issueWatcherDTO = issueWatcherMapper.toDto(issueWatcher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueWatcherMockMvc.perform(put("/api/issue-watchers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueWatcherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueWatcher in the database
        List<IssueWatcher> issueWatcherList = issueWatcherRepository.findAll();
        assertThat(issueWatcherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueWatcher() throws Exception {
        // Initialize the database
        issueWatcherRepository.saveAndFlush(issueWatcher);

        int databaseSizeBeforeDelete = issueWatcherRepository.findAll().size();

        // Delete the issueWatcher
        restIssueWatcherMockMvc.perform(delete("/api/issue-watchers/{id}", issueWatcher.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueWatcher> issueWatcherList = issueWatcherRepository.findAll();
        assertThat(issueWatcherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
