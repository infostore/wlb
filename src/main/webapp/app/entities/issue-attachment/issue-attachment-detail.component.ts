import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueAttachment } from 'app/shared/model/issue-attachment.model';

@Component({
  selector: 'jhi-issue-attachment-detail',
  templateUrl: './issue-attachment-detail.component.html'
})
export class IssueAttachmentDetailComponent implements OnInit {
  issueAttachment: IIssueAttachment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAttachment }) => (this.issueAttachment = issueAttachment));
  }

  previousState(): void {
    window.history.back();
  }
}
