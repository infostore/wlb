package kr.etcsoft.wlb.service;

import com.querydsl.core.BooleanBuilder;
import kr.etcsoft.wlb.domain.Issue;
import kr.etcsoft.wlb.domain.QIssue;
import kr.etcsoft.wlb.domain.QMilestone;
import kr.etcsoft.wlb.domain.QProject;
import kr.etcsoft.wlb.repository.IssueRepository;
import kr.etcsoft.wlb.service.dto.IssueDTO;
import kr.etcsoft.wlb.service.mapper.IssueMapper;
import kr.etcsoft.wlb.web.rest.vm.IssueVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;

/**
 * Service Implementation for managing {@link Issue}.
 */
@Service
@Transactional
public class IssueService {

    private final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper;

    public IssueService(IssueRepository issueRepository, IssueMapper issueMapper) {
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
    }

    /**
     * Save a issue.
     *
     * @param issueDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueDTO save(IssueDTO issueDTO) {
        log.debug("Request to save Issue : {}", issueDTO);
        Issue issue = issueMapper.toEntity(issueDTO);
        issue = issueRepository.save(issue);
        return issueMapper.toDto(issue);
    }

    /**
     * Get all the issues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueDTO> findAll(IssueVM issueVM, Pageable pageable) {
        log.debug("Request to get all Issues");

        QIssue qIssue = QIssue.issue;
        QProject qProject = QProject.project;
        QMilestone qMilestone = QMilestone.milestone;

        BooleanBuilder predicate = new BooleanBuilder();

        if (issueVM.getProjectId() != null) {
            predicate.and(qProject.id.eq(issueVM.getProjectId()));
        }

        if (issueVM.getMilestoneId() != null) {
            predicate.and(qMilestone.id.eq(issueVM.getMilestoneId()));
        }

        if (issueVM.getIssueStatus() != null) {
            predicate.and(qIssue.issueStatus.eq(issueVM.getIssueStatus()));
        }

        if (issueVM.getIssueType() != null) {
            predicate.and(qIssue.issueType.eq(issueVM.getIssueType()));
        }

        if (issueVM.getPriority() != null) {
            predicate.and(qIssue.priority.eq(issueVM.getPriority()));
        }

        if (issueVM.getResolution() != null) {
            predicate.and(qIssue.resolution.eq(issueVM.getResolution()));
        }

        if (issueVM.getName() != null) {
            predicate.and(qIssue.name.contains(issueVM.getName()));
        }

        pageable = setDefaultSort(pageable);

        return issueRepository.findAll(predicate, pageable)
            .map(issueMapper::toDto);
    }

    private Pageable setDefaultSort(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(DEFAULT_DIRECTION, "dueDate"));
        }
        return pageable;
    }

    /**
     * Get one issue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueDTO> findOne(Long id) {
        log.debug("Request to get Issue : {}", id);
        return issueRepository.findById(id)
            .map(issueMapper::toDto);
    }

    /**
     * Delete the issue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Issue : {}", id);
        issueRepository.deleteById(id);
    }
}
