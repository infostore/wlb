import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIssueAttachment, IssueAttachment } from 'app/shared/model/issue-attachment.model';
import { IssueAttachmentService } from './issue-attachment.service';
import { IAttachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from 'app/entities/attachment/attachment.service';
import { IIssue } from 'app/shared/model/issue.model';
import { IssueService } from 'app/entities/issue/issue.service';

type SelectableEntity = IAttachment | IIssue;

@Component({
  selector: 'jhi-issue-attachment-update',
  templateUrl: './issue-attachment-update.component.html'
})
export class IssueAttachmentUpdateComponent implements OnInit {
  isSaving = false;
  attachments: IAttachment[] = [];
  issues: IIssue[] = [];

  editForm = this.fb.group({
    id: [],
    attachmentId: [],
    issueId: []
  });

  constructor(
    protected issueAttachmentService: IssueAttachmentService,
    protected attachmentService: AttachmentService,
    protected issueService: IssueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAttachment }) => {
      this.updateForm(issueAttachment);

      this.attachmentService.query().subscribe((res: HttpResponse<IAttachment[]>) => (this.attachments = res.body || []));

      this.issueService.query().subscribe((res: HttpResponse<IIssue[]>) => (this.issues = res.body || []));
    });
  }

  updateForm(issueAttachment: IIssueAttachment): void {
    this.editForm.patchValue({
      id: issueAttachment.id,
      attachmentId: issueAttachment.attachmentId,
      issueId: issueAttachment.issueId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueAttachment = this.createFromForm();
    if (issueAttachment.id !== undefined) {
      this.subscribeToSaveResponse(this.issueAttachmentService.update(issueAttachment));
    } else {
      this.subscribeToSaveResponse(this.issueAttachmentService.create(issueAttachment));
    }
  }

  private createFromForm(): IIssueAttachment {
    return {
      ...new IssueAttachment(),
      id: this.editForm.get(['id'])!.value,
      attachmentId: this.editForm.get(['attachmentId'])!.value,
      issueId: this.editForm.get(['issueId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueAttachment>>): void {
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
