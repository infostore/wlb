package kr.etcsoft.wlb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import kr.etcsoft.wlb.domain.Issue;
import kr.etcsoft.wlb.domain.*; // for static metamodels
import kr.etcsoft.wlb.repository.IssueRepository;
import kr.etcsoft.wlb.service.dto.IssueCriteria;
import kr.etcsoft.wlb.service.dto.IssueDTO;
import kr.etcsoft.wlb.service.mapper.IssueMapper;

/**
 * Service for executing complex queries for {@link Issue} entities in the database.
 * The main input is a {@link IssueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IssueDTO} or a {@link Page} of {@link IssueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IssueQueryService extends QueryService<Issue> {

    private final Logger log = LoggerFactory.getLogger(IssueQueryService.class);

    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper;

    public IssueQueryService(IssueRepository issueRepository, IssueMapper issueMapper) {
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
    }

    /**
     * Return a {@link List} of {@link IssueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IssueDTO> findByCriteria(IssueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Issue> specification = createSpecification(criteria);
        return issueMapper.toDto(issueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IssueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueDTO> findByCriteria(IssueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Issue> specification = createSpecification(criteria);
        return issueRepository.findAll(specification, page)
            .map(issueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IssueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Issue> specification = createSpecification(criteria);
        return issueRepository.count(specification);
    }

    /**
     * Function to convert {@link IssueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Issue> createSpecification(IssueCriteria criteria) {
        Specification<Issue> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Issue_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Issue_.name));
            }
            if (criteria.getIssueType() != null) {
                specification = specification.and(buildSpecification(criteria.getIssueType(), Issue_.issueType));
            }
            if (criteria.getIssueStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getIssueStatus(), Issue_.issueStatus));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), Issue_.priority));
            }
            if (criteria.getResolution() != null) {
                specification = specification.and(buildSpecification(criteria.getResolution(), Issue_.resolution));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Issue_.dueDate));
            }
            if (criteria.getAssigneeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssigneeId(),
                    root -> root.join(Issue_.assignees, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getWatcherId() != null) {
                specification = specification.and(buildSpecification(criteria.getWatcherId(),
                    root -> root.join(Issue_.watchers, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(Issue_.comments, JoinType.LEFT).get(Comment_.id)));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(Issue_.project, JoinType.LEFT).get(Project_.id)));
            }
            if (criteria.getMilestoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getMilestoneId(),
                    root -> root.join(Issue_.milestone, JoinType.LEFT).get(Milestone_.id)));
            }
        }
        return specification;
    }
}
