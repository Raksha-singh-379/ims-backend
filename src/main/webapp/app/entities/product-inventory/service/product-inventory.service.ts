import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductInventory, getProductInventoryIdentifier } from '../product-inventory.model';

export type EntityResponseType = HttpResponse<IProductInventory>;
export type EntityArrayResponseType = HttpResponse<IProductInventory[]>;

@Injectable({ providedIn: 'root' })
export class ProductInventoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-inventories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productInventory: IProductInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productInventory);
    return this.http
      .post<IProductInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productInventory: IProductInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productInventory);
    return this.http
      .put<IProductInventory>(`${this.resourceUrl}/${getProductInventoryIdentifier(productInventory) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(productInventory: IProductInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productInventory);
    return this.http
      .patch<IProductInventory>(`${this.resourceUrl}/${getProductInventoryIdentifier(productInventory) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductInventoryToCollectionIfMissing(
    productInventoryCollection: IProductInventory[],
    ...productInventoriesToCheck: (IProductInventory | null | undefined)[]
  ): IProductInventory[] {
    const productInventories: IProductInventory[] = productInventoriesToCheck.filter(isPresent);
    if (productInventories.length > 0) {
      const productInventoryCollectionIdentifiers = productInventoryCollection.map(
        productInventoryItem => getProductInventoryIdentifier(productInventoryItem)!
      );
      const productInventoriesToAdd = productInventories.filter(productInventoryItem => {
        const productInventoryIdentifier = getProductInventoryIdentifier(productInventoryItem);
        if (productInventoryIdentifier == null || productInventoryCollectionIdentifiers.includes(productInventoryIdentifier)) {
          return false;
        }
        productInventoryCollectionIdentifiers.push(productInventoryIdentifier);
        return true;
      });
      return [...productInventoriesToAdd, ...productInventoryCollection];
    }
    return productInventoryCollection;
  }

  protected convertDateFromClient(productInventory: IProductInventory): IProductInventory {
    return Object.assign({}, productInventory, {
      inwardOutwardDate: productInventory.inwardOutwardDate?.isValid() ? productInventory.inwardOutwardDate.toJSON() : undefined,
      expiryDate: productInventory.expiryDate?.isValid() ? productInventory.expiryDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inwardOutwardDate = res.body.inwardOutwardDate ? dayjs(res.body.inwardOutwardDate) : undefined;
      res.body.expiryDate = res.body.expiryDate ? dayjs(res.body.expiryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productInventory: IProductInventory) => {
        productInventory.inwardOutwardDate = productInventory.inwardOutwardDate ? dayjs(productInventory.inwardOutwardDate) : undefined;
        productInventory.expiryDate = productInventory.expiryDate ? dayjs(productInventory.expiryDate) : undefined;
      });
    }
    return res;
  }
}
