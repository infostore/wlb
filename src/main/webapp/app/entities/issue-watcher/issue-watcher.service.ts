import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssueWatcher } from 'app/shared/model/issue-watcher.model';

type EntityResponseType = HttpResponse<IIssueWatcher>;
type EntityArrayResponseType = HttpResponse<IIssueWatcher[]>;

@Injectable({ providedIn: 'root' })
export class IssueWatcherService {
  public resourceUrl = SERVER_API_URL + 'api/issue-watchers';

  constructor(protected http: HttpClient) {}

  create(issueWatcher: IIssueWatcher): Observable<EntityResponseType> {
    return this.http.post<IIssueWatcher>(this.resourceUrl, issueWatcher, { observe: 'response' });
  }

  update(issueWatcher: IIssueWatcher): Observable<EntityResponseType> {
    return this.http.put<IIssueWatcher>(this.resourceUrl, issueWatcher, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueWatcher>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueWatcher[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
