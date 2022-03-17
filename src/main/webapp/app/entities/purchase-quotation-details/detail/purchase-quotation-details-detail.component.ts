import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseQuotationDetails } from '../purchase-quotation-details.model';

@Component({
  selector: 'jhi-purchase-quotation-details-detail',
  templateUrl: './purchase-quotation-details-detail.component.html',
})
export class PurchaseQuotationDetailsDetailComponent implements OnInit {
  purchaseQuotationDetails: IPurchaseQuotationDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseQuotationDetails }) => {
      this.purchaseQuotationDetails = purchaseQuotationDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
