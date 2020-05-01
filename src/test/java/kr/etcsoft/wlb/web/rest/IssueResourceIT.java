package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.WlbApp;
import kr.etcsoft.wlb.domain.Issue;
import kr.etcsoft.wlb.domain.IssueAssignee;
import kr.etcsoft.wlb.domain.IssueWatcher;
import kr.etcsoft.wlb.domain.Comment;
import kr.etcsoft.wlb.domain.IssueLabel;
import kr.etcsoft.wlb.domain.IssueAttachment;
import kr.etcsoft.wlb.domain.IssueActivity;
import kr.etcsoft.wlb.domain.Project;
import kr.etcsoft.wlb.domain.Milestone;
import kr.etcsoft.wlb.repository.IssueRepository;
import kr.etcsoft.wlb.service.IssueService;
import kr.etcsoft.wlb.service.dto.IssueDTO;
import kr.etcsoft.wlb.service.mapper.IssueMapper;
import kr.etcsoft.wlb.service.dto.IssueCriteria;
import kr.etcsoft.wlb.service.IssueQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kr.etcsoft.wlb.domain.enumeration.IssueType;
import kr.etcsoft.wlb.domain.enumeration.IssueStatus;
import kr.etcsoft.wlb.domain.enumeration.Priority;
import kr.etcsoft.wlb.domain.enumeration.Resolution;
/**
 * Integration tests for the {@link IssueResource} REST controller.
 */
