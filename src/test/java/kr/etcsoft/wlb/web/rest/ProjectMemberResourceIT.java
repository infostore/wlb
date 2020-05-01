package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.ProjectMember;
import kr.etcsoft.wlb.repository.ProjectMemberRepository;
import kr.etcsoft.wlb.service.ProjectMemberService;
import kr.etcsoft.wlb.service.dto.ProjectMemberDTO;
import kr.etcsoft.wlb.service.mapper.ProjectMemberMapper;

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

import kr.etcsoft.wlb.domain.enumeration.Role;
/**
 * Integration tests for the {@link ProjectMemberResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProjectMemberResourceIT {

    private static final Role DEFAULT_ROLE = Role.MANAGER;
    private static final Role UPDATED_ROLE = Role.DEVELOPER;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectMemberMapper projectMemberMapper;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMemberMockMvc;

    private ProjectMember projectMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectMember createEntity(EntityManager em) {
        ProjectMember projectMember = new ProjectMember()
            .role(DEFAULT_ROLE);
        return projectMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectMember createUpdatedEntity(EntityManager em) {
        ProjectMember projectMember = new ProjectMember()
            .role(UPDATED_ROLE);
        return projectMember;
    }

    @BeforeEach
    public void initTest() {
        projectMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectMember() throws Exception {
        int databaseSizeBeforeCreate = projectMemberRepository.findAll().size();

        // Create the ProjectMember
        ProjectMemberDTO projectMemberDTO = projectMemberMapper.toDto(projectMember);
        restProjectMemberMockMvc.perform(post("/api/project-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectMemberDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectMember in the database
        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectMember testProjectMember = projectMemberList.get(projectMemberList.size() - 1);
        assertThat(testProjectMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createProjectMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectMemberRepository.findAll().size();

        // Create the ProjectMember with an existing ID
        projectMember.setId(1L);
        ProjectMemberDTO projectMemberDTO = projectMemberMapper.toDto(projectMember);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMemberMockMvc.perform(post("/api/project-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectMemberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectMember in the database
        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectMemberRepository.findAll().size();
        // set the field null
        projectMember.setRole(null);

        // Create the ProjectMember, which fails.
        ProjectMemberDTO projectMemberDTO = projectMemberMapper.toDto(projectMember);

        restProjectMemberMockMvc.perform(post("/api/project-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectMemberDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectMembers() throws Exception {
        // Initialize the database
        projectMemberRepository.saveAndFlush(projectMember);

        // Get all the projectMemberList
        restProjectMemberMockMvc.perform(get("/api/project-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectMember() throws Exception {
        // Initialize the database
        projectMemberRepository.saveAndFlush(projectMember);

        // Get the projectMember
        restProjectMemberMockMvc.perform(get("/api/project-members/{id}", projectMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectMember.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectMember() throws Exception {
        // Get the projectMember
        restProjectMemberMockMvc.perform(get("/api/project-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectMember() throws Exception {
        // Initialize the database
        projectMemberRepository.saveAndFlush(projectMember);

        int databaseSizeBeforeUpdate = projectMemberRepository.findAll().size();

        // Update the projectMember
        ProjectMember updatedProjectMember = projectMemberRepository.findById(projectMember.getId()).get();
        // Disconnect from session so that the updates on updatedProjectMember are not directly saved in db
        em.detach(updatedProjectMember);
        updatedProjectMember
            .role(UPDATED_ROLE);
        ProjectMemberDTO projectMemberDTO = projectMemberMapper.toDto(updatedProjectMember);

        restProjectMemberMockMvc.perform(put("/api/project-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectMemberDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectMember in the database
        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeUpdate);
        ProjectMember testProjectMember = projectMemberList.get(projectMemberList.size() - 1);
        assertThat(testProjectMember.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectMember() throws Exception {
        int databaseSizeBeforeUpdate = projectMemberRepository.findAll().size();

        // Create the ProjectMember
        ProjectMemberDTO projectMemberDTO = projectMemberMapper.toDto(projectMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMemberMockMvc.perform(put("/api/project-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectMemberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectMember in the database
        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectMember() throws Exception {
        // Initialize the database
        projectMemberRepository.saveAndFlush(projectMember);

        int databaseSizeBeforeDelete = projectMemberRepository.findAll().size();

        // Delete the projectMember
        restProjectMemberMockMvc.perform(delete("/api/project-members/{id}", projectMember.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectMember> projectMemberList = projectMemberRepository.findAll();
        assertThat(projectMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
