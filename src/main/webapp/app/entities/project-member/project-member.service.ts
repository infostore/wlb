import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProjectMember } from 'app/shared/model/project-member.model';

type EntityResponseType = HttpResponse<IProjectMember>;
type EntityArrayResponseType = HttpResponse<IProjectMember[]>;

@Injectable({ providedIn: 'root' })
export class ProjectMemberService {
  public resourceUrl = SERVER_API_URL + 'api/project-members';

  constructor(protected http: HttpClient) {}

  create(projectMember: IProjectMember): Observable<EntityResponseType> {
    return this.http.post<IProjectMember>(this.resourceUrl, projectMember, { observe: 'response' });
  }

  update(projectMember: IProjectMember): Observable<EntityResponseType> {
    return this.http.put<IProjectMember>(this.resourceUrl, projectMember, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectMember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
