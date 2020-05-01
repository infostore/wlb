package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.IssueAssigneeService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.IssueAssigneeDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kr.etcsoft.wlb.domain.IssueAssignee}.
 */
@RestController
@RequestMapping("/api")
public class IssueAssigneeResource {

    private final Logger log = LoggerFactory.getLogger(IssueAssigneeResource.class);

    private static final String ENTITY_NAME = "issueAssignee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueAssigneeService issueAssigneeService;

    public IssueAssigneeResource(IssueAssigneeService issueAssigneeService) {
        this.issueAssigneeService = issueAssigneeService;
    }

    /**
     * {@code POST  /issue-assignees} : Create a new issueAssignee.
     *
     * @param issueAssigneeDTO the issueAssigneeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueAssigneeDTO, or with status {@code 400 (Bad Request)} if the issueAssignee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-assignees")
    public ResponseEntity<IssueAssigneeDTO> createIssueAssignee(@RequestBody IssueAssigneeDTO issueAssigneeDTO) throws URISyntaxException {
        log.debug("REST request to save IssueAssignee : {}", issueAssigneeDTO);
        if (issueAssigneeDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueAssignee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueAssigneeDTO result = issueAssigneeService.save(issueAssigneeDTO);
        return ResponseEntity.created(new URI("/api/issue-assignees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-assignees} : Updates an existing issueAssignee.
     *
     * @param issueAssigneeDTO the issueAssigneeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueAssigneeDTO,
     * or with status {@code 400 (Bad Request)} if the issueAssigneeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueAssigneeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-assignees")
    public ResponseEntity<IssueAssigneeDTO> updateIssueAssignee(@RequestBody IssueAssigneeDTO issueAssigneeDTO) throws URISyntaxException {
        log.debug("REST request to update IssueAssignee : {}", issueAssigneeDTO);
        if (issueAssigneeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueAssigneeDTO result = issueAssigneeService.save(issueAssigneeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueAssigneeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-assignees} : get all the issueAssignees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueAssignees in body.
     */
    @GetMapping("/issue-assignees")
    public ResponseEntity<List<IssueAssigneeDTO>> getAllIssueAssignees(Pageable pageable) {
        log.debug("REST request to get a page of IssueAssignees");
        Page<IssueAssigneeDTO> page = issueAssigneeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /issue-assignees/:id} : get the "id" issueAssignee.
     *
     * @param id the id of the issueAssigneeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueAssigneeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-assignees/{id}")
    public ResponseEntity<IssueAssigneeDTO> getIssueAssignee(@PathVariable Long id) {
        log.debug("REST request to get IssueAssignee : {}", id);
        Optional<IssueAssigneeDTO> issueAssigneeDTO = issueAssigneeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueAssigneeDTO);
    }

    /**
     * {@code DELETE  /issue-assignees/:id} : delete the "id" issueAssignee.
     *
     * @param id the id of the issueAssigneeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-assignees/{id}")
    public ResponseEntity<Void> deleteIssueAssignee(@PathVariable Long id) {
        log.debug("REST request to delete IssueAssignee : {}", id);
        issueAssigneeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
