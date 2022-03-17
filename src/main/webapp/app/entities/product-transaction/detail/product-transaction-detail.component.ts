import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductTransaction } from '../product-transaction.model';

@Component({
  selector: 'jhi-product-transaction-detail',
  templateUrl: './product-transaction-detail.component.html',
})
export class ProductTransactionDetailComponent implements OnInit {
  productTransaction: IProductTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransaction }) => {
      this.productTransaction = productTransaction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
