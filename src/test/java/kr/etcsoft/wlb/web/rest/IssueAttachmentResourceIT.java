package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.IssueAttachment;
import kr.etcsoft.wlb.repository.IssueAttachmentRepository;
import kr.etcsoft.wlb.service.IssueAttachmentService;
import kr.etcsoft.wlb.service.dto.IssueAttachmentDTO;
import kr.etcsoft.wlb.service.mapper.IssueAttachmentMapper;

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
 * Integration tests for the {@link IssueAttachmentResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueAttachmentResourceIT {

    @Autowired
    private IssueAttachmentRepository issueAttachmentRepository;

    @Autowired
    private IssueAttachmentMapper issueAttachmentMapper;

    @Autowired
    private IssueAttachmentService issueAttachmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueAttachmentMockMvc;

    private IssueAttachment issueAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAttachment createEntity(EntityManager em) {
        IssueAttachment issueAttachment = new IssueAttachment();
        return issueAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueAttachment createUpdatedEntity(EntityManager em) {
        IssueAttachment issueAttachment = new IssueAttachment();
        return issueAttachment;
    }

    @BeforeEach
    public void initTest() {
        issueAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssueAttachment() throws Exception {
        int databaseSizeBeforeCreate = issueAttachmentRepository.findAll().size();

        // Create the IssueAttachment
        IssueAttachmentDTO issueAttachmentDTO = issueAttachmentMapper.toDto(issueAttachment);
        restIssueAttachmentMockMvc.perform(post("/api/issue-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the IssueAttachment in the database
        List<IssueAttachment> issueAttachmentList = issueAttachmentRepository.findAll();
        assertThat(issueAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        IssueAttachment testIssueAttachment = issueAttachmentList.get(issueAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    public void createIssueAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueAttachmentRepository.findAll().size();

        // Create the IssueAttachment with an existing ID
        issueAttachment.setId(1L);
        IssueAttachmentDTO issueAttachmentDTO = issueAttachmentMapper.toDto(issueAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueAttachmentMockMvc.perform(post("/api/issue-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueAttachment in the database
        List<IssueAttachment> issueAttachmentList = issueAttachmentRepository.findAll();
        assertThat(issueAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIssueAttachments() throws Exception {
        // Initialize the database
        issueAttachmentRepository.saveAndFlush(issueAttachment);

        // Get all the issueAttachmentList
        restIssueAttachmentMockMvc.perform(get("/api/issue-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueAttachment.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getIssueAttachment() throws Exception {
        // Initialize the database
        issueAttachmentRepository.saveAndFlush(issueAttachment);

        // Get the issueAttachment
        restIssueAttachmentMockMvc.perform(get("/api/issue-attachments/{id}", issueAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueAttachment.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIssueAttachment() throws Exception {
        // Get the issueAttachment
        restIssueAttachmentMockMvc.perform(get("/api/issue-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssueAttachment() throws Exception {
        // Initialize the database
        issueAttachmentRepository.saveAndFlush(issueAttachment);

        int databaseSizeBeforeUpdate = issueAttachmentRepository.findAll().size();

        // Update the issueAttachment
        IssueAttachment updatedIssueAttachment = issueAttachmentRepository.findById(issueAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedIssueAttachment are not directly saved in db
        em.detach(updatedIssueAttachment);
        IssueAttachmentDTO issueAttachmentDTO = issueAttachmentMapper.toDto(updatedIssueAttachment);

        restIssueAttachmentMockMvc.perform(put("/api/issue-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the IssueAttachment in the database
        List<IssueAttachment> issueAttachmentList = issueAttachmentRepository.findAll();
        assertThat(issueAttachmentList).hasSize(databaseSizeBeforeUpdate);
        IssueAttachment testIssueAttachment = issueAttachmentList.get(issueAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIssueAttachment() throws Exception {
        int databaseSizeBeforeUpdate = issueAttachmentRepository.findAll().size();

        // Create the IssueAttachment
        IssueAttachmentDTO issueAttachmentDTO = issueAttachmentMapper.toDto(issueAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueAttachmentMockMvc.perform(put("/api/issue-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IssueAttachment in the database
        List<IssueAttachment> issueAttachmentList = issueAttachmentRepository.findAll();
        assertThat(issueAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssueAttachment() throws Exception {
        // Initialize the database
        issueAttachmentRepository.saveAndFlush(issueAttachment);

        int databaseSizeBeforeDelete = issueAttachmentRepository.findAll().size();

        // Delete the issueAttachment
        restIssueAttachmentMockMvc.perform(delete("/api/issue-attachments/{id}", issueAttachment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueAttachment> issueAttachmentList = issueAttachmentRepository.findAll();
        assertThat(issueAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
