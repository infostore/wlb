import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMilestone } from 'app/shared/model/milestone.model';

type EntityResponseType = HttpResponse<IMilestone>;
type EntityArrayResponseType = HttpResponse<IMilestone[]>;

@Injectable({ providedIn: 'root' })
export class MilestoneService {
  public resourceUrl = SERVER_API_URL + 'api/milestones';

  constructor(protected http: HttpClient) {}

  create(milestone: IMilestone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(milestone);
    return this.http
      .post<IMilestone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(milestone: IMilestone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(milestone);
    return this.http
      .put<IMilestone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMilestone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMilestone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(milestone: IMilestone): IMilestone {
    const copy: IMilestone = Object.assign({}, milestone, {
      dueDate: milestone.dueDate && milestone.dueDate.isValid() ? milestone.dueDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dueDate = res.body.dueDate ? moment(res.body.dueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((milestone: IMilestone) => {
        milestone.dueDate = milestone.dueDate ? moment(milestone.dueDate) : undefined;
      });
    }
    return res;
  }
}
