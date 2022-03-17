import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGoodsRecived, getGoodsRecivedIdentifier } from '../goods-recived.model';

export type EntityResponseType = HttpResponse<IGoodsRecived>;
export type EntityArrayResponseType = HttpResponse<IGoodsRecived[]>;

@Injectable({ providedIn: 'root' })
export class GoodsRecivedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/goods-reciveds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(goodsRecived: IGoodsRecived): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodsRecived);
    return this.http
      .post<IGoodsRecived>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(goodsRecived: IGoodsRecived): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodsRecived);
    return this.http
      .put<IGoodsRecived>(`${this.resourceUrl}/${getGoodsRecivedIdentifier(goodsRecived) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(goodsRecived: IGoodsRecived): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodsRecived);
    return this.http
      .patch<IGoodsRecived>(`${this.resourceUrl}/${getGoodsRecivedIdentifier(goodsRecived) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGoodsRecived>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGoodsRecived[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGoodsRecivedToCollectionIfMissing(
    goodsRecivedCollection: IGoodsRecived[],
    ...goodsRecivedsToCheck: (IGoodsRecived | null | undefined)[]
  ): IGoodsRecived[] {
    const goodsReciveds: IGoodsRecived[] = goodsRecivedsToCheck.filter(isPresent);
    if (goodsReciveds.length > 0) {
      const goodsRecivedCollectionIdentifiers = goodsRecivedCollection.map(
        goodsRecivedItem => getGoodsRecivedIdentifier(goodsRecivedItem)!
      );
      const goodsRecivedsToAdd = goodsReciveds.filter(goodsRecivedItem => {
        const goodsRecivedIdentifier = getGoodsRecivedIdentifier(goodsRecivedItem);
        if (goodsRecivedIdentifier == null || goodsRecivedCollectionIdentifiers.includes(goodsRecivedIdentifier)) {
          return false;
        }
        goodsRecivedCollectionIdentifiers.push(goodsRecivedIdentifier);
        return true;
      });
      return [...goodsRecivedsToAdd, ...goodsRecivedCollection];
    }
    return goodsRecivedCollection;
  }

  protected convertDateFromClient(goodsRecived: IGoodsRecived): IGoodsRecived {
    return Object.assign({}, goodsRecived, {
      grDate: goodsRecived.grDate?.isValid() ? goodsRecived.grDate.toJSON() : undefined,
      manufacturingDate: goodsRecived.manufacturingDate?.isValid() ? goodsRecived.manufacturingDate.toJSON() : undefined,
      expiryDate: goodsRecived.expiryDate?.isValid() ? goodsRecived.expiryDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.grDate = res.body.grDate ? dayjs(res.body.grDate) : undefined;
      res.body.manufacturingDate = res.body.manufacturingDate ? dayjs(res.body.manufacturingDate) : undefined;
      res.body.expiryDate = res.body.expiryDate ? dayjs(res.body.expiryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((goodsRecived: IGoodsRecived) => {
        goodsRecived.grDate = goodsRecived.grDate ? dayjs(goodsRecived.grDate) : undefined;
        goodsRecived.manufacturingDate = goodsRecived.manufacturingDate ? dayjs(goodsRecived.manufacturingDate) : undefined;
        goodsRecived.expiryDate = goodsRecived.expiryDate ? dayjs(goodsRecived.expiryDate) : undefined;
      });
    }
    return res;
  }
}
