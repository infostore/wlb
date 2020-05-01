package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.IssueAttachmentService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.IssueAttachmentDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.IssueAttachment}.
 */
@RestController
@RequestMapping("/api")
public class IssueAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(IssueAttachmentResource.class);

    private static final String ENTITY_NAME = "issueAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueAttachmentService issueAttachmentService;

    public IssueAttachmentResource(IssueAttachmentService issueAttachmentService) {
        this.issueAttachmentService = issueAttachmentService;
    }

    /**
     * {@code POST  /issue-attachments} : Create a new issueAttachment.
     *
     * @param issueAttachmentDTO the issueAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueAttachmentDTO, or with status {@code 400 (Bad Request)} if the issueAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-attachments")
    public ResponseEntity<IssueAttachmentDTO> createIssueAttachment(@RequestBody IssueAttachmentDTO issueAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save IssueAttachment : {}", issueAttachmentDTO);
        if (issueAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueAttachmentDTO result = issueAttachmentService.save(issueAttachmentDTO);
        return ResponseEntity.created(new URI("/api/issue-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-attachments} : Updates an existing issueAttachment.
     *
     * @param issueAttachmentDTO the issueAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the issueAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-attachments")
    public ResponseEntity<IssueAttachmentDTO> updateIssueAttachment(@RequestBody IssueAttachmentDTO issueAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update IssueAttachment : {}", issueAttachmentDTO);
        if (issueAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueAttachmentDTO result = issueAttachmentService.save(issueAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-attachments} : get all the issueAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueAttachments in body.
     */
    @GetMapping("/issue-attachments")
    public ResponseEntity<List<IssueAttachmentDTO>> getAllIssueAttachments(Pageable pageable) {
        log.debug("REST request to get a page of IssueAttachments");
        Page<IssueAttachmentDTO> page = issueAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /issue-attachments/:id} : get the "id" issueAttachment.
     *
     * @param id the id of the issueAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-attachments/{id}")
    public ResponseEntity<IssueAttachmentDTO> getIssueAttachment(@PathVariable Long id) {
        log.debug("REST request to get IssueAttachment : {}", id);
        Optional<IssueAttachmentDTO> issueAttachmentDTO = issueAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueAttachmentDTO);
    }

    /**
     * {@code DELETE  /issue-attachments/:id} : delete the "id" issueAttachment.
     *
     * @param id the id of the issueAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-attachments/{id}")
    public ResponseEntity<Void> deleteIssueAttachment(@PathVariable Long id) {
        log.debug("REST request to delete IssueAttachment : {}", id);
        issueAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
