package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.IssueWatcherService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.IssueWatcherDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.IssueWatcher}.
 */
@RestController
@RequestMapping("/api")
public class IssueWatcherResource {

    private final Logger log = LoggerFactory.getLogger(IssueWatcherResource.class);

    private static final String ENTITY_NAME = "issueWatcher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueWatcherService issueWatcherService;

    public IssueWatcherResource(IssueWatcherService issueWatcherService) {
        this.issueWatcherService = issueWatcherService;
    }

    /**
     * {@code POST  /issue-watchers} : Create a new issueWatcher.
     *
     * @param issueWatcherDTO the issueWatcherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueWatcherDTO, or with status {@code 400 (Bad Request)} if the issueWatcher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-watchers")
    public ResponseEntity<IssueWatcherDTO> createIssueWatcher(@RequestBody IssueWatcherDTO issueWatcherDTO) throws URISyntaxException {
        log.debug("REST request to save IssueWatcher : {}", issueWatcherDTO);
        if (issueWatcherDTO.getId() != null) {
            throw new BadRequestAlertException("A new issueWatcher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueWatcherDTO result = issueWatcherService.save(issueWatcherDTO);
        return ResponseEntity.created(new URI("/api/issue-watchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-watchers} : Updates an existing issueWatcher.
     *
     * @param issueWatcherDTO the issueWatcherDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueWatcherDTO,
     * or with status {@code 400 (Bad Request)} if the issueWatcherDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueWatcherDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-watchers")
    public ResponseEntity<IssueWatcherDTO> updateIssueWatcher(@RequestBody IssueWatcherDTO issueWatcherDTO) throws URISyntaxException {
        log.debug("REST request to update IssueWatcher : {}", issueWatcherDTO);
        if (issueWatcherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssueWatcherDTO result = issueWatcherService.save(issueWatcherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueWatcherDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issue-watchers} : get all the issueWatchers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueWatchers in body.
     */
    @GetMapping("/issue-watchers")
    public ResponseEntity<List<IssueWatcherDTO>> getAllIssueWatchers(Pageable pageable) {
        log.debug("REST request to get a page of IssueWatchers");
        Page<IssueWatcherDTO> page = issueWatcherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /issue-watchers/:id} : get the "id" issueWatcher.
     *
     * @param id the id of the issueWatcherDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueWatcherDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-watchers/{id}")
    public ResponseEntity<IssueWatcherDTO> getIssueWatcher(@PathVariable Long id) {
        log.debug("REST request to get IssueWatcher : {}", id);
        Optional<IssueWatcherDTO> issueWatcherDTO = issueWatcherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issueWatcherDTO);
    }

    /**
     * {@code DELETE  /issue-watchers/:id} : delete the "id" issueWatcher.
     *
     * @param id the id of the issueWatcherDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-watchers/{id}")
    public ResponseEntity<Void> deleteIssueWatcher(@PathVariable Long id) {
        log.debug("REST request to delete IssueWatcher : {}", id);
        issueWatcherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
