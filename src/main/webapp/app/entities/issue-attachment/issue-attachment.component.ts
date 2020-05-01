import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueAttachment } from 'app/shared/model/issue-attachment.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IssueAttachmentService } from './issue-attachment.service';
import { IssueAttachmentDeleteDialogComponent } from './issue-attachment-delete-dialog.component';

@Component({
  selector: 'jhi-issue-attachment',
  templateUrl: './issue-attachment.component.html'
})
export class IssueAttachmentComponent implements OnInit, OnDestroy {
  issueAttachments?: IIssueAttachment[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected issueAttachmentService: IssueAttachmentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.issueAttachmentService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIssueAttachment[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInIssueAttachments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIssueAttachment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIssueAttachments(): void {
    this.eventSubscriber = this.eventManager.subscribe('issueAttachmentListModification', () => this.loadPage());
  }

  delete(issueAttachment: IIssueAttachment): void {
    const modalRef = this.modalService.open(IssueAttachmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issueAttachment = issueAttachment;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIssueAttachment[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/issue-attachment'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.issueAttachments = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
