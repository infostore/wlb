import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILabelGroup, LabelGroup } from 'app/shared/model/label-group.model';
import { LabelGroupService } from './label-group.service';
import { LabelGroupComponent } from './label-group.component';
import { LabelGroupDetailComponent } from './label-group-detail.component';
import { LabelGroupUpdateComponent } from './label-group-update.component';

@Injectable({ providedIn: 'root' })
export class LabelGroupResolve implements Resolve<ILabelGroup> {
  constructor(private service: LabelGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILabelGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((labelGroup: HttpResponse<LabelGroup>) => {
          if (labelGroup.body) {
            return of(labelGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LabelGroup());
  }
}

export const labelGroupRoute: Routes = [
  {
    path: '',
    component: LabelGroupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.labelGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LabelGroupDetailComponent,
    resolve: {
      labelGroup: LabelGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.labelGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LabelGroupUpdateComponent,
    resolve: {
      labelGroup: LabelGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.labelGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LabelGroupUpdateComponent,
    resolve: {
      labelGroup: LabelGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.labelGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
