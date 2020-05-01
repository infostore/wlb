package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.ProjectActivityService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.ProjectActivityDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kr.etcsoft.wlb.domain.ProjectActivity}.
 */
@RestController
@RequestMapping("/api")
public class ProjectActivityResource {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityResource.class);

    private static final String ENTITY_NAME = "projectActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectActivityService projectActivityService;

    public ProjectActivityResource(ProjectActivityService projectActivityService) {
        this.projectActivityService = projectActivityService;
    }

    /**
     * {@code POST  /project-activities} : Create a new projectActivity.
     *
     * @param projectActivityDTO the projectActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectActivityDTO, or with status {@code 400 (Bad Request)} if the projectActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-activities")
    public ResponseEntity<ProjectActivityDTO> createProjectActivity(@Valid @RequestBody ProjectActivityDTO projectActivityDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectActivity : {}", projectActivityDTO);
        if (projectActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectActivityDTO result = projectActivityService.save(projectActivityDTO);
        return ResponseEntity.created(new URI("/api/project-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-activities} : Updates an existing projectActivity.
     *
     * @param projectActivityDTO the projectActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectActivityDTO,
     * or with status {@code 400 (Bad Request)} if the projectActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-activities")
    public ResponseEntity<ProjectActivityDTO> updateProjectActivity(@Valid @RequestBody ProjectActivityDTO projectActivityDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectActivity : {}", projectActivityDTO);
        if (projectActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectActivityDTO result = projectActivityService.save(projectActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-activities} : get all the projectActivities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectActivities in body.
     */
    @GetMapping("/project-activities")
    public ResponseEntity<List<ProjectActivityDTO>> getAllProjectActivities(Pageable pageable) {
        log.debug("REST request to get a page of ProjectActivities");
        Page<ProjectActivityDTO> page = projectActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-activities/:id} : get the "id" projectActivity.
     *
     * @param id the id of the projectActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-activities/{id}")
    public ResponseEntity<ProjectActivityDTO> getProjectActivity(@PathVariable Long id) {
        log.debug("REST request to get ProjectActivity : {}", id);
        Optional<ProjectActivityDTO> projectActivityDTO = projectActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectActivityDTO);
    }

    /**
     * {@code DELETE  /project-activities/:id} : delete the "id" projectActivity.
     *
     * @param id the id of the projectActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-activities/{id}")
    public ResponseEntity<Void> deleteProjectActivity(@PathVariable Long id) {
        log.debug("REST request to delete ProjectActivity : {}", id);
        projectActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
