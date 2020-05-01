import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjectMember, ProjectMember } from 'app/shared/model/project-member.model';
import { ProjectMemberService } from './project-member.service';
import { ProjectMemberComponent } from './project-member.component';
import { ProjectMemberDetailComponent } from './project-member-detail.component';
import { ProjectMemberUpdateComponent } from './project-member-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectMemberResolve implements Resolve<IProjectMember> {
  constructor(private service: ProjectMemberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projectMember: HttpResponse<ProjectMember>) => {
          if (projectMember.body) {
            return of(projectMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectMember());
  }
}

export const projectMemberRoute: Routes = [
  {
    path: '',
    component: ProjectMemberComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.projectMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectMemberDetailComponent,
    resolve: {
      projectMember: ProjectMemberResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectMemberUpdateComponent,
    resolve: {
      projectMember: ProjectMemberResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectMemberUpdateComponent,
    resolve: {
      projectMember: ProjectMemberResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.projectMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
