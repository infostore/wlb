package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.IssueAssignee;
import kr.etcsoft.wlb.repository.IssueAssigneeRepository;
import kr.etcsoft.wlb.service.IssueAssigneeService;
import kr.etcsoft.wlb.service.dto.IssueAssigneeDTO;
import kr.etcsoft.wlb.service.mapper.IssueAssigneeMapper;

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
 * Integration tests for the {@link IssueAssigneeResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueAssigneeResourceIT {

    @Autowired
    private IssueAssigneeRepository issueAssigneeRepository;

    @Autowired
    private IssueAssigneeMapper issueAssigneeMapper;

    @Autowired
    private IssueAssigneeService issueAssigneeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueAssigneeMockMvc;

    private IssueAssignee issueAssignee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAssignee createEntity(EntityManager em) {
        IssueAssignee issueAssignee = new IssueAssignee();
        return issueAssignee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAssignee createUpdatedEntity(EntityManager em) {
        IssueAssignee issueAssignee = new IssueAssignee();
        return issueAssignee;
    }

    @BeforeEach
    public void initTest() {
        issueAssignee = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueAssignee() throws Exception {
        int databaseSizeBeforeCreate = issueAssigneeRepository.findAll().size();

        // Create the IssueAssignee
        IssueAssigneeDTO issueAssigneeDTO = issueAssigneeMapper.toDto(issueAssignee);
        restIssueAssigneeMockMvc.perform(post("/api/issue-assignees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAssigneeDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueAssignee in the database
        List<IssueAssignee> issueAssigneeList = issueAssigneeRepository.findAll();
        assertThat(issueAssigneeList).hasSize(databaseSizeBeforeCreate + 1);
        IssueAssignee testIssueAssignee = issueAssigneeList.get(issueAssigneeList.size() - 1);
    }

    @Test
    @Transactional
    public void createIssueAssigneeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueAssigneeRepository.findAll().size();

        // Create the IssueAssignee with an existing ID
        issueAssignee.setId(1L);
        IssueAssigneeDTO issueAssigneeDTO = issueAssigneeMapper.toDto(issueAssignee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueAssigneeMockMvc.perform(post("/api/issue-assignees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAssigneeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueAssignee in the database
        List<IssueAssignee> issueAssigneeList = issueAssigneeRepository.findAll();
        assertThat(issueAssigneeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIssueAssignees() throws Exception {
        // Initialize the database
        issueAssigneeRepository.saveAndFlush(issueAssignee);

        // Get all the issueAssigneeList
        restIssueAssigneeMockMvc.perform(get("/api/issue-assignees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueAssignee.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getIssueAssignee() throws Exception {
        // Initialize the database
        issueAssigneeRepository.saveAndFlush(issueAssignee);

        // Get the issueAssignee
        restIssueAssigneeMockMvc.perform(get("/api/issue-assignees/{id}", issueAssignee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueAssignee.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssueAssignee() throws Exception {
        // Get the issueAssignee
        restIssueAssigneeMockMvc.perform(get("/api/issue-assignees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueAssignee() throws Exception {
        // Initialize the database
        issueAssigneeRepository.saveAndFlush(issueAssignee);

        int databaseSizeBeforeUpdate = issueAssigneeRepository.findAll().size();

        // Update the issueAssignee
        IssueAssignee updatedIssueAssignee = issueAssigneeRepository.findById(issueAssignee.getId()).get();
        // Disconnect from session so that the updates on updatedIssueAssignee are not directly saved in db
        em.detach(updatedIssueAssignee);
        IssueAssigneeDTO issueAssigneeDTO = issueAssigneeMapper.toDto(updatedIssueAssignee);

        restIssueAssigneeMockMvc.perform(put("/api/issue-assignees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAssigneeDTO)))
            .andExpect(status().isOk());

        // Validate the IssueAssignee in the database
        List<IssueAssignee> issueAssigneeList = issueAssigneeRepository.findAll();
        assertThat(issueAssigneeList).hasSize(databaseSizeBeforeUpdate);
        IssueAssignee testIssueAssignee = issueAssigneeList.get(issueAssigneeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueAssignee() throws Exception {
        int databaseSizeBeforeUpdate = issueAssigneeRepository.findAll().size();

        // Create the IssueAssignee
        IssueAssigneeDTO issueAssigneeDTO = issueAssigneeMapper.toDto(issueAssignee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueAssigneeMockMvc.perform(put("/api/issue-assignees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAssigneeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueAssignee in the database
        List<IssueAssignee> issueAssigneeList = issueAssigneeRepository.findAll();
        assertThat(issueAssigneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueAssignee() throws Exception {
        // Initialize the database
        issueAssigneeRepository.saveAndFlush(issueAssignee);

        int databaseSizeBeforeDelete = issueAssigneeRepository.findAll().size();

        // Delete the issueAssignee
        restIssueAssigneeMockMvc.perform(delete("/api/issue-assignees/{id}", issueAssignee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueAssignee> issueAssigneeList = issueAssigneeRepository.findAll();
        assertThat(issueAssigneeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
