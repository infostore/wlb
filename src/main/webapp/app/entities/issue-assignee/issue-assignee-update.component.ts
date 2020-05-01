import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIssueAssignee, IssueAssignee } from 'app/shared/model/issue-assignee.model';
import { IssueAssigneeService } from './issue-assignee.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIssue } from 'app/shared/model/issue.model';
import { IssueService } from 'app/entities/issue/issue.service';

type SelectableEntity = IUser | IIssue;

@Component({
  selector: 'jhi-issue-assignee-update',
  templateUrl: './issue-assignee-update.component.html'
})
export class IssueAssigneeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  issues: IIssue[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [],
    issueId: []
  });

  constructor(
    protected issueAssigneeService: IssueAssigneeService,
    protected userService: UserService,
    protected issueService: IssueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAssignee }) => {
      this.updateForm(issueAssignee);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.issueService.query().subscribe((res: HttpResponse<IIssue[]>) => (this.issues = res.body || []));
    });
  }

  updateForm(issueAssignee: IIssueAssignee): void {
    this.editForm.patchValue({
      id: issueAssignee.id,
      userId: issueAssignee.userId,
      issueId: issueAssignee.issueId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueAssignee = this.createFromForm();
    if (issueAssignee.id !== undefined) {
      this.subscribeToSaveResponse(this.issueAssigneeService.update(issueAssignee));
    } else {
      this.subscribeToSaveResponse(this.issueAssigneeService.create(issueAssignee));
    }
  }

  private createFromForm(): IIssueAssignee {
    return {
      ...new IssueAssignee(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      issueId: this.editForm.get(['issueId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueAssignee>>): void {
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
