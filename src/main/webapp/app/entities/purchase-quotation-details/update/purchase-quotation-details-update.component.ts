import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPurchaseQuotationDetails, PurchaseQuotationDetails } from '../purchase-quotation-details.model';
import { PurchaseQuotationDetailsService } from '../service/purchase-quotation-details.service';
import { IPurchaseQuotation } from 'app/entities/purchase-quotation/purchase-quotation.model';
import { PurchaseQuotationService } from 'app/entities/purchase-quotation/service/purchase-quotation.service';

@Component({
  selector: 'jhi-purchase-quotation-details-update',
  templateUrl: './purchase-quotation-details-update.component.html',
})
export class PurchaseQuotationDetailsUpdateComponent implements OnInit {
  isSaving = false;

  purchaseQuotationsSharedCollection: IPurchaseQuotation[] = [];

  editForm = this.fb.group({
    id: [],
    qtyordered: [],
    gstTaxPercentage: [],
    pricePerUnit: [],
    totalPrice: [],
    discount: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    purchaseQuotation: [],
  });

  constructor(
    protected purchaseQuotationDetailsService: PurchaseQuotationDetailsService,
    protected purchaseQuotationService: PurchaseQuotationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseQuotationDetails }) => {
      this.updateForm(purchaseQuotationDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseQuotationDetails = this.createFromForm();
    if (purchaseQuotationDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseQuotationDetailsService.update(purchaseQuotationDetails));
    } else {
      this.subscribeToSaveResponse(this.purchaseQuotationDetailsService.create(purchaseQuotationDetails));
    }
  }

  trackPurchaseQuotationById(index: number, item: IPurchaseQuotation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseQuotationDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(purchaseQuotationDetails: IPurchaseQuotationDetails): void {
    this.editForm.patchValue({
      id: purchaseQuotationDetails.id,
      qtyordered: purchaseQuotationDetails.qtyordered,
      gstTaxPercentage: purchaseQuotationDetails.gstTaxPercentage,
      pricePerUnit: purchaseQuotationDetails.pricePerUnit,
      totalPrice: purchaseQuotationDetails.totalPrice,
      discount: purchaseQuotationDetails.discount,
      lastModified: purchaseQuotationDetails.lastModified,
      lastModifiedBy: purchaseQuotationDetails.lastModifiedBy,
      freeField1: purchaseQuotationDetails.freeField1,
      freeField2: purchaseQuotationDetails.freeField2,
      purchaseQuotation: purchaseQuotationDetails.purchaseQuotation,
    });

    this.purchaseQuotationsSharedCollection = this.purchaseQuotationService.addPurchaseQuotationToCollectionIfMissing(
      this.purchaseQuotationsSharedCollection,
      purchaseQuotationDetails.purchaseQuotation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.purchaseQuotationService
      .query()
      .pipe(map((res: HttpResponse<IPurchaseQuotation[]>) => res.body ?? []))
      .pipe(
        map((purchaseQuotations: IPurchaseQuotation[]) =>
          this.purchaseQuotationService.addPurchaseQuotationToCollectionIfMissing(
            purchaseQuotations,
            this.editForm.get('purchaseQuotation')!.value
          )
        )
      )
      .subscribe((purchaseQuotations: IPurchaseQuotation[]) => (this.purchaseQuotationsSharedCollection = purchaseQuotations));
  }

  protected createFromForm(): IPurchaseQuotationDetails {
    return {
      ...new PurchaseQuotationDetails(),
      id: this.editForm.get(['id'])!.value,
      qtyordered: this.editForm.get(['qtyordered'])!.value,
      gstTaxPercentage: this.editForm.get(['gstTaxPercentage'])!.value,
      pricePerUnit: this.editForm.get(['pricePerUnit'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      purchaseQuotation: this.editForm.get(['purchaseQuotation'])!.value,
    };
  }
}
