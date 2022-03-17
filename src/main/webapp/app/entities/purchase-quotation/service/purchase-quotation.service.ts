import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPurchaseQuotation, getPurchaseQuotationIdentifier } from '../purchase-quotation.model';

export type EntityResponseType = HttpResponse<IPurchaseQuotation>;
export type EntityArrayResponseType = HttpResponse<IPurchaseQuotation[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseQuotationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/purchase-quotations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(purchaseQuotation: IPurchaseQuotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseQuotation);
    return this.http
      .post<IPurchaseQuotation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchaseQuotation: IPurchaseQuotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseQuotation);
    return this.http
      .put<IPurchaseQuotation>(`${this.resourceUrl}/${getPurchaseQuotationIdentifier(purchaseQuotation) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(purchaseQuotation: IPurchaseQuotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseQuotation);
    return this.http
      .patch<IPurchaseQuotation>(`${this.resourceUrl}/${getPurchaseQuotationIdentifier(purchaseQuotation) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchaseQuotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchaseQuotation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPurchaseQuotationToCollectionIfMissing(
    purchaseQuotationCollection: IPurchaseQuotation[],
    ...purchaseQuotationsToCheck: (IPurchaseQuotation | null | undefined)[]
  ): IPurchaseQuotation[] {
    const purchaseQuotations: IPurchaseQuotation[] = purchaseQuotationsToCheck.filter(isPresent);
    if (purchaseQuotations.length > 0) {
      const purchaseQuotationCollectionIdentifiers = purchaseQuotationCollection.map(
        purchaseQuotationItem => getPurchaseQuotationIdentifier(purchaseQuotationItem)!
      );
      const purchaseQuotationsToAdd = purchaseQuotations.filter(purchaseQuotationItem => {
        const purchaseQuotationIdentifier = getPurchaseQuotationIdentifier(purchaseQuotationItem);
        if (purchaseQuotationIdentifier == null || purchaseQuotationCollectionIdentifiers.includes(purchaseQuotationIdentifier)) {
          return false;
        }
        purchaseQuotationCollectionIdentifiers.push(purchaseQuotationIdentifier);
        return true;
      });
      return [...purchaseQuotationsToAdd, ...purchaseQuotationCollection];
    }
    return purchaseQuotationCollection;
  }

  protected convertDateFromClient(purchaseQuotation: IPurchaseQuotation): IPurchaseQuotation {
    return Object.assign({}, purchaseQuotation, {
      expectedDeliveryDate: purchaseQuotation.expectedDeliveryDate?.isValid() ? purchaseQuotation.expectedDeliveryDate.toJSON() : undefined,
      poDate: purchaseQuotation.poDate?.isValid() ? purchaseQuotation.poDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.expectedDeliveryDate = res.body.expectedDeliveryDate ? dayjs(res.body.expectedDeliveryDate) : undefined;
      res.body.poDate = res.body.poDate ? dayjs(res.body.poDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchaseQuotation: IPurchaseQuotation) => {
        purchaseQuotation.expectedDeliveryDate = purchaseQuotation.expectedDeliveryDate
          ? dayjs(purchaseQuotation.expectedDeliveryDate)
          : undefined;
        purchaseQuotation.poDate = purchaseQuotation.poDate ? dayjs(purchaseQuotation.poDate) : undefined;
      });
    }
    return res;
  }
}
