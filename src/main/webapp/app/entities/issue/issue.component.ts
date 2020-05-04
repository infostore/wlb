import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssue } from 'app/shared/model/issue.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IssueService } from './issue.service';
import { IssueDeleteDialogComponent } from './issue-delete-dialog.component';

import { jqxButtonComponent } from 'jqwidgets-ng/jqxbuttons';
import { ProjectService } from 'app/entities/project/project.service';
import { IProject } from 'app/shared/model/project.model';
import { IMilestone } from 'app/shared/model/milestone.model';
import { MilestoneService } from 'app/entities/milestone/milestone.service';

@Component({
  selector: 'jhi-issue',
  templateUrl: './issue.component.html'
})
export class IssueComponent implements AfterViewInit, OnInit, OnDestroy {
  issues?: IIssue[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  @ViewChild('searchButton')
  searchButton!: jqxButtonComponent;

  @ViewChild('newButton')
  newButton!: jqxButtonComponent;

  projects: IProject[] | null = [];
  milestones: IMilestone[] | null = [];

  constructor(
    protected issueService: IssueService,
    protected projectService: ProjectService,
    protected milestoneService: MilestoneService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.issueService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIssue[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
      this.loadProjects();
    });
    this.registerChangeInIssues();
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIssue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInIssues(): void {
    this.eventSubscriber = this.eventManager.subscribe('issueListModification', () => this.loadPage());
  }

  delete(issue: IIssue): void {
    const modalRef = this.modalService.open(IssueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.issue = issue;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIssue[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/issue'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.issues = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  search(event: any): void {}

  projectOnSelect(event: any): void {
    this.milestoneService
      .query({
        projectId: event.args.item.value,
        page: 0,
        size: 100,
        sort: ['name,asc']
      })
      .subscribe(
        (res: HttpResponse<IMilestone[]>) => {
          this.milestones = res.body;
        },
        () => {
          // TODO 에러 메시지를 표시해야 한다.
        }
      );
  }

  private loadProjects(): void {
    this.projectService
      .query({
        page: 0,
        size: 100,
        sort: ['name,asc']
      })
      .subscribe(
        (res: HttpResponse<IProject[]>) => {
          this.projects = res.body;
        },
        () => {
          // TODO 에러 메시지를 표시해야 한다.
        }
      );
  }

  milestoneOnSelect(event: any): void {}
}
