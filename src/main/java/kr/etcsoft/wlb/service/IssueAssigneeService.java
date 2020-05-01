package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.IssueAssignee;
import kr.etcsoft.wlb.repository.IssueAssigneeRepository;
import kr.etcsoft.wlb.service.dto.IssueAssigneeDTO;
import kr.etcsoft.wlb.service.mapper.IssueAssigneeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IssueAssignee}.
 */
@Service
@Transactional
public class IssueAssigneeService {

    private final Logger log = LoggerFactory.getLogger(IssueAssigneeService.class);

    private final IssueAssigneeRepository issueAssigneeRepository;

    private final IssueAssigneeMapper issueAssigneeMapper;

    public IssueAssigneeService(IssueAssigneeRepository issueAssigneeRepository, IssueAssigneeMapper issueAssigneeMapper) {
        this.issueAssigneeRepository = issueAssigneeRepository;
        this.issueAssigneeMapper = issueAssigneeMapper;
    }

    /**
     * Save a issueAssignee.
     *
     * @param issueAssigneeDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueAssigneeDTO save(IssueAssigneeDTO issueAssigneeDTO) {
        log.debug("Request to save IssueAssignee : {}", issueAssigneeDTO);
        IssueAssignee issueAssignee = issueAssigneeMapper.toEntity(issueAssigneeDTO);
        issueAssignee = issueAssigneeRepository.save(issueAssignee);
        return issueAssigneeMapper.toDto(issueAssignee);
    }

    /**
     * Get all the issueAssignees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueAssigneeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueAssignees");
        return issueAssigneeRepository.findAll(pageable)
            .map(issueAssigneeMapper::toDto);
    }

    /**
     * Get one issueAssignee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueAssigneeDTO> findOne(Long id) {
        log.debug("Request to get IssueAssignee : {}", id);
        return issueAssigneeRepository.findById(id)
            .map(issueAssigneeMapper::toDto);
    }

    /**
     * Delete the issueAssignee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IssueAssignee : {}", id);
        issueAssigneeRepository.deleteById(id);
    }
}
