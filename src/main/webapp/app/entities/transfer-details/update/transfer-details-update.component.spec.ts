import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferDetailsService } from '../service/transfer-details.service';
import { ITransferDetails, TransferDetails } from '../transfer-details.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

import { TransferDetailsUpdateComponent } from './transfer-details-update.component';

describe('TransferDetails Management Update Component', () => {
  let comp: TransferDetailsUpdateComponent;
  let fixture: ComponentFixture<TransferDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferDetailsService: TransferDetailsService;
  let productService: ProductService;
  let transferService: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferDetailsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TransferDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferDetailsService = TestBed.inject(TransferDetailsService);
    productService = TestBed.inject(ProductService);
    transferService = TestBed.inject(TransferService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const transferDetails: ITransferDetails = { id: 456 };
      const product: IProduct = { id: 36391 };
      transferDetails.product = product;

      const productCollection: IProduct[] = [{ id: 37257 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transfer query and add missing value', () => {
      const transferDetails: ITransferDetails = { id: 456 };
      const transfer: ITransfer = { id: 71286 };
      transferDetails.transfer = transfer;

      const transferCollection: ITransfer[] = [{ id: 44318 }];
      jest.spyOn(transferService, 'query').mockReturnValue(of(new HttpResponse({ body: transferCollection })));
      const additionalTransfers = [transfer];
      const expectedCollection: ITransfer[] = [...additionalTransfers, ...transferCollection];
      jest.spyOn(transferService, 'addTransferToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      expect(transferService.query).toHaveBeenCalled();
      expect(transferService.addTransferToCollectionIfMissing).toHaveBeenCalledWith(transferCollection, ...additionalTransfers);
      expect(comp.transfersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transferDetails: ITransferDetails = { id: 456 };
      const product: IProduct = { id: 10362 };
      transferDetails.product = product;
      const transfer: ITransfer = { id: 28297 };
      transferDetails.transfer = transfer;

      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transferDetails));
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.transfersSharedCollection).toContain(transfer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetails>>();
      const transferDetails = { id: 123 };
      jest.spyOn(transferDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferDetailsService.update).toHaveBeenCalledWith(transferDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetails>>();
      const transferDetails = new TransferDetails();
      jest.spyOn(transferDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferDetails }));
      saveSubject.complete();

      // THEN
      expect(transferDetailsService.create).toHaveBeenCalledWith(transferDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetails>>();
      const transferDetails = { id: 123 };
      jest.spyOn(transferDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferDetailsService.update).toHaveBeenCalledWith(transferDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTransferById', () => {
      it('Should return tracked Transfer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransferById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
