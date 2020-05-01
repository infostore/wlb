package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.IssueLabelService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.IssueLabelDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.IssueLabel}.
 */
@RestController
@RequestMapping("/api")
public class IssueLabelResource {

    private final Logger log = LoggerFactory.getLogger(IssueLabelResource.class);

    private static final String ENTITY_NAME = "issueLabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueLabelService issueLabelService;

    public IssueLabelResource(IssueLabelService issueLabelService) {
        this.issueLabelService = issueLabelService;
    }

    /**
     * {@code POST  /issue-labels} : Create a new issueLabel.
     *
     * @param issueLabelDTO the issueLabelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueLabelDTO, or with status {@code 400 (Bad Request)} if the issueLabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-labels")
    public ResponseEntity<IssueLabelDTO> createIssueLabel(@RequestBody IssueLabelDTO issueLabelDTO) throws URISyntaxException {
        log.debug("REST request to save IssueLabel : {}", issueLabelDTO);
        if (issueLabelDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueLabelDTO result = issueLabelService.save(issueLabelDTO);
        return ResponseEntity.created(new URI("/api/issue-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-labels} : Updates an existing issueLabel.
     *
     * @param issueLabelDTO the issueLabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueLabelDTO,
     * or with status {@code 400 (Bad Request)} if the issueLabelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueLabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-labels")
    public ResponseEntity<IssueLabelDTO> updateIssueLabel(@RequestBody IssueLabelDTO issueLabelDTO) throws URISyntaxException {
        log.debug("REST request to update IssueLabel : {}", issueLabelDTO);
        if (issueLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueLabelDTO result = issueLabelService.save(issueLabelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueLabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-labels} : get all the issueLabels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueLabels in body.
     */
    @GetMapping("/issue-labels")
    public ResponseEntity<List<IssueLabelDTO>> getAllIssueLabels(Pageable pageable) {
        log.debug("REST request to get a page of IssueLabels");
        Page<IssueLabelDTO> page = issueLabelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /issue-labels/:id} : get the "id" issueLabel.
     *
     * @param id the id of the issueLabelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueLabelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-labels/{id}")
    public ResponseEntity<IssueLabelDTO> getIssueLabel(@PathVariable Long id) {
        log.debug("REST request to get IssueLabel : {}", id);
        Optional<IssueLabelDTO> issueLabelDTO = issueLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueLabelDTO);
    }

    /**
     * {@code DELETE  /issue-labels/:id} : delete the "id" issueLabel.
     *
     * @param id the id of the issueLabelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-labels/{id}")
    public ResponseEntity<Void> deleteIssueLabel(@PathVariable Long id) {
        log.debug("REST request to delete IssueLabel : {}", id);
        issueLabelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
