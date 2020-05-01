import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMilestone, Milestone } from 'app/shared/model/milestone.model';
import { MilestoneService } from './milestone.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  selector: 'jhi-milestone-update',
  templateUrl: './milestone-update.component.html'
})
export class MilestoneUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [],
    milestoneStatus: [],
    dueDate: [],
    projectId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected milestoneService: MilestoneService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ milestone }) => {
      if (!milestone.id) {
        const today = moment().startOf('day');
        milestone.dueDate = today;
      }

      this.updateForm(milestone);

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(milestone: IMilestone): void {
    this.editForm.patchValue({
      id: milestone.id,
      name: milestone.name,
      description: milestone.description,
      milestoneStatus: milestone.milestoneStatus,
      dueDate: milestone.dueDate ? milestone.dueDate.format(DATE_TIME_FORMAT) : null,
      projectId: milestone.projectId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('wlbApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const milestone = this.createFromForm();
    if (milestone.id !== undefined) {
      this.subscribeToSaveResponse(this.milestoneService.update(milestone));
    } else {
      this.subscribeToSaveResponse(this.milestoneService.create(milestone));
    }
  }

  private createFromForm(): IMilestone {
    return {
      ...new Milestone(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      milestoneStatus: this.editForm.get(['milestoneStatus'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value ? moment(this.editForm.get(['dueDate'])!.value, DATE_TIME_FORMAT) : undefined,
      projectId: this.editForm.get(['projectId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMilestone>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProject): any {
    return item.id;
  }
}
