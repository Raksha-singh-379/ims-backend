import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IPurchaseQuotationDetails } from 'app/entities/purchase-quotation-details/purchase-quotation-details.model';
import { PurchaseQuotationDetailsService } from 'app/entities/purchase-quotation-details/service/purchase-quotation-details.service';
import { ProductType } from 'app/entities/enumerations/product-type.model';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  productTypeValues = Object.keys(ProductType);

  categoriesSharedCollection: ICategories[] = [];
  unitsSharedCollection: IUnit[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];
  purchaseQuotationDetailsSharedCollection: IPurchaseQuotationDetails[] = [];

  editForm = this.fb.group({
    id: [],
    shortName: [],
    chemicalFormula: [],
    hsnNo: [],
    materialImage: [],
    materialImageContentType: [],
    isDeleted: [],
    isActive: [],
    productName: [],
    alertUnits: [],
    casNumber: [],
    catlogNumber: [],
    molecularWt: [],
    molecularFormula: [],
    chemicalName: [],
    structureImg: [],
    description: [],
    qrCode: [],
    barCode: [],
    gstPercentage: [],
    productType: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    categories: [],
    unit: [],
    securityUser: [],
    purchaseQuotationDetails: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected productService: ProductService,
    protected categoriesService: CategoriesService,
    protected unitService: UnitService,
    protected securityUserService: SecurityUserService,
    protected purchaseQuotationDetailsService: PurchaseQuotationDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('inventoryManagementApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  trackCategoriesById(index: number, item: ICategories): number {
    return item.id!;
  }

  trackUnitById(index: number, item: IUnit): number {
    return item.id!;
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackPurchaseQuotationDetailsById(index: number, item: IPurchaseQuotationDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      shortName: product.shortName,
      chemicalFormula: product.chemicalFormula,
      hsnNo: product.hsnNo,
      materialImage: product.materialImage,
      materialImageContentType: product.materialImageContentType,
      isDeleted: product.isDeleted,
      isActive: product.isActive,
      productName: product.productName,
      alertUnits: product.alertUnits,
      casNumber: product.casNumber,
      catlogNumber: product.catlogNumber,
      molecularWt: product.molecularWt,
      molecularFormula: product.molecularFormula,
      chemicalName: product.chemicalName,
      structureImg: product.structureImg,
      description: product.description,
      qrCode: product.qrCode,
      barCode: product.barCode,
      gstPercentage: product.gstPercentage,
      productType: product.productType,
      lastModified: product.lastModified,
      lastModifiedBy: product.lastModifiedBy,
      freeField1: product.freeField1,
      freeField2: product.freeField2,
      categories: product.categories,
      unit: product.unit,
      securityUser: product.securityUser,
      purchaseQuotationDetails: product.purchaseQuotationDetails,
    });

    this.categoriesSharedCollection = this.categoriesService.addCategoriesToCollectionIfMissing(
      this.categoriesSharedCollection,
      product.categories
    );
    this.unitsSharedCollection = this.unitService.addUnitToCollectionIfMissing(this.unitsSharedCollection, product.unit);
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      product.securityUser
    );
    this.purchaseQuotationDetailsSharedCollection = this.purchaseQuotationDetailsService.addPurchaseQuotationDetailsToCollectionIfMissing(
      this.purchaseQuotationDetailsSharedCollection,
      product.purchaseQuotationDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriesService
      .query()
      .pipe(map((res: HttpResponse<ICategories[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategories[]) =>
          this.categoriesService.addCategoriesToCollectionIfMissing(categories, this.editForm.get('categories')!.value)
        )
      )
      .subscribe((categories: ICategories[]) => (this.categoriesSharedCollection = categories));

    this.unitService
      .query()
      .pipe(map((res: HttpResponse<IUnit[]>) => res.body ?? []))
      .pipe(map((units: IUnit[]) => this.unitService.addUnitToCollectionIfMissing(units, this.editForm.get('unit')!.value)))
      .subscribe((units: IUnit[]) => (this.unitsSharedCollection = units));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.purchaseQuotationDetailsService
      .query()
      .pipe(map((res: HttpResponse<IPurchaseQuotationDetails[]>) => res.body ?? []))
      .pipe(
        map((purchaseQuotationDetails: IPurchaseQuotationDetails[]) =>
          this.purchaseQuotationDetailsService.addPurchaseQuotationDetailsToCollectionIfMissing(
            purchaseQuotationDetails,
            this.editForm.get('purchaseQuotationDetails')!.value
          )
        )
      )
      .subscribe(
        (purchaseQuotationDetails: IPurchaseQuotationDetails[]) =>
          (this.purchaseQuotationDetailsSharedCollection = purchaseQuotationDetails)
      );
  }

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      chemicalFormula: this.editForm.get(['chemicalFormula'])!.value,
      hsnNo: this.editForm.get(['hsnNo'])!.value,
      materialImageContentType: this.editForm.get(['materialImageContentType'])!.value,
      materialImage: this.editForm.get(['materialImage'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      alertUnits: this.editForm.get(['alertUnits'])!.value,
      casNumber: this.editForm.get(['casNumber'])!.value,
      catlogNumber: this.editForm.get(['catlogNumber'])!.value,
      molecularWt: this.editForm.get(['molecularWt'])!.value,
      molecularFormula: this.editForm.get(['molecularFormula'])!.value,
      chemicalName: this.editForm.get(['chemicalName'])!.value,
      structureImg: this.editForm.get(['structureImg'])!.value,
      description: this.editForm.get(['description'])!.value,
      qrCode: this.editForm.get(['qrCode'])!.value,
      barCode: this.editForm.get(['barCode'])!.value,
      gstPercentage: this.editForm.get(['gstPercentage'])!.value,
      productType: this.editForm.get(['productType'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      purchaseQuotationDetails: this.editForm.get(['purchaseQuotationDetails'])!.value,
    };
  }
}
