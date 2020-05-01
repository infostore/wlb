package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.Milestone;
import kr.etcsoft.wlb.repository.MilestoneRepository;
import kr.etcsoft.wlb.service.dto.MilestoneDTO;
import kr.etcsoft.wlb.service.mapper.MilestoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;

/**
 * Service Implementation for managing {@link Milestone}.
 */
@Service
@Transactional
public class MilestoneService {

    private final Logger log = LoggerFactory.getLogger(MilestoneService.class);

    private final MilestoneRepository milestoneRepository;

    private final MilestoneMapper milestoneMapper;

    public MilestoneService(MilestoneRepository milestoneRepository, MilestoneMapper milestoneMapper) {
        this.milestoneRepository = milestoneRepository;
        this.milestoneMapper = milestoneMapper;
    }

    /**
     * Save a milestone.
     *
     * @param milestoneDTO the entity to save.
     * @return the persisted entity.
     */
    public MilestoneDTO save(MilestoneDTO milestoneDTO) {
        log.debug("Request to save Milestone : {}", milestoneDTO);
        Milestone milestone = milestoneMapper.toEntity(milestoneDTO);
        milestone = milestoneRepository.save(milestone);
        return milestoneMapper.toDto(milestone);
    }

    /**
     * Get all the milestones.
     *
     *
     * @param milestoneDTO
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MilestoneDTO> findAll(MilestoneDTO milestoneDTO, Pageable pageable) {
        log.debug("Request to get all Milestones");

        pageable = setDefaultSort(pageable);

        return milestoneRepository.findByProjectId(milestoneDTO.getProjectId(), pageable)
            .map(milestoneMapper::toDto);
//
//        return milestoneRepository.findAll(pageable)
//            .map(milestoneMapper::toDto);
    }

    private Pageable setDefaultSort(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(DEFAULT_DIRECTION, "dueDate"));
        }
        return pageable;
    }

    /**
     * Get one milestone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MilestoneDTO> findOne(Long id) {
        log.debug("Request to get Milestone : {}", id);
        return milestoneRepository.findById(id)
            .map(milestoneMapper::toDto);
    }

    /**
     * Delete the milestone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Milestone : {}", id);
        milestoneRepository.deleteById(id);
    }
}
