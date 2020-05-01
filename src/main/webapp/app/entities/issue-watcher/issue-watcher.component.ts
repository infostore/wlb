import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueWatcher } from 'app/shared/model/issue-watcher.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IssueWatcherService } from './issue-watcher.service';
import { IssueWatcherDeleteDialogComponent } from './issue-watcher-delete-dialog.component';

@Component({
  selector: 'jhi-issue-watcher',
  templateUrl: './issue-watcher.component.html'
})
export class IssueWatcherComponent implements OnInit, OnDestroy {
  issueWatchers?: IIssueWatcher[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected issueWatcherService: IssueWatcherService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.issueWatcherService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIssueWatcher[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInIssueWatchers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIssueWatcher): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIssueWatchers(): void {
    this.eventSubscriber = this.eventManager.subscribe('issueWatcherListModification', () => this.loadPage());
  }

  delete(issueWatcher: IIssueWatcher): void {
    const modalRef = this.modalService.open(IssueWatcherDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issueWatcher = issueWatcher;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIssueWatcher[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/issue-watcher'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.issueWatchers = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
