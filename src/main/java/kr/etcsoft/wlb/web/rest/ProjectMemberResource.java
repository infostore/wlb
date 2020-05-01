package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.ProjectMemberService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.ProjectMemberDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.ProjectMember}.
 */
@RestController
@RequestMapping("/api")
public class ProjectMemberResource {

    private final Logger log = LoggerFactory.getLogger(ProjectMemberResource.class);

    private static final String ENTITY_NAME = "projectMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectMemberService projectMemberService;

    public ProjectMemberResource(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    /**
     * {@code POST  /project-members} : Create a new projectMember.
     *
     * @param projectMemberDTO the projectMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectMemberDTO, or with status {@code 400 (Bad Request)} if the projectMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-members")
    public ResponseEntity<ProjectMemberDTO> createProjectMember(@Valid @RequestBody ProjectMemberDTO projectMemberDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectMember : {}", projectMemberDTO);
        if (projectMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectMemberDTO result = projectMemberService.save(projectMemberDTO);
        return ResponseEntity.created(new URI("/api/project-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-members} : Updates an existing projectMember.
     *
     * @param projectMemberDTO the projectMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectMemberDTO,
     * or with status {@code 400 (Bad Request)} if the projectMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-members")
    public ResponseEntity<ProjectMemberDTO> updateProjectMember(@Valid @RequestBody ProjectMemberDTO projectMemberDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectMember : {}", projectMemberDTO);
        if (projectMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectMemberDTO result = projectMemberService.save(projectMemberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-members} : get all the projectMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectMembers in body.
     */
    @GetMapping("/project-members")
    public ResponseEntity<List<ProjectMemberDTO>> getAllProjectMembers(Pageable pageable) {
        log.debug("REST request to get a page of ProjectMembers");
        Page<ProjectMemberDTO> page = projectMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-members/:id} : get the "id" projectMember.
     *
     * @param id the id of the projectMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-members/{id}")
    public ResponseEntity<ProjectMemberDTO> getProjectMember(@PathVariable Long id) {
        log.debug("REST request to get ProjectMember : {}", id);
        Optional<ProjectMemberDTO> projectMemberDTO = projectMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectMemberDTO);
    }

    /**
     * {@code DELETE  /project-members/:id} : delete the "id" projectMember.
     *
     * @param id the id of the projectMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-members/{id}")
    public ResponseEntity<Void> deleteProjectMember(@PathVariable Long id) {
        log.debug("REST request to delete ProjectMember : {}", id);
        projectMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
