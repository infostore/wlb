import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectActivity } from 'app/shared/model/project-activity.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProjectActivityService } from './project-activity.service';
import { ProjectActivityDeleteDialogComponent } from './project-activity-delete-dialog.component';

@Component({
  selector: 'jhi-project-activity',
  templateUrl: './project-activity.component.html'
})
export class ProjectActivityComponent implements OnInit, OnDestroy {
  projectActivities?: IProjectActivity[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected projectActivityService: ProjectActivityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.projectActivityService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProjectActivity[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInProjectActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProjectActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProjectActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('projectActivityListModification', () => this.loadPage());
  }

  delete(projectActivity: IProjectActivity): void {
    const modalRef = this.modalService.open(ProjectActivityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.projectActivity = projectActivity;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IProjectActivity[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/project-activity'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.projectActivities = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
