import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIssueAssignee, IssueAssignee } from 'app/shared/model/issue-assignee.model';
import { IssueAssigneeService } from './issue-assignee.service';
import { IssueAssigneeComponent } from './issue-assignee.component';
import { IssueAssigneeDetailComponent } from './issue-assignee-detail.component';
import { IssueAssigneeUpdateComponent } from './issue-assignee-update.component';

@Injectable({ providedIn: 'root' })
export class IssueAssigneeResolve implements Resolve<IIssueAssignee> {
  constructor(private service: IssueAssigneeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueAssignee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((issueAssignee: HttpResponse<IssueAssignee>) => {
          if (issueAssignee.body) {
            return of(issueAssignee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IssueAssignee());
  }
}

export const issueAssigneeRoute: Routes = [
  {
    path: '',
    component: IssueAssigneeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.issueAssignee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssueAssigneeDetailComponent,
    resolve: {
      issueAssignee: IssueAssigneeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAssignee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssueAssigneeUpdateComponent,
    resolve: {
      issueAssignee: IssueAssigneeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAssignee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssueAssigneeUpdateComponent,
    resolve: {
      issueAssignee: IssueAssigneeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAssignee.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
