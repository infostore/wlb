package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.IssueAttachment;
import kr.etcsoft.wlb.repository.IssueAttachmentRepository;
import kr.etcsoft.wlb.service.dto.IssueAttachmentDTO;
import kr.etcsoft.wlb.service.mapper.IssueAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IssueAttachment}.
 */
@Service
@Transactional
public class IssueAttachmentService {

    private final Logger log = LoggerFactory.getLogger(IssueAttachmentService.class);

    private final IssueAttachmentRepository issueAttachmentRepository;

    private final IssueAttachmentMapper issueAttachmentMapper;

    public IssueAttachmentService(IssueAttachmentRepository issueAttachmentRepository, IssueAttachmentMapper issueAttachmentMapper) {
        this.issueAttachmentRepository = issueAttachmentRepository;
        this.issueAttachmentMapper = issueAttachmentMapper;
    }

    /**
     * Save a issueAttachment.
     *
     * @param issueAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueAttachmentDTO save(IssueAttachmentDTO issueAttachmentDTO) {
        log.debug("Request to save IssueAttachment : {}", issueAttachmentDTO);
        IssueAttachment issueAttachment = issueAttachmentMapper.toEntity(issueAttachmentDTO);
        issueAttachment = issueAttachmentRepository.save(issueAttachment);
        return issueAttachmentMapper.toDto(issueAttachment);
    }

    /**
     * Get all the issueAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueAttachments");
        return issueAttachmentRepository.findAll(pageable)
            .map(issueAttachmentMapper::toDto);
    }

    /**
     * Get one issueAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueAttachmentDTO> findOne(Long id) {
        log.debug("Request to get IssueAttachment : {}", id);
        return issueAttachmentRepository.findById(id)
            .map(issueAttachmentMapper::toDto);
    }

    /**
     * Delete the issueAttachment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IssueAttachment : {}", id);
        issueAttachmentRepository.deleteById(id);
    }
}
