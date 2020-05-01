import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjectActivity, ProjectActivity } from 'app/shared/model/project-activity.model';
import { ProjectActivityService } from './project-activity.service';
import { ProjectActivityComponent } from './project-activity.component';
import { ProjectActivityDetailComponent } from './project-activity-detail.component';
import { ProjectActivityUpdateComponent } from './project-activity-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectActivityResolve implements Resolve<IProjectActivity> {
  constructor(private service: ProjectActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projectActivity: HttpResponse<ProjectActivity>) => {
          if (projectActivity.body) {
            return of(projectActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectActivity());
  }
}

export const projectActivityRoute: Routes = [
  {
    path: '',
    component: ProjectActivityComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.projectActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectActivityDetailComponent,
    resolve: {
      projectActivity: ProjectActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectActivityUpdateComponent,
    resolve: {
      projectActivity: ProjectActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectActivityUpdateComponent,
    resolve: {
      projectActivity: ProjectActivityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
