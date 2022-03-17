import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWareHouse, getWareHouseIdentifier } from '../ware-house.model';

export type EntityResponseType = HttpResponse<IWareHouse>;
export type EntityArrayResponseType = HttpResponse<IWareHouse[]>;

@Injectable({ providedIn: 'root' })
export class WareHouseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ware-houses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wareHouse: IWareHouse): Observable<EntityResponseType> {
    return this.http.post<IWareHouse>(this.resourceUrl, wareHouse, { observe: 'response' });
  }

  update(wareHouse: IWareHouse): Observable<EntityResponseType> {
    return this.http.put<IWareHouse>(`${this.resourceUrl}/${getWareHouseIdentifier(wareHouse) as number}`, wareHouse, {
      observe: 'response',
    });
  }

  partialUpdate(wareHouse: IWareHouse): Observable<EntityResponseType> {
    return this.http.patch<IWareHouse>(`${this.resourceUrl}/${getWareHouseIdentifier(wareHouse) as number}`, wareHouse, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWareHouse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWareHouse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWareHouseToCollectionIfMissing(
    wareHouseCollection: IWareHouse[],
    ...wareHousesToCheck: (IWareHouse | null | undefined)[]
  ): IWareHouse[] {
    const wareHouses: IWareHouse[] = wareHousesToCheck.filter(isPresent);
    if (wareHouses.length > 0) {
      const wareHouseCollectionIdentifiers = wareHouseCollection.map(wareHouseItem => getWareHouseIdentifier(wareHouseItem)!);
      const wareHousesToAdd = wareHouses.filter(wareHouseItem => {
        const wareHouseIdentifier = getWareHouseIdentifier(wareHouseItem);
        if (wareHouseIdentifier == null || wareHouseCollectionIdentifiers.includes(wareHouseIdentifier)) {
          return false;
        }
        wareHouseCollectionIdentifiers.push(wareHouseIdentifier);
        return true;
      });
      return [...wareHousesToAdd, ...wareHouseCollection];
    }
    return wareHouseCollection;
  }
}
