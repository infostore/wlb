import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIssueActivity, IssueActivity } from 'app/shared/model/issue-activity.model';
import { IssueActivityService } from './issue-activity.service';
import { IssueActivityComponent } from './issue-activity.component';
import { IssueActivityDetailComponent } from './issue-activity-detail.component';
import { IssueActivityUpdateComponent } from './issue-activity-update.component';

@Injectable({ providedIn: 'root' })
export class IssueActivityResolve implements Resolve<IIssueActivity> {
  constructor(private service: IssueActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((issueActivity: HttpResponse<IssueActivity>) => {
          if (issueActivity.body) {
            return of(issueActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IssueActivity());
  }
}

export const issueActivityRoute: Routes = [
  {
    path: '',
    component: IssueActivityComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.issueActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssueActivityDetailComponent,
    resolve: {
      issueActivity: IssueActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssueActivityUpdateComponent,
    resolve: {
      issueActivity: IssueActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssueActivityUpdateComponent,
    resolve: {
      issueActivity: IssueActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
