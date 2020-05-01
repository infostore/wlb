import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProjectActivity } from 'app/shared/model/project-activity.model';

type EntityResponseType = HttpResponse<IProjectActivity>;
type EntityArrayResponseType = HttpResponse<IProjectActivity[]>;

@Injectable({ providedIn: 'root' })
export class ProjectActivityService {
  public resourceUrl = SERVER_API_URL + 'api/project-activities';

  constructor(protected http: HttpClient) {}

  create(projectActivity: IProjectActivity): Observable<EntityResponseType> {
    return this.http.post<IProjectActivity>(this.resourceUrl, projectActivity, { observe: 'response' });
  }

  update(projectActivity: IProjectActivity): Observable<EntityResponseType> {
    return this.http.put<IProjectActivity>(this.resourceUrl, projectActivity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectActivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
