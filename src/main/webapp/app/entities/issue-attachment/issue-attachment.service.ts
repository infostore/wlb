import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIssueAttachment } from 'app/shared/model/issue-attachment.model';

type EntityResponseType = HttpResponse<IIssueAttachment>;
type EntityArrayResponseType = HttpResponse<IIssueAttachment[]>;

@Injectable({ providedIn: 'root' })
export class IssueAttachmentService {
  public resourceUrl = SERVER_API_URL + 'api/issue-attachments';

  constructor(protected http: HttpClient) {}

  create(issueAttachment: IIssueAttachment): Observable<EntityResponseType> {
    return this.http.post<IIssueAttachment>(this.resourceUrl, issueAttachment, { observe: 'response' });
  }

  update(issueAttachment: IIssueAttachment): Observable<EntityResponseType> {
    return this.http.put<IIssueAttachment>(this.resourceUrl, issueAttachment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssueAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssueAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
