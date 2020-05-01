import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueAssignee } from 'app/shared/model/issue-assignee.model';

@Component({
  selector: 'jhi-issue-assignee-detail',
  templateUrl: './issue-assignee-detail.component.html'
})
export class IssueAssigneeDetailComponent implements OnInit {
  issueAssignee: IIssueAssignee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAssignee }) => (this.issueAssignee = issueAssignee));
  }

  previousState(): void {
    window.history.back();
  }
}
