import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssueAssignee } from 'app/shared/model/issue-assignee.model';

type EntityResponseType = HttpResponse<IIssueAssignee>;
type EntityArrayResponseType = HttpResponse<IIssueAssignee[]>;

@Injectable({ providedIn: 'root' })
export class IssueAssigneeService {
  public resourceUrl = SERVER_API_URL + 'api/issue-assignees';

  constructor(protected http: HttpClient) {}

  create(issueAssignee: IIssueAssignee): Observable<EntityResponseType> {
    return this.http.post<IIssueAssignee>(this.resourceUrl, issueAssignee, { observe: 'response' });
  }

  update(issueAssignee: IIssueAssignee): Observable<EntityResponseType> {
    return this.http.put<IIssueAssignee>(this.resourceUrl, issueAssignee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueAssignee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueAssignee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
