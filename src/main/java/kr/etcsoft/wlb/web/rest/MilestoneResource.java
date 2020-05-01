package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.MilestoneService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.MilestoneDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kr.etcsoft.wlb.domain.Milestone}.
 */
@RestController
@RequestMapping("/api")
public class MilestoneResource {

    private final Logger log = LoggerFactory.getLogger(MilestoneResource.class);

    private static final String ENTITY_NAME = "milestone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MilestoneService milestoneService;

    public MilestoneResource(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * {@code POST  /milestones} : Create a new milestone.
     *
     * @param milestoneDTO the milestoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new milestoneDTO, or with status {@code 400 (Bad Request)} if the milestone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/milestones")
    public ResponseEntity<MilestoneDTO> createMilestone(@Valid @RequestBody MilestoneDTO milestoneDTO) throws URISyntaxException {
        log.debug("REST request to save Milestone : {}", milestoneDTO);
        if (milestoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new milestone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MilestoneDTO result = milestoneService.save(milestoneDTO);
        return ResponseEntity.created(new URI("/api/milestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /milestones} : Updates an existing milestone.
     *
     * @param milestoneDTO the milestoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated milestoneDTO,
     * or with status {@code 400 (Bad Request)} if the milestoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the milestoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/milestones")
    public ResponseEntity<MilestoneDTO> updateMilestone(@Valid @RequestBody MilestoneDTO milestoneDTO) throws URISyntaxException {
        log.debug("REST request to update Milestone : {}", milestoneDTO);
        if (milestoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MilestoneDTO result = milestoneService.save(milestoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, milestoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /milestones} : get all the milestones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of milestones in body.
     */
    @GetMapping("/milestones")
    public ResponseEntity<List<MilestoneDTO>> getAllMilestones(MilestoneDTO milestoneDTO, Pageable pageable) {
        log.debug("REST request to get a page of Milestones");
        Page<MilestoneDTO> page = milestoneService.findAll(milestoneDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /milestones/:id} : get the "id" milestone.
     *
     * @param id the id of the milestoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the milestoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/milestones/{id}")
    public ResponseEntity<MilestoneDTO> getMilestone(@PathVariable Long id) {
        log.debug("REST request to get Milestone : {}", id);
        Optional<MilestoneDTO> milestoneDTO = milestoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(milestoneDTO);
    }

    /**
     * {@code DELETE  /milestones/:id} : delete the "id" milestone.
     *
     * @param id the id of the milestoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/milestones/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        log.debug("REST request to delete Milestone : {}", id);
        milestoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
