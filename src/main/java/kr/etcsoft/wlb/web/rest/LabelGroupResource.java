package kr.etcsoft.wlb.web.rest;

import kr.etcsoft.wlb.service.LabelGroupService;
import kr.etcsoft.wlb.web.rest.errors.BadRequestAlertException;
import kr.etcsoft.wlb.service.dto.LabelGroupDTO;

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
 * REST controller for managing {@link kr.etcsoft.wlb.domain.LabelGroup}.
 */
@RestController
@RequestMapping("/api")
public class LabelGroupResource {

    private final Logger log = LoggerFactory.getLogger(LabelGroupResource.class);

    private static final String ENTITY_NAME = "labelGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LabelGroupService labelGroupService;

    public LabelGroupResource(LabelGroupService labelGroupService) {
        this.labelGroupService = labelGroupService;
    }

    /**
     * {@code POST  /label-groups} : Create a new labelGroup.
     *
     * @param labelGroupDTO the labelGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new labelGroupDTO, or with status {@code 400 (Bad Request)} if the labelGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/label-groups")
    public ResponseEntity<LabelGroupDTO> createLabelGroup(@Valid @RequestBody LabelGroupDTO labelGroupDTO) throws URISyntaxException {
        log.debug("REST request to save LabelGroup : {}", labelGroupDTO);
        if (labelGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new labelGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LabelGroupDTO result = labelGroupService.save(labelGroupDTO);
        return ResponseEntity.created(new URI("/api/label-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /label-groups} : Updates an existing labelGroup.
     *
     * @param labelGroupDTO the labelGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated labelGroupDTO,
     * or with status {@code 400 (Bad Request)} if the labelGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the labelGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/label-groups")
    public ResponseEntity<LabelGroupDTO> updateLabelGroup(@Valid @RequestBody LabelGroupDTO labelGroupDTO) throws URISyntaxException {
        log.debug("REST request to update LabelGroup : {}", labelGroupDTO);
        if (labelGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LabelGroupDTO result = labelGroupService.save(labelGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, labelGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /label-groups} : get all the labelGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of labelGroups in body.
     */
    @GetMapping("/label-groups")
    public ResponseEntity<List<LabelGroupDTO>> getAllLabelGroups(Pageable pageable) {
        log.debug("REST request to get a page of LabelGroups");
        Page<LabelGroupDTO> page = labelGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /label-groups/:id} : get the "id" labelGroup.
     *
     * @param id the id of the labelGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the labelGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/label-groups/{id}")
    public ResponseEntity<LabelGroupDTO> getLabelGroup(@PathVariable Long id) {
        log.debug("REST request to get LabelGroup : {}", id);
        Optional<LabelGroupDTO> labelGroupDTO = labelGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(labelGroupDTO);
    }

    /**
     * {@code DELETE  /label-groups/:id} : delete the "id" labelGroup.
     *
     * @param id the id of the labelGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/label-groups/{id}")
    public ResponseEntity<Void> deleteLabelGroup(@PathVariable Long id) {
        log.debug("REST request to delete LabelGroup : {}", id);
        labelGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
