import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueLabel } from 'app/shared/model/issue-label.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IssueLabelService } from './issue-label.service';
import { IssueLabelDeleteDialogComponent } from './issue-label-delete-dialog.component';

@Component({
  selector: 'jhi-issue-label',
  templateUrl: './issue-label.component.html'
})
export class IssueLabelComponent implements OnInit, OnDestroy {
  issueLabels?: IIssueLabel[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected issueLabelService: IssueLabelService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.issueLabelService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIssueLabel[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInIssueLabels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIssueLabel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIssueLabels(): void {
    this.eventSubscriber = this.eventManager.subscribe('issueLabelListModification', () => this.loadPage());
  }

  delete(issueLabel: IIssueLabel): void {
    const modalRef = this.modalService.open(IssueLabelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issueLabel = issueLabel;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIssueLabel[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/issue-label'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.issueLabels = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
