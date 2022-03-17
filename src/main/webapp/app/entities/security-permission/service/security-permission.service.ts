import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityPermission, getSecurityPermissionIdentifier } from '../security-permission.model';

export type EntityResponseType = HttpResponse<ISecurityPermission>;
export type EntityArrayResponseType = HttpResponse<ISecurityPermission[]>;

@Injectable({ providedIn: 'root' })
export class SecurityPermissionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-permissions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    return this.http.post<ISecurityPermission>(this.resourceUrl, securityPermission, { observe: 'response' });
  }

  update(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    return this.http.put<ISecurityPermission>(
      `${this.resourceUrl}/${getSecurityPermissionIdentifier(securityPermission) as number}`,
      securityPermission,
      { observe: 'response' }
    );
  }

  partialUpdate(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    return this.http.patch<ISecurityPermission>(
      `${this.resourceUrl}/${getSecurityPermissionIdentifier(securityPermission) as number}`,
      securityPermission,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISecurityPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISecurityPermission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityPermissionToCollectionIfMissing(
    securityPermissionCollection: ISecurityPermission[],
    ...securityPermissionsToCheck: (ISecurityPermission | null | undefined)[]
  ): ISecurityPermission[] {
    const securityPermissions: ISecurityPermission[] = securityPermissionsToCheck.filter(isPresent);
    if (securityPermissions.length > 0) {
      const securityPermissionCollectionIdentifiers = securityPermissionCollection.map(
        securityPermissionItem => getSecurityPermissionIdentifier(securityPermissionItem)!
      );
      const securityPermissionsToAdd = securityPermissions.filter(securityPermissionItem => {
        const securityPermissionIdentifier = getSecurityPermissionIdentifier(securityPermissionItem);
        if (securityPermissionIdentifier == null || securityPermissionCollectionIdentifiers.includes(securityPermissionIdentifier)) {
          return false;
        }
        securityPermissionCollectionIdentifiers.push(securityPermissionIdentifier);
        return true;
      });
      return [...securityPermissionsToAdd, ...securityPermissionCollection];
    }
    return securityPermissionCollection;
  }
}
