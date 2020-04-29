import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IIssue, Issue } from 'app/shared/model/issue.model';
import { IssueService } from './issue.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';
import { IMilestone } from 'app/shared/model/milestone.model';
import { MilestoneService } from 'app/entities/milestone/milestone.service';

type SelectableEntity = IProject | IMilestone;

@Component({
  selector: 'jhi-issue-update',
  templateUrl: './issue-update.component.html'
})
export class IssueUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProject[] = [];
  milestones: IMilestone[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: [],
    issueType: [null, [Validators.required]],
    issueStatus: [null, [Validators.required]],
    priority: [null, [Validators.required]],
    resolution: [],
    dueDate: [],
    projectId: [],
    milestoneId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected issueService: IssueService,
    protected projectService: ProjectService,
    protected milestoneService: MilestoneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issue }) => {
      if (!issue.id) {
        const today = moment().startOf('day');
        issue.dueDate = today;
      }

      this.updateForm(issue);

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));

      this.milestoneService.query().subscribe((res: HttpResponse<IMilestone[]>) => (this.milestones = res.body || []));
    });
  }

  updateForm(issue: IIssue): void {
    this.editForm.patchValue({
      id: issue.id,
      name: issue.name,
      description: issue.description,
      issueType: issue.issueType,
      issueStatus: issue.issueStatus,
      priority: issue.priority,
      resolution: issue.resolution,
      dueDate: issue.dueDate ? issue.dueDate.format(DATE_TIME_FORMAT) : null,
      projectId: issue.projectId,
      milestoneId: issue.milestoneId
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
    const issue = this.createFromForm();
    if (issue.id !== undefined) {
      this.subscribeToSaveResponse(this.issueService.update(issue));
    } else {
      this.subscribeToSaveResponse(this.issueService.create(issue));
    }
  }

  private createFromForm(): IIssue {
    return {
      ...new Issue(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      issueType: this.editForm.get(['issueType'])!.value,
      issueStatus: this.editForm.get(['issueStatus'])!.value,
      priority: this.editForm.get(['priority'])!.value,
      resolution: this.editForm.get(['resolution'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value ? moment(this.editForm.get(['dueDate'])!.value, DATE_TIME_FORMAT) : undefined,
      projectId: this.editForm.get(['projectId'])!.value,
      milestoneId: this.editForm.get(['milestoneId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssue>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
