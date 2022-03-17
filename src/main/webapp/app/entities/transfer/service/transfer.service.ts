import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransfer, getTransferIdentifier } from '../transfer.model';

export type EntityResponseType = HttpResponse<ITransfer>;
export type EntityArrayResponseType = HttpResponse<ITransfer[]>;

@Injectable({ providedIn: 'root' })
export class TransferService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transfers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transfer: ITransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transfer);
    return this.http
      .post<ITransfer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transfer: ITransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transfer);
    return this.http
      .put<ITransfer>(`${this.resourceUrl}/${getTransferIdentifier(transfer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transfer: ITransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transfer);
    return this.http
      .patch<ITransfer>(`${this.resourceUrl}/${getTransferIdentifier(transfer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransfer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransferToCollectionIfMissing(transferCollection: ITransfer[], ...transfersToCheck: (ITransfer | null | undefined)[]): ITransfer[] {
    const transfers: ITransfer[] = transfersToCheck.filter(isPresent);
    if (transfers.length > 0) {
      const transferCollectionIdentifiers = transferCollection.map(transferItem => getTransferIdentifier(transferItem)!);
      const transfersToAdd = transfers.filter(transferItem => {
        const transferIdentifier = getTransferIdentifier(transferItem);
        if (transferIdentifier == null || transferCollectionIdentifiers.includes(transferIdentifier)) {
          return false;
        }
        transferCollectionIdentifiers.push(transferIdentifier);
        return true;
      });
      return [...transfersToAdd, ...transferCollection];
    }
    return transferCollection;
  }

  protected convertDateFromClient(transfer: ITransfer): ITransfer {
    return Object.assign({}, transfer, {
      tranferDate: transfer.tranferDate?.isValid() ? transfer.tranferDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tranferDate = res.body.tranferDate ? dayjs(res.body.tranferDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transfer: ITransfer) => {
        transfer.tranferDate = transfer.tranferDate ? dayjs(transfer.tranferDate) : undefined;
      });
    }
    return res;
  }
}
