import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransferDetailsApprovals, getTransferDetailsApprovalsIdentifier } from '../transfer-details-approvals.model';

export type EntityResponseType = HttpResponse<ITransferDetailsApprovals>;
export type EntityArrayResponseType = HttpResponse<ITransferDetailsApprovals[]>;

@Injectable({ providedIn: 'root' })
export class TransferDetailsApprovalsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transfer-details-approvals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transferDetailsApprovals: ITransferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetailsApprovals);
    return this.http
      .post<ITransferDetailsApprovals>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transferDetailsApprovals: ITransferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetailsApprovals);
    return this.http
      .put<ITransferDetailsApprovals>(
        `${this.resourceUrl}/${getTransferDetailsApprovalsIdentifier(transferDetailsApprovals) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transferDetailsApprovals: ITransferDetailsApprovals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferDetailsApprovals);
    return this.http
      .patch<ITransferDetailsApprovals>(
        `${this.resourceUrl}/${getTransferDetailsApprovalsIdentifier(transferDetailsApprovals) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransferDetailsApprovals>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransferDetailsApprovals[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransferDetailsApprovalsToCollectionIfMissing(
    transferDetailsApprovalsCollection: ITransferDetailsApprovals[],
    ...transferDetailsApprovalsToCheck: (ITransferDetailsApprovals | null | undefined)[]
  ): ITransferDetailsApprovals[] {
    const transferDetailsApprovals: ITransferDetailsApprovals[] = transferDetailsApprovalsToCheck.filter(isPresent);
    if (transferDetailsApprovals.length > 0) {
      const transferDetailsApprovalsCollectionIdentifiers = transferDetailsApprovalsCollection.map(
        transferDetailsApprovalsItem => getTransferDetailsApprovalsIdentifier(transferDetailsApprovalsItem)!
      );
      const transferDetailsApprovalsToAdd = transferDetailsApprovals.filter(transferDetailsApprovalsItem => {
        const transferDetailsApprovalsIdentifier = getTransferDetailsApprovalsIdentifier(transferDetailsApprovalsItem);
        if (
          transferDetailsApprovalsIdentifier == null ||
          transferDetailsApprovalsCollectionIdentifiers.includes(transferDetailsApprovalsIdentifier)
        ) {
          return false;
        }
        transferDetailsApprovalsCollectionIdentifiers.push(transferDetailsApprovalsIdentifier);
        return true;
      });
      return [...transferDetailsApprovalsToAdd, ...transferDetailsApprovalsCollection];
    }
    return transferDetailsApprovalsCollection;
  }

  protected convertDateFromClient(transferDetailsApprovals: ITransferDetailsApprovals): ITransferDetailsApprovals {
    return Object.assign({}, transferDetailsApprovals, {
      approvalDate: transferDetailsApprovals.approvalDate?.isValid() ? transferDetailsApprovals.approvalDate.toJSON() : undefined,
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
      res.body.forEach((transferDetailsApprovals: ITransferDetailsApprovals) => {
        transferDetailsApprovals.approvalDate = transferDetailsApprovals.approvalDate
          ? dayjs(transferDetailsApprovals.approvalDate)
          : undefined;
      });
    }
    return res;
  }
}
