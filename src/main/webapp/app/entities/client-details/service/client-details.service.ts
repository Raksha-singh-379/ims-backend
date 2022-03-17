import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClientDetails, getClientDetailsIdentifier } from '../client-details.model';

export type EntityResponseType = HttpResponse<IClientDetails>;
export type EntityArrayResponseType = HttpResponse<IClientDetails[]>;

@Injectable({ providedIn: 'root' })
export class ClientDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/client-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(clientDetails: IClientDetails): Observable<EntityResponseType> {
    return this.http.post<IClientDetails>(this.resourceUrl, clientDetails, { observe: 'response' });
  }

  update(clientDetails: IClientDetails): Observable<EntityResponseType> {
    return this.http.put<IClientDetails>(`${this.resourceUrl}/${getClientDetailsIdentifier(clientDetails) as number}`, clientDetails, {
      observe: 'response',
    });
  }

  partialUpdate(clientDetails: IClientDetails): Observable<EntityResponseType> {
    return this.http.patch<IClientDetails>(`${this.resourceUrl}/${getClientDetailsIdentifier(clientDetails) as number}`, clientDetails, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addClientDetailsToCollectionIfMissing(
    clientDetailsCollection: IClientDetails[],
    ...clientDetailsToCheck: (IClientDetails | null | undefined)[]
  ): IClientDetails[] {
    const clientDetails: IClientDetails[] = clientDetailsToCheck.filter(isPresent);
    if (clientDetails.length > 0) {
      const clientDetailsCollectionIdentifiers = clientDetailsCollection.map(
        clientDetailsItem => getClientDetailsIdentifier(clientDetailsItem)!
      );
      const clientDetailsToAdd = clientDetails.filter(clientDetailsItem => {
        const clientDetailsIdentifier = getClientDetailsIdentifier(clientDetailsItem);
        if (clientDetailsIdentifier == null || clientDetailsCollectionIdentifiers.includes(clientDetailsIdentifier)) {
          return false;
        }
        clientDetailsCollectionIdentifiers.push(clientDetailsIdentifier);
        return true;
      });
      return [...clientDetailsToAdd, ...clientDetailsCollection];
    }
    return clientDetailsCollection;
  }
}
