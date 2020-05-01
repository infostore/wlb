import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIssueAttachment, IssueAttachment } from 'app/shared/model/issue-attachment.model';
import { IssueAttachmentService } from './issue-attachment.service';
import { IssueAttachmentComponent } from './issue-attachment.component';
import { IssueAttachmentDetailComponent } from './issue-attachment-detail.component';
import { IssueAttachmentUpdateComponent } from './issue-attachment-update.component';

@Injectable({ providedIn: 'root' })
export class IssueAttachmentResolve implements Resolve<IIssueAttachment> {
  constructor(private service: IssueAttachmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssueAttachment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((issueAttachment: HttpResponse<IssueAttachment>) => {
          if (issueAttachment.body) {
            return of(issueAttachment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IssueAttachment());
  }
}

export const issueAttachmentRoute: Routes = [
  {
    path: '',
    component: IssueAttachmentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'wlbApp.issueAttachment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IssueAttachmentDetailComponent,
    resolve: {
      issueAttachment: IssueAttachmentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAttachment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IssueAttachmentUpdateComponent,
    resolve: {
      issueAttachment: IssueAttachmentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAttachment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IssueAttachmentUpdateComponent,
    resolve: {
      issueAttachment: IssueAttachmentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wlbApp.issueAttachment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
