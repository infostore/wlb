import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIssueLabel, IssueLabel } from 'app/shared/model/issue-label.model';
import { IssueLabelService } from './issue-label.service';
import { IssueLabelComponent } from './issue-label.component';
import { IssueLabelDetailComponent } from './issue-label-detail.component';
import { IssueLabelUpdateComponent } from './issue-label-update.component';

@Injectable({ providedIn: 'root' })
export class IssueLabelResolve implements Resolve<IIssueLabel> {
  constructor(private service: IssueLabelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueLabel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((issueLabel: HttpResponse<IssueLabel>) => {
          if (issueLabel.body) {
            return of(issueLabel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IssueLabel());
  }
}

export const issueLabelRoute: Routes = [
  {
    path: '',
    component: IssueLabelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.issueLabel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssueLabelDetailComponent,
    resolve: {
      issueLabel: IssueLabelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueLabel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssueLabelUpdateComponent,
    resolve: {
      issueLabel: IssueLabelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueLabel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssueLabelUpdateComponent,
    resolve: {
      issueLabel: IssueLabelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueLabel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
