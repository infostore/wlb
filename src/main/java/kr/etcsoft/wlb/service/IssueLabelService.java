package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.IssueLabel;
import kr.etcsoft.wlb.repository.IssueLabelRepository;
import kr.etcsoft.wlb.service.dto.IssueLabelDTO;
import kr.etcsoft.wlb.service.mapper.IssueLabelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IssueLabel}.
 */
@Service
@Transactional
public class IssueLabelService {

    private final Logger log = LoggerFactory.getLogger(IssueLabelService.class);

    private final IssueLabelRepository issueLabelRepository;

    private final IssueLabelMapper issueLabelMapper;

    public IssueLabelService(IssueLabelRepository issueLabelRepository, IssueLabelMapper issueLabelMapper) {
        this.issueLabelRepository = issueLabelRepository;
        this.issueLabelMapper = issueLabelMapper;
    }

    /**
     * Save a issueLabel.
     *
     * @param issueLabelDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueLabelDTO save(IssueLabelDTO issueLabelDTO) {
        log.debug("Request to save IssueLabel : {}", issueLabelDTO);
        IssueLabel issueLabel = issueLabelMapper.toEntity(issueLabelDTO);
        issueLabel = issueLabelRepository.save(issueLabel);
        return issueLabelMapper.toDto(issueLabel);
    }

    /**
     * Get all the issueLabels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueLabelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueLabels");
        return issueLabelRepository.findAll(pageable)
            .map(issueLabelMapper::toDto);
    }

    /**
     * Get one issueLabel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueLabelDTO> findOne(Long id) {
        log.debug("Request to get IssueLabel : {}", id);
        return issueLabelRepository.findById(id)
            .map(issueLabelMapper::toDto);
    }

    /**
     * Delete the issueLabel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IssueLabel : {}", id);
        issueLabelRepository.deleteById(id);
    }
}
