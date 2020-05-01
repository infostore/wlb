package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.IssueLabel;
import kr.etcsoft.wlb.repository.IssueLabelRepository;
import kr.etcsoft.wlb.service.IssueLabelService;
import kr.etcsoft.wlb.service.dto.IssueLabelDTO;
import kr.etcsoft.wlb.service.mapper.IssueLabelMapper;

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
 * Integration tests for the {@link IssueLabelResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueLabelResourceIT {

    @Autowired
    private IssueLabelRepository issueLabelRepository;

    @Autowired
    private IssueLabelMapper issueLabelMapper;

    @Autowired
    private IssueLabelService issueLabelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueLabelMockMvc;

    private IssueLabel issueLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueLabel createEntity(EntityManager em) {
        IssueLabel issueLabel = new IssueLabel();
        return issueLabel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueLabel createUpdatedEntity(EntityManager em) {
        IssueLabel issueLabel = new IssueLabel();
        return issueLabel;
    }

    @BeforeEach
    public void initTest() {
        issueLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueLabel() throws Exception {
        int databaseSizeBeforeCreate = issueLabelRepository.findAll().size();

        // Create the IssueLabel
        IssueLabelDTO issueLabelDTO = issueLabelMapper.toDto(issueLabel);
        restIssueLabelMockMvc.perform(post("/api/issue-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueLabel in the database
        List<IssueLabel> issueLabelList = issueLabelRepository.findAll();
        assertThat(issueLabelList).hasSize(databaseSizeBeforeCreate + 1);
        IssueLabel testIssueLabel = issueLabelList.get(issueLabelList.size() - 1);
    }

    @Test
    @Transactional
    public void createIssueLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueLabelRepository.findAll().size();

        // Create the IssueLabel with an existing ID
        issueLabel.setId(1L);
        IssueLabelDTO issueLabelDTO = issueLabelMapper.toDto(issueLabel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueLabelMockMvc.perform(post("/api/issue-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueLabel in the database
        List<IssueLabel> issueLabelList = issueLabelRepository.findAll();
        assertThat(issueLabelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIssueLabels() throws Exception {
        // Initialize the database
        issueLabelRepository.saveAndFlush(issueLabel);

        // Get all the issueLabelList
        restIssueLabelMockMvc.perform(get("/api/issue-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueLabel.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getIssueLabel() throws Exception {
        // Initialize the database
        issueLabelRepository.saveAndFlush(issueLabel);

        // Get the issueLabel
        restIssueLabelMockMvc.perform(get("/api/issue-labels/{id}", issueLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueLabel.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssueLabel() throws Exception {
        // Get the issueLabel
        restIssueLabelMockMvc.perform(get("/api/issue-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueLabel() throws Exception {
        // Initialize the database
        issueLabelRepository.saveAndFlush(issueLabel);

        int databaseSizeBeforeUpdate = issueLabelRepository.findAll().size();

        // Update the issueLabel
        IssueLabel updatedIssueLabel = issueLabelRepository.findById(issueLabel.getId()).get();
        // Disconnect from session so that the updates on updatedIssueLabel are not directly saved in db
        em.detach(updatedIssueLabel);
        IssueLabelDTO issueLabelDTO = issueLabelMapper.toDto(updatedIssueLabel);

        restIssueLabelMockMvc.perform(put("/api/issue-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueLabelDTO)))
            .andExpect(status().isOk());

        // Validate the IssueLabel in the database
        List<IssueLabel> issueLabelList = issueLabelRepository.findAll();
        assertThat(issueLabelList).hasSize(databaseSizeBeforeUpdate);
        IssueLabel testIssueLabel = issueLabelList.get(issueLabelList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueLabel() throws Exception {
        int databaseSizeBeforeUpdate = issueLabelRepository.findAll().size();

        // Create the IssueLabel
        IssueLabelDTO issueLabelDTO = issueLabelMapper.toDto(issueLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueLabelMockMvc.perform(put("/api/issue-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueLabel in the database
        List<IssueLabel> issueLabelList = issueLabelRepository.findAll();
        assertThat(issueLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueLabel() throws Exception {
        // Initialize the database
        issueLabelRepository.saveAndFlush(issueLabel);

        int databaseSizeBeforeDelete = issueLabelRepository.findAll().size();

        // Delete the issueLabel
        restIssueLabelMockMvc.perform(delete("/api/issue-labels/{id}", issueLabel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueLabel> issueLabelList = issueLabelRepository.findAll();
        assertThat(issueLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
