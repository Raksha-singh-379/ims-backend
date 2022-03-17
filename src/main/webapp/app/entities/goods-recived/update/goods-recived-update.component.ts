import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGoodsRecived, GoodsRecived } from '../goods-recived.model';
import { GoodsRecivedService } from '../service/goods-recived.service';
import { IPurchaseQuotation } from 'app/entities/purchase-quotation/purchase-quotation.model';
import { PurchaseQuotationService } from 'app/entities/purchase-quotation/service/purchase-quotation.service';

@Component({
  selector: 'jhi-goods-recived-update',
  templateUrl: './goods-recived-update.component.html',
})
export class GoodsRecivedUpdateComponent implements OnInit {
  isSaving = false;

  purchaseQuotationsSharedCollection: IPurchaseQuotation[] = [];

  editForm = this.fb.group({
    id: [],
    grDate: [],
    qtyOrdered: [],
    qtyRecieved: [],
    manufacturingDate: [],
    expiryDate: [],
    lotNo: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    purchaseQuotation: [],
  });

  constructor(
    protected goodsRecivedService: GoodsRecivedService,
    protected purchaseQuotationService: PurchaseQuotationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodsRecived }) => {
      if (goodsRecived.id === undefined) {
        const today = dayjs().startOf('day');
        goodsRecived.grDate = today;
        goodsRecived.manufacturingDate = today;
        goodsRecived.expiryDate = today;
      }

      this.updateForm(goodsRecived);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goodsRecived = this.createFromForm();
    if (goodsRecived.id !== undefined) {
      this.subscribeToSaveResponse(this.goodsRecivedService.update(goodsRecived));
    } else {
      this.subscribeToSaveResponse(this.goodsRecivedService.create(goodsRecived));
    }
  }

  trackPurchaseQuotationById(index: number, item: IPurchaseQuotation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoodsRecived>>): void {
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

  protected updateForm(goodsRecived: IGoodsRecived): void {
    this.editForm.patchValue({
      id: goodsRecived.id,
      grDate: goodsRecived.grDate ? goodsRecived.grDate.format(DATE_TIME_FORMAT) : null,
      qtyOrdered: goodsRecived.qtyOrdered,
      qtyRecieved: goodsRecived.qtyRecieved,
      manufacturingDate: goodsRecived.manufacturingDate ? goodsRecived.manufacturingDate.format(DATE_TIME_FORMAT) : null,
      expiryDate: goodsRecived.expiryDate ? goodsRecived.expiryDate.format(DATE_TIME_FORMAT) : null,
      lotNo: goodsRecived.lotNo,
      freeField1: goodsRecived.freeField1,
      freeField2: goodsRecived.freeField2,
      freeField3: goodsRecived.freeField3,
      purchaseQuotation: goodsRecived.purchaseQuotation,
    });

    this.purchaseQuotationsSharedCollection = this.purchaseQuotationService.addPurchaseQuotationToCollectionIfMissing(
      this.purchaseQuotationsSharedCollection,
      goodsRecived.purchaseQuotation
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

  protected createFromForm(): IGoodsRecived {
    return {
      ...new GoodsRecived(),
      id: this.editForm.get(['id'])!.value,
      grDate: this.editForm.get(['grDate'])!.value ? dayjs(this.editForm.get(['grDate'])!.value, DATE_TIME_FORMAT) : undefined,
      qtyOrdered: this.editForm.get(['qtyOrdered'])!.value,
      qtyRecieved: this.editForm.get(['qtyRecieved'])!.value,
      manufacturingDate: this.editForm.get(['manufacturingDate'])!.value
        ? dayjs(this.editForm.get(['manufacturingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      expiryDate: this.editForm.get(['expiryDate'])!.value ? dayjs(this.editForm.get(['expiryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lotNo: this.editForm.get(['lotNo'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      purchaseQuotation: this.editForm.get(['purchaseQuotation'])!.value,
    };
  }
}
