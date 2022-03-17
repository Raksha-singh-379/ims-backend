import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransferDetails, getTransferDetailsIdentifier } from '../transfer-details.model';

export type EntityResponseType = HttpResponse<ITransferDetails>;
export type EntityArrayResponseType = HttpResponse<ITransferDetails[]>;

@Injectable({ providedIn: 'root' })
export class TransferDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transfer-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transferDetails: ITransferDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetails);
    return this.http
      .post<ITransferDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transferDetails: ITransferDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetails);
    return this.http
      .put<ITransferDetails>(`${this.resourceUrl}/${getTransferDetailsIdentifier(transferDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transferDetails: ITransferDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetails);
    return this.http
      .patch<ITransferDetails>(`${this.resourceUrl}/${getTransferDetailsIdentifier(transferDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransferDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransferDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransferDetailsToCollectionIfMissing(
    transferDetailsCollection: ITransferDetails[],
    ...transferDetailsToCheck: (ITransferDetails | null | undefined)[]
  ): ITransferDetails[] {
    const transferDetails: ITransferDetails[] = transferDetailsToCheck.filter(isPresent);
    if (transferDetails.length > 0) {
      const transferDetailsCollectionIdentifiers = transferDetailsCollection.map(
        transferDetailsItem => getTransferDetailsIdentifier(transferDetailsItem)!
      );
      const transferDetailsToAdd = transferDetails.filter(transferDetailsItem => {
        const transferDetailsIdentifier = getTransferDetailsIdentifier(transferDetailsItem);
        if (transferDetailsIdentifier == null || transferDetailsCollectionIdentifiers.includes(transferDetailsIdentifier)) {
          return false;
        }
        transferDetailsCollectionIdentifiers.push(transferDetailsIdentifier);
        return true;
      });
      return [...transferDetailsToAdd, ...transferDetailsCollection];
    }
    return transferDetailsCollection;
  }

  protected convertDateFromClient(transferDetails: ITransferDetails): ITransferDetails {
    return Object.assign({}, transferDetails, {
      approvalDate: transferDetails.approvalDate?.isValid() ? transferDetails.approvalDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.approvalDate = res.body.approvalDate ? dayjs(res.body.approvalDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transferDetails: ITransferDetails) => {
        transferDetails.approvalDate = transferDetails.approvalDate ? dayjs(transferDetails.approvalDate) : undefined;
      });
    }
    return res;
  }
}
