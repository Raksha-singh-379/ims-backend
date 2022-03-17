import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseQuotation } from '../purchase-quotation.model';

@Component({
  selector: 'jhi-purchase-quotation-detail',
  templateUrl: './purchase-quotation-detail.component.html',
})
export class PurchaseQuotationDetailComponent implements OnInit {
  purchaseQuotation: IPurchaseQuotation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseQuotation }) => {
      this.purchaseQuotation = purchaseQuotation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
