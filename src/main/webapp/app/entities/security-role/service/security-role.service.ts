import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityRole, getSecurityRoleIdentifier } from '../security-role.model';

export type EntityResponseType = HttpResponse<ISecurityRole>;
export type EntityArrayResponseType = HttpResponse<ISecurityRole[]>;

@Injectable({ providedIn: 'root' })
export class SecurityRoleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-roles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityRole: ISecurityRole): Observable<EntityResponseType> {
    return this.http.post<ISecurityRole>(this.resourceUrl, securityRole, { observe: 'response' });
  }

  update(securityRole: ISecurityRole): Observable<EntityResponseType> {
    return this.http.put<ISecurityRole>(`${this.resourceUrl}/${getSecurityRoleIdentifier(securityRole) as number}`, securityRole, {
      observe: 'response',
    });
  }

  partialUpdate(securityRole: ISecurityRole): Observable<EntityResponseType> {
    return this.http.patch<ISecurityRole>(`${this.resourceUrl}/${getSecurityRoleIdentifier(securityRole) as number}`, securityRole, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISecurityRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISecurityRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityRoleToCollectionIfMissing(
    securityRoleCollection: ISecurityRole[],
    ...securityRolesToCheck: (ISecurityRole | null | undefined)[]
  ): ISecurityRole[] {
    const securityRoles: ISecurityRole[] = securityRolesToCheck.filter(isPresent);
    if (securityRoles.length > 0) {
      const securityRoleCollectionIdentifiers = securityRoleCollection.map(
        securityRoleItem => getSecurityRoleIdentifier(securityRoleItem)!
      );
      const securityRolesToAdd = securityRoles.filter(securityRoleItem => {
        const securityRoleIdentifier = getSecurityRoleIdentifier(securityRoleItem);
        if (securityRoleIdentifier == null || securityRoleCollectionIdentifiers.includes(securityRoleIdentifier)) {
          return false;
        }
        securityRoleCollectionIdentifiers.push(securityRoleIdentifier);
        return true;
      });
      return [...securityRolesToAdd, ...securityRoleCollection];
    }
    return securityRoleCollection;
  }
}
