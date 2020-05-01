package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.LabelGroup;
import kr.etcsoft.wlb.repository.LabelGroupRepository;
import kr.etcsoft.wlb.service.dto.LabelGroupDTO;
import kr.etcsoft.wlb.service.mapper.LabelGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LabelGroup}.
 */
@Service
@Transactional
public class LabelGroupService {

    private final Logger log = LoggerFactory.getLogger(LabelGroupService.class);

    private final LabelGroupRepository labelGroupRepository;

    private final LabelGroupMapper labelGroupMapper;

    public LabelGroupService(LabelGroupRepository labelGroupRepository, LabelGroupMapper labelGroupMapper) {
        this.labelGroupRepository = labelGroupRepository;
        this.labelGroupMapper = labelGroupMapper;
    }

    /**
     * Save a labelGroup.
     *
     * @param labelGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public LabelGroupDTO save(LabelGroupDTO labelGroupDTO) {
        log.debug("Request to save LabelGroup : {}", labelGroupDTO);
        LabelGroup labelGroup = labelGroupMapper.toEntity(labelGroupDTO);
        labelGroup = labelGroupRepository.save(labelGroup);
        return labelGroupMapper.toDto(labelGroup);
    }

    /**
     * Get all the labelGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LabelGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LabelGroups");
        return labelGroupRepository.findAll(pageable)
            .map(labelGroupMapper::toDto);
    }

    /**
     * Get one labelGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LabelGroupDTO> findOne(Long id) {
        log.debug("Request to get LabelGroup : {}", id);
        return labelGroupRepository.findById(id)
            .map(labelGroupMapper::toDto);
    }

    /**
     * Delete the labelGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LabelGroup : {}", id);
        labelGroupRepository.deleteById(id);
    }
}
