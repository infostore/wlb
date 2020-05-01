import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueAssignee } from 'app/shared/model/issue-assignee.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IssueAssigneeService } from './issue-assignee.service';
import { IssueAssigneeDeleteDialogComponent } from './issue-assignee-delete-dialog.component';

@Component({
  selector: 'jhi-issue-assignee',
  templateUrl: './issue-assignee.component.html'
})
export class IssueAssigneeComponent implements OnInit, OnDestroy {
  issueAssignees?: IIssueAssignee[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected issueAssigneeService: IssueAssigneeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.issueAssigneeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIssueAssignee[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInIssueAssignees();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIssueAssignee): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIssueAssignees(): void {
    this.eventSubscriber = this.eventManager.subscribe('issueAssigneeListModification', () => this.loadPage());
  }

  delete(issueAssignee: IIssueAssignee): void {
    const modalRef = this.modalService.open(IssueAssigneeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issueAssignee = issueAssignee;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIssueAssignee[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/issue-assignee'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.issueAssignees = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
