import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGoodsRecived, GoodsRecived } from '../goods-recived.model';
import { GoodsRecivedService } from '../service/goods-recived.service';

@Injectable({ providedIn: 'root' })
export class GoodsRecivedRoutingResolveService implements Resolve<IGoodsRecived> {
  constructor(protected service: GoodsRecivedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoodsRecived> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((goodsRecived: HttpResponse<GoodsRecived>) => {
          if (goodsRecived.body) {
            return of(goodsRecived.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoodsRecived());
  }
}