@SpringBootTest(classes = WlbApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IssueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final IssueType DEFAULT_ISSUE_TYPE = IssueType.ISSUE;
    private static final IssueType UPDATED_ISSUE_TYPE = IssueType.TASK;

    private static final IssueStatus DEFAULT_ISSUE_STATUS = IssueStatus.NEW;
    private static final IssueStatus UPDATED_ISSUE_STATUS = IssueStatus.OPEN;

    private static final Priority DEFAULT_PRIORITY = Priority.LOWEST;
    private static final Priority UPDATED_PRIORITY = Priority.LOW;

    private static final Resolution DEFAULT_RESOLUTION = Resolution.DONE;
    private static final Resolution UPDATED_RESOLUTION = Resolution.DUPLICATE;

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueMapper issueMapper;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueQueryService issueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueMockMvc;

    private Issue issue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Issue createEntity(EntityManager em) {
        Issue issue = new Issue()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .issueType(DEFAULT_ISSUE_TYPE)
            .issueStatus(DEFAULT_ISSUE_STATUS)
            .priority(DEFAULT_PRIORITY)
            .resolution(DEFAULT_RESOLUTION)
            .dueDate(DEFAULT_DUE_DATE);
        return issue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Issue createUpdatedEntity(EntityManager em) {
        Issue issue = new Issue()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .issueType(UPDATED_ISSUE_TYPE)
            .issueStatus(UPDATED_ISSUE_STATUS)
            .priority(UPDATED_PRIORITY)
            .resolution(UPDATED_RESOLUTION)
            .dueDate(UPDATED_DUE_DATE);
        return issue;
    }

    @BeforeEach
    public void initTest() {
        issue = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssue() throws Exception {
        int databaseSizeBeforeCreate = issueRepository.findAll().size();

        // Create the Issue
        IssueDTO issueDTO = issueMapper.toDto(issue);
        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isCreated());

        // Validate the Issue in the database
        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeCreate + 1);
        Issue testIssue = issueList.get(issueList.size() - 1);
        assertThat(testIssue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIssue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIssue.getIssueType()).isEqualTo(DEFAULT_ISSUE_TYPE);
        assertThat(testIssue.getIssueStatus()).isEqualTo(DEFAULT_ISSUE_STATUS);
        assertThat(testIssue.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testIssue.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testIssue.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    public void createIssueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issueRepository.findAll().size();

        // Create the Issue with an existing ID
        issue.setId(1L);
        IssueDTO issueDTO = issueMapper.toDto(issue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Issue in the database
        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRepository.findAll().size();
        // set the field null
        issue.setName(null);

        // Create the Issue, which fails.
        IssueDTO issueDTO = issueMapper.toDto(issue);

        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRepository.findAll().size();
        // set the field null
        issue.setIssueType(null);

        // Create the Issue, which fails.
        IssueDTO issueDTO = issueMapper.toDto(issue);

        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRepository.findAll().size();
        // set the field null
        issue.setIssueStatus(null);

        // Create the Issue, which fails.
        IssueDTO issueDTO = issueMapper.toDto(issue);

        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = issueRepository.findAll().size();
        // set the field null
        issue.setPriority(null);

        // Create the Issue, which fails.
        IssueDTO issueDTO = issueMapper.toDto(issue);

        restIssueMockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIssues() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList
        restIssueMockMvc.perform(get("/api/issues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].issueType").value(hasItem(DEFAULT_ISSUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].issueStatus").value(hasItem(DEFAULT_ISSUE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get the issue
        restIssueMockMvc.perform(get("/api/issues/{id}", issue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.issueType").value(DEFAULT_ISSUE_TYPE.toString()))
            .andExpect(jsonPath("$.issueStatus").value(DEFAULT_ISSUE_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getIssuesByIdFiltering() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        Long id = issue.getId();

        defaultIssueShouldBeFound("id.equals=" + id);
        defaultIssueShouldNotBeFound("id.notEquals=" + id);

        defaultIssueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIssueShouldNotBeFound("id.greaterThan=" + id);

        defaultIssueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIssueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIssuesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name equals to DEFAULT_NAME
        defaultIssueShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the issueList where name equals to UPDATED_NAME
        defaultIssueShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIssuesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name not equals to DEFAULT_NAME
        defaultIssueShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the issueList where name not equals to UPDATED_NAME
        defaultIssueShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIssuesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIssueShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the issueList where name equals to UPDATED_NAME
        defaultIssueShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIssuesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name is not null
        defaultIssueShouldBeFound("name.specified=true");

        // Get all the issueList where name is null
        defaultIssueShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllIssuesByNameContainsSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name contains DEFAULT_NAME
        defaultIssueShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the issueList where name contains UPDATED_NAME
        defaultIssueShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIssuesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where name does not contain DEFAULT_NAME
        defaultIssueShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the issueList where name does not contain UPDATED_NAME
        defaultIssueShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllIssuesByIssueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueType equals to DEFAULT_ISSUE_TYPE
        defaultIssueShouldBeFound("issueType.equals=" + DEFAULT_ISSUE_TYPE);

        // Get all the issueList where issueType equals to UPDATED_ISSUE_TYPE
        defaultIssueShouldNotBeFound("issueType.equals=" + UPDATED_ISSUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueType not equals to DEFAULT_ISSUE_TYPE
        defaultIssueShouldNotBeFound("issueType.notEquals=" + DEFAULT_ISSUE_TYPE);

        // Get all the issueList where issueType not equals to UPDATED_ISSUE_TYPE
        defaultIssueShouldBeFound("issueType.notEquals=" + UPDATED_ISSUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueType in DEFAULT_ISSUE_TYPE or UPDATED_ISSUE_TYPE
        defaultIssueShouldBeFound("issueType.in=" + DEFAULT_ISSUE_TYPE + "," + UPDATED_ISSUE_TYPE);

        // Get all the issueList where issueType equals to UPDATED_ISSUE_TYPE
        defaultIssueShouldNotBeFound("issueType.in=" + UPDATED_ISSUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueType is not null
        defaultIssueShouldBeFound("issueType.specified=true");

        // Get all the issueList where issueType is null
        defaultIssueShouldNotBeFound("issueType.specified=false");
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueStatus equals to DEFAULT_ISSUE_STATUS
        defaultIssueShouldBeFound("issueStatus.equals=" + DEFAULT_ISSUE_STATUS);

        // Get all the issueList where issueStatus equals to UPDATED_ISSUE_STATUS
        defaultIssueShouldNotBeFound("issueStatus.equals=" + UPDATED_ISSUE_STATUS);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueStatus not equals to DEFAULT_ISSUE_STATUS
        defaultIssueShouldNotBeFound("issueStatus.notEquals=" + DEFAULT_ISSUE_STATUS);

        // Get all the issueList where issueStatus not equals to UPDATED_ISSUE_STATUS
        defaultIssueShouldBeFound("issueStatus.notEquals=" + UPDATED_ISSUE_STATUS);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueStatusIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueStatus in DEFAULT_ISSUE_STATUS or UPDATED_ISSUE_STATUS
        defaultIssueShouldBeFound("issueStatus.in=" + DEFAULT_ISSUE_STATUS + "," + UPDATED_ISSUE_STATUS);

        // Get all the issueList where issueStatus equals to UPDATED_ISSUE_STATUS
        defaultIssueShouldNotBeFound("issueStatus.in=" + UPDATED_ISSUE_STATUS);
    }

    @Test
    @Transactional
    public void getAllIssuesByIssueStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where issueStatus is not null
        defaultIssueShouldBeFound("issueStatus.specified=true");

        // Get all the issueList where issueStatus is null
        defaultIssueShouldNotBeFound("issueStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllIssuesByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where priority equals to DEFAULT_PRIORITY
        defaultIssueShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the issueList where priority equals to UPDATED_PRIORITY
        defaultIssueShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllIssuesByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where priority not equals to DEFAULT_PRIORITY
        defaultIssueShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the issueList where priority not equals to UPDATED_PRIORITY
        defaultIssueShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllIssuesByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultIssueShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the issueList where priority equals to UPDATED_PRIORITY
        defaultIssueShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllIssuesByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where priority is not null
        defaultIssueShouldBeFound("priority.specified=true");

        // Get all the issueList where priority is null
        defaultIssueShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllIssuesByResolutionIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where resolution equals to DEFAULT_RESOLUTION
        defaultIssueShouldBeFound("resolution.equals=" + DEFAULT_RESOLUTION);

        // Get all the issueList where resolution equals to UPDATED_RESOLUTION
        defaultIssueShouldNotBeFound("resolution.equals=" + UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    public void getAllIssuesByResolutionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where resolution not equals to DEFAULT_RESOLUTION
        defaultIssueShouldNotBeFound("resolution.notEquals=" + DEFAULT_RESOLUTION);

        // Get all the issueList where resolution not equals to UPDATED_RESOLUTION
        defaultIssueShouldBeFound("resolution.notEquals=" + UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    public void getAllIssuesByResolutionIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where resolution in DEFAULT_RESOLUTION or UPDATED_RESOLUTION
        defaultIssueShouldBeFound("resolution.in=" + DEFAULT_RESOLUTION + "," + UPDATED_RESOLUTION);

        // Get all the issueList where resolution equals to UPDATED_RESOLUTION
        defaultIssueShouldNotBeFound("resolution.in=" + UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    public void getAllIssuesByResolutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where resolution is not null
        defaultIssueShouldBeFound("resolution.specified=true");

        // Get all the issueList where resolution is null
        defaultIssueShouldNotBeFound("resolution.specified=false");
    }

    @Test
    @Transactional
    public void getAllIssuesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where dueDate equals to DEFAULT_DUE_DATE
        defaultIssueShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the issueList where dueDate equals to UPDATED_DUE_DATE
        defaultIssueShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIssuesByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where dueDate not equals to DEFAULT_DUE_DATE
        defaultIssueShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the issueList where dueDate not equals to UPDATED_DUE_DATE
        defaultIssueShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIssuesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultIssueShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the issueList where dueDate equals to UPDATED_DUE_DATE
        defaultIssueShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIssuesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        // Get all the issueList where dueDate is not null
        defaultIssueShouldBeFound("dueDate.specified=true");

        // Get all the issueList where dueDate is null
        defaultIssueShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllIssuesByAssigneeIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        IssueAssignee assignee = IssueAssigneeResourceIT.createEntity(em);
        em.persist(assignee);
        em.flush();
        issue.addAssignee(assignee);
        issueRepository.saveAndFlush(issue);
        Long assigneeId = assignee.getId();

        // Get all the issueList where assignee equals to assigneeId
        defaultIssueShouldBeFound("assigneeId.equals=" + assigneeId);

        // Get all the issueList where assignee equals to assigneeId + 1
        defaultIssueShouldNotBeFound("assigneeId.equals=" + (assigneeId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByWatcherIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        IssueWatcher watcher = IssueWatcherResourceIT.createEntity(em);
        em.persist(watcher);
        em.flush();
        issue.addWatcher(watcher);
        issueRepository.saveAndFlush(issue);
        Long watcherId = watcher.getId();

        // Get all the issueList where watcher equals to watcherId
        defaultIssueShouldBeFound("watcherId.equals=" + watcherId);

        // Get all the issueList where watcher equals to watcherId + 1
        defaultIssueShouldNotBeFound("watcherId.equals=" + (watcherId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        Comment comment = CommentResourceIT.createEntity(em);
        em.persist(comment);
        em.flush();
        issue.addComment(comment);
        issueRepository.saveAndFlush(issue);
        Long commentId = comment.getId();

        // Get all the issueList where comment equals to commentId
        defaultIssueShouldBeFound("commentId.equals=" + commentId);

        // Get all the issueList where comment equals to commentId + 1
        defaultIssueShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByIssueLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        IssueLabel issueLabel = IssueLabelResourceIT.createEntity(em);
        em.persist(issueLabel);
        em.flush();
        issue.addIssueLabel(issueLabel);
        issueRepository.saveAndFlush(issue);
        Long issueLabelId = issueLabel.getId();

        // Get all the issueList where issueLabel equals to issueLabelId
        defaultIssueShouldBeFound("issueLabelId.equals=" + issueLabelId);

        // Get all the issueList where issueLabel equals to issueLabelId + 1
        defaultIssueShouldNotBeFound("issueLabelId.equals=" + (issueLabelId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByIssueAttachmentIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        IssueAttachment issueAttachment = IssueAttachmentResourceIT.createEntity(em);
        em.persist(issueAttachment);
        em.flush();
        issue.addIssueAttachment(issueAttachment);
        issueRepository.saveAndFlush(issue);
        Long issueAttachmentId = issueAttachment.getId();

        // Get all the issueList where issueAttachment equals to issueAttachmentId
        defaultIssueShouldBeFound("issueAttachmentId.equals=" + issueAttachmentId);

        // Get all the issueList where issueAttachment equals to issueAttachmentId + 1
        defaultIssueShouldNotBeFound("issueAttachmentId.equals=" + (issueAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByIssueActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        IssueActivity issueActivity = IssueActivityResourceIT.createEntity(em);
        em.persist(issueActivity);
        em.flush();
        issue.addIssueActivity(issueActivity);
        issueRepository.saveAndFlush(issue);
        Long issueActivityId = issueActivity.getId();

        // Get all the issueList where issueActivity equals to issueActivityId
        defaultIssueShouldBeFound("issueActivityId.equals=" + issueActivityId);

        // Get all the issueList where issueActivity equals to issueActivityId + 1
        defaultIssueShouldNotBeFound("issueActivityId.equals=" + (issueActivityId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        Project project = ProjectResourceIT.createEntity(em);
        em.persist(project);
        em.flush();
        issue.setProject(project);
        issueRepository.saveAndFlush(issue);
        Long projectId = project.getId();

        // Get all the issueList where project equals to projectId
        defaultIssueShouldBeFound("projectId.equals=" + projectId);

        // Get all the issueList where project equals to projectId + 1
        defaultIssueShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }


    @Test
    @Transactional
    public void getAllIssuesByMilestoneIsEqualToSomething() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);
        Milestone milestone = MilestoneResourceIT.createEntity(em);
        em.persist(milestone);
        em.flush();
        issue.setMilestone(milestone);
        issueRepository.saveAndFlush(issue);
        Long milestoneId = milestone.getId();

        // Get all the issueList where milestone equals to milestoneId
        defaultIssueShouldBeFound("milestoneId.equals=" + milestoneId);

        // Get all the issueList where milestone equals to milestoneId + 1
        defaultIssueShouldNotBeFound("milestoneId.equals=" + (milestoneId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIssueShouldBeFound(String filter) throws Exception {
        restIssueMockMvc.perform(get("/api/issues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].issueType").value(hasItem(DEFAULT_ISSUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].issueStatus").value(hasItem(DEFAULT_ISSUE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));

        // Check, that the count call also returns 1
        restIssueMockMvc.perform(get("/api/issues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIssueShouldNotBeFound(String filter) throws Exception {
        restIssueMockMvc.perform(get("/api/issues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIssueMockMvc.perform(get("/api/issues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIssue() throws Exception {
        // Get the issue
        restIssueMockMvc.perform(get("/api/issues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        int databaseSizeBeforeUpdate = issueRepository.findAll().size();

        // Update the issue
        Issue updatedIssue = issueRepository.findById(issue.getId()).get();
        // Disconnect from session so that the updates on updatedIssue are not directly saved in db
        em.detach(updatedIssue);
        updatedIssue
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .issueType(UPDATED_ISSUE_TYPE)
            .issueStatus(UPDATED_ISSUE_STATUS)
            .priority(UPDATED_PRIORITY)
            .resolution(UPDATED_RESOLUTION)
            .dueDate(UPDATED_DUE_DATE);
        IssueDTO issueDTO = issueMapper.toDto(updatedIssue);

        restIssueMockMvc.perform(put("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isOk());

        // Validate the Issue in the database
        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeUpdate);
        Issue testIssue = issueList.get(issueList.size() - 1);
        assertThat(testIssue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIssue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIssue.getIssueType()).isEqualTo(UPDATED_ISSUE_TYPE);
        assertThat(testIssue.getIssueStatus()).isEqualTo(UPDATED_ISSUE_STATUS);
        assertThat(testIssue.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testIssue.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testIssue.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingIssue() throws Exception {
        int databaseSizeBeforeUpdate = issueRepository.findAll().size();

        // Create the Issue
        IssueDTO issueDTO = issueMapper.toDto(issue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueMockMvc.perform(put("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(issueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Issue in the database
        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssue() throws Exception {
        // Initialize the database
        issueRepository.saveAndFlush(issue);

        int databaseSizeBeforeDelete = issueRepository.findAll().size();

        // Delete the issue
        restIssueMockMvc.perform(delete("/api/issues/{id}", issue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Issue> issueList = issueRepository.findAll();
        assertThat(issueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
