import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssueActivity } from 'app/shared/model/issue-activity.model';

type EntityResponseType = HttpResponse<IIssueActivity>;
type EntityArrayResponseType = HttpResponse<IIssueActivity[]>;

@Injectable({ providedIn: 'root' })
export class IssueActivityService {
  public resourceUrl = SERVER_API_URL + 'api/issue-activities';

  constructor(protected http: HttpClient) {}

  create(issueActivity: IIssueActivity): Observable<EntityResponseType> {
    return this.http.post<IIssueActivity>(this.resourceUrl, issueActivity, { observe: 'response' });
  }

  update(issueActivity: IIssueActivity): Observable<EntityResponseType> {
    return this.http.put<IIssueActivity>(this.resourceUrl, issueActivity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueActivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
