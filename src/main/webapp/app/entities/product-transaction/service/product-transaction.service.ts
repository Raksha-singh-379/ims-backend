import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductTransaction, getProductTransactionIdentifier } from '../product-transaction.model';

export type EntityResponseType = HttpResponse<IProductTransaction>;
export type EntityArrayResponseType = HttpResponse<IProductTransaction[]>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-transactions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productTransaction: IProductTransaction): Observable<EntityResponseType> {
    return this.http.post<IProductTransaction>(this.resourceUrl, productTransaction, { observe: 'response' });
  }

  update(productTransaction: IProductTransaction): Observable<EntityResponseType> {
    return this.http.put<IProductTransaction>(
      `${this.resourceUrl}/${getProductTransactionIdentifier(productTransaction) as number}`,
      productTransaction,
      { observe: 'response' }
    );
  }

  partialUpdate(productTransaction: IProductTransaction): Observable<EntityResponseType> {
    return this.http.patch<IProductTransaction>(
      `${this.resourceUrl}/${getProductTransactionIdentifier(productTransaction) as number}`,
      productTransaction,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductTransactionToCollectionIfMissing(
    productTransactionCollection: IProductTransaction[],
    ...productTransactionsToCheck: (IProductTransaction | null | undefined)[]
  ): IProductTransaction[] {
    const productTransactions: IProductTransaction[] = productTransactionsToCheck.filter(isPresent);
    if (productTransactions.length > 0) {
      const productTransactionCollectionIdentifiers = productTransactionCollection.map(
        productTransactionItem => getProductTransactionIdentifier(productTransactionItem)!
      );
      const productTransactionsToAdd = productTransactions.filter(productTransactionItem => {
        const productTransactionIdentifier = getProductTransactionIdentifier(productTransactionItem);
        if (productTransactionIdentifier == null || productTransactionCollectionIdentifiers.includes(productTransactionIdentifier)) {
          return false;
        }
        productTransactionCollectionIdentifiers.push(productTransactionIdentifier);
        return true;
      });
      return [...productTransactionsToAdd, ...productTransactionCollection];
    }
    return productTransactionCollection;
  }
}
