import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIssueWatcher, IssueWatcher } from 'app/shared/model/issue-watcher.model';
import { IssueWatcherService } from './issue-watcher.service';
import { IssueWatcherComponent } from './issue-watcher.component';
import { IssueWatcherDetailComponent } from './issue-watcher-detail.component';
import { IssueWatcherUpdateComponent } from './issue-watcher-update.component';

@Injectable({ providedIn: 'root' })
export class IssueWatcherResolve implements Resolve<IIssueWatcher> {
  constructor(private service: IssueWatcherService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueWatcher> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((issueWatcher: HttpResponse<IssueWatcher>) => {
          if (issueWatcher.body) {
            return of(issueWatcher.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IssueWatcher());
  }
}

export const issueWatcherRoute: Routes = [
  {
    path: '',
    component: IssueWatcherComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.issueWatcher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssueWatcherDetailComponent,
    resolve: {
      issueWatcher: IssueWatcherResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueWatcher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssueWatcherUpdateComponent,
    resolve: {
      issueWatcher: IssueWatcherResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueWatcher.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssueWatcherUpdateComponent,
    resolve: {
      issueWatcher: IssueWatcherResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueWatcher.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
