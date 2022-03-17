import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPurchaseQuotation, PurchaseQuotation } from '../purchase-quotation.model';
import { PurchaseQuotationService } from '../service/purchase-quotation.service';

@Injectable({ providedIn: 'root' })
export class PurchaseQuotationRoutingResolveService implements Resolve<IPurchaseQuotation> {
  constructor(protected service: PurchaseQuotationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurchaseQuotation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((purchaseQuotation: HttpResponse<PurchaseQuotation>) => {
          if (purchaseQuotation.body) {
            return of(purchaseQuotation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PurchaseQuotation());
  }
}
