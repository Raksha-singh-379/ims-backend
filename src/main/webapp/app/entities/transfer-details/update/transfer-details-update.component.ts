import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransferDetails, TransferDetails } from '../transfer-details.model';
import { TransferDetailsService } from '../service/transfer-details.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

@Component({
  selector: 'jhi-transfer-details-update',
  templateUrl: './transfer-details-update.component.html',
})
export class TransferDetailsUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  transfersSharedCollection: ITransfer[] = [];

  editForm = this.fb.group({
    id: [],
    approvalDate: [],
    qty: [],
    comment: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
    product: [],
    transfer: [],
  });

  constructor(
    protected transferDetailsService: TransferDetailsService,
    protected productService: ProductService,
    protected transferService: TransferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferDetails }) => {
      if (transferDetails.id === undefined) {
        const today = dayjs().startOf('day');
        transferDetails.approvalDate = today;
      }

      this.updateForm(transferDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transferDetails = this.createFromForm();
    if (transferDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.transferDetailsService.update(transferDetails));
    } else {
      this.subscribeToSaveResponse(this.transferDetailsService.create(transferDetails));
    }
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackTransferById(index: number, item: ITransfer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferDetails>>): void {
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

  protected updateForm(transferDetails: ITransferDetails): void {
    this.editForm.patchValue({
      id: transferDetails.id,
      approvalDate: transferDetails.approvalDate ? transferDetails.approvalDate.format(DATE_TIME_FORMAT) : null,
      qty: transferDetails.qty,
      comment: transferDetails.comment,
      freeField1: transferDetails.freeField1,
      freeField2: transferDetails.freeField2,
      lastModified: transferDetails.lastModified,
      lastModifiedBy: transferDetails.lastModifiedBy,
      isDeleted: transferDetails.isDeleted,
      isActive: transferDetails.isActive,
      product: transferDetails.product,
      transfer: transferDetails.transfer,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      transferDetails.product
    );
    this.transfersSharedCollection = this.transferService.addTransferToCollectionIfMissing(
      this.transfersSharedCollection,
      transferDetails.transfer
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

    this.transferService
      .query()
      .pipe(map((res: HttpResponse<ITransfer[]>) => res.body ?? []))
      .pipe(
        map((transfers: ITransfer[]) =>
          this.transferService.addTransferToCollectionIfMissing(transfers, this.editForm.get('transfer')!.value)
        )
      )
      .subscribe((transfers: ITransfer[]) => (this.transfersSharedCollection = transfers));
  }

  protected createFromForm(): ITransferDetails {
    return {
      ...new TransferDetails(),
      id: this.editForm.get(['id'])!.value,
      approvalDate: this.editForm.get(['approvalDate'])!.value
        ? dayjs(this.editForm.get(['approvalDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qty: this.editForm.get(['qty'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      product: this.editForm.get(['product'])!.value,
      transfer: this.editForm.get(['transfer'])!.value,
    };
  }
}
