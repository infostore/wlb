import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssueLabel } from 'app/shared/model/issue-label.model';

type EntityResponseType = HttpResponse<IIssueLabel>;
type EntityArrayResponseType = HttpResponse<IIssueLabel[]>;

@Injectable({ providedIn: 'root' })
export class IssueLabelService {
  public resourceUrl = SERVER_API_URL + 'api/issue-labels';

  constructor(protected http: HttpClient) {}

  create(issueLabel: IIssueLabel): Observable<EntityResponseType> {
    return this.http.post<IIssueLabel>(this.resourceUrl, issueLabel, { observe: 'response' });
  }

  update(issueLabel: IIssueLabel): Observable<EntityResponseType> {
    return this.http.put<IIssueLabel>(this.resourceUrl, issueLabel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueLabel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueLabel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
