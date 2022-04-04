import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPurchaseQuotationDetails, PurchaseQuotationDetails } from '../purchase-quotation-details.model';
import { PurchaseQuotationDetailsService } from '../service/purchase-quotation-details.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IPurchaseQuotation } from 'app/entities/purchase-quotation/purchase-quotation.model';
import { PurchaseQuotationService } from 'app/entities/purchase-quotation/service/purchase-quotation.service';

@Component({
  selector: 'jhi-purchase-quotation-details-update',
  templateUrl: './purchase-quotation-details-update.component.html',
})
export class PurchaseQuotationDetailsUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
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
    product: [],
    purchaseQuotation: [],
  });

  constructor(
    protected purchaseQuotationDetailsService: PurchaseQuotationDetailsService,
    protected productService: ProductService,
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

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
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
      product: purchaseQuotationDetails.product,
      purchaseQuotation: purchaseQuotationDetails.purchaseQuotation,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      purchaseQuotationDetails.product
    );
    this.purchaseQuotationsSharedCollection = this.purchaseQuotationService.addPurchaseQuotationToCollectionIfMissing(
      this.purchaseQuotationsSharedCollection,
      purchaseQuotationDetails.purchaseQuotation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('product')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

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
      product: this.editForm.get(['product'])!.value,
      purchaseQuotation: this.editForm.get(['purchaseQuotation'])!.value,
    };
  }
}
