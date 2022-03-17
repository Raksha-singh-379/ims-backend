import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserAccess, getUserAccessIdentifier } from '../user-access.model';

export type EntityResponseType = HttpResponse<IUserAccess>;
export type EntityArrayResponseType = HttpResponse<IUserAccess[]>;

@Injectable({ providedIn: 'root' })
export class UserAccessService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-accesses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userAccess: IUserAccess): Observable<EntityResponseType> {
    return this.http.post<IUserAccess>(this.resourceUrl, userAccess, { observe: 'response' });
  }

  update(userAccess: IUserAccess): Observable<EntityResponseType> {
    return this.http.put<IUserAccess>(`${this.resourceUrl}/${getUserAccessIdentifier(userAccess) as number}`, userAccess, {
      observe: 'response',
    });
  }

  partialUpdate(userAccess: IUserAccess): Observable<EntityResponseType> {
    return this.http.patch<IUserAccess>(`${this.resourceUrl}/${getUserAccessIdentifier(userAccess) as number}`, userAccess, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserAccess[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserAccessToCollectionIfMissing(
    userAccessCollection: IUserAccess[],
    ...userAccessesToCheck: (IUserAccess | null | undefined)[]
  ): IUserAccess[] {
    const userAccesses: IUserAccess[] = userAccessesToCheck.filter(isPresent);
    if (userAccesses.length > 0) {
      const userAccessCollectionIdentifiers = userAccessCollection.map(userAccessItem => getUserAccessIdentifier(userAccessItem)!);
      const userAccessesToAdd = userAccesses.filter(userAccessItem => {
        const userAccessIdentifier = getUserAccessIdentifier(userAccessItem);
        if (userAccessIdentifier == null || userAccessCollectionIdentifiers.includes(userAccessIdentifier)) {
          return false;
        }
        userAccessCollectionIdentifiers.push(userAccessIdentifier);
        return true;
      });
      return [...userAccessesToAdd, ...userAccessCollection];
    }
    return userAccessCollection;
  }
}
