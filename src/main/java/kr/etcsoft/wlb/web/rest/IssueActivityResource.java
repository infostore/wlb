package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.IssueActivityService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.IssueActivityDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.IssueActivity}.
 */
@RestController
@RequestMapping("/api")
public class IssueActivityResource {

    private final Logger log = LoggerFactory.getLogger(IssueActivityResource.class);

    private static final String ENTITY_NAME = "issueActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueActivityService issueActivityService;

    public IssueActivityResource(IssueActivityService issueActivityService) {
        this.issueActivityService = issueActivityService;
    }

    /**
     * {@code POST  /issue-activities} : Create a new issueActivity.
     *
     * @param issueActivityDTO the issueActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueActivityDTO, or with status {@code 400 (Bad Request)} if the issueActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-activities")
    public ResponseEntity<IssueActivityDTO> createIssueActivity(@Valid @RequestBody IssueActivityDTO issueActivityDTO) throws URISyntaxException {
        log.debug("REST request to save IssueActivity : {}", issueActivityDTO);
        if (issueActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueActivityDTO result = issueActivityService.save(issueActivityDTO);
        return ResponseEntity.created(new URI("/api/issue-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-activities} : Updates an existing issueActivity.
     *
     * @param issueActivityDTO the issueActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueActivityDTO,
     * or with status {@code 400 (Bad Request)} if the issueActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-activities")
    public ResponseEntity<IssueActivityDTO> updateIssueActivity(@Valid @RequestBody IssueActivityDTO issueActivityDTO) throws URISyntaxException {
        log.debug("REST request to update IssueActivity : {}", issueActivityDTO);
        if (issueActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueActivityDTO result = issueActivityService.save(issueActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-activities} : get all the issueActivities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueActivities in body.
     */
    @GetMapping("/issue-activities")
    public ResponseEntity<List<IssueActivityDTO>> getAllIssueActivities(Pageable pageable) {
        log.debug("REST request to get a page of IssueActivities");
        Page<IssueActivityDTO> page = issueActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /issue-activities/:id} : get the "id" issueActivity.
     *
     * @param id the id of the issueActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-activities/{id}")
    public ResponseEntity<IssueActivityDTO> getIssueActivity(@PathVariable Long id) {
        log.debug("REST request to get IssueActivity : {}", id);
        Optional<IssueActivityDTO> issueActivityDTO = issueActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueActivityDTO);
    }

    /**
     * {@code DELETE  /issue-activities/:id} : delete the "id" issueActivity.
     *
     * @param id the id of the issueActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-activities/{id}")
    public ResponseEntity<Void> deleteIssueActivity(@PathVariable Long id) {
        log.debug("REST request to delete IssueActivity : {}", id);
        issueActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
