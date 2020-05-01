import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILabelGroup } from 'app/shared/model/label-group.model';

type EntityResponseType = HttpResponse<ILabelGroup>;
type EntityArrayResponseType = HttpResponse<ILabelGroup[]>;

@Injectable({ providedIn: 'root' })
export class LabelGroupService {
  public resourceUrl = SERVER_API_URL + 'api/label-groups';

  constructor(protected http: HttpClient) {}

  create(labelGroup: ILabelGroup): Observable<EntityResponseType> {
    return this.http.post<ILabelGroup>(this.resourceUrl, labelGroup, { observe: 'response' });
  }

  update(labelGroup: ILabelGroup): Observable<EntityResponseType> {
    return this.http.put<ILabelGroup>(this.resourceUrl, labelGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILabelGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILabelGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
