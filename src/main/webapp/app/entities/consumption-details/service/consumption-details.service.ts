import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConsumptionDetails, getConsumptionDetailsIdentifier } from '../consumption-details.model';

export type EntityResponseType = HttpResponse<IConsumptionDetails>;
export type EntityArrayResponseType = HttpResponse<IConsumptionDetails[]>;

@Injectable({ providedIn: 'root' })
export class ConsumptionDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/consumption-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(consumptionDetails: IConsumptionDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consumptionDetails);
    return this.http
      .post<IConsumptionDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(consumptionDetails: IConsumptionDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consumptionDetails);
    return this.http
      .put<IConsumptionDetails>(`${this.resourceUrl}/${getConsumptionDetailsIdentifier(consumptionDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(consumptionDetails: IConsumptionDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consumptionDetails);
    return this.http
      .patch<IConsumptionDetails>(`${this.resourceUrl}/${getConsumptionDetailsIdentifier(consumptionDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConsumptionDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConsumptionDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConsumptionDetailsToCollectionIfMissing(
    consumptionDetailsCollection: IConsumptionDetails[],
    ...consumptionDetailsToCheck: (IConsumptionDetails | null | undefined)[]
  ): IConsumptionDetails[] {
    const consumptionDetails: IConsumptionDetails[] = consumptionDetailsToCheck.filter(isPresent);
    if (consumptionDetails.length > 0) {
      const consumptionDetailsCollectionIdentifiers = consumptionDetailsCollection.map(
        consumptionDetailsItem => getConsumptionDetailsIdentifier(consumptionDetailsItem)!
      );
      const consumptionDetailsToAdd = consumptionDetails.filter(consumptionDetailsItem => {
        const consumptionDetailsIdentifier = getConsumptionDetailsIdentifier(consumptionDetailsItem);
        if (consumptionDetailsIdentifier == null || consumptionDetailsCollectionIdentifiers.includes(consumptionDetailsIdentifier)) {
          return false;
        }
        consumptionDetailsCollectionIdentifiers.push(consumptionDetailsIdentifier);
        return true;
      });
      return [...consumptionDetailsToAdd, ...consumptionDetailsCollection];
    }
    return consumptionDetailsCollection;
  }

  protected convertDateFromClient(consumptionDetails: IConsumptionDetails): IConsumptionDetails {
    return Object.assign({}, consumptionDetails, {
      comsumptionDate: consumptionDetails.comsumptionDate?.isValid() ? consumptionDetails.comsumptionDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.comsumptionDate = res.body.comsumptionDate ? dayjs(res.body.comsumptionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((consumptionDetails: IConsumptionDetails) => {
        consumptionDetails.comsumptionDate = consumptionDetails.comsumptionDate ? dayjs(consumptionDetails.comsumptionDate) : undefined;
      });
    }
    return res;
  }
}
