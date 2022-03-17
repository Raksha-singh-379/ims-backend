import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransferRecieved, getTransferRecievedIdentifier } from '../transfer-recieved.model';

export type EntityResponseType = HttpResponse<ITransferRecieved>;
export type EntityArrayResponseType = HttpResponse<ITransferRecieved[]>;

@Injectable({ providedIn: 'root' })
export class TransferRecievedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transfer-recieveds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transferRecieved: ITransferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferRecieved);
    return this.http
      .post<ITransferRecieved>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transferRecieved: ITransferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferRecieved);
    return this.http
      .put<ITransferRecieved>(`${this.resourceUrl}/${getTransferRecievedIdentifier(transferRecieved) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transferRecieved: ITransferRecieved): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferRecieved);
    return this.http
      .patch<ITransferRecieved>(`${this.resourceUrl}/${getTransferRecievedIdentifier(transferRecieved) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransferRecieved>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransferRecieved[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransferRecievedToCollectionIfMissing(
    transferRecievedCollection: ITransferRecieved[],
    ...transferRecievedsToCheck: (ITransferRecieved | null | undefined)[]
  ): ITransferRecieved[] {
    const transferRecieveds: ITransferRecieved[] = transferRecievedsToCheck.filter(isPresent);
    if (transferRecieveds.length > 0) {
      const transferRecievedCollectionIdentifiers = transferRecievedCollection.map(
        transferRecievedItem => getTransferRecievedIdentifier(transferRecievedItem)!
      );
      const transferRecievedsToAdd = transferRecieveds.filter(transferRecievedItem => {
        const transferRecievedIdentifier = getTransferRecievedIdentifier(transferRecievedItem);
        if (transferRecievedIdentifier == null || transferRecievedCollectionIdentifiers.includes(transferRecievedIdentifier)) {
          return false;
        }
        transferRecievedCollectionIdentifiers.push(transferRecievedIdentifier);
        return true;
      });
      return [...transferRecievedsToAdd, ...transferRecievedCollection];
    }
    return transferRecievedCollection;
  }

  protected convertDateFromClient(transferRecieved: ITransferRecieved): ITransferRecieved {
    return Object.assign({}, transferRecieved, {
      transferDate: transferRecieved.transferDate?.isValid() ? transferRecieved.transferDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transferDate = res.body.transferDate ? dayjs(res.body.transferDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transferRecieved: ITransferRecieved) => {
        transferRecieved.transferDate = transferRecieved.transferDate ? dayjs(transferRecieved.transferDate) : undefined;
      });
    }
    return res;
  }
}
